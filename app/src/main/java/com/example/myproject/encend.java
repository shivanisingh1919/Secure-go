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
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.io.File;

public class encend extends AppCompatActivity {
    ProgressBar pb;
    ImageView img;
    SQLiteDatabase db;
    TextView slogan;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encend);
        img=findViewById(R.id.encryptedimg);
        pb=findViewById(R.id.pb);
        slogan=findViewById(R.id.slogan);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),home.class));
                finish();
            }
        });
        try{
            pb.setVisibility(View.VISIBLE);
            //session.encrypt();
            if(!Python.isStarted()){
                Python.start(new AndroidPlatform(getApplicationContext()));
            }
            Python pyth=Python.getInstance();
            PyObject pyo=pyth.getModule("SecurePyth.encrypt");
            PyObject result=pyo.callAttr("encrypt",session.destination,session.source,session.key);
            session.output=result.toString();
            if(session.output.equals("error") || session.output.equals("file:///error")) {
                session.output = "";
                throw new Exception("error");
            }
            img.setImageURI( Uri.fromFile(new File(session.output)));
            db=openOrCreateDatabase("securego", Context.MODE_PRIVATE,null);
            String operationvalue="encryption";
            String insertquery ="INSERT INTO transactioninfo(email,operation,encdeckey,imgsrc) VALUES('" + session.mail + "','" + operationvalue + "','" + session.key + "','"+session.output+"')";
            db.execSQL(insertquery);
            slogan.setText("Encryption successful!\n The Encryption has been saved \nto your gallery");
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),"An error has occurred! Please check the image!"+e,Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(),tabLogin.class));
            finish();
        }
        finally {
            session.reset();
            pb.setVisibility(View.GONE);
        }
    }
}
