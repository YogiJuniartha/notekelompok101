package com.example.notekelompok10;



import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    private Context mContext;
    //list fields to be displayed
    private ArrayList<String> list_id;
    private ArrayList<String> list_value;
    private ArrayList<String> list_status;

    // ATTRIBUTE TO FETCH DATA FROM DATABASE
    private DBHelper mHelper;
    private SQLiteDatabase dataBase;

    boolean[] itemChecked;

    public ListAdapter(Context c, ArrayList<String> list_id, ArrayList<String> list_value, ArrayList<String> list_status) {
        super();
        this.mContext = c;
        //transfer content from database to temporary memory, GET THIS ARRAY VALUE FROM ListActivity
        this.list_id = list_id;
        this.list_value = list_value;
        this.list_status = list_status;
        //instantiate the SQLiteHelper class every time you open the database
        mHelper = new DBHelper(c);

        itemChecked = new boolean[list_id.size()];
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return list_id.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int pos, View child, ViewGroup parent) {
        final Holder mHolder;
        LayoutInflater layoutInflater;
        if (child == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.activity_list_list, null);//CHOOSE YOUR LIST VIEW

            mHolder = new Holder();
            //link to TextView / INSERT VALUE INTO YOUR LIST FOR VIEW
            mHolder.list_ID = (TextView) child.findViewById(R.id.list_IdView);
            mHolder.list_Status = (CheckBox) child.findViewById(R.id.cbListView);
            mHolder.noteValue = (TextView) child.findViewById(R.id.textView);

                //AUTO CHECKED CHECKBOX BASED ON STATUS FROM DATABASE
                mHolder.list_Status.setChecked(Integer.parseInt(this.list_status.get(pos))==1?true:false);//SET THE CHECK BOX STATUS 1 = CHECKED 0 = NOT CHECKED

                if (mHolder.list_Status.isChecked()) {//SET LINE STRIKE THROUGH ON TEXTVIEW IF THE CHECKBOX IS CHEKCED
                    TextView strikethroughTextView = mHolder.noteValue;
                    strikethroughTextView.setPaintFlags(strikethroughTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }

                // HERE IS CLICK EVENT FUCNTION ON CHECKBOX
                mHolder.list_Status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        boolean isChecked = mHolder.list_Status.isChecked();
                        //Log.d("myCheckBox", "This is my message");
                        if (isChecked==true) {//SET LINE STRIKE THROUGH ON TEXTVIEW IF THE CHECKBOX IS CHEKCED / ALSO UPDATE STATUS ON DATABASE
                            TextView strikethroughTextView = mHolder.noteValue;
                            strikethroughTextView.setPaintFlags(strikethroughTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                            //YOU CAN USE BELOW CODE TO GET ID FROM CHECKED CHECKBOX ON LIST
                            String listIdParam = mHolder.list_ID.getText().toString();

                            //OPEN NEW DATABASE CONNECTION
                            SQLiteDatabase db = mHelper.getWritableDatabase();
                            ContentValues values = new ContentValues ();
                            values.put(List.KEY_status,1);//SET VALUE TO 1 FOR CHECKED CHECKBOX, SET THE FIELD VALUE HERE
                            // It's a good practice to use parameter ?, instead of concatenate string
                            db.update(List.TABLE, values, List.KEY_ID + "= ?", new String[] { String.valueOf(listIdParam/*PLACE YOUE ID HERE*/) });
                            db.close(); // Closing database connection
                        }
                        if (isChecked==false) {//REMOVE LINE STRIKE THROUGH ON TEXTVIEW IF THE CHECKBOX IS UNCHEKCED / ALSO UPDATE STATUS ON DATABASE
                            TextView strikethroughTextView = mHolder.noteValue;
                            strikethroughTextView.setPaintFlags(strikethroughTextView.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));

                            //YOU CAN USE BELOW CODE TO GET ID FROM CHECKED CHECKBOX ON LIST
                            String listIdParam = mHolder.list_ID.getText().toString();

                            //OPEN NEW DATABASE CONNECTION
                            SQLiteDatabase db = mHelper.getWritableDatabase();
                            ContentValues values = new ContentValues ();
                            values.put(List.KEY_status,0);//SET VALUE TO 0 FOR UNCHECKED CHECKBOX, SET THE FIELD VALUE HERE
                            // It's a good practice to use parameter ?, instead of concatenate string
                            db.update(List.TABLE, values, List.KEY_ID + "= ?", new String[] { String.valueOf(listIdParam/*PLACE YOUE ID HERE*/) });
                            db.close(); // Closing database connection
                        }
                    }
                });

            child.setTag(mHolder);
        } else {
            mHolder = (Holder) child.getTag();
        }

        //transfer to TextView in screen / INSERT VALUE FROM ListActivity CLASS HERE
        mHolder.list_ID.setText(list_id.get(pos));
        mHolder.noteValue.setText(list_value.get(pos));
        //mHolder.list_Status.setTag(list_status.get(pos));

        return child;
    }

    public class Holder {
        TextView noteValue;
        TextView list_ID;
        CheckBox list_Status;
    }

}
