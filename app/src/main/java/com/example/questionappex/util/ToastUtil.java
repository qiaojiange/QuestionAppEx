package com.example.questionappex.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.provider.Settings;
import android.text.StaticLayout;
import android.widget.Toast;

/**
 * Created by qiaojiange on 2017/3/1.
 */

public class ToastUtil {

    private static String oldMsg;
    private static Toast toast=null;
    private static long oneTime = 0;
    private static  long twoTime = 0;
    public static void showToast(Context context,String s){
        if(toast ==null){
            oldMsg =s ;
            toast = Toast.makeText(context,s,Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        }else{
            twoTime = System.currentTimeMillis();
            if(s.equals(oldMsg)){
                if( (twoTime - oneTime)>Toast.LENGTH_SHORT){
                    toast.show();
                }
            }else{
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }
        oneTime = twoTime;
    }


}
