package com.example.questionappex;

import android.support.test.runner.AndroidJUnit4;

import com.example.questionappex.model.Question;
import com.example.questionappex.setting.Setting;
import com.example.questionappex.util.DBUtil;
import com.example.questionappex.util.SPUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

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


//        Question question = new Question();
//        question.setCollect(1);
//        question.setError(0);
//        question.setTitleId("000001");
//        question.setHasDo(0);
//        question.setDelete(0);
//
//
//        DBUtil.newInstance().updateQuestion(question);
//        Question question1 = DBUtil.newInstance().query(1,1,1);
//        System.out.println(question1);

//        SPUtil.putBoolean("isRemeber",false);
//        System.out.println(SPUtil.getBoolean("isRemeber",true));
//        SPUtil.putInt("test1",10);
//        System.out.println("test1: "+SPUtil.getInt("test1",4));


//        int num = DBUtil.newInstance().countEveryStatistic(Setting.Statistics.HaveDoQuestion, 1);
//        System.out.println(num);
//
//        num = DBUtil.newInstance().countEveryStatistic(Setting.Statistics.CollectionQuestion, 2);
//        System.out.println(num);
//
//        num = DBUtil.newInstance().countEveryStatistic(Setting.Statistics.DeleteQuestion, 3);
//        System.out.println(num);
//
//        num = DBUtil.newInstance().countEveryStatistic(Setting.Statistics.ErrorQuestion, 4);
//        System.out.println(num);
//
//        num = DBUtil.newInstance().countEveryStatistic(Setting.Statistics.NoDoQuestion, 1);
//        System.out.println(num);
//        Log.d(TAG, "useAppContext: "+question);


//        List<String> list = DBUtil.newInstance().statistics(Setting.Statistics.NoDoQuestion,1);
//        System.out.println(list.size());


        List<String> list = DBUtil.newInstance().query("000005");
        System.out.println(list.size());
    }
}
