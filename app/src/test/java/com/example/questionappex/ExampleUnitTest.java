package com.example.questionappex;

import android.util.Log;

import com.example.questionappex.util.DBUtil;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private static final String TAG = "ExampleUnitTest";

    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
//        DBUtil dbUtil = new DBUtil();
//        Log.d(TAG, "useAppContext: count = "+dbUtil.getCount(1,1));
//        String id = "000377";

        String id = "000001";
        String subString =id.substring( id.lastIndexOf("0")+1);
        System.out.println(Integer.parseInt(subString));
//        Log.d(TAG, "useAppContext: -----"+Integer.parseInt(subString));
    }
}