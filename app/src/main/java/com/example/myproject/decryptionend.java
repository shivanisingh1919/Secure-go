package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.io.File;

public class decryptionend extends AppCompatActivity {
    ImageView decryptedimg;
    Button back;
    Intent i = this.getIntent();
    SQLiteDatabase db;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decryptionend);
        db=openOrCreateDatabase("securego", Context.MODE_PRIVATE,null);
        decryptedimg=findViewById(R.id.decryptedimage);
        back=findViewById(R.id.back);
        pb=findViewById(R.id.decpb);



        try{
            pb.setVisibility(View.VISIBLE);
            //session.decrypt();
            if(!Python.isStarted()){
                Python.start(new AndroidPlatform(getApplicationContext()));
            }
            Python pyth=Python.getInstance();
            PyObject pyo=pyth.getModule("SecurePyth.decrypt");
            PyObject result=pyo.callAttr("decrypt",session.source,session.key);
            session.output=result.toString();
            if(session.output.equals("error") || session.output.equals("file:///error")){
                session.output="";
                throw new Exception("error");
            }
            else {
                decryptedimg.setImageURI(Uri.fromFile(new File(session.output)));
                db = openOrCreateDatabase("securego", Context.MODE_PRIVATE, null);
                String operationvalue = "decryption";
                String decinsert = "INSERT INTO transactioninfo(email,operation,encdeckey,imgsrc) VALUES('" + session.mail + "','" + operationvalue + "','" + session.key + "','" + session.output + "')";
                db.execSQL(decinsert);
            }
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"An error has occurred! Please check the key or the image!",Toast.LENGTH_SHORT).show();
            //e.printStackTrace();
            startActivity(new Intent(getApplicationContext(),home.class));
        }
        finally {
            session.reset();
            pb.setVisibility(View.GONE);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i=new Intent(getApplicationContext(),tabLogin.class);
                startActivity(i);
                finish();
            }
        });
    }
}
