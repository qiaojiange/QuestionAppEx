package com.example.questionappex.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

import com.example.questionappex.setting.Setting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by qiaojiange on 2017/2/18.
 */

public class CopyDatabase {
    private static final String TAG = "CopyDatabase";
    private static final String filePath  = "data/data/com.example.questionappex/question.db";



    public static SQLiteDatabase openDatabase(Context context){
        File file = new File(filePath);
        if( Setting.DatabaseVersion>5){
            return SQLiteDatabase.openDatabase(filePath,null,SQLiteDatabase.OPEN_READWRITE);
        }else{
            if(file.exists()){
                file.delete();
            }
            //得到资源
            AssetManager assetManager = context.getAssets();
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                 is = assetManager.open("question.db");
                 fos = new FileOutputStream(file);

                byte[] b = new byte[1024];
                int len = 0;
                while((len = is.read(b))>-1){
                    fos.write(b,0,len);
                }
                is.close();
                fos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }finally {

                    try {
                        if(is!=null){
                            is.close();
                        }
                        if(fos!=null){
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            return SQLiteDatabase.openOrCreateDatabase(file,null);
        }
    }


}
