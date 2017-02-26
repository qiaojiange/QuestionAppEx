package com.example.questionappex.ui;

import android.widget.Toast;

import com.example.questionappex.model.Question;
import com.example.questionappex.setting.Setting;
import com.example.questionappex.util.DBUtil;
import com.example.questionappex.util.SPUtil;

import java.util.Collections;
import java.util.List;

/**
 * Created by qiaojiange on 2017/2/26.
 */

//  用于遍历题目
public class QuestionUtil {
    private List<String> list;

    int currIndex = 0;
    String currIndexStr = "";

//    如果是从setting界面进入的就应该使用setting的设置，如果是从统计信息进入的就应该使用统计中的设置进行加载题目id
//    这点写的不对，之后要进行修改

    public void loadQuestion(String id) {
        list = DBUtil.newInstance().queryAllTitleID(id);
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
                Collections.shuffle(list);
                isFirstShuffle = true;
            }
        }
        if (currIndex > 0) {
            currIndex--;
            return DBUtil.newInstance().query(list.get(currIndex));
        } else {
            Toast.makeText(MyApplication.getContext(), "这是第一题，不能往下翻了", Toast.LENGTH_SHORT).show();
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
        if (currIndex < list.size()) {
            currIndex++;
            return DBUtil.newInstance().query(list.get(currIndex));
        } else {
            Toast.makeText(MyApplication.getContext(), "这是最后一道题，不能往下翻了", Toast.LENGTH_SHORT).show();
        }
        return null;
    }

}
