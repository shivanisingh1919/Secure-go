package com.example.myproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class tabLogin extends AppCompatActivity {
    EditText entername,rgsenteremail,enterphoneno,rgsenterpassword,enterconfirmpassword,entercompanyname;
    Spinner countryspinner;
    Button rgssubmit;
    SQLiteDatabase db;
    Cursor cr;
    String countrynames[]={"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe"};

    Button submit;
    EditText enteremail,enterpassword;

    TabHost thb;
    TabHost.TabSpec login,register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_tab_login);


        //tabhost
        thb = findViewById(android.R.id.tabhost);
        thb.setup();
        login=thb.newTabSpec("Login");
        login.setIndicator("Login");
        login.setContent(R.id.login);
        thb.addTab(login);
        register=thb.newTabSpec("Register");
        register.setIndicator("Register");
        register.setContent(R.id.register);
        thb.addTab(register);
        thb.setCurrentTab(1);


        //login
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



        //REGISTER
        final ArrayAdapter<String> countrya=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,countrynames);
        entercompanyname=findViewById(R.id.entercompanyname);
        enterphoneno=findViewById(R.id.enterphoneno);
        entername=findViewById(R.id.entername);
        rgsenteremail=findViewById(R.id.rgsenteremail);
        rgsenterpassword=findViewById(R.id.rgsenterpassword);
        enterconfirmpassword=findViewById(R.id.enterconfirmpassword);
        rgssubmit=findViewById(R.id.rgssubmit);
        countryspinner=findViewById(R.id.countryspinner);
        countryspinner.setAdapter(countrya);



        db = openOrCreateDatabase("securego", Context.MODE_PRIVATE, null);
        String createtablequery = "CREATE TABLE IF NOT EXISTS userdata(email varchar(15) NOT NULL , username Varchar(15) NOT NULL, password Varchar(20) NOT NULL,phoneno int(14) NOT NULL,companyname varchar (20)NOT NULL,country varchar(15) NOT NULL,signupdate datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,PRIMARY KEY(email) )";
        db.execSQL(createtablequery);


        rgssubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailvalue=rgsenteremail.getText().toString().trim();
                boolean isDataValid=true;
                if(TextUtils.isEmpty(emailvalue))
                {
                    rgsenteremail.setError("please enter email");
                    isDataValid=false;
                }
                if(emailvalid(rgsenteremail.getText().toString())==false)
                {
                    isDataValid=false;
                    rgsenteremail.setError("kindly enter correct email");
                }
                String passwordvalue=enterconfirmpassword.getText().toString().trim();
                if(TextUtils.isEmpty(passwordvalue))
                {
                    rgsenterpassword.setError("please enter password");
                    isDataValid=false;
                }
                if(!(rgsenterpassword.getText().toString().equals(enterconfirmpassword.getText().toString())))
                {
                    isDataValid=false;
                    enterconfirmpassword.setError("password not matches");
                }

                String countryvalue=countryspinner.getSelectedItem().toString();

                String usernamevalue=entername.getText().toString();
                if(TextUtils.isEmpty(usernamevalue))
                {
                    isDataValid=false;
                    entername.setError("please enter username");
                }

                String companynamevalue=entercompanyname.getText().toString();
                if(TextUtils.isEmpty(companynamevalue))
                {
                    isDataValid=false;
                    entercompanyname.setError("please enter companyname");
                }
                String phoneno=enterphoneno.getText().toString();
                if(TextUtils.isEmpty(phoneno))
                {
                    isDataValid=false;
                    enterphoneno.setError("please enter phone no");
                }
                if (!session.strongkeyvalid(rgsenterpassword.getText().toString())){
                    isDataValid=false;
                    rgsenterpassword.setError("Password is too weak");
                }
                if(isDataValid){
                    String q2 ="INSERT INTO userdata(email,username,password,phoneno,companyname,country) VALUES('" + emailvalue + "','" + usernamevalue + "','" + passwordvalue + "'," + phoneno + ",'"+companynamevalue+"','"+countryvalue+"')";
                    db.execSQL(q2);
                    thb.setCurrentTab(0);
                    //Intent i = new Intent(getApplicationContext(),login.class);
                    //startActivity(i);
                    //finish();
                }
            }
        });

    }
    public Boolean emailvalid(String emailid)
    {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if( emailid.matches(regex))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
