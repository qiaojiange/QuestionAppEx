package com.example.questionappex;

import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Binder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.questionappex.setting.Setting;
import com.example.questionappex.ui.BaseActivity;
import com.example.questionappex.ui.MainActivity;
import com.example.questionappex.util.DBUtil;
import com.example.questionappex.util.LogUtil;

public class SequenceActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout bt_sequence_order;
    private LinearLayout bt_sequence_complete;
    private LinearLayout bt_sequence_random;
    private LinearLayout bt_sequence_error;
    private LinearLayout bt_sequence_incomplete;
    private LinearLayout bt_sequence_delete;
    private LinearLayout bt_sequence_collect;

    private static final String TAG = "SequenceActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence);


        bt_sequence_order = (LinearLayout) findViewById(R.id.bt_sequence_order);
        bt_sequence_complete = (LinearLayout) findViewById(R.id.bt_sequence_complete);
        bt_sequence_random = (LinearLayout) findViewById(R.id.bt_sequence_random);
        bt_sequence_error = (LinearLayout) findViewById(R.id.bt_sequence_error);
        bt_sequence_incomplete = (LinearLayout) findViewById(R.id.bt_sequence_incomplete);
        bt_sequence_delete = (LinearLayout) findViewById(R.id.bt_sequence_delete);
        bt_sequence_collect = (LinearLayout) findViewById(R.id.bt_sequence_collect);


        bt_sequence_order.setOnClickListener(this);
        bt_sequence_complete.setOnClickListener(this);
        bt_sequence_random.setOnClickListener(this);
        bt_sequence_error.setOnClickListener(this);
        bt_sequence_incomplete.setOnClickListener(this);
        bt_sequence_delete.setOnClickListener(this);
        bt_sequence_collect.setOnClickListener(this);
    }


    @Override
    public void onBackPressed() {
        LogUtil.d(TAG,"--------onBackPressed------");
       // super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(SequenceActivity.this).setMessage("确定退出吗？");
        builder.setTitle("提醒");
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LogUtil.d(TAG,"----确定退出----");
                //        关闭数据库
                DBUtil.newInstance().closeDatabase();
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LogUtil.d(TAG,"---取消退出--");
            }
        });
        AlertDialog dialog = builder.create();
        if(!this.isFinishing()){
            LogUtil.d(TAG," --- this is not finish---");
            dialog.show();
        }else{
            LogUtil.d(TAG,"---this is finish----");
        }

//        super.onBackPressed();


    }

    @Override
    public void onClick(View v) {
        Intent intent =null;

        switch (v.getId()){
            case R.id.bt_sequence_order:
                LogUtil.d(TAG,"--bt_sequence_order--");

                intent = new Intent(SequenceActivity.this,MainActivity.class);

                break;

            case R.id.bt_sequence_incomplete:
                LogUtil.d(TAG,"--bt_sequence_incomplete--");

                intent = new Intent(SequenceActivity.this,RecordActivity.class);
                intent.putExtra(RecordActivity.TORECORDACTIVITY, Setting.Statistics.NoDoQuestion.ordinal());

                break;

            case R.id.bt_sequence_complete:
                LogUtil.d(TAG,"--bt_sequence_complete--");

                intent = new Intent(SequenceActivity.this,RecordActivity.class);
                intent.putExtra(RecordActivity.TORECORDACTIVITY, Setting.Statistics.HaveDoQuestion.ordinal());

                break;


            case R.id.bt_sequence_collect:
                LogUtil.d(TAG,"--bt_sequence_collect--");
                intent = new Intent(SequenceActivity.this,RecordActivity.class);
                intent.putExtra(RecordActivity.TORECORDACTIVITY, Setting.Statistics.CollectionQuestion.ordinal());

                break;

            case R.id.bt_sequence_delete:
                LogUtil.d(TAG,"--bt_sequence_delete--");
                intent = new Intent(SequenceActivity.this,RecordActivity.class);
                intent.putExtra(RecordActivity.TORECORDACTIVITY, Setting.Statistics.DeleteQuestion.ordinal());
                break;

            case R.id.bt_sequence_random:
                LogUtil.d(TAG,"--bt_sequence_random--");
                intent = new Intent(SequenceActivity.this,MainActivity.class);

//                parameter.setPatternByIndex(1);//随机答题
//                editor.putInt(SettingParameter.PATTERN_SHAREPREFERENCE, parameter.pattern.ordinal());
//                editor.commit();
//                intent = new Intent(SequenceSetting02Activity.this,MainActivity.class);

                break;

//            我的错题
            case R.id.bt_sequence_error:
                LogUtil.d(TAG,"--bt_sequence_error--");

                intent = new Intent(SequenceActivity.this,RecordActivity.class);
                intent.putExtra(RecordActivity.TORECORDACTIVITY, Setting.Statistics.ErrorQuestion.ordinal());

                break;
        }

        startActivity(intent);
//        this.finish();
    }
}
