package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class keycreation extends AppCompatActivity {
    EditText enterkey,enterconfirmkey;
    Button proceed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keycreation);
        enterkey=findViewById(R.id.enterkey);
        enterconfirmkey=findViewById(R.id.enterconfirmkey);
        proceed =findViewById(R.id.proceed);

       proceed.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              String enterkeyvalue=enterkey.getText().toString();
              boolean error=false;
               if(TextUtils.isEmpty(enterkeyvalue))
               {
                   enterkey.setError("please enter a key");
                   error=true;
               }
               if(!(session.strongkeyvalid(enterkeyvalue)))
               {
                   enterconfirmkey.setError("please enter  a strong key");
                   error=true;
               }
               String enterconfirmkeyvalue=enterconfirmkey.getText().toString();

               if(TextUtils.isEmpty(enterconfirmkeyvalue)){
                   enterconfirmkey.setError(" Please confirm key value");
                   error=true;
               }

               if(!(enterkeyvalue.equals(enterconfirmkeyvalue)))
               {
                   enterconfirmkey.setError("keys don't match");
                   error=true;
               }

               if(!error)
               {
                   Intent i=new Intent(getApplicationContext(),encend.class);
                   session.key=enterconfirmkeyvalue;
                   startActivity(i);
                   finish();
               }
           }
       });


    }
}
