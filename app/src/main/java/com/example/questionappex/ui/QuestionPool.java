package com.example.questionappex.ui;

import android.widget.Toast;

import com.example.questionappex.model.Question;
import com.example.questionappex.setting.Setting;
import com.example.questionappex.util.DBUtil;
import com.example.questionappex.util.LogUtil;
import com.example.questionappex.util.SPUtil;

import java.util.Collections;
import java.util.List;

/**
 * Created by qiaojiange on 2017/2/26.
 */

//  用于遍历题目
public class QuestionPool {
    private List<String> list;

    int currIndex = 0;
    String currIndexStr = "";
    private static final String TAG = "----QuestionPool----";

//    如果是从setting界面进入的就应该使用setting的设置，如果是从统计信息进入的就应该使用统计中的设置进行加载题目id

    public void loadQuestion(String id) {
        String strId;
        if(id.equals("")){
            int grade = SPUtil.getInt(Question.GRADE,Question.GRADE_1);
            int type = SPUtil.getInt(Question.TYPE,Question.TYPE_SINGLE);
//          根据题目类型、等级，获取第一道题
           strId= DBUtil.newInstance().getFirstId(type,grade);
        }else {
            strId = id;
        }
        list = DBUtil.newInstance().queryAllTitleID(strId);
        currIndex = 0;

    }


    boolean isFirstShuffle = false;

    public Question prev() {
        int parttern = SPUtil.getInt(Setting.PATTERN, Setting.ORDER_PATTERN);
        //顺序答题，不用打乱list集合中的数据
        if (parttern == Setting.ORDER_PATTERN) {
//          随机答题，打乱list集合中的数据
        } else if (parttern == Setting.RANDOM_PATTERN) {
            if (!isFirstShuffle) {
                //将list中的顺序打乱
//                Collections.shuffle(list);
                isFirstShuffle = true;
            }
        }
        if (currIndex > 0) {
            currIndex--;
            Question question = DBUtil.newInstance().query(list.get(currIndex));
            return question;
        } else {
            Toast.makeText(MyApplication.getContext(), "亲，这是第一题，不能往上翻了", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public Question next() {
        int parttern = SPUtil.getInt(Setting.PATTERN, Setting.ORDER_PATTERN);
        //顺序答题，不用打乱list集合中的数据
        if (parttern == Setting.ORDER_PATTERN) {
//          随机答题，打乱list集合中的数据
        } else if (parttern == Setting.RANDOM_PATTERN) {
            if (!isFirstShuffle) {
                //将list中的顺序打乱
                Collections.shuffle(list);
                isFirstShuffle = true;
            }
        }

        if ( (currIndex+1) < list.size()) {
            currIndex++;
            Question question = DBUtil.newInstance().query(list.get(currIndex));
            return question;
        } else {
            Toast.makeText(MyApplication.getContext(), "亲，这是最后一道题，不能往下翻了", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

}
