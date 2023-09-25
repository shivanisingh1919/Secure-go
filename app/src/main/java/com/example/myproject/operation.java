package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class operation extends AppCompatActivity {
    Button encryption,decrytion,history;
    TextView slogan,slogan1;
    ImageView logout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);

        slogan=findViewById(R.id.slogan);
        encryption=findViewById(R.id.encryption);
        decrytion=findViewById(R.id.decryption);
        slogan1=findViewById(R.id.slogan1);
        history=findViewById(R.id.History);
        logout=findViewById(R.id.logOut);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.reset();
                startActivity(new Intent(getApplicationContext(),home.class));
                finish();
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),history.class);
                startActivity(i);
            }
        });

        encryption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(getApplicationContext(),browseimage.class);
                startActivity(i);

            }
        });
        decrytion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),decryption.class);
                startActivity(i);
            }
        });
    }
    
}
