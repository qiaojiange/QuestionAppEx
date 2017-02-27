package com.example.questionappex.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.questionappex.model.Choice;
import com.example.questionappex.model.Question;
import com.example.questionappex.setting.Setting;
import com.example.questionappex.setting.Setting.Statistics;
import com.example.questionappex.ui.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiaojiange on 2017/2/17.
 */

public class DBUtil {
//    public static SQLiteDatabase db = ;
    private SQLiteDatabase db;
    private IDBInfo idbInfo;

    private DBUtil() {
        this.db = CopyDatabase.openDatabase(MyApplication.getContext());
    }

    private static DBUtil dbUtil ;

//    双重锁，单例设计模式--懒汉式
    public static DBUtil newInstance(){
        if (dbUtil == null){
            synchronized (DBUtil.class){
                if(null == dbUtil){
                    dbUtil = new DBUtil();
                }
            }
        }
        return dbUtil;
    }

    public void setIdbInfo(IDBInfo idbInfo) {
        this.idbInfo = idbInfo;
    }

    private DBUtil(IDBInfo idbInfo){
        this.db = CopyDatabase.openDatabase(MyApplication.getContext());
    }

    public Question query(int type, int grade, int id) {
        Question question = null;
        //id超出了范围，让用户修改设置
        if(!checkID(type,grade,id)){
            return null;
        }
        String strId = getStringId(id);

        question = getQuestion(strId);
        return question;
    }

    @NonNull
    private Question getQuestion(String strId) {
        Question question;
        question = new Question();
        Cursor cursor = db.rawQuery("select * from tb_title where f_titleID=?", new String[]{strId});
        if (cursor.moveToFirst()) {
            question.setTitleId(cursor.getString(cursor.getColumnIndex(Question.F_TITLEID)));
            question.setTitle(cursor.getString(cursor.getColumnIndex(Question.F_TITLE)));
            question.setType(cursor.getInt(cursor.getColumnIndex(Question.f_TYPE)));
            question.setExplain(cursor.getString(cursor.getColumnIndex(Question.F_EXPLAIN)));
            question.setGrade(cursor.getInt(cursor.getColumnIndex(Question.F_GRADE)));
            question.setError(cursor.getInt(cursor.getColumnIndex(Question.F_ERROR)));


            question.setCollect(cursor.getInt(cursor.getColumnIndex(Question.F_COLLECT)));
            question.setHasDo(cursor.getInt(cursor.getColumnIndex(Question.F_DO)));
            question.setDelete(cursor.getInt(cursor.getColumnIndex(Question.F_DELETE)));
            question.setResult(cursor.getString(cursor.getColumnIndex(Question.F_RESULT)));
        }
        cursor.close();

        if (question != null) {
            List<Choice> choices = new ArrayList<>();
            cursor = db.rawQuery("select * from tb_answer where f_titleID=?", new String[]{question.getTitleId()});
            char ch = 'A'-1;
//            int count = 0;
            if (cursor.moveToFirst()) {
                do {
                    String titleID = cursor.getString(cursor.getColumnIndex(Choice.F_TITLE_ID));
                    ch += 1;
//                    count++;
                    String position = "" + ch;
                    String answer = cursor.getString(cursor.getColumnIndex(Choice.f_ANSWER));

                    Choice choice = new Choice(titleID, position, answer);

                    choices.add(choice);
                } while (cursor.moveToNext());
            }

            question.setChoices(choices);
        }
        return question;
    }


    public int getCount(int type,int grade){
        Cursor cursor = db.rawQuery("select count(distinct f_titleID) as f_count from question_view where f_type=? and f_grade=?",new String[]{""+type,""+grade});
        int count = 0;
        if(cursor.moveToFirst()){
            count = cursor.getInt(cursor.getColumnIndex("f_count"));
        }
        cursor.close();
        return count;
    }

/****
 * 获取每种类型的第一个ID
 */
    public String getFirstId(int type,int grade){
        Cursor cursor = db.rawQuery("select distinct f_titleID from question_view where f_type=? and f_grade=? limit 1",new String[]{""+type,""+grade});
        String firstId ="";
        if(cursor.moveToFirst()){
            firstId = cursor.getString(cursor.getColumnIndex("f_titleID"));
        }
        cursor.close();
        return firstId;
    }

    /***
     * 将整数id转换成String类型的id
     * @param id:整型
     * @return String格式的id
     */
    private String getStringId(int id){
        String string1 = ""+id;
        StringBuilder sb = new StringBuilder();
        if (string1.length()<6){
            int count = 6- string1.length();
            for(int i = 0;i<count;i++){
                sb.append('0');
            }
            sb.append(string1);
        }
        return sb.toString();
    }

    private int getIntegerId(String id){
        String subString =id.substring( id.lastIndexOf("0")+1);
        return Integer.parseInt(subString);
    }

