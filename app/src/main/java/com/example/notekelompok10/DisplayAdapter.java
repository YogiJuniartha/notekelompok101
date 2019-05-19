package com.example.notekelompok10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class DisplayAdapter extends BaseAdapter {

    private Context mContext;
    //list fields to be displayed
    private ArrayList<String> note_id;
    private ArrayList<String> note_title;
    private ArrayList<String> note_updated_at;


    public DisplayAdapter(Context c, ArrayList<String> note_id, ArrayList<String> note_title, ArrayList<String> note_updated_at) {
        this.mContext = c;
        //transfer content from database to temporary memory
        this.note_id = note_id;
        this.note_title = note_title;
        this.note_updated_at = note_updated_at;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return note_id.size();
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
        Holder mHolder;
        LayoutInflater layoutInflater;
        if (child == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.activity_note_list, null);
            mHolder = new Holder();

            //link to TextView
            mHolder.note_ID = (TextView) child.findViewById(R.id.note_IdView);
            mHolder.noteTitle = (TextView) child.findViewById(R.id.textView);
            mHolder.noteUpdatedAt = (TextView) child.findViewById(R.id.updatedAtView);
            child.setTag(mHolder);
        } else {
            mHolder = (Holder) child.getTag();
        }
        //transfer to TextView in screen
        mHolder.note_ID.setText(note_id.get(pos));
        mHolder.noteTitle.setText(note_title.get(pos));
        mHolder.noteUpdatedAt.setText(note_updated_at.get(pos));

        return child;
    }

    public class Holder {
        TextView noteTitle;
        TextView note_ID;
        TextView noteUpdatedAt;
    }

}
