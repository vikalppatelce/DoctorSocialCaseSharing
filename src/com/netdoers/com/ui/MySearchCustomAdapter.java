/* HISTORY
 * CATEGORY 		:- UI | DASHBOARD
 * DEVELOPER		:- VIKALP PATEL
 * AIM      		:- DASHBOARD
 * ADDED    		:- 15/01/2014
 * DESCRIPTION 		:- APPLICATION MAIN DASHBOARD SEARCH ADAPTER WITH DELETE FUNCTIONALITY
 * 
 * S - START E- END  C- COMMENTED  U -EDITED A -ADDED
 * --------------------------------------------------------------------------------------------------------------------
 * INDEX       DEVELOPER		DATE			FUNCTION 		DESCRIPTION
 * --------------------------------------------------------------------------------------------------------------------
 * 10001       VIKALP PATEL    15/01/2014       				ADDED DELETE OPTION WITH SEARCH OPTION DIRECTLY
 * --------------------------------------------------------------------------------------------------------------------
 */

package com.netdoers.com.ui;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.netdoers.com.SmartConsultant;
import com.netdoers.com.dto.DBConstant;
import com.smarthumanoid.com.R;

public class MySearchCustomAdapter extends SimpleCursorAdapter{

	private Context context; //ADDED 10001
	public MySearchCustomAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, layout, c, from, to);
		this.context=context;//ADDED 10001
	}  
//SA 10001
	@Override
	public View getView(int position, View convertView, ViewGroup parent) { 
		// get reference to the row
		View view = super.getView(position, convertView, parent);
		// check for odd or even to set alternate colors to the row background
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.edt_delete_item, null);
		if(position % 2 == 0) { 
		view.setBackgroundColor(Color.rgb(238, 233, 233));
		} else {
		view.setBackgroundColor(Color.rgb(255, 255, 255));
		}
		// get the id
		getCursor().moveToPosition(position); 
		
		long id = getCursor().getLong(getCursor().getColumnIndex(DBConstant.Patient_Name_Columns.COLUMN_ID));
		
		TextView name = (TextView)view.findViewById(R.id.txtText);
		TextView location = (TextView)view.findViewById(R.id.txtLocation);
		TextView date = (TextView)view.findViewById(R.id.txtDate);
		ImageView delete = (ImageView) view.findViewById(R.id.deleteIcon);
		
		String strName = getCursor().getString(getCursor().getColumnIndex(DBConstant.Patient_Name_Columns.COLUMN_NAME));
		String strLoc = getCursor().getString(getCursor().getColumnIndex(DBConstant.Patient_Name_Columns.COLUMN_LOCATION));
		String strDate = getCursor().getString(getCursor().getColumnIndex(DBConstant.Patient_Name_Columns.COLUMN_DATE));
		
//		getCursor().close();
		name.setText(strName);
		location.setText(strLoc);
		date.setText(strDate);
		
		delete.setTag(String.valueOf(id));
		// retrieve the tag with view.getTag() in the onClickListener
		return view; 
		}
	/*public View getView(int position, View convertView, ViewGroup parent) {  
		//get reference to the row
		View view = super.getView(position, convertView, parent);
		//check for odd or even to set alternate colors to the row background
		if(position % 2 == 0){  
			view.setBackgroundColor(Color.rgb(238, 233, 233));
		}
		else {
			view.setBackgroundColor(Color.rgb(255, 255, 255));
		}
		return view;
	} */
//EA 10001	
}
