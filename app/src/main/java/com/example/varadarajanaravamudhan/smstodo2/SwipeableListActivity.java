package com.example.varadarajanaravamudhan.smstodo2;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by varadarajanaravamudhan on 21/05/16.
 */
public class SwipeableListActivity extends Activity implements AdapterView.OnItemSelectedListener{

    ListView listView;
    MyDBHelper dbhelper;
    String[] names={"User 1","User 2","User 3","User 4","User 5","User 6"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipeableview);

        Spinner spin_home3 = (Spinner) findViewById(R.id.home_spinner3);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this, R.array.TopLevelMenu2, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_home3.setAdapter(adapter1);
        spin_home3.setOnItemSelectedListener(this);


        listView = (ListView) findViewById(R.id.listView1);
        String [] messages = getSMS();
        MyListAdapter adapter = new MyListAdapter(this,messages);
        dbhelper = new MyDBHelper(this);
        adapter.setDBHelper(dbhelper);
        listView.setAdapter(adapter);

    }


    private String [] getSMS(){

        ArrayList <SMSSearchResults> listSMSSearchResults = new ArrayList<SMSSearchResults>();
        Uri inboxUri = Uri.parse("content://sms/inbox");
        String[] reqCols = new String[] { "_id", "address", "body" };
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(inboxUri, reqCols, null, null, null);
        if(c!= null){
            while(c.moveToNext()){
                SMSSearchResults smsSearchResults = new SMSSearchResults();
                smsSearchResults.setStrAddr(c.getString(1));
                smsSearchResults.setStrMsg(c.getString(2));
                smsSearchResults.setSelected(false);
                listSMSSearchResults.add(smsSearchResults);
            }
        }
        String [] messages = new String [listSMSSearchResults.size()];
        int i = 0;
        for (SMSSearchResults smsSearchResult : listSMSSearchResults){
            messages[i] = smsSearchResult.getStrMsg()+ "£££" + smsSearchResult.getStrAddr();
            i++;
        }
        return messages;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        if (item.equals("Message")){
           /* Intent intent = new Intent(getApplicationContext(), SwipeableListActivity.class);
            startActivity(intent);*/
        }
        if (item.equals("TODO")){
            Intent intent = new Intent(getApplicationContext(), ManageTODOActivity.class);
            startActivity(intent);
        }
        if(item.equals("Clean TODO")){
            dbhelper.cleanTODO();
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, "TODO list cleaned", duration).show();
        }
        if(item.equals("Exit")){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        if(item.equals("Trial")){
            Intent intent = new Intent(getApplicationContext(), ManageTODOActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}