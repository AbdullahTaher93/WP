package com.apps.puzzleword;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.zip.Inflater;

class AdapterGrid extends BaseAdapter {
    Context context;
    String []Grid;
    LayoutInflater layoutInflater;

    public AdapterGrid(Context context, String[] grid) {
        this.context = context;
        Grid = grid;
    }

    @Override
    public int getCount() {
        return Grid.length;
    }

    @Override
    public Object getItem(int position) {
        return Grid[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        //layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        //convertView=layoutInflater.inflate(R.layout.setalpha,null);
        TextView textView=new TextView(this.context);

        textView.setText(Grid[position]);
        textView.setGravity(Gravity.CENTER);

        textView.setBackgroundColor(Color.rgb(251,220,187));

        textView.setTextColor(Color.parseColor("#040404"));

        textView.setLayoutParams(new GridView.LayoutParams(100, 100));




        return  textView;
    }
}
