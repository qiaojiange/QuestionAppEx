package com.example.questionappex;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.questionappex.util.DBUtil;
import com.example.questionappex.model.Question;
import com.example.questionappex.ui.BaseActivity;
import com.example.questionappex.ui.ChoiceAdapter;
import com.example.questionappex.util.LogUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
//    1.设置标题
//    2.加载问题和选项
//    3.判断结果
//    4.更新结果
    private Question question;
    private Button confirm;
    private TextView tvTitle;
    private ImageView ivReturn;
    private ImageView ivSetting;
    private ImageView ivDelete;
    private ImageView ivCollect;
    private ImageView ivExplain;
    private Button btPrevQuestion;
    private Button btNextQuestion;
    //标题，表示是单选题，多选题，还是判断题
    private TextView ivMain;

//    显示解释
    private LinearLayout llExplain;


//    保存当前题目索引
    private int currentIndex;

    private ListView listView;
    ChoiceAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        confirm = (Button) findViewById(R.id.bt_confirm);
        ivReturn = (ImageView)findViewById(R.id.iv_return);
        ivSetting = (ImageView)findViewById(R.id.iv_setting);
        ivDelete = (ImageView)findViewById(R.id.iv_delete);
        ivCollect = (ImageView)findViewById(R.id.iv_collect);
        ivExplain = (ImageView)findViewById(R.id.iv_explain);
        ivMain = (TextView)findViewById(R.id.tv_main);
        tvTitle = (TextView)findViewById(R.id.tv_title);
        btPrevQuestion = (Button)findViewById(R.id.bt_prev_question);
        btNextQuestion = (Button)findViewById(R.id.bt_next_question);

        llExplain = (LinearLayout)findViewById(R.id.ll_explain);



        confirm.setOnClickListener(this);
        ivReturn.setOnClickListener(this);
        ivSetting.setOnClickListener(this);
        ivDelete.setOnClickListener(this);
        ivCollect.setOnClickListener(this);
        ivExplain.setOnClickListener(this);
        btPrevQuestion.setOnClickListener(this);
        btNextQuestion.setOnClickListener(this);

        listView = (ListView)findViewById(R.id.lv_select_area);

        //先写成为1；
        currentIndex = 1;

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter= new ChoiceAdapter(this,R.layout.item_choice);

        loadQuestion(currentIndex);
        listView.setAdapter(adapter);



    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    //加载题目
    private boolean loadQuestion(int id) {
//        SharedPreferences sharedPreferences =  getSharedPreferences("Config",MODE_PRIVATE);
//        boolean isConfig = sharedPreferences.getBoolean("isConfig",false);

//        int type = sharedPreferences.getInt("type",1);
//        int grade = sharedPreferences.getInt("grade",1);
//        int id = sharedPreferences.getInt("currentIndex",1);
//        this.question = DBUtil.query(type,grade,id);

        Question newQuestion = DBUtil.newInstance().query(1,1,id);
        if(newQuestion!=null){
            this.question = newQuestion;
        }else{
            return false;
        }
        initShowQuestion();
        adapter.setQuestion(question);
        adapter.notifyDataSetChanged();
        return true;
    }

//    初始化问题的所有信息，包括是否删除，是否收藏
    private void initShowQuestion() {
        switch (this.question.getType()){
            case Question.TYPE_SINGLE:
                ivMain.setText("单选题");
                break;
            case Question.TYPE_CHECKING:
                ivMain.setText("判断题");
                break;
            case Question.TYPE_MUTIL:
                ivMain.setText("多选题");
                break;
        }


        tvTitle.setText(this.question.getTitleId()+"、 "+this.question.getTitle());
        if(this.question.getCollect()==1){

            ivCollect.setSelected(true);
        }else{
            ivCollect.setSelected(false);
        }

        if(this.question.getDelete()==1){
            ivDelete.setSelected(true);
        }else{
            ivDelete.setSelected(false);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.iv_return:
                LogUtil.d(TAG,"onClick: return------");
                break;

            case R.id.iv_setting:
               LogUtil.d(TAG,"onClick: setting ------");
                break;

            case R.id.bt_prev_question:
                LogUtil.d(TAG,"onClick: bt_prev_question ------");
                int prevIndex = currentIndex-1;
                if(loadQuestion(prevIndex)){
                    currentIndex = prevIndex;
                }
                break;
            case R.id.bt_confirm:
                LogUtil.d(TAG, "onClick: confirm------");
                adapter.judge();
                break;

            case R.id.bt_next_question:
                LogUtil.d(TAG,"onClick: bt_next_question ------");
                int nextIndex = currentIndex+1;
                if(loadQuestion(nextIndex)){
                    currentIndex = nextIndex;
                }
                break;

            case R.id.iv_delete:
                LogUtil.d(TAG,"onClick: delete------");
                ivDelete.setSelected(!ivDelete.isSelected());
                if(ivDelete.isSelected()){
                    this.question.setDelete(1);
                }else{
                    this.question.setDelete(0);
                }
                //更新数据库
                DBUtil.newInstance().updateQuestion(question);
                break;
            case R.id.iv_collect:
                LogUtil.d(TAG,"onClick: collect---------");
                ivCollect.setSelected(!ivCollect.isSelected());
                if(ivCollect.isSelected()){
                    this.question.setCollect(1);
                }else{
                    this.question.setCollect(0);
                }
                //更新数据库
                DBUtil.newInstance().updateQuestion(question);
                break;
            case R.id.iv_explain:
                LogUtil.d(TAG,"onClick: iv_explain ------");
                ivExplain.setSelected(!ivExplain.isSelected());
                showExplain(ivExplain.isSelected());
                break;
        }
    }



    private void showExplain(boolean selected) {
        if(selected){
            llExplain.setVisibility(View.VISIBLE);
        }else{
            llExplain.setVisibility(View.INVISIBLE);
        }

    }


}
