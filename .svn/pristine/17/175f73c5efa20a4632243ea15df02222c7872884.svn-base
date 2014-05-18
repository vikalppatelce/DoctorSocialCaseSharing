/* HISTORY
 * CATEGORY 		:- ACTIVITY
 * DEVELOPER		:- VIKALP PATEL
 * AIM			    :- ADD IPD ACTIVITY
 * DESCRIPTION 		:- SAVE IPD
 * 
 * S - START E- END  C- COMMENTED  U -EDITED A -ADDED
 * --------------------------------------------------------------------------------------------------------------------
 * INDEX       DEVELOPER		DATE			FUNCTION		DESCRIPTION
 * --------------------------------------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------------------------------------
 */
package com.netdoers.com.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.smarthumanoid.com.R;
import com.netdoers.com.CustomToast;
import com.netdoers.com.SmartConsultant;
import com.netdoers.com.service.UploadData;

public class UploadStatusActivity extends Activity{

	TextView txtText;
	ProgressBar progressBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.syncing_screen);
		txtText = (TextView) findViewById(R.id.upload_status);
		
		progressBar = (ProgressBar) findViewById(R.id.progress);
		Intent i =  new Intent(this, UploadData.class);
		startService(i);
		
//		Toast.makeText(this, "Syncing started", Toast.LENGTH_LONG).show();
		
		CustomToast.showToastMessage(SmartConsultant.getApplication().getApplicationContext(), "Syncing started");
	}
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	updateUI(intent);       
        }
    };
    
    private void updateUI(Intent intent) {
    	String counter = intent.getStringExtra("text"); 
    	
    	if(counter.equals("Upload finish."))
    	{
    		progressBar.setVisibility(View.INVISIBLE);
    	}
// SA B0S01    	
    	else if(counter.equals("Connection lost. Please try again"))
    	{
    		progressBar.setVisibility(View.INVISIBLE);
    	}
// EA B0S01   	
    	else if(progressBar.getVisibility() == View.INVISIBLE)
    	{
    		progressBar.setVisibility(View.VISIBLE);
    	}
    	txtText.setText(counter);
    }
    
    @Override
	public void onResume() {
		super.onResume();		
		registerReceiver(broadcastReceiver, new IntentFilter(UploadData.BROADCAST_ACTION));
	}
 
	@Override
	public void onPause() {
		super.onPause();
		unregisterReceiver(broadcastReceiver);
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
     
    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
         
        switch (item.getItemId())
        {
        case R.id.mnuHelp:
        	Intent i = new Intent(this, HelpActivity.class);
        	startActivity(i);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }   
}
