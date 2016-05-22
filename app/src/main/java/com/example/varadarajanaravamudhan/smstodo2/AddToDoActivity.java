package com.example.varadarajanaravamudhan.smstodo2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by varadarajanaravamudhan on 22/05/16.
 */
public class AddToDoActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Button addTODO;
    Button cleanTODO;
    TextView todoItem;
    TextView todoDate;
    TextView todoWhom;
    MyDBHelper dbhelper;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtodoitem);
        Spinner spin_home4 = (Spinner) findViewById(R.id.home_spinner4);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.TopLevelMenu3, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_home4.setAdapter(adapter);
        spin_home4.setOnItemSelectedListener(this);

        todoItem = (TextView)findViewById(R.id.todoText);
        todoDate = (TextView)findViewById(R.id.dateText);
        todoWhom = (TextView)findViewById(R.id.forWhom);
        addTODO = (Button)findViewById(R.id.btnSubmit);

        addTODO.setOnClickListener(this);

        cleanTODO = (Button)findViewById(R.id.btnCancel);
        cleanTODO.setOnClickListener(this);

        dbhelper = new MyDBHelper(this);
    }

    @Override
    public void onClick(View v) {
        String strTodo = todoItem.getText().toString();
        String strDate = todoDate.getText().toString();
        String strWhom = todoWhom.getText().toString();
        dbhelper.insertTODO(strWhom,strTodo);
        Toast.makeText(getApplicationContext(), strTodo, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        if (item.equals("TODO")){
            Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
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
        if (item.equals("Home")){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
