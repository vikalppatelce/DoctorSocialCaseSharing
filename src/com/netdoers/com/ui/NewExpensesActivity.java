/* HISTORY
 * CATEGORY 		:- EXPENSES | ADD EXPENSES
 * DEVELOPER		:- VIKALP PATEL
 * AIM      		:- EXPENSES
 * DESCRIPTION 		:- ADDING EXPENSES WITH PICTURE
 * 
 * S - START E- END  C- COMMENTED  U -EDITED A -ADDED
 * --------------------------------------------------------------------------------------------------------------------
 * INDEX       DEVELOPER		DATE			FUNCTION		DESCRIPTION
 * --------------------------------------------------------------------------------------------------------------------
 * 10001       VIKALP PATEL    03/01/2014       				ADDING CUSTOMIZE CAMERA
 * 10002       VIKALP PATEL    09/01/2014       				INITCAP VALUES.
 * 10003       VIKALP PATEL    10/01/2014       				ADD FULLSCREEN THEME TO APPLICATION THROUGH PREFERENCES
 * 10004       VIKALP PATEL    10/01/2014       				SELECT CAMERA ON THE BASIS OF PREFERENCES
 * 10005       VIKALP PATEL    13/01/2014       				APPLYING PROPER VALIDATION ON MANDATORY FIELDS.
 * 10006       VIKALP PATEL    13/01/2014       				TOOLTIP FEATURE.
 * 10007       VIKALP PATEL    15/01/2014       				INDIVIDUAL SETTINGS
 * 10013 	   VIKALP PATEL    20/01/2014						LAYOUT RE-DESIGNED
 * 10015       VIKALP PATEL    21/01/2014						TOOLTIP EVENT CHANGE TO ONFOCUS
 * 10017       VIKALP PATEL    23/01/2014						TAKE PICTURE REPLACES WITH ADD PICTURE
 * 10018 	   VIKALP PATEL    23/01/2014						IMAGE COMPRESSION ON PICTURES
 * 1000A       VIKALP PATEL    05/02/2014       RELEASE         EXPENSE ID SAME IN EXPENSE DETAIL TABLE
 * 1000B       VIKALP PATEL    07/02/2014       RELEASE         ADD VIDEO OPTION
 * 1000B-2     VIKALP PATEL    10/02/2014       RELEASE         FIX LOAD PATH CURSOR
 * U0001       VIKALP PATEL    03/03/2014       UIX            	RECORD TRAVERSAL - INSERT & UPDATE
 * U0002       VIKALP PATEL    01/03/2014       UIX            GESTURE FLING LIKE VIEW PAGER
 * BOA01       VIKALP PATEL    05/03/2014       BUG            INTENT FOR PICTURE ONLY
 * P2M01       VIKALP PATEL    11/03/2014       BUG             ADD CANCEL & SAVEANDMAIN
 * P2B01       VIKALP PATEL    11/03/2014       BUG             INTENT FOR VIDEOS ONLY
 * P2B02       VIKALP PATEL    11/03/2014       BUG             IMPORT MP4 VIDEOS ONLY
 * P2B03       VIKALP PATEL    12/03/2014       BUG             COPY IMPORT VIDEOS
 * P3MX01	   VIKALP PATEL    27/03/2014       IMPROVE         VIDEOS OPTION VISIBLITY GONE FOR ADD MEDIA
 * P3B04       VIKALP PATEL    24/04/2014        BUG            SHOWING CALENDAR WITH EXPENSE DATE ONLY
 * --------------------------------------------------------------------------------------------------------------------
 */
 

package com.netdoers.com.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.UndeclaredThrowableException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.CursorToStringConverter;
import android.widget.TextView;
import android.widget.Toast;

import com.netdoers.com.CustomToast;
import com.netdoers.com.SmartConsultant;
import com.netdoers.com.dto.DBConstant;
import com.netdoers.com.utils.AppConstants;
import com.netdoers.com.utils.GalleryMedia;
import com.netdoers.com.utils.ImageCompression;
import com.netdoers.com.utils.InitCap;
import com.smarthumanoid.com.R;

@SuppressLint("ValidFragment")
public class NewExpensesActivity extends FragmentActivity{

	EditText expensesDate;
	EditText expensesAmount;
	AutoCompleteTextView  paymentMode;
	EditText description;
	AutoCompleteTextView category;
	Uri currentFileUri;
	final int REQUEST_CAMERA = 1001;
	final int REQUEST_SMARTHUMANOID_CAMERA = 1002; //Added10001
	final int REQUEST_VIDEO = 1011;//ADDED 1000B
	final int REQUEST_VIDEO_IMPORT=1012;//ADDED 1000B
	SharedPreferences pref;//ADDED 10003
	
	ContentValues contentValues;
	
	ArrayList<String> paths;
	SimpleCursorAdapter mAdapter;
	SimpleCursorAdapter catAdapter;
	Gallery gallery;
	InitCap initCap;//ADDED 10002
	public static final int SECSETTINGS = 1007;//ADDED 10007
	public static final int PIC = 117;//SA 10017
	public static final int IMPORT_PICTURE = 118;
	Uri outputFileUri;//EA 10017
	String compressedPath;//ADDED 10018
	
	ImagesAdapter imagesAdapter = null;
	
	ArrayList<Bitmap> data;
	
	Cursor expenseCursor;
	Cursor pathsCursor;
	TextView txtTitle;
	
	int currentRecord = 0;
	
	String _id = null;
	
