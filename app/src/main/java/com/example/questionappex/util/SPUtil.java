package com.example.questionappex.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.questionappex.ui.MyApplication;

import java.util.Set;

/**
 * Created by qiaojiange on 2017/2/22.
 */

public class SPUtil {
    private static  SharedPreferences sp = MyApplication.getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
    private static SharedPreferences.Editor editor = sp.edit();



    public static boolean contains(String key){
        return sp.contains(key);
    }


    public static void  putString(String key,  String value){
        editor.putString(key,value);
        editor.commit();
    }

    public static String getString(String key,String value){
        return sp.getString(key,value);
    }


    public static void  putStringSet(String key,  Set<String> values){
        editor.putStringSet(key,values);
        editor.commit();
    }

    public static Set<String> getStringSet(String key,Set<String> values){
        return sp.getStringSet(key,values);
    }

    public static void  putInt(String key, int value){
        editor.putInt(key,value);
        editor.commit();
    }

    public static int getInt(String key,int value){
        return sp.getInt(key,value);
    }



    public static void  putBoolean(String key, boolean value){
        editor.putBoolean(key,value);
        editor.commit();
    }


    public static boolean getBoolean(String key, boolean defValue){
        return sp.getBoolean(key,defValue);
    }





}
