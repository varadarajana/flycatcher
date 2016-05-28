package com.example.varadarajanaravamudhan.smstodo2;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by varadarajanaravamudhan on 23/05/16.
 */
public class ManageTODOActivity extends AbstractSwipeListActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener{

    Button btnAddTODO;
    TextView lblMsg, lblNo;
    ListView lvMsg;
    MyDBHelper dbhelper;
    MyCustomBaseAdapter myCustomBaseAdapter;


    SimpleCursorAdapter adapter;
    ArrayList<SMSSearchResults> listResults;
    ArrayList<SMSSearchResults> selectedList;
    ArrayList<SMSSearchResults> todoList;
    //private ListView mListView;

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
        // Init GUI Widget
        //ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, smslist);
        //ListView listView = (ListView)findViewById(R.id.lvMsg);
        //listView.setAdapter(adapter);
        todoList = dbhelper.getAllTODO();
        Log.d("TODO", String.valueOf(todoList.size()));
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(context, "TODO : " + String.valueOf(todoList.size()), duration).show();
        lvMsg.setAdapter(new MyCustomBaseAdapter(this, todoList));

    }


    @Override
    public ListView getListView() {
        return lvMsg;
    }

    @Override
    public void getSwipeItem(boolean isRight, int position) {
        Toast.makeText(this,
                "Swipe to " + (isRight ? "right" : "left") + " direction",
                Toast.LENGTH_SHORT).show();

        SMSSearchResults result = (SMSSearchResults)lvMsg.getItemAtPosition(position);
        //int todoList.lastIndexOf(result);
        if(isRight) {
            //view.setBackgroundColor(Color.BLUE);
        }
    }

    @Override
    public void onItemClickListener(ListAdapter adapter, int position) {
        Toast.makeText(this, "Single tap on item position " + position,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
