package com.leovaldez42.safety;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    
    Button b1, b2, b3;
    EditText e1;
    ListView listView;
    SQLiteOpenHelper s1;
    SQLiteDatabase sqlitedb;
    DataBaseHandler myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        e1 = findViewById(R.id.reg_editxt_phone);
        b1 = findViewById(R.id.reg_btn_add);
        b2 = findViewById(R.id.reg_btn_del);
        b3 = findViewById(R.id.reg_btn_view);
        
        myDB = new DataBaseHandler(this);
        
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sr = e1.getText().toString();
                addData(sr);
                Toast.makeText(RegisterActivity.this, "Data added", Toast.LENGTH_SHORT).show();
                e1.setText("");
            }
        });
        
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlitedb = myDB.getWritableDatabase();
                String x = e1.getText().toString();
                DeleteData(x);
                Toast.makeText(RegisterActivity.this, "Data deleted", Toast.LENGTH_SHORT).show();
            }
        });
        
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });
        
        
    }

    private void loadData() {
        ArrayList<String> theList = new ArrayList<>();
        Cursor data = myDB.getListContents();
        if(data.getCount() == 0) {
            Toast.makeText(RegisterActivity.this, "", Toast.LENGTH_SHORT).show();
        } else {
            while (data.moveToNext() ) {
                theList.add(data.getString(1));
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, theList);
                listView.setAdapter(listAdapter);
            }
        }
    }

    private boolean  DeleteData(String x) {
        return sqlitedb.delete(DataBaseHandler.TABLE_NAME, DataBaseHandler.COL2 + "=?", new String[]{x}) > 0; 
    }

    private void addData(String newEntry) {
        boolean insertData = myDB.addData(newEntry);

        if(insertData) {
            Toast.makeText(this, "Data Added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }
}