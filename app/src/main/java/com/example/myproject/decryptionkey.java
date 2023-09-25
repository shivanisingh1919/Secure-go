package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class decryptionkey extends AppCompatActivity {
    EditText enterdecryptionkey;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decryptionkey);

        enterdecryptionkey=findViewById(R.id.enterdecryptionkey);
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enterdecryptionkeyvalue=enterdecryptionkey.getText().toString();
                if(TextUtils.isEmpty(enterdecryptionkeyvalue))
                {
                    enterdecryptionkey.setError("please enter a key");
                }
                else{
                    session.key=enterdecryptionkeyvalue;
                    Intent i=new Intent(getApplicationContext(),decryptionend.class);
                    startActivity(i);
                }
            }
        });
    }
}
