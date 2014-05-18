/*HISTORY
* CATEGORY			 :- CAMERA
* DEVELOPER			 :- VIKALP PATEL
* AIM 				 :- CAPTURE IMAGE
* DESCRIPTION 		 :- TAKING PICTURE WITH THE ACTIVITY
* 
* S - START E- END C- COMMENTED U -EDITED A -ADDED
* --------------------------------------------------------------------------------------------------------------------
* INDEX 		DEVELOPER 			DATE 			FUNCTION			DESCRIPTION
* --------------------------------------------------------------------------------------------------------------------
* 10001 	  VIKALP PATEL 		 10/01/2014 							ADD FULLSCREEN THEME TO APPLICATION THROUGH PREFERENCES
* P3A01       VIKALP PATEL       10/05/2014         PHASEIII            ADDED IMAGE PAGER FOR SERVICES
* --------------------------------------------------------------------------------------------------------------------
*/


package com.netdoers.com.ui;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.netdoers.com.SmartConsultant;
import com.netdoers.com.utils.GalleryMedia;
import com.smarthumanoid.com.R;


public class DetailedImage extends SherlockFragmentActivity{

	ImageView imageView;
	Uri uri;
	SharedPreferences pref;//ADDED 10001
	//SA P3A01
	public ViewPager mPager;
	public int NUM_PAGES = 0;
	public ArrayList<String> picPaths;
	public TextView serviceName,serviceLocation,pageTotal,pageCurrent;
	String strServiceName,strServiceLocation,strCurrPage;
	public int currentPage = 0;
	//EA P3A01
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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
		setContentView(R.layout.detail_image);
		String url = getIntent().getStringExtra("url");
		imageView = (ImageView) findViewById(R.id.detailedImage);
		Bitmap myImg = BitmapFactory.decodeFile(url);
		Matrix matrix = new Matrix();
		matrix.postRotate(0);
		Bitmap rotated = Bitmap.createBitmap(myImg, 0, 0, myImg.getWidth(), myImg.getHeight(), matrix, true);
		imageView.setImageBitmap(rotated);
		
//		SA P3A01
//		ActionBar bar = getSupportActionBar();
////		bar.setTitle(getResources().getString(R.string.pick_contacts));
//		View actionBarView = getLayoutInflater().inflate(R.layout.custom_action_bar_detail_image, null);
//		
//		bar.setCustomView(actionBarView);
//		bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//		
//		bar.setHomeButtonEnabled(false);
//		bar.setDisplayShowHomeEnabled(false);
//		bar.setDisplayHomeAsUpEnabled(false);
//		
//		mPager = (ViewPager)findViewById(R.id.viewPager);
//		serviceName = (TextView)actionBarView.findViewById(R.id.serviceName);
//		serviceLocation = (TextView)actionBarView.findViewById(R.id.serviceLocation);
//		pageCurrent = (TextView)actionBarView.findViewById(R.id.pageCurrent);
//		pageTotal = (TextView)actionBarView.findViewById(R.id.pageTotal);
//		
//		picPaths = getIntent().getStringArrayListExtra("url");
//		currentPage = getIntent().getIntExtra("current_image",0);
//		NUM_PAGES = picPaths.size();
//		strServiceLocation = getIntent().getStringExtra("servicelocation");
//		strServiceName = getIntent().getStringExtra("servicename");
//
//		if(!TextUtils.isEmpty(strServiceName))
//		{
//			serviceName.setText(strServiceName);
//		}
//		if(!TextUtils.isEmpty(strServiceLocation))
//		{
//			serviceLocation.setText(strServiceLocation);
//		}
//	    pageTotal.setText(String.valueOf(picPaths.size()));
//		mPager.setAdapter(new MyPagerAdapter());
//		mPager.setPageTransformer(true, new DepthPageTransformer());
//		mPager.setCurrentItem(currentPage);
//		EA P3A01
	}
//	SA P3A01
	/**
     * A simple pager adapter that represents ImageView objects, in
     * sequence.
     */
    private class MyPagerAdapter extends PagerAdapter {
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
        	ImageView imageView = new ImageView(DetailedImage.this);
        	imageView.setScaleType(ScaleType.FIT_CENTER);
        	
        	Bitmap myImg=null;
        	String extension = "";
        	int j = picPaths.get(position).lastIndexOf('.');
			if (j >= 0) {
				extension = picPaths.get(position).substring(j + 1);
			}
			if (extension.equalsIgnoreCase("mp4")) {
				myImg = ThumbnailUtils.createVideoThumbnail(picPaths.get(position),MediaStore.Video.Thumbnails.MICRO_KIND);
				try {
					Resources r = getResources();
					Drawable[] layers = new Drawable[2];
					layers[0] = new BitmapDrawable(myImg);
					layers[1] = r.getDrawable(R.drawable.big_play_icon);
					LayerDrawable layerDrawable = new LayerDrawable(layers);
					myImg=GalleryMedia.drawableToBitmap(GalleryMedia.geSingleDrawable(layerDrawable));
				}
				catch (Exception e) {
					Log.e("LoadPathCursor", e.toString());
				}
			 } else {
				myImg = BitmapFactory.decodeFile(picPaths.get(position));
			}

        	
//        	Bitmap myImg = BitmapFactory.decodeFile(picPaths.get(position));
    		Matrix matrix = new Matrix();
    		matrix.postRotate(0);
    		Bitmap rotated = Bitmap.createBitmap(myImg, 0, 0, myImg.getWidth(), myImg.getHeight(),matrix, true);
    		imageView.setImageBitmap(rotated);
        	container.addView(imageView);
			return imageView;
        	}

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            pageCurrent.setText(String.valueOf(position+1));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }
    }
//    EA P3A01
    
//    SA P3A01
	public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        @SuppressLint("NewApi")
		public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
//    EA P3A01
}
