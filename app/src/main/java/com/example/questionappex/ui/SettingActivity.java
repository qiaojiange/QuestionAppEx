package com.example.questionappex.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.questionappex.R;
import com.example.questionappex.model.Question;
import com.example.questionappex.setting.Setting;
import com.example.questionappex.ui.BaseActivity;
import com.example.questionappex.util.LogUtil;
import com.example.questionappex.util.SPUtil;

public class SettingActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener,View.OnClickListener{

    //选择等级
    private SeekBar seekBar;

//    题型
    private RadioGroup rgType;

//    答题模式
    private RadioGroup rgPattern;

//    等级显示
    private TextView tvGrade;

    private static final String TAG = "SettingActivity";

    private int grade = 0;

//    原来的等级、题型，答题模式
    private int oldGrade =0;
    private int oldType = 0;
    private int oldParttern = 0;


    private Button btFinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        LogUtil.d(TAG,"---onCreate---");

        tvGrade = (TextView)findViewById(R.id.tv_curr_grade);
        seekBar = (SeekBar)findViewById(R.id.seekBar);

        rgType = (RadioGroup)findViewById(R.id.rg_type);
        rgPattern = (RadioGroup)findViewById(R.id.rg_pattern);

        btFinish = (Button)findViewById(R.id.bt_setting_finish);
        btFinish.setOnClickListener(this);



//      获取起始的选中状态
        int type = SPUtil.getInt(Question.TYPE,Question.TYPE_SINGLE);
        oldType = type;
        LogUtil.d(TAG," get type = "+type);
        switch (type){
            case Question.TYPE_SINGLE:
                rgType.check(R.id.rBtn_1);
                break;
            case Question.TYPE_MUTIL:
                rgType.check(R.id.rBtn_2);
                break;
            case Question.TYPE_CHECKING:
                rgType.check(R.id.rBtn_3);
                break;
        }

    //  设置题型
        rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                获取变更后的选中项的ID
                int radioId = group.getCheckedRadioButtonId();
//                根据ID获取RadioButton的实例
                int type = 0;
                switch (radioId){
                    case R.id.rBtn_1:
                        type = Question.TYPE_SINGLE;
                        LogUtil.d(TAG,"单选题");
                    break;
                    case R.id.rBtn_2:
                        type = Question.TYPE_MUTIL;
                        LogUtil.d(TAG,"多选题");
                        break;
                    case R.id.rBtn_3:
                        type = Question.TYPE_CHECKING;
                        LogUtil.d(TAG,"判断题");
                        break;
                }
                LogUtil.d(TAG,"--------type = "+type);

                SPUtil.putInt(Question.TYPE,type);
            }
        });


//        获取起始的选中状态
        int partter1 = SPUtil.getInt(Setting.PATTERN,Setting.ORDER_PATTERN);
        this.oldParttern = partter1;
        switch (partter1){
            case Setting.ORDER_PATTERN:
                rgPattern.check(R.id.rBtn_order);
                break;
            case Setting.RANDOM_PATTERN:
                rgPattern.check(R.id.rBtn_random);
                break;
        }


        rgPattern.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = group.getCheckedRadioButtonId();
                int parttern = 0;
                switch (id){
                    case R.id.rBtn_order:
                        parttern = Setting.ORDER_PATTERN;
                        LogUtil.d(TAG,"顺序答题");
                        break;
                    case R.id.rBtn_random:
                        parttern = Setting.RANDOM_PATTERN;
                        LogUtil.d(TAG,"随机答题");
                        break;
                }
                SPUtil.putInt(Setting.PATTERN,parttern);
            }
        });

        seekBar.setOnSeekBarChangeListener(this);

        this.oldGrade = SPUtil.getInt(Question.GRADE,Question.GRADE_1);
        LogUtil.d(TAG,"-----oldGrade---"+this.oldGrade);
        seekBar.setProgress(this.oldGrade-1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishSettingAcitivity();
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        LogUtil.d(TAG,"---progress---"+progress);
        switch (progress){
            case 0:
                grade = Question.GRADE_1;
                tvGrade.setText("初级");
                break;
            case 1:
                grade = Question.GRADE_2;
                tvGrade.setText("中级");
                break;
            case 2:
                grade = Question.GRADE_3;
                tvGrade.setText("高级");
                break;
            case 3:
                grade = Question.GRADE_4;
                tvGrade.setText("技师");
                break;
            case 4:
                grade = Question.GRADE_5;
                tvGrade.setText("高级技师");
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        LogUtil.d(TAG,"-----grade :"+grade);
        SPUtil.putInt(Question.GRADE,grade);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onClick(View v) {
        finishSettingAcitivity();
    }

    private void finishSettingAcitivity() {
        Intent intent = new Intent();
//是否改变了设置
        if(isChangeSetting()){
            intent.putExtra("data_return","SettingActivity:true");
        }else{
            intent.putExtra("data_return","SettingActivity:false");
        }
        setResult(RESULT_OK,intent);
        this.finish();
    }

    /***
     * 是否改变了setting设置
     * @return：true，改变了，false,没有改变
     */
    private boolean isChangeSetting(){
        if(this.oldParttern != SPUtil.getInt(Setting.PATTERN,Setting.ORDER_PATTERN) ){
            return true;
        }
        if(this.oldType != SPUtil.getInt(Question.TYPE,Question.TYPE_SINGLE)){
            return true;
        }

        if(this.oldGrade != SPUtil.getInt(Question.GRADE,Question.GRADE_1)){
            return true;
        }
        return false;
    }

}
