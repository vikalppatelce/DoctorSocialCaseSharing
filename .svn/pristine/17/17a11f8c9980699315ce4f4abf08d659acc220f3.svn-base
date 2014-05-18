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


package com.netdoers.com.utils;

import android.util.Log;

public class InitCap {
	public String toTitleCase(String givenString) {
		StringBuffer sb = new StringBuffer();
		String str;
		try
		{
			givenString=givenString.trim();
			if (givenString.length() > 0)
	        {
				givenString= givenString.toLowerCase();
	        	String[] arr = givenString.split(" ");
	            for (int i = 0; i < arr.length; i++) {
	            sb.append(Character.toUpperCase(arr[i].charAt(0))).append(arr[i].substring(1)).append(" ");
	            }  
	            str=sb.toString().trim();
	        }
			else
			{
				str=givenString;
			}			
		}
		catch(Exception e)
		{
			Log.e("InitCap", e.toString());
			str=givenString.trim();
		}
      return str;
    }
}
