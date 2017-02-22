package com.example.questionappex;

import android.support.test.runner.AndroidJUnit4;

import com.example.questionappex.model.Question;
import com.example.questionappex.util.DBUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String TAG = "ExampleInstrumentedTest";
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getTargetContext();
//
//        assertEquals("com.example.questionappex", appContext.getPackageName());

//        DBUtil dbUtil = new DBUtil();
//        Log.d(TAG, "useAppContext: count = "+dbUtil.getCount(1,1));

//        Question question = DBUtil.newInstance().query(1,1,1);
//        System.out.println(question);



        Question question = new Question();
        question.setCollect(1);
        question.setError(0);
        question.setTitleId("000001");
        question.setHasDo(0);
        question.setDelete(0);


        DBUtil.newInstance().updateQuestion(question);
        Question question1 = DBUtil.newInstance().query(1,1,1);
        System.out.println(question1);


//        Log.d(TAG, "useAppContext: "+question);
    }
}