	ImageView next;
	ImageView prev;
	ArrayList<String> strModeOfPayment = new ArrayList<String>();
	ArrayList<String> strBank = new ArrayList<String>();
	//SA U0001
	boolean boolInsert = false;
	int currentImage = 0;
	int getImageCount = 0;
	//EA U0001
	//SA U0002
	final static ViewConfiguration vc = ViewConfiguration.get(SmartConsultant.getApplication().getApplicationContext());
	private static final int SWIPE_MIN_DISTANCE = vc.getScaledPagingTouchSlop();
    private static final int SWIPE_MAX_OFF_PATH = vc.getScaledMinimumFlingVelocity();
    private static final int SWIPE_THRESHOLD_VELOCITY = vc.getScaledTouchSlop();
    private GestureDetector gestureDetector;
    LinearLayout flingLayout;
    View.OnTouchListener gestureListener;	//EA U0002
    boolean isSaveMain = false;//    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//SA 10003
		pref = PreferenceManager.getDefaultSharedPreferences(SmartConsultant.getApplication());
		if(pref.getBoolean("prefIsFullScreen", false))
		{
			//setTheme(R.style.FullScreen);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		//EA 10003
		//SA 10013
		//setContentView(R.layout.newexpenses);
		//setContentView(R.layout.new_expenses); Accordion Layout
		setContentView(R.layout.new_expenses_);
		//EA 10013
		
		_id = null;
		initializeData();
		initCap = new InitCap();//ADDED 10001
		txtTitle = (TextView) findViewById(R.id.add_sx_location_text);
		
		next = (ImageView) findViewById(R.id.btn_header_next);
		prev = (ImageView) findViewById(R.id.btn_header_prev);
		
		expensesDate = (EditText) findViewById(R.id.expenses_date);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date = sdf.format(new Date(System.currentTimeMillis()));
		if(date.contains("/"))
		{
			date = date.replace("/", "-");
		}
		expensesDate.setText(date);
		
		expensesAmount = (EditText) findViewById(R.id.expenses_amount);
		paymentMode = (AutoCompleteTextView) findViewById(R.id.expenses_mode_of_payment);
		setModeAdapter();
		
		description = (EditText) findViewById(R.id.expenses_description);		
				
		category = (AutoCompleteTextView) findViewById(R.id.expense_category);
		
		gallery = (Gallery) findViewById(R.id.imagePreview);
		gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				// SU 1000B
				String extension = "";
				int i = paths.get(arg2).lastIndexOf('.');
				if (i >= 0) {
					extension = paths.get(arg2).substring(i + 1);
				}
				if (extension.equalsIgnoreCase("mp4")) {
					File file = new File(paths.get(arg2));
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.fromFile(file), "video/*");
					startActivity(intent);
				} else {
					Intent intent = new Intent(NewExpensesActivity.this,
							DetailedImage.class);
					intent.putExtra("url", paths.get(arg2));
					startActivity(intent);
				}
				// EU 1000B
			}
		});
		setCategoryAdapter();
		contentValues = new ContentValues();
		
		data = new ArrayList<Bitmap>();
		imagesAdapter = new ImagesAdapter(this, data);
		gallery.setAdapter(imagesAdapter);
		paths =  new ArrayList<String>();
		
		loadExpenseCursor();
		//loadPathsCursor(0);
		
		// SA U0002
		gestureDetector = new GestureDetector(this, new MyGestureDetector());
		gestureListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		};
		flingLayout = (LinearLayout) findViewById(R.id.flingLayout);
		flingLayout.setOnTouchListener(gestureListener);
		// EA U0002
		
		//SA 10006
		if(pref.getBoolean("prefIsToolTip", false) || pref.getBoolean("prefIsExpenseToolTip", false))//EDITED 10007
		{
			onToolTipOn();
		}
		//EA 10006
	}
	
	@Override
	protected void onResume() {
	    // TODO Auto-generated method stub
	    super.onResume();
	}


	@Override
	protected void onPause() {
	    // TODO Auto-generated method stub
	    super.onPause();
	}
	public void initializeData()
	{
		strModeOfPayment = SmartConsultant.getApplication().loadModeOfPayment();
		strBank = SmartConsultant.getApplication().loadBank();
	}
	public void setModeAdapter()
	{
		//int[] to = new int[] { android.R.id.text1 };
		int[] to = new int[] { R.id.txtText};
	    String[] from = new String[] { DBConstant.ModeOfPayment_Columns.COLUMN_NAME };
	    
		mAdapter = 
            /*new SimpleCursorAdapter(this, 
                    android.R.layout.simple_dropdown_item_1line, null,
                    from, to);*/new MyCursorAdapter(this, R.layout.edt_item, null, from, to);
		paymentMode.setAdapter(mAdapter);

        // Set an OnItemClickListener, to update dependent fields when
        // a choice is made in the AutoCompleteTextView.
		paymentMode.setOnItemClickListener(new OnItemClickListener() {
            @Override
			public void onItemClick(AdapterView<?> listView, View view,
                        int position, long id) {
                // Get the cursor, positioned to the corresponding row in the
                // result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
            }
        });

        // Set the CursorToStringConverter, to provide the labels for the
        // choices to be displayed in the AutoCompleteTextView.
        mAdapter.setCursorToStringConverter(new CursorToStringConverter() {
            @Override
			public String convertToString(android.database.Cursor cursor) {
                // Get the label for this row out of the "state" column
                //final int columnIndex = cursor.getColumnIndexOrThrow("state");
                final String str = cursor.getString(cursor.getColumnIndex(DBConstant.ModeOfPayment_Columns.COLUMN_NAME));
                return str;
            }
        });
      // Set the FilterQueryProvider, to run queries for choices
        // that match the specified input.
        mAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
			public Cursor runQuery(CharSequence constraint) {
                // Search for states whose names begin with the specified letters.
                //Cursor cursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, projection, DBConstant.Patient_Columns.COLUMN_NAME + "=%" + constraint.toString()+"%", new String[]{constraint.toString()}, null);
            	Cursor cursor = getContentResolver().query(DBConstant.ModeOfPayment_Columns.CONTENT_URI, null, DBConstant.ModeOfPayment_Columns.COLUMN_NAME + " like '%" + constraint.toString()+"%'", null, null);
                return cursor;
            }
        });
	}
	
	public void setCategoryAdapter()
	{
		//int[] to = new int[] { android.R.id.text1 };
		int[] to = new int[] { R.id.txtText};
	    String[] from = new String[] { DBConstant.Bank_Columns.COLUMN_NAME };
	    
		catAdapter = 
            /*new SimpleCursorAdapter(this, 
                    android.R.layout.simple_dropdown_item_1line, null,
                    from, to);*/new MyCursorAdapter(this, R.layout.edt_item, null, from, to);
		category.setAdapter(catAdapter);

        // Set an OnItemClickListener, to update dependent fields when
        // a choice is made in the AutoCompleteTextView.
		category.setOnItemClickListener(new OnItemClickListener() {
            @Override
			public void onItemClick(AdapterView<?> listView, View view,
                        int position, long id) {
                // Get the cursor, positioned to the corresponding row in the
                // result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
            }
        });

        // Set the CursorToStringConverter, to provide the labels for the
        // choices to be displayed in the AutoCompleteTextView.
        catAdapter.setCursorToStringConverter(new CursorToStringConverter() {
            @Override
			public String convertToString(android.database.Cursor cursor) {
                // Get the label for this row out of the "state" column
                //final int columnIndex = cursor.getColumnIndexOrThrow("state");
                final String str = cursor.getString(cursor.getColumnIndex(DBConstant.Bank_Columns.COLUMN_NAME));
                return str;
            }
        });
      // Set the FilterQueryProvider, to run queries for choices
        // that match the specified input.
        catAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
			public Cursor runQuery(CharSequence constraint) {
                // Search for states whose names begin with the specified letters.
                //Cursor cursor = getContentResolver().query(DBConstant.Patient_Columns.CONTENT_URI, projection, DBConstant.Patient_Columns.COLUMN_NAME + "=%" + constraint.toString()+"%", new String[]{constraint.toString()}, null);
            	Cursor cursor = getContentResolver().query(DBConstant.Bank_Columns.CONTENT_URI, null, DBConstant.Bank_Columns.COLUMN_NAME + " like '%" + constraint.toString()+"%'", null, null);
                return cursor;
            }
        });
	}
	public void onManagePaymentMode(View v)
	{
		Intent i = new Intent(this, ManageLOVActivity.class);
		i.putExtra("index", 8);
		startActivityForResult(i, 100);
	}
	
	public void onManageExpenseCategory(View v)
	{
		Intent i = new Intent(this, ManageLOVActivity.class);
		i.putExtra("index", 9);
		startActivityForResult(i, 101);
	}
	
	
	public void onTime(View v)
	{
		DialogFragment newFragment = new FromDatePickerFragment();
	    newFragment.show(getSupportFragmentManager(), "From Date");
	}
	
	public void onPrev(View v)
	{
		Log.e(">>>>>>>>> onPrev >>>", currentRecord+"");
		if(currentRecord > 0)
		{
			currentRecord--;
			setText();
			populateTextFields(currentRecord);
			expenseCursor.moveToPosition(currentRecord);
			loadPathsCursor(currentRecord);
			BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
			btmapOptions.inSampleSize = 2;
			//data = new ArrayList<Bitmap>();
			createAdapter();
			if(paths != null)
			{
				for(int i = 0; i < paths.size(); i++)
				{
					
					//Bitmap bm = BitmapFactory.decodeFile(paths.get(i), btmapOptions);
					//NewExpensesActivity.this.data.add(bm);
					//SA 1000B-2
					String extension = "";
					Bitmap bm=null;
					int j = paths.get(i).lastIndexOf('.');
					if (j >= 0) {
						extension = paths.get(i).substring(j + 1);
					}
					if (extension.equalsIgnoreCase("mp4")) {
						bm = ThumbnailUtils.createVideoThumbnail(paths.get(i),MediaStore.Video.Thumbnails.MICRO_KIND);

						try {
							Resources r = getResources();
							Drawable[] layers = new Drawable[2];
							layers[0] = new BitmapDrawable(bm);
							layers[1] = r.getDrawable(R.drawable.play_icon);
							LayerDrawable layerDrawable = new LayerDrawable(layers);
							NewExpensesActivity.this.data.add(GalleryMedia.drawableToBitmap(GalleryMedia.geSingleDrawable(layerDrawable)));
						}
						catch(Exception e)
						{
							Log.e("LoadPathCursor",e.toString());
						}
					} else {
						bm = BitmapFactory.decodeFile(paths.get(i), btmapOptions);
						NewExpensesActivity.this.data.add(bm);
					}
					//EA 1000B-2
				}
				imagesAdapter.notifyDataSetChanged();
			}
			enableDisableButton();
		}
	}
	public void onFirst(View v)
	{
		Log.e(">>>>>>>>> onFirst >>>", currentRecord+"");
		if(currentRecord > 0)
		{
			currentRecord = 0;
			setText();
			populateTextFields(currentRecord);
			expenseCursor.moveToPosition(currentRecord);
			loadPathsCursor(currentRecord);
			BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
			btmapOptions.inSampleSize = 2;
			//data = new ArrayList<Bitmap>();
			createAdapter();
			if(paths != null)
			{
				for(int i = 0; i < paths.size(); i++)
				{
//					Bitmap bm = BitmapFactory.decodeFile(paths.get(i), btmapOptions);
//					NewExpensesActivity.this.data.add(bm);
					//SA 1000B-2
					String extension = "";
					Bitmap bm=null;
					int j = paths.get(i).lastIndexOf('.');
					if (j >= 0) {
						extension = paths.get(i).substring(j + 1);
					}
					if (extension.equalsIgnoreCase("mp4")) {
						bm = ThumbnailUtils.createVideoThumbnail(paths.get(i),MediaStore.Video.Thumbnails.MICRO_KIND);

						try {
							Resources r = getResources();
							Drawable[] layers = new Drawable[2];
							layers[0] = new BitmapDrawable(bm);
							layers[1] = r.getDrawable(R.drawable.play_icon);
							LayerDrawable layerDrawable = new LayerDrawable(layers);
							NewExpensesActivity.this.data.add(GalleryMedia.drawableToBitmap(GalleryMedia.geSingleDrawable(layerDrawable)));
						}
						catch(Exception e)
						{
							Log.e("LoadPathCursor",e.toString());
						}
					} else {
						bm = BitmapFactory.decodeFile(paths.get(i), btmapOptions);
						NewExpensesActivity.this.data.add(bm);
					}
					//EA 1000B-2
				}
				imagesAdapter.notifyDataSetChanged();
			}
			enableDisableButton();
		}
	}
	
	public void onNext(View v)
	{
		Log.e(">>>>>>>>> onNext >>>", currentRecord+"");
		currentRecord++;
		if(currentRecord < expenseCursor.getCount())
		{
			
			setText();
			populateTextFields(currentRecord);
			expenseCursor.moveToPosition(currentRecord);
			loadPathsCursor(currentRecord);
			BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
			btmapOptions.inSampleSize = 2;
			//data.removeAll(data);//data = new ArrayList<Bitmap>();
			createAdapter();
			if(paths != null)
			{
				for(int i = 0; i < paths.size(); i++)
				{
//					Bitmap bm = BitmapFactory.decodeFile(paths.get(i), btmapOptions);
//					NewExpensesActivity.this.data.add(bm);
					//SA 1000B-2
					String extension = "";
					Bitmap bm=null;
					int j = paths.get(i).lastIndexOf('.');
					if (j >= 0) {
						extension = paths.get(i).substring(j + 1);
					}
					if (extension.equalsIgnoreCase("mp4")) {
						bm = ThumbnailUtils.createVideoThumbnail(paths.get(i),MediaStore.Video.Thumbnails.MICRO_KIND);

						try {
							Resources r = getResources();
							Drawable[] layers = new Drawable[2];
							layers[0] = new BitmapDrawable(bm);
							layers[1] = r.getDrawable(R.drawable.play_icon);
							LayerDrawable layerDrawable = new LayerDrawable(layers);
							NewExpensesActivity.this.data.add(GalleryMedia.drawableToBitmap(GalleryMedia.geSingleDrawable(layerDrawable)));
						}
						catch(Exception e)
						{
							Log.e("LoadPathCursor",e.toString());
						}
					} else {
						bm = BitmapFactory.decodeFile(paths.get(i), btmapOptions);
						NewExpensesActivity.this.data.add(bm);
					}
					//EA 1000B-2
				}
				imagesAdapter.notifyDataSetChanged();
			}
			
			enableDisableButton();
		}
		else
		{
			_id = null;
			currentRecord = Math.max(expenseCursor.getCount(), 0);
			clearField();
		}
	}
	
	public void onLast(View v)
	{
		Log.e(">>>>>>>>> onLast >>>", currentRecord+"");
		_id = null;
		currentRecord = expenseCursor.getCount();
		{
			currentRecord = Math.max(expenseCursor.getCount(), 0);
			clearField();
		}
	}
	
	public void enableDisableButton()
	{
		/*if(expenseCursor.getCount() == 0)
		{
			next.setEnabled(false);
			prev.setEnabled(false);
		}
		else 
		{
			if(currentRecord == 0)
			{
				prev.setEnabled(false);
				next.setEnabled(true);
			}
			if(currentRecord == expenseCursor.getCount())
			{
				prev.setEnabled(true);
				next.setEnabled(false);
			}
		}*/
	}
	public void populateTextFields(int index)
	{
		if(expenseCursor != null && expenseCursor.getCount() > index)
		{
			expenseCursor.moveToPosition(index);
			_id 		= expenseCursor.getString(expenseCursor.getColumnIndex(DBConstant.Expeses_Columns.COLUMN_ID));
			String date = expenseCursor.getString(expenseCursor.getColumnIndex(DBConstant.Expeses_Columns.COLUMN_DATE));
			String amount = expenseCursor.getString(expenseCursor.getColumnIndex(DBConstant.Expeses_Columns.COLUMN_AMOUNT));
			String desc = expenseCursor.getString(expenseCursor.getColumnIndex(DBConstant.Expeses_Columns.COLUMN_DESCRIPTION));
			String cat = expenseCursor.getString(expenseCursor.getColumnIndex(DBConstant.Expeses_Columns.COLUMN_CATEGORY));
			String mode = expenseCursor.getString(expenseCursor.getColumnIndex(DBConstant.Expeses_Columns.COLUMN_PAYMENT_MODE));
			
			
			expensesDate.setText(date);
			expensesAmount.setText(amount);
			description.setText(desc);
			category.setText(cat);
			paymentMode.setText(mode);
		}
	}
	
