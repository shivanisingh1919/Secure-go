package com.example.myproject;


import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;

abstract class session extends AppCompatActivity {
    static String mail;
    static String source;
    static String destination;
    static String output;
    static String key;

    static void reset(){
        source=null;
        destination=null;
        output=null;
        key=null;
        mail=null;
    }
    static boolean strongkeyvalid(String key)
    {
        boolean upper=false , lower=false , num=false ,sym=false;
        if(key.length()>7 && key.length()<21){
            char ch;
            for(int i=0;i<key.length();i++){
                ch=key.charAt(i);
                if(ch>='a' && ch<='z'){
                    lower=true;
                    continue;
                }
                if(ch>='A' && ch<='Z'){
                    upper=true;
                    continue;
                }
                if(ch>='0' && ch<='9'){
                    num=true;
                    continue;
                }
                else{
                    sym=true;
                    continue;
                }
            }
        }
        if(upper && lower && num && sym)
            return true;
        return false;
    }
}