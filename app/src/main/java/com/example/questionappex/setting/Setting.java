package com.example.questionappex.setting;

import com.example.questionappex.util.SPUtil;

/**
 * Created by qiaojiange on 2017/2/18.
 */
//设置
public class Setting {
//    public static int type1Start
//    将次参数改为大于5，就不用每次复制数据库文件
    public static final  int  DatabaseVersion = 1;

    //答题模式，是顺序还是随机
    public static final String PATTERN= "pattern";
    public static final int ORDER_PATTERN = 0;
    public static final int RANDOM_PATTERN = 1;


//    当前索引
    public static final String CurrentIndex ="currentIndex";
    public static String currentIndex = SPUtil.getString(Setting.CurrentIndex,"000001");



//    统计信息
    public static enum  Statistics{
        ErrorQuestion,HaveDoQuestion,CollectionQuestion,DeleteQuestion,NoDoQuestion;
    }

}
