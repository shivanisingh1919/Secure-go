package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.sql.Date;

public class history extends AppCompatActivity {
    SQLiteDatabase transactions;
    Cursor values;
    TextView display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        display=findViewById(R.id.DIsplayHistory);
        transactions=openOrCreateDatabase("securego", Context.MODE_PRIVATE,null);
        String sql="select operation, operationtime ,imgsrc,email from transactioninfo where email='"+session.mail+"'";
        values=transactions.rawQuery(sql,null);

        String date;
        Date d;
        while(values.moveToNext()){

            display.append(values.getString(0)+" performed on "+values.getString(1)+"   of   "+values.getString(2) +"  by  "+values.getString(3)+"\n\n");
        }


    }
}