//SA 10017	
	@Override
	protected Dialog onCreateDialog(int id, Bundle b) {
		// TODO Auto-generated method stub
		switch(id)
		{
			case PIC:
				 LayoutInflater inflater = LayoutInflater.from(this);
				 View dialogview = inflater.inflate(R.layout.add_pic_dialog, null);
				 AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
				 //dialogbuilder.setTitle("Add Picture");
				 dialogbuilder.setView(dialogview);
				 return dialogbuilder.create();
		}
		return super.onCreateDialog(id);
	}
//EA 10017	
	class FromDatePickerFragment extends DialogFragment implements OnDateSetListener
	{

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
//			SA P3B05
			int day,mnth,yr;
			try {
				String[] strDate = expensesDate.getText().toString().split("-");
				day = Integer.parseInt(strDate[0]) == 1 ? Integer.parseInt("0"+strDate[0]) : Integer.parseInt(strDate[0]);  
				mnth = Integer.parseInt(strDate[1]) == 1 ? Integer.parseInt("0"+strDate[1])-1 : Integer.parseInt(strDate[1])-1;
				yr = Integer.parseInt(strDate[2]);
			} catch (Exception e) {
				final Calendar c = Calendar.getInstance();
				yr = c.get(Calendar.YEAR);
				mnth = c.get(Calendar.MONTH);
				day = c.get(Calendar.DAY_OF_MONTH);
			}
//			EA P3B05
			// Create a new instance of TimePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, yr, mnth, day);
		}
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
			// TODO Auto-generated method stub
			String date;
			monthOfYear++;
			if(dayOfMonth < 10)
			{
				date = "0" + dayOfMonth+"-";
			}
			else 
			{
				date = dayOfMonth+"-";
			}
			if(monthOfYear < 10)
			{
				date += "0" + monthOfYear+"-";
			}
			else
			{
				date += monthOfYear+"-";
			}
			
			date += year;
			//addSxDate.setText(dayOfMonth+"-"+monthOfYear+"-"+year);
			expensesDate.setText(date);
		}
	}
	//SA 10017
			@SuppressWarnings("deprecation")
			public void onAddPicture(View v)
			{
				showDialog(PIC);
			}
			//SA 1000B
			public void onTakeVideo(View v) {
				Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
				getVideoPath();
				intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
				startActivityForResult(intent, REQUEST_VIDEO);
			}

			public void onImportVideo(View v) {
//				SA P2B01
//				Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
//				getVideoPath();
//				intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
//				startActivityForResult(intent, REQUEST_VIDEO_IMPORT);
				
				Intent intent = new Intent();
				intent.setType("video/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Select Video"), REQUEST_VIDEO_IMPORT);
