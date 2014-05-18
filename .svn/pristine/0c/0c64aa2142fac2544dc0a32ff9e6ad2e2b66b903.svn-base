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

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.smarthumanoid.com.R;

public class MySpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private List<String> items; // replace MyListItem with your model object type
    private Context context;
    private String message;
    LayoutInflater inflater;

    public MySpinnerAdapter(Context aContext, String message, ArrayList<String> data) {
        context = aContext;
        items = new ArrayList<String>();
        this.message = message;
        items.add(null); // add first dummy item - selection of this will be ignored
        // TODO: add other items;
        
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for(int i = 0; i < data.size(); i++)
        {
        	items.add(data.get(i));
        }
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int aPosition) {
        return items.get(aPosition);
    }

    @Override
    public long getItemId(int aPosition) {
        return aPosition;
    }

    @Override
    public View getView(int aPosition, View aView, ViewGroup aParent) {
    	
    	View v = inflater.inflate(R.layout.list_item, null);
        TextView text = (TextView) v.findViewById(R.id.txtLovValue);//new TextView(context);
        if (aPosition == 0) {
            text.setText(message); // text for first dummy item
        } else {
            text.setText(items.get(aPosition).toString());
            // or use whatever model attribute you'd like displayed instead of toString()
        }
        return v;
    }
    @Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {

    	View v = inflater.inflate(R.layout.list_item, null);
        TextView text = (TextView) v.findViewById(R.id.txtLovValue);//new TextView(context);
        if (position == 0) {
            text.setText(message); // text for first dummy item
        } else {
            text.setText(items.get(position).toString());
            // or use whatever model attribute you'd like displayed instead of toString()
        }
        return v;
        }
}