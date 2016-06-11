package com.example.varadarajanaravamudhan.smstodo2;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.varadarajanaravamudhan.smstodo2.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by varadarajanaravamudhan on 14/05/16.
 */
public class MyCustomBaseAdapter extends BaseAdapter {
    private static ArrayList<SMSSearchResults> smsSearchArrayList;

    private LayoutInflater mInflater;

    public MyCustomBaseAdapter(Context context, ArrayList<SMSSearchResults> results){
        smsSearchArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount(){
        return smsSearchArrayList.size();
    }

    public Object getItem(int position){
        return smsSearchArrayList.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    public ArrayList<SMSSearchResults> getList(){
        return this.smsSearchArrayList;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.activity_listview, null);
            holder = new ViewHolder();
            holder.txtId = (TextView)convertView.findViewById(R.id.lblId);
            holder.txtAddr = (TextView)convertView.findViewById(R.id.lbladdr);
            holder.txtMsg = (TextView)convertView.findViewById(R.id.lblMsg);
            holder.check = (CheckBox)convertView.findViewById(R.id.msgcheck);
            holder.imgForRight = (ImageView)convertView.findViewById(R.id.imgForRight);
            holder.imgForLeft = (ImageView)convertView.findViewById(R.id.imgForLeft);
            convertView.setTag(holder);
            convertView.setTag(R.id.lblId,holder.txtId);
            convertView.setTag(R.id.lbladdr, holder.txtAddr);
            convertView.setTag(R.id.lblMsg, holder.txtMsg);
            convertView.setTag(R.id.msgcheck,holder.check);
            convertView.setTag(R.id.imgForRight,holder.imgForRight);
            convertView.setTag(R.id.imgForLeft, holder.imgForLeft);
            Log.d("TODO","set TAG " + R.id.msgcheck);
            holder.check.setOnCheckedChangeListener(
                    new CompoundButton.OnCheckedChangeListener(){

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            //int getPosition = (Integer) buttonView.getTag();
                            //buttonView.getTag();
                            int getPosition = 1;
                            View row = (View)buttonView.getParent();
                            ViewHolder parentRowHolder = new ViewHolder();
                            parentRowHolder.txtAddr = (TextView)row.findViewById(R.id.lbladdr);
                            String selectedAddr = parentRowHolder.txtAddr.getText().toString();
                            parentRowHolder.txtMsg = (TextView)row.findViewById(R.id.lblMsg);
                            String selectedMsg = parentRowHolder.txtMsg.getText().toString();
                            parentRowHolder.check = (CheckBox)row.findViewById(R.id.msgcheck);
                            parentRowHolder.txtId = (TextView)row.findViewById(R.id.lblId);
                            String strId = parentRowHolder.txtId.getText().toString();

                            boolean select = parentRowHolder.check.isChecked();
                            Log.d("TODO", "getPostion " + selectedAddr);
                            SMSSearchResults selectResult = new SMSSearchResults();
                            selectResult.setId(strId);
                            selectResult.setStrMsg(selectedMsg);
                            selectResult.setStrAddr(selectedAddr);
                            selectResult.setSelected(select);
                            smsSearchArrayList.add(selectResult);
                        }
                    }
            );
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.txtId.setText(smsSearchArrayList.get(position).getId());
        holder.txtAddr.setText(smsSearchArrayList.get(position).getStrAddr());
        holder.txtMsg.setText(smsSearchArrayList.get(position).getStrMsg());
        holder.check.setChecked(smsSearchArrayList.get(position).isSelected());
        holder.imgForRight.setVisibility(View.VISIBLE);
        holder.imgForLeft.setVisibility(View.VISIBLE);

        return convertView;
    }




    static class ViewHolder{
        TextView txtId;
        ImageView imgForRight;
        TextView txtMsg;
        TextView txtAddr;
        CheckBox check;
        ImageView imgForLeft;
    }
}
