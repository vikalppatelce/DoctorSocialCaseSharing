/* HISTORY
 * CATEGORY			 :- ACTIVITY
 * DEVELOPER		 :- VIKALP PATEL
 * AIM     			 :- ADD IPD ACTIVITY
 * DESCRIPTION		 :- SAVE IPD
 * 
 * S - START E- END  C- COMMENTED  U -EDITED A -ADDED
 * --------------------------------------------------------------------------------------------------------------------
 * INDEX       DEVELOPER		DATE			FUNCTION 			DESCRIPTION
 * --------------------------------------------------------------------------------------------------------------------
 * 10001       VIKALP PATEL    10/01/2014       					APPLYING FULLSCREEN THROUGH PREFERENCES.
 * --------------------------------------------------------------------------------------------------------------------
 */
package com.netdoers.com.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore.MediaColumns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.netdoers.com.CustomToast;
import com.netdoers.com.SmartConsultant;
import com.smarthumanoid.com.R;

public class PicSaveActivity extends Activity{

	ImageView imgView;
	Uri absPathUri;
	SharedPreferences pref; //ADDED 10001
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
	super.onCreate(savedInstanceState);
	//SA 10001
			pref = PreferenceManager.getDefaultSharedPreferences(SmartConsultant.getApplication());
			if(pref.getBoolean("prefIsFullScreen", false))
			{
				//setTheme(R.style.FullScreen);
				requestWindowFeature(Window.FEATURE_NO_TITLE);
		        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
			}
			//EA 10001
	setContentView(R.layout.pic_screen);
	imgView = (ImageView)findViewById(R.id.picView);
	Bundle b= getIntent().getExtras();
	absPathUri = Uri.parse(b.getString("URI"));
	//Toast.makeText(getApplicationContext(), ""+absPathUri, Toast.LENGTH_SHORT).show();
	//galleryAddPic();
	
	if(absPathUri!=null)
	{
		Bitmap myImg = BitmapFactory.decodeFile(absPathUri.getPath());
		Matrix matrix = new Matrix();
		matrix.postRotate(90);
		Bitmap rotated = Bitmap.createBitmap(myImg, 0, 0, myImg.getWidth(), myImg.getHeight(),
		        matrix, true);
		imgView.setImageBitmap(rotated);
		}
	}
	
	private void galleryAddPic() {
	    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
	    File f = new File(absPathUri.getPath());
	    Uri contentUri = Uri.fromFile(f);
	    mediaScanIntent.setData(contentUri);
	    this.sendBroadcast(mediaScanIntent);
	}
	
	private Bitmap getScaledBitmap(Uri uri){
        Bitmap thumb = null ;
        try {
            ContentResolver cr = getContentResolver();
            InputStream in = cr.openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize=8;
            thumb = BitmapFactory.decodeStream(in,null,options);
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext() , "File not found" , Toast.LENGTH_SHORT).show();
        }
        return thumb ; 
    }
	
	
	public String getPath(Uri photoUri) {

        String filePath = "";
        if (photoUri != null) {
            String[] filePathColumn = { MediaColumns.DATA };
            try
            {
            
            	Cursor cursor = getContentResolver().query(photoUri,
                        filePathColumn, null, null, null);
                try
                {
                	cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    filePath = cursor.getString(columnIndex);	
                }
                catch(Exception e)
                {
                                   	
                }
                cursor.close();
            }catch(Exception e)
            {
            Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_SHORT).show();	
            }
        }
        return filePath;
    }
	
	public void onSave(View v)
	{
		setResult(RESULT_OK);
		finish();
	}
	
	public void onDiscard(View v)
	{
		setResult(RESULT_CANCELED);
		File file = new File(absPathUri.getPath());
		boolean deleted = file.delete();
		if(deleted)
			CustomToast.showToastMessage(getApplicationContext(), "Discarded!");
		else
		{
			CustomToast.showToastMessage(getApplicationContext(), "Error while discard!");
		}
		finish();
	}
}