//				EA P2B01
			}
			public void getVideoPath() {
				File imageDirectory = null;
				String state = Environment.getExternalStorageState();
				if (Environment.MEDIA_MOUNTED.equals(state)) {
					imageDirectory = new File(AppConstants.IMAGE_DIRECTORY_PATH);
				} else {
					imageDirectory = new File(AppConstants.IMAGE_DIRECTORY_PATH_DATA);
				}

				imageDirectory.mkdirs();
				File tempFile = new File(imageDirectory, getVideoName()
						+ AppConstants.VIDEO_EXTENSION);
				outputFileUri = Uri.fromFile(tempFile);
				currentFileUri = outputFileUri;
			}
			// EA 1000B
			public void getImagePath()
			{
				File imageDirectory =null;
				String state = Environment.getExternalStorageState();
				if (Environment.MEDIA_MOUNTED.equals(state)) 
				{
					imageDirectory = new File(AppConstants.IMAGE_DIRECTORY_PATH);
				}
				else
				{
					imageDirectory = new File(AppConstants.IMAGE_DIRECTORY_PATH_DATA);
				}

				imageDirectory.mkdirs();
				File tempFile = new File(imageDirectory, getVideoName()+ AppConstants.EXTENSION);
				outputFileUri = Uri.fromFile( tempFile );
				currentFileUri = outputFileUri;
			}
			
			public void copy(File src, File dst) throws IOException {
			    InputStream in = new FileInputStream(src);
			    OutputStream out = new FileOutputStream(dst);

			    // Transfer bytes from in to out
			    byte[] buf = new byte[1024];
			    int len;
			    while ((len = in.read(buf)) > 0) {
			        out.write(buf, 0, len);
			    }
			    in.close();
			    out.close();
			}
			
			public void onImportPicture(View v)
			{
//				SA B0A01
//				Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//				startActivityForResult(i, IMPORT_PICTURE);
				
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMPORT_PICTURE);
//				EA B0A01
			}
			
			@Override
			protected void onPrepareDialog(int id, Dialog dialog) {
				switch (id) {
				case PIC:
					final AlertDialog alertDialog = (AlertDialog) dialog;
					Button import_picture = (Button) alertDialog.findViewById(R.id.import_picture);
					Button take_picture = (Button) alertDialog.findViewById(R.id.take_picture);
					Button take_video = (Button)alertDialog.findViewById(R.id.take_video);//ADDED 1000B
					Button import_video = (Button)alertDialog.findViewById(R.id.import_video);//ADDED 1000B
					take_video.setVisibility(View.GONE);//SA P3MX01
					import_video.setVisibility(View.GONE);//EA P3MX01
					import_picture.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							onImportPicture(v);
							alertDialog.dismiss();
						}
					});
					take_picture.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							onTakePicture(v);
							alertDialog.dismiss();
						}
					});
					//SA 1000B
					take_video.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							onTakeVideo(v);
							alertDialog.dismiss();
						}
					});
					import_video.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							onImportVideo(v);
							alertDialog.dismiss();
						}
					});
					// EA 1000B
					break;
				}
			}
			//EA 10017

	public void onTakePicture(View v)
	{
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		getImagePath();//added 10017
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
	    //SA 10004
  		pref = PreferenceManager.getDefaultSharedPreferences(SmartConsultant.getApplication());
  		if(pref.getBoolean("prefSmartHumanoidCamera", false))
  		{
  			Intent cameraIntent = new Intent(getApplicationContext(),CameraActivity.class);
  	        cameraIntent.putExtra("FILE_URI", outputFileUri.toString());
  	        startActivityForResult(cameraIntent, REQUEST_SMARTHUMANOID_CAMERA);
  		}
  		else
  		{
  			startActivityForResult(intent, REQUEST_CAMERA);
  		}
  		//EA 10004
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Bitmap bm = null;
		if (resultCode == RESULT_OK) 
		{
			//SA 10017
			if (requestCode == IMPORT_PICTURE) {
				//SA U0001
				if(currentRecord+1 < expenseCursor.getCount()+1)
				{
					currentImage = paths.size();	
				}
				//EA U0001
				
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaColumns.DATA };
				Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = cursor.getString(columnIndex);
				cursor.close();
				
				getImagePath();
				try {
					copy(new File(picturePath), new File(currentFileUri.getPath()));
					} 
				catch (IOException e) 
				{
					Log.e("IMPORT_PICTURE", e.toString());
				}
		         
				BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
				btmapOptions.inSampleSize = 2;
				bm = BitmapFactory.decodeFile(picturePath,btmapOptions);
				NewExpensesActivity.this.data.add(bm);
				imagesAdapter.notifyDataSetChanged();
				compressedPath = ImageCompression.compressImage(currentFileUri.getPath());//ADDED 10018
				galleryAddPic();
				paths.add(compressedPath);//EDITED 10018
			}
			//EA 10017
			if (requestCode == REQUEST_CAMERA) {
				if(resultCode == RESULT_OK)
	    		{
					//SA U0001
					if(currentRecord+1 < expenseCursor.getCount()+1)
					{
						currentImage = paths.size();	
					}
					//EA U0001
	    			BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
					btmapOptions.inSampleSize = 2;
					try
					{
						bm = BitmapFactory.decodeFile(currentFileUri.getPath(), btmapOptions);	
					}
					catch(Exception e)
					{
						Log.e("currentFileUri", "currentFileUri"+e.toString());
						throw new UndeclaredThrowableException(e);
					}
					NewExpensesActivity.this.data.add(bm);
					imagesAdapter.notifyDataSetChanged();
					try
					{
						compressedPath = ImageCompression.compressImage(currentFileUri.getPath());//ADDED 10018
						galleryAddPic();
						paths.add(compressedPath);//EDITED 10018
					}
					catch(Exception e)
					{
						throw new UndeclaredThrowableException(e);
					}
	    		}
			}
			
			if (requestCode == REQUEST_SMARTHUMANOID_CAMERA) {
				
				if (resultCode == RESULT_OK) {
					
					//SA U0001
					if(currentRecord+1 < expenseCursor.getCount()+1)
					{
						currentImage = paths.size();	
					}
					//EA U0001
					BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
					btmapOptions.inSampleSize = 2;
					bm = BitmapFactory.decodeFile(currentFileUri.getPath(),
							btmapOptions);
					NewExpensesActivity.this.data.add(bm);
					imagesAdapter.notifyDataSetChanged();
					compressedPath = ImageCompression.compressImage(currentFileUri.getPath());//ADDED 10018
					galleryAddPic();
					paths.add(compressedPath);//EDITED 10018
				} else {
					Toast.makeText(getApplicationContext(), "Error while taking picture!", Toast.LENGTH_SHORT).show();
				}
			}
			// SA 1000B
						if (requestCode == REQUEST_VIDEO) {
							if (resultCode == RESULT_OK) {
								//SA U0001
								if(currentRecord+1 < expenseCursor.getCount()+1)
								{
									currentImage = paths.size();	
								}
								//EA U0001
								
								bm = ThumbnailUtils.createVideoThumbnail(currentFileUri.getPath(),MediaStore.Video.Thumbnails.MICRO_KIND);
								try {
									Resources r = getResources();
									Drawable[] layers = new Drawable[2];
									layers[0] = new BitmapDrawable(bm);
									layers[1] = r.getDrawable(R.drawable.play_icon);
									LayerDrawable layerDrawable = new LayerDrawable(layers);
									NewExpensesActivity.this.data.add(GalleryMedia.drawableToBitmap(GalleryMedia.geSingleDrawable(layerDrawable)));
								} catch (Exception e) {
									Log.e("Creating Thumbnail",e.toString());
								}
								imagesAdapter.notifyDataSetChanged();
								galleryAddPic();
								paths.add(currentFileUri.getPath());
							} else {
								CustomToast.showToastMessage(getApplicationContext(),
										"Error while taking video!");
							}
						}

						if (requestCode == REQUEST_VIDEO_IMPORT) {
							if (resultCode == RESULT_OK) {
//								SC P2B02
								//SA U0001
//								if(currentRecord+1 < expenseCursor.getCount()+1)
//								{
//									currentImage = paths.size();	
//								}
								//EA U0001
//								Uri selectedImage = data.getData();
//								String[] filePathColumn = { MediaColumns.DATA };
//								Cursor cursor = getContentResolver().query(selectedImage,
//										filePathColumn, null, null, null);
//								cursor.moveToFirst();
//								int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//								String videoPath = cursor.getString(columnIndex);
//								cursor.close();

//								getVideoPath();
//								try {
//									copy(new File(videoPath),
//											new File(currentFileUri.getPath()));
//								} catch (IOException e) {
//									Log.e("IMPORT_VIDEO", e.toString());
//								}
//
//								bm = ThumbnailUtils.createVideoThumbnail(currentFileUri.getPath(),MediaStore.Video.Thumbnails.MICRO_KIND);
//
//								try {
//									Resources r = getResources();
//									Drawable[] layers = new Drawable[2];
//									layers[0] = new BitmapDrawable(bm);
//									layers[1] = r.getDrawable(R.drawable.play_icon);
//									LayerDrawable layerDrawable = new LayerDrawable(layers);
//									NewExpensesActivity.this.data.add(GalleryMedia.drawableToBitmap(GalleryMedia.geSingleDrawable(layerDrawable)));
//								} catch (Exception e) {
//									Log.e("Creating Thumbnail",e.toString());
//								}
//								imagesAdapter.notifyDataSetChanged();
//								galleryAddPic();
//								paths.add(currentFileUri.getPath());
//								EC P2B02 SA P2B02
								Uri selectedImage = data.getData();
								String[] filePathColumn = { MediaColumns.DATA };
								Cursor cursor = getContentResolver().query(selectedImage,
										filePathColumn, null, null, null);
								cursor.moveToFirst();
								int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
								String videoPath = cursor.getString(columnIndex);
								cursor.close();
								
								if(videoPath.endsWith(".mp4"))
								{
									if(currentRecord+1 < expenseCursor.getCount()+1)
									{
										currentImage = paths.size();	
									}
									
									getVideoPath();
//									SC P2B03
//									try {
//										copy(new File(videoPath),new File(currentFileUri.getPath()));
//									} catch (Exception e) {
//										Log.e("COPY VIDEO", e.toString());
//									}

//									bm = ThumbnailUtils.createVideoThumbnail(currentFileUri.getPath(),MediaStore.Video.Thumbnails.MICRO_KIND); EC P2B03
									bm = ThumbnailUtils.createVideoThumbnail(videoPath,MediaStore.Video.Thumbnails.MICRO_KIND);//ADDED P2B03


									try {
										Resources r = getResources();
										Drawable[] layers = new Drawable[2];
										layers[0] = new BitmapDrawable(bm);
										layers[1] = r.getDrawable(R.drawable.play_icon);
										LayerDrawable layerDrawable = new LayerDrawable(layers);
										NewExpensesActivity.this.data.add(GalleryMedia.drawableToBitmap(GalleryMedia.geSingleDrawable(layerDrawable)));
									} catch (Exception e) {
										Log.e("Creating Thumbnail",e.toString());
									}
//									SA P2B03
									try {
										new CopyMediaTask(videoPath,currentFileUri).execute();
									} catch (Exception e) {
										Log.e("COPY VIDEO", e.toString());
									}
//									EA P2B03
									imagesAdapter.notifyDataSetChanged();
									galleryAddPic();
									paths.add(currentFileUri.getPath());
								}
								else
								{
									CustomToast.showToastMessage(getApplicationContext(),"Import mp4 videos only");	
								}
//								EA P2B02
							} else {
								CustomToast.showToastMessage(getApplicationContext(),
										"Error while importing video!");
							}
						}
						// EA 1000B
			
			
			if (requestCode == 100) { //payment mode
				String str = data.getStringExtra("data");
				paymentMode.setText(str);
				Handler handler = new Handler() 
				{
					@Override
					public void handleMessage(Message msg) {
					    ((AutoCompleteTextView)msg.obj).setAdapter(mAdapter);
					};
				};
				Message msg = handler.obtainMessage();
				msg.obj = paymentMode;
				handler.sendMessageDelayed(msg, 200);
			}
			if (requestCode == 101) { //category
				String str = data.getStringExtra("data");
				category.setText(str);
				Handler handler = new Handler() 
				{
					@Override
					public void handleMessage(Message msg) {
					    ((AutoCompleteTextView)msg.obj).setAdapter(catAdapter);
					};
				};
				Message msg = handler.obtainMessage();
				msg.obj = category;
				handler.sendMessageDelayed(msg, 200);
			}
		}
		
		// SA 10007
		if (requestCode == SECSETTINGS) {
			Intent intent = getIntent();
			finish();
			startActivity(intent);
		}
		// EA 10007
	}
//	SA P2B03
	private class CopyMediaTask extends AsyncTask<Void, Void, String>
	{
		String videoPath;
		Uri currentFileUri;
		public CopyMediaTask(String v,Uri c)
		{
			videoPath = v;
			currentFileUri = c;
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String n = null;
			try {
				copy(new File(videoPath),new File(currentFileUri.getPath()));
			} 
			 catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			return n;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
	}
//	EA P2B03

	
	public void onSave(View v)
	{
		if((expensesAmount.getText() != null && expensesAmount.getText().toString().length() > 0) 
				&& (description.getText() != null && description.getText().toString().length() > 0))
			
		{
			contentValues = new ContentValues();
			contentValues.put(DBConstant.Expeses_Columns.COLUMN_DATE, expensesDate.getText().toString());
			contentValues.put(DBConstant.Expeses_Columns.COLUMN_AMOUNT, expensesAmount.getText().toString());
			contentValues.put(DBConstant.Expeses_Columns.COLUMN_DESCRIPTION, initCap.toTitleCase(description.getText().toString()));
			contentValues.put(DBConstant.Expeses_Columns.COLUMN_PAYMENT_MODE, initCap.toTitleCase(paymentMode.getText().toString()));
			contentValues.put(DBConstant.Expeses_Columns.COLUMN_CATEGORY, initCap.toTitleCase(category.getText().toString()));
			contentValues.put(DBConstant.Expeses_Columns.COLUMN_SYNC_STATUS, 0);
			
			//SA U0001
			if(currentRecord+1 < expenseCursor.getCount()+1)
			{
				expenseCursor.moveToPosition(currentRecord);
				String update_id = expenseCursor.getString(expenseCursor.getColumnIndex(DBConstant.Expeses_Columns.COLUMN_ID));
				getContentResolver().update(DBConstant.Expeses_Columns.CONTENT_URI,contentValues,DBConstant.Expeses_Columns.COLUMN_ID + "=?",new String[]{update_id});
				
				getImageCount = getContentResolver().query(DBConstant.Expeses_Details_Columns.CONTENT_URI, null, DBConstant.Expeses_Details_Columns.COLUMN_EXP_ID + "=?",new String[]{update_id}, null).getCount();
				boolInsert = false;
			}
			else
			{
				getContentResolver().insert(DBConstant.Expeses_Columns.CONTENT_URI,contentValues);
				boolInsert = true;
			}
			//EA U0001
//			String myId = null;
//			Uri uri = null;
//
//			if (_id != null) {
//				getContentResolver().update(
//						DBConstant.Expeses_Columns.CONTENT_URI, contentValues,
//						"_id=?", new String[] { _id });
//
//				int del = getContentResolver()
//						.delete(DBConstant.Expeses_Details_Columns.CONTENT_URI,
//								DBConstant.Expeses_Details_Columns.COLUMN_EXP_ID
//										+ "=?", new String[] { _id });
//				Log.e("DELETE", del + "");
//				myId = _id;
//			} else {
//				uri = getContentResolver().insert(
//						DBConstant.Expeses_Columns.CONTENT_URI, contentValues);
//				myId = uri.toString().substring(
//						uri.toString().lastIndexOf("/") + 1);
//			}
			//SA 1000A
//			Cursor expenseCursor;
//			String exp_id = null;
			if(paths.size() > 0)
			{
				expenseCursor = getContentResolver().query(DBConstant.Expeses_Columns.CONTENT_URI, null, null, null, null);
				if (expenseCursor != null && expenseCursor.getCount() > 0) 
				{
					//SA U0001
					if(currentRecord+1 < expenseCursor.getCount()+1)
					{
						expenseCursor.moveToPosition(currentRecord);
						_id = expenseCursor.getString(expenseCursor.getColumnIndex(DBConstant.Expeses_Columns.COLUMN_ID));
					}
					else
					{
						expenseCursor.moveToLast();
						_id = expenseCursor.getString(expenseCursor.getColumnIndex(DBConstant.Expeses_Columns.COLUMN_ID));
					}
					if(boolInsert)
					{
						for (int i = 0; i < paths.size(); i++) { 
							String url = paths.get(i);
							String file_name = url.substring(url.lastIndexOf("/") + 1);
							ContentValues temp = new ContentValues();
							temp.put(DBConstant.Expeses_Details_Columns.COLUMN_EXP_ID, _id);
							temp.put(DBConstant.Expeses_Details_Columns.COLUMN_NAME, file_name);
							temp.put(DBConstant.Expeses_Details_Columns.COLUMN_URL, url);
							temp.put(DBConstant.Expeses_Details_Columns.COLUMN_SYNC_STATUS, 0);
							getContentResolver().insert(DBConstant.Expeses_Details_Columns.CONTENT_URI, temp);
							Log.i("Expense Details", "Expense ID:-"+_id + " --> "+url);
						}
					}
					if(currentImage+1 > getImageCount && !boolInsert)
					{
						for (int i = currentImage; i < paths.size(); i++) { 
							String url = paths.get(i);
							String file_name = url.substring(url.lastIndexOf("/") + 1);
							ContentValues temp = new ContentValues();
							temp.put(DBConstant.Expeses_Details_Columns.COLUMN_EXP_ID, _id);
							temp.put(DBConstant.Expeses_Details_Columns.COLUMN_NAME, file_name);
							temp.put(DBConstant.Expeses_Details_Columns.COLUMN_URL, url);
							temp.put(DBConstant.Expeses_Details_Columns.COLUMN_SYNC_STATUS, 0);
							getContentResolver().insert(DBConstant.Expeses_Details_Columns.CONTENT_URI, temp);
							Log.i("Updating Expense Details", "Expense ID:-"+_id + " --> "+url);
						}
					}
					//EA U0001
				}
			}
			//EA 1000A
			addModeOfPayment();
			addBank();
//			SA P2M01
//			clearField(); 
//			loadExpenseCursor();
			if(!isSaveMain)
			{
				clearField();
				loadExpenseCursor();	
			}
			//EA P2M01
		}
		else
		{
			validateFields();//ADDED 10005
			//Toast.makeText(this, "Please enter mandatory fields!", Toast.LENGTH_LONG).show(); COMMENTED 10005
		}
	}
//	SA P2M01
	public void onSaveMain(View v) {
		if((expensesAmount.getText() != null && expensesAmount.getText().toString().length() > 0) 
			 && (description.getText() != null && description.getText().toString().length() > 0))
		{
			onSave(v);
			isSaveMain = true;
			finish();	
		}
		else
		{
			validateFields();
		}
	}

	public void onCancel(View v) {
		finish();
	}

//	EA P2M01
	
	//SA 10005
		public void validateFields()
	    {
	    	if((expensesAmount.getText().toString() == null || expensesAmount.getText().toString()
					.length() == 0))
			{
	    		expensesAmount.requestFocus();
	    		expensesAmount.setError("Please enter expense amount");
			}
			if((description.getText().toString() == null || description.getText()
					.toString().length() == 0))
			{
				description.requestFocus();
				description.setError("Please enter proper description");
			}
	    }
		//EA 10005
	
	public void addModeOfPayment()
	{
		String s = initCap.toTitleCase(paymentMode.getText().toString());//EDITED 10002
		if(s!= null && s.length() > 0 && !strModeOfPayment.contains(new String(s)))
		{
			addContent(DBConstant.ModeOfPayment_Columns.CONTENT_URI, s);
		}
	}
	
	public void addBank()
	{
		String s = initCap.toTitleCase(category.getText().toString());//EDITED 10002
		if(s!= null && s.length() > 0 && !strBank.contains(new String(s)))
		{
			addContent(DBConstant.Bank_Columns.CONTENT_URI, s);
		}
	}
	
	public void addContent(Uri uri, String str)
	{
		ContentValues values = new ContentValues();
		str = initCap.toTitleCase(str);//ADDED 10002
		values.put(DBConstant.Location_Columns.COLUMN_NAME, str);
		values.put(DBConstant.Location_Columns.COLUMN_SYNC_STATUS, 0);
		getContentResolver().insert(uri, values);
	}
	
	//SA 10006
	public void onToolTipOn()
	{
		final Context c = getApplicationContext();
		
		//SU 10015
		expensesAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus)
					CustomToast.showToastMessage(c, getString(R.string.texpense));
			}
		});
		description.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus)
					CustomToast.showToastMessage(c, getString(R.string.tdesc));
			}
		});
		category.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus)
					CustomToast.showToastMessage(c, getString(R.string.tcategory));
			}
		});
		paymentMode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus)
					CustomToast.showToastMessage(c, getString(R.string.texpensemode));
			}
		});
		//EU 10015
	}
	//EA 10006
	
	public void clearField()
	{
		paths.clear();
		paths = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date = sdf.format(new Date(System.currentTimeMillis()));
		if(date.contains("/"))
		{
			date = date.replace("/", "-");
		}
		expensesDate.setText(date);
		
		description.setText("");
		paymentMode.setText("");
		category.setText("");
		expensesAmount.setText("");
		
		/*expensesAmount.setHint("Amount");
		description.setHint("Description");
		paymentMode.setHint("Mode e.g Bank transfer, Cash");
		category.setHint("Expense category");*/
		
		expenseCursor = null;
		pathsCursor = null;
		loadExpenseCursor();
		//loadPathsCursor(0);
		
		data.removeAll(data);//data = new ArrayList<Bitmap>();
		currentRecord = Math.max(expenseCursor.getCount(), 0);
		gallery.setAdapter(null);
		
		createAdapter();
	}
	
	public void createAdapter()
	{
		data = new ArrayList<Bitmap>();
		imagesAdapter = new ImagesAdapter(this, data);
		gallery.setAdapter(imagesAdapter);
	}
	//SA U0002
		class MyGestureDetector extends SimpleOnGestureListener {
	        @Override
	        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
	            try {
	            	View v = new View(SmartConsultant.getApplication().getApplicationContext());
	                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
	                    return false;
	                // right to left swipe
	                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//	                    Toast.makeText(SmartConsultant.getApplication().getApplicationContext(), "Left Swipe", Toast.LENGTH_SHORT).show();
	                    onNext(v);
	                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
//	                    Toast.makeText(SmartConsultant.getApplication().getApplicationContext(), "Right Swipe", Toast.LENGTH_SHORT).show();
	                    onPrev(v);
	                }
	            } catch (Exception e) {
	                // nothing
	            }
	            return false;
	        }

	            @Override
	        public boolean onDown(MotionEvent e) {
	              return true;
	        }
	    }
		//EA U0002

	
	public void loadExpenseCursor()
	{
		expenseCursor = getContentResolver().query(DBConstant.Expeses_Columns.CONTENT_URI, null, null, null, null);	
		currentRecord = Math.max(expenseCursor.getCount(), 0);
		setText();
	}
	public void setText()
	{
		txtTitle.setText("Expense " + (currentRecord+1) +" of " + (expenseCursor.getCount()+1));
	}
	
	public void loadPathsCursor(int index)
	{
		paths = new ArrayList<String>();
		if(expenseCursor != null && expenseCursor.getCount() > index)
		{
			expenseCursor.moveToPosition(index);
			String id = expenseCursor.getString(expenseCursor.getColumnIndex(DBConstant.Expeses_Columns.COLUMN_ID));
			pathsCursor = getContentResolver().query(DBConstant.Expeses_Details_Columns.CONTENT_URI, null, DBConstant.Expeses_Details_Columns.COLUMN_EXP_ID + "=?", new String[]{id}, null);
			if(pathsCursor != null && pathsCursor.getCount() > 0)
			{
				//paths = new ArrayList<String>();
				int colIndex = pathsCursor.getColumnIndex(DBConstant.Expeses_Details_Columns.COLUMN_URL);
				while(pathsCursor.moveToNext())
				{
					paths.add(pathsCursor.getString(colIndex));
				}
			}
		}
	}
	
	public static String getVideoName()
	{
		String name = "smartConsultant";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			name = sdf.format(new Date(System.currentTimeMillis()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}
	private void galleryAddPic() {
	    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
	    File f = new File(currentFileUri.getPath());
	    Uri contentUri = Uri.fromFile(f);
	    mediaScanIntent.setData(contentUri);
	    this.sendBroadcast(mediaScanIntent);
	}
	
	public void saveData()
	{
		
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
        	i.putExtra("caller", "expense");
        	startActivity(i);
            return true;
          //SA 10007
          		case com.smarthumanoid.com.R.id.mnuSettings:
          			Intent in = new Intent(this, PrefsSecondaryActivity.class);
          			in.putExtra("caller", "expense");
          			startActivityForResult(in, SECSETTINGS);
          			return true;
          			//EA 10007
        default:
            return super.onOptionsItemSelected(item);
        }
    }   
}