    public  void closeDatabase(){
        if(this.db!=null){
            db.close();
        }
    }

//    判断输入的id是否合法，有没有下一道题
    public boolean checkID(int type, int grade,int id){
        int firstID = getIntegerId(getFirstId(type,grade));
        int count = getCount(type,grade);
        int lastID = count+firstID-1;
        if(id>=firstID && id<=lastID){
            return true;
        }else if(id<firstID){
            Toast.makeText(MyApplication.getContext(),"这是第一道题，前面已经没有了!!!",Toast.LENGTH_SHORT).toString();
        }else if(id>lastID){
            Toast.makeText(MyApplication.getContext(),"亲，这是最后一道题了，后面已经没有了!!!",Toast.LENGTH_SHORT).toString();
        }
        return false;
    }

    public void updateQuestion(Question question){

//        db.execSQL("updata ",new String[]{question.getTitleId(),question.getCollect(),question.getDelete(),question.getHasDo()});
        String explain = question.getExplain();
        if(explain==null||explain.equals("")){

            db.execSQL("update tb_title set f_error=?,f_collect=?,f_do=?,f_delete=? where f_titleID=?"
                    ,new String[]{""+question.getError(),""+question.getCollect(),""+question.getHasDo(),""+question.getDelete(),question.getTitleId()});
        }else{
            db.execSQL("update tb_title set f_explain=?,f_error=?,f_collect=?,f_do=?,f_delete=? where f_titleID=?"
                    ,new String[]{question.getExplain(),""+question.getError(),""+question.getCollect(),""+question.getHasDo(),""+question.getDelete(),question.getTitleId()});
        }

    }


    /***
     * 按照Setting界面进行查询
     * @param id：题目的id
     * @return: 所有满足setting条件的QuestionId.
     */
    public List<String> queryAllTitleID(String id){
        List<String> list = new ArrayList<>();

        int grade = SPUtil.getInt(Question.GRADE,Question.GRADE_1);
        int type = SPUtil.getInt(Question.TYPE,Question.TYPE_SINGLE);

        Cursor cursor = db.rawQuery("select f_titleID from tb_title where f_grade=? and f_type=? and f_titleID>?",new String[]{""+grade,""+type,id});
        int index = cursor.getColumnIndex("f_titleID");
        if (cursor.moveToFirst()){
            do{
                list.add(cursor.getString(index));
            }while (cursor.moveToNext());

        }

        if(cursor!=null){
            cursor.close();
        }
        return list;
    }

    public Question query(String id){
        return getQuestion(id);
    }

    /***
     * 获取有统计界面产生的所有的题目的Id
     * @param statistics:统计信息类型，如：是否删除、是否错题、是否收藏、是否已做
     * @param type:单选题、多选题、判断题
     * @return: 根据条件查找出所有题目的id
     */
    public List<String> statistics(Setting.Statistics statistics,int type){
        List<String> list  = new ArrayList<>();
        StringBuilder sb = new StringBuilder("select f_titleID from tb_title where f_type=");
        sb.append(type+" and ");

        switch(statistics){
            case NoDoQuestion:
                sb.append("f_do=0");
                break;

            case CollectionQuestion:
                sb.append("f_collect=1");
                break;

            case DeleteQuestion:
                sb.append("f_delete=1");
                break;

            case ErrorQuestion:
                sb.append("f_error=1");
                break;

            case HaveDoQuestion:
                sb.append("f_do=1");
                break;
        }

        Cursor cursor = db.rawQuery(sb.toString(),null);
        int index = cursor.getColumnIndex("f_titleID");

        if(cursor.moveToFirst()){
            do{
                list.add(cursor.getString(index));
            }while (cursor.moveToNext());
        }
        if (cursor!=null){
            cursor.close();
        }
        return list;
    }



    /***
     * 获取每种类型的统计信息
     * @param statistics：统计信息类型
     * @param type：题目信息类型
     * @return 不重复的个数
     */

    public int countEveryStatistic(Statistics statistics,int type){
        StringBuilder sb = new StringBuilder("select count(distinct f_titleID) as countNum from tb_title where f_type=");
        sb.append(type+" and ");
        switch (statistics){
            case NoDoQuestion:
                sb.append("f_do=0");
                break;

            case CollectionQuestion:
                sb.append("f_collect=1");
                break;

            case DeleteQuestion:
                sb.append("f_delete=1");
                break;

            case ErrorQuestion:
                sb.append("f_error=1");
                break;

            case HaveDoQuestion:
                sb.append("f_do=1");
                break;
        }
        int countNum = 0;
        Cursor cursor = db.rawQuery(sb.toString(),null);
        if (cursor.moveToFirst()){
            countNum = cursor.getInt(cursor.getColumnIndex("countNum"));
        }
        if (null != cursor){
            cursor.close();
        }
        return countNum;
    }


}

