package com.example.myproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class decryption extends AppCompatActivity {
    ImageView img;
    Button next;
    String ImageLocation;
    static int reqcode=1;
    static  int REQUESTCODE=1;
    Uri pickedimageurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decryption);
        img=findViewById(R.id.decimage);
        next=findViewById(R.id.next);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >=22)
                {
                    checkandrequestpermission();
                }
                else
                {
                    opengallery();

                }


            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pickedimageurl == null)
                {
                    Toast.makeText(getApplicationContext(),"please browse image first",Toast.LENGTH_SHORT).show();
                }
                else {
                    session.source= ImageLocation;
                    Intent i = new Intent(getApplicationContext(), decryptionkey.class);
                    startActivity(i);
                }
            }
        });

    }

    private void opengallery() {
        //to do opengallery intent and wait for user to pick image
        Intent galleryintent= new Intent(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,REQUESTCODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUESTCODE && data!=null)
        {
              pickedimageurl = data.getData();
              try{
                  img.setImageURI(pickedimageurl);
              }
              catch(RuntimeException e) {}
              String[] filePathColumn = { MediaStore.Images.Media.DATA };

               Cursor cursor = getContentResolver().query(pickedimageurl,
                       filePathColumn, null, null, null);
               cursor.moveToFirst();

               int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
               ImageLocation = cursor.getString(columnIndex);
               cursor.close();
        }
    }

    private void checkandrequestpermission() {
        if (ContextCompat.checkSelfPermission(decryption.this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED)
        {
         if (ActivityCompat.shouldShowRequestPermissionRationale(decryption.this,Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                Toast.makeText(getApplicationContext(),"PLEASE ACCEPT THE REQUIRED PERMISSION ",Toast.LENGTH_SHORT).show();
            }
         else
         {
            ActivityCompat.requestPermissions(decryption.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},reqcode);
         }
        }

        else
        {
            opengallery();
        }
    }
}
