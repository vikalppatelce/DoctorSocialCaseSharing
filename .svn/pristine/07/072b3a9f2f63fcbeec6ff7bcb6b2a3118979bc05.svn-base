package com.netdoers.com.receiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.util.SparseArray;

public abstract class WakefulBroadcastReceiver extends BroadcastReceiver
{
  private static final String EXTRA_WAKE_LOCK_ID = "android.support.content.wakelockid";
  private static final SparseArray<PowerManager.WakeLock> mActiveWakeLocks = new SparseArray();

  private static int mNextId = 1;

  public static ComponentName startWakefulService(Context context, Intent intent)
  {
    synchronized (mActiveWakeLocks) {
      int id = mNextId;
      mNextId += 1;
      if (mNextId <= 0) {
        mNextId = 1;
      }

      intent.putExtra("android.support.content.wakelockid", id);
      ComponentName comp = context.startService(intent);
      if (comp == null) {
        return null;
      }

      PowerManager pm = (PowerManager)context.getSystemService("power");
      PowerManager.WakeLock wl = pm.newWakeLock(1, "wake:" + comp.flattenToShortString());

      wl.setReferenceCounted(false);
      wl.acquire(60000L);
      mActiveWakeLocks.put(id, wl);
      return comp;
    }
  }

  public static boolean completeWakefulIntent(Intent intent)
  {
    int id = intent.getIntExtra("android.support.content.wakelockid", 0);
    if (id == 0) {
      return false;
    }
    synchronized (mActiveWakeLocks) {
      PowerManager.WakeLock wl = mActiveWakeLocks.get(id);
      if (wl != null) {
        wl.release();
        mActiveWakeLocks.remove(id);
        return true;
      }

      Log.w("WakefulBroadcastReceiver", "No active wake lock id #" + id);
      return true;
    }
  }
}