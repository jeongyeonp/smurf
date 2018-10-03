package com.project.smurf.smurf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class mp_adapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<mp_list> data;
    private int layout;
    public mp_adapter(Context context, int layout, ArrayList<mp_list> data){
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data=data;
        this.layout=layout;
    }
    @Override
    public int getCount(){return data.size();}
    @Override
    public String getItem(int position){return data.get(position).getAllergy();}
    @Override
    public long getItemId(int position){return position;}
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);
        }
        mp_list listviewitem=data.get(position);
        TextView name=(TextView)convertView.findViewById(R.id.fa);
        name.setText(listviewitem.getAllergy());
        return convertView;
    }

}
