/*HISTORY
* CATEGORY			 :- PREFERENCES 
* DEVELOPER			 :- VIKALP PATEL
* AIM				 :- CAPTURE IMAGE
* DESCRIPTION		 :- TAKING PICTURE WITH THE ACTIVITY
* 
* S - START E- END C- COMMENTED U -EDITED A -ADDED
* --------------------------------------------------------------------------------------------------------------------
* INDEX 		DEVELOPER 			DATE 			FUNCTION		DESCRIPTION
* --------------------------------------------------------------------------------------------------------------------
* 10001 	  VIKALP PATEL 		 10/01/2014 						ADD FULLSCREEN THEME TO APPLICATION THROUGH PREFERENCES
* --------------------------------------------------------------------------------------------------------------------
*/

package com.netdoers.com.ui;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.smarthumanoid.com.R;

public class DashboardManageActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		PreferenceManager pref = getPreferenceManager();
		addPreferencesFromResource(R.xml.dashboard_settings);
	}
	@Override
	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

}
