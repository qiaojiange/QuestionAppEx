package com.example.questionappex.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiaojiange on 2017/2/17.
 */

public class Question {

    public static final String TYPE="TYPE";
    public static final int TYPE_SINGLE = 1;//单选
    public static final int TYPE_MUTIL = 2;//多选
    public static final int TYPE_CHECKING = 3;//判断
//    public static final int TYPE_PIC = 4;//识图
//    public static final int TYPE_QA = 5;//简答


    public static final String GRADE = "GRADE";

    public static final int GRADE_1 = 1;
    public static final int GRADE_2 = 2;
    public static final int GRADE_3 = 3;
    public static final int GRADE_4 = 4;
    public static final int GRADE_5 = 5;

    public static final String F_GRADE = "f_grade";
    public static final String F_DELETE = "f_delete";
    public static final String F_DO = "f_do";
    public static final String F_COLLECT = "f_collect";
    public static final String F_ERROR = "f_error";
    public static final String F_TITLEID ="f_titleID";
//    public static final String F_ALL = "f_all";
    public static final String F_EXPLAIN = "f_explain";
    public static final String F_TITLE = "f_title";


    public static final String f_TYPE = "f_type";
    public static final String F_RESULT = "f_result";

//分类
//    int category;



    String titleId;
    //    标题
    String title;
    int type;
    String explain;
    int grade;
    int error;
    int collect;
    int hasDo;
    int delete;
    String result;

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    /**
     * 问题对应的选项
     */
    List<Choice> choices;



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }



    public String getTitleId() {
        return titleId;
    }


    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }




    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }



    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

    public int getHasDo() {
        return hasDo;
    }

    public void setHasDo(int hasDo) {
        this.hasDo = hasDo;
    }

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "Question{" +
                "titleId='" + titleId + '\'' +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", explain='" + explain + '\'' +
                ", grade=" + grade +
                ", error=" + error +
                ", collect=" + collect +
                ", hasDo=" + hasDo +
                ", delete=" + delete +
                ", result='" + result + '\'' +
                ", choices=" + choices +
                '}';
    }
}
