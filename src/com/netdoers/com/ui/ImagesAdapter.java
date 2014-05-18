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

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.smarthumanoid.com.R;

public class ImagesAdapter extends BaseAdapter
{
	List<Bitmap> myData;
	Context context;
	LayoutInflater inflater;
	
	public ImagesAdapter(Context context, List<Bitmap> objects) 
	{
		// TODO Auto-generated constructor stub
		this.myData = objects;
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return myData.size();
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = null;
		if(convertView == null)
		{
			v = inflater.inflate(R.layout.item, null);
		}
		else
		{
			v = convertView;
		}
		ImageView txt = (ImageView) v.findViewById(R.id.imagePreview);
		txt.setImageBitmap(getItem(position));
		return v;
		//return super.getView(position, convertView, parent);
	}
	@Override
	public Bitmap getItem(int position) {
		// TODO Auto-generated method stub
		return myData.get(position);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
}