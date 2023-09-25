package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {
    Button submit;
    EditText enteremail,enterpassword;

    SQLiteDatabase db;
    Cursor cr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
     submit=findViewById(R.id.submit);
     enteremail=findViewById(R.id.enteremail);
     enterpassword=findViewById(R.id.enterpassword);

        db=openOrCreateDatabase("securego", Context.MODE_PRIVATE,null);
        String createquery = "CREATE TABLE IF NOT EXISTS transactioninfo(email varchar(15) NOT NULL ,operationtime datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,operation varchar NOT NULL,encdeckey varchar NOT NULL,imgsrc varchar NOT NULL)";
        db.execSQL(createquery);


     submit.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             String enteremailvalue=enteremail.getText().toString();

             if(TextUtils.isEmpty(enteremailvalue))
             {
                 enteremail.setError("please enter email");
             }
             String enterpasswordvalue=enterpassword.getText().toString();
             if(TextUtils.isEmpty(enterpasswordvalue))
             {
                 enterpassword.setError("required");
             }
             try {
                 cr = db.rawQuery("select email, password from userdata", null);
                 String pass, user;
                 int c = 0;
                 boolean flag = true;
                 while (cr.moveToNext()) {
                     user = cr.getString(0);
                     pass = cr.getString(1);
                     c++;
                     if (pass.equals(enterpassword.getText().toString()) && user.equals(enteremail.getText().toString())) {
                         Intent i = new Intent(getApplicationContext(), operation.class);
                         session.mail = enteremailvalue;

                         flag = false;
                         startActivity(i);
                         finish();
                     }
                 }
                 if (c == 0) {
                     Toast.makeText(getApplicationContext(), " No such account exists ", Toast.LENGTH_SHORT).show();

                 }
                 if (flag) {
                     Toast.makeText(getApplicationContext(), " Enter correct details ", Toast.LENGTH_SHORT).show();
                 }
             }
            catch (SQLException e){
                 Toast.makeText(getApplicationContext(),"No Such account exists! Please register",Toast.LENGTH_LONG).show();
            }
         }
     });
    }
}
