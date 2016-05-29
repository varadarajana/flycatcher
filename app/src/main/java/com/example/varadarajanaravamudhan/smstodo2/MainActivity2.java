package com.example.varadarajanaravamudhan.smstodo2;

import android.R.*;
import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity2 extends Activity  implements  OnClickListener, AdapterView.OnItemSelectedListener{

    //Button btnSent;
    // Button btnInbox;
    //Button btnDraft, btnTODO;
    Button btnAddTODO;
    TextView lblMsg, lblNo;
    ListView lvMsg;
    MyDBHelper dbhelper;
    MyCustomBaseAdapter myCustomBaseAdapter;

    //  GUI Widget
    String [] smslist = {"Om Namo Narayana","Om Namo Narasimha","Om Namo Bhagvate Vasudevah"};
    // Cursor Adapter
    SimpleCursorAdapter adapter;
    ArrayList<SMSSearchResults> listResults;
    ArrayList<SMSSearchResults> selectedList;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Spinner spin_home2 = (Spinner) findViewById(R.id.home_spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.TopLevelMenu, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_home2.setAdapter(adapter);
        spin_home2.setOnItemSelectedListener(this);
        // Init GUI Widget
        btnAddTODO = (Button) findViewById(R.id.btnAddTODO);
        btnAddTODO.setOnClickListener(this);
        //btnInbox = (Button) findViewById(R.id.btnInbox);
        //btnInbox.setOnClickListener(this);

        /*btnSent = (Button)findViewById(R.id.btnSentBox);
        btnSent.setOnClickListener(this);

        btnDraft = (Button)findViewById(R.id.btnDraft);
        btnDraft.setOnClickListener(this);

        btnTODO = (Button)findViewById(R.id.btnTODO);
        btnTODO.setOnClickListener(this);*/

        lvMsg = (ListView) findViewById(R.id.lvMsg);
        dbhelper = new MyDBHelper(this);
        //dbhelper.cleanAndStart();
        // Init GUI Widget
        //ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, smslist);
        //ListView listView = (ListView)findViewById(R.id.lvMsg);
        //listView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v){
        Toast.makeText(getApplicationContext(),"Add Clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), AddToDoActivity.class);
        startActivity(intent);

        /* if (v == btnInbox) {
            Uri inboxUri = Uri.parse("content://sms/inbox");
            String[] reqCols = new String[]{"_id", "address", "body"};

            // Get Content Resolver object, which will deal with Content Provider
            ContentResolver cr = getContentResolver();

            // Fetch Inbox SMS Message from Built-in Content Provider
            //Cursor c = cr.query(inboxUri, reqCols, null, null, null);

            // Attached Cursor with adapter and display in listview
        adapter = new SimpleCursorAdapter(this, R.layout.activity_listview, c,
                new String[] { "body", "address" }, new int[] {
                R.id.lblMsg, R.id.lbladdr },0);
        lvMsg.setAdapter(adapter);
           listResults = getSMSSearchResults();
            selectedList = new ArrayList<SMSSearchResults>();
            final CheckBox chk = (CheckBox)v.findViewById(R.id.msgcheck);
            myCustomBaseAdapter = new MyCustomBaseAdapter(this,listResults);
            lvMsg.setAdapter(myCustomBaseAdapter);
            lvMsg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object o = lvMsg.getItemAtPosition(position);
                    SMSSearchResults fullObject = (SMSSearchResults) o;
                    Context contextForToast = getApplicationContext();
                    CharSequence text = "Inserted Addr ::" + ((SMSSearchResults) o).getStrAddr() +
                            " :: Message :: " + ((SMSSearchResults) o).getStrMsg();
                    int duration = Toast.LENGTH_SHORT;

                    if(((SMSSearchResults) o).isSelected()){
                        ((SMSSearchResults) o).setSelected(false);
                        chk.setChecked(false);
                        Log.d("TODO", "Added searchresult to list");
                    } else {
                        ((SMSSearchResults) o).setSelected(true);
                        chk.setChecked(true);
                        Log.d("TODO","Added searchresult to list");
                        selectedList.add((SMSSearchResults)o);
                    }
                    //dbhelper.insertTODO(((SMSSearchResults) o).getStrAddr(),
                    //        ((SMSSearchResults) o).getStrMsg());
                    Toast.makeText(contextForToast, text, duration).show();
                }
            });
        }*/
       /* if (v== btnDraft){
            ArrayList <SMSSearchResults> todoList = dbhelper.getAllTODO();
            Log.d("TODO", String.valueOf(todoList.size()));
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, "TODO : " + String.valueOf(todoList.size()), duration).show();
            lvMsg.setAdapter(new MyCustomBaseAdapter(this, todoList));
        }
        if (v == btnSent){
            dbhelper.cleanTODO();
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, "TODO list cleaned", duration).show();
        }
        if (v == btnTODO){
            selectedList = myCustomBaseAdapter.getList();
            for(SMSSearchResults result : selectedList){
                if(result.isSelected()){
                    dbhelper.insertTODO(result.getStrAddr(),result.getStrMsg());
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_SHORT;
                    Toast.makeText(context, "TODO inserted ::" + result.getStrAddr()
                            + " select " + result.isSelected(), duration).show();
                }
            }

        }*/

    }

    private ArrayList<SMSSearchResults> getSMSSearchResults(){
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
                listSMSSearchResults.add(smsSearchResults);
            }
        }
        return listSMSSearchResults;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        /*if(item.equals("Message")) {
            listResults = getSMSSearchResults();
            selectedList = new ArrayList<SMSSearchResults>();
            final CheckBox chk = (CheckBox)view.findViewById(R.id.msgcheck);
            myCustomBaseAdapter = new MyCustomBaseAdapter(this,listResults);
            lvMsg.setAdapter(myCustomBaseAdapter);
            lvMsg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Object o = lvMsg.getItemAtPosition(position);
                    SMSSearchResults fullObject = (SMSSearchResults) o;
                    Context contextForToast = getApplicationContext();
                    CharSequence text = "Inserted Addr ::" + ((SMSSearchResults) o).getStrAddr() +
                            " :: Message :: " + ((SMSSearchResults) o).getStrMsg();
                    int duration = Toast.LENGTH_SHORT;

                    if(((SMSSearchResults) o).isSelected()){
                        ((SMSSearchResults) o).setSelected(false);
                        chk.setChecked(false);
                        Log.d("TODO", "Added searchresult to list");
                    } else {
                        ((SMSSearchResults) o).setSelected(true);
                        chk.setChecked(true);
                        Log.d("TODO","Added searchresult to list");
                        selectedList.add((SMSSearchResults)o);
                    }
                    //dbhelper.insertTODO(((SMSSearchResults) o).getStrAddr(),
                    //        ((SMSSearchResults) o).getStrMsg());
                    Toast.makeText(contextForToast, text, duration).show();
                }
            });
        }*/
        //TODO has to be made by swping right to left
        if (item.equals("Message")){
           Intent intent = new Intent(getApplicationContext(), SwipeableListActivity.class);
           startActivity(intent);
        }
        if (item.equals("TODO")){
            ArrayList <SMSSearchResults> todoList = dbhelper.getAllTODO();
            Log.d("TODO", String.valueOf(todoList.size()));
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, "TODO : " + String.valueOf(todoList.size()), duration).show();
            lvMsg.setAdapter(new MyCustomBaseAdapter(this, todoList));
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
