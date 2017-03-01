package com.example.questionappex;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.questionappex.model.Question;
import com.example.questionappex.setting.Setting;
import com.example.questionappex.ui.BaseActivity;
import com.example.questionappex.ui.ChoiceAdapter;
import com.example.questionappex.ui.MainActivity;
import com.example.questionappex.util.DBUtil;
import com.example.questionappex.util.LogUtil;
import com.example.questionappex.util.ToastUtil;


import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;


public class RecordActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ListView listView;
    private TextView recordTitle;
    private Button recordReset;//重置按钮
    private TextView recordAllTitle;
    private Button recordBack;//返回键
    private Button recordAllCount;//所有个数

    //    记录布局
    private RelativeLayout recordLayoutAll;

    //    统计每种类型的错题
    private int[] count = new int[3];

    public static final String TORECORDACTIVITY = "record";

    private static final String TAG = "RecordActivity";

    //    设置当前题目分类
    private Setting.Statistics statistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        //设置题目标题
        recordTitle = (TextView) findViewById(R.id.record_title);
        //重置按钮
        recordReset = (Button) findViewById(R.id.record_bt_reset);
        recordReset.setOnClickListener(this);

        //加载资源
        listView = (ListView) findViewById(R.id.record_listview);

        recordBack = (Button) findViewById(R.id.record_bt_back);
        recordBack.setOnClickListener(this);

        //统计总个数
        recordAllCount = (Button) findViewById(R.id.record_bt_all_count);

        //统计所有的标题
        recordAllTitle = (TextView) findViewById(R.id.record_tv_all_title);
        recordLayoutAll = (RelativeLayout) findViewById(R.id.record_layout_all);

//        loadStatisticData();
        initSurface();

        MyAdapter adapter = new MyAdapter(getDataResourse(getResources().getStringArray(R.array.type), count));
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void onClick(View v) {
        String str = "";
        switch (v.getId()) {
            case R.id.record_bt_back:
                this.finish();
                break;
//            清空，并且把所有数值都恢复成0
            case R.id.record_bt_reset:
                LogUtil.d(TAG,"--点击了清空按钮--");
                String limit = "";
                switch (this.statistics) {
                    case ErrorQuestion:
                        limit = "f_error=1";
                        str = "f_error=0";
                        break;
                    case HaveDoQuestion:
                        str = "f_do=0";
                        limit="f_do=1";
                        break;
                    case DeleteQuestion:
                        str="f_delete=0";
                        limit="f_delete=1";
                        break;


                    case CollectionQuestion:
                        str = "f_collect=0";
                        limit = "f_collect=1";
                        break;
                    default:
                        break;
                }
                DBUtil.newInstance().reset(str,limit);
//                更新UI
//                RecordActivity.this.finish();
                initSurface();
                MyAdapter adapter = new MyAdapter(getDataResourse(getResources().getStringArray(R.array.type),count));
                listView.setAdapter(adapter);
                break;
            default:
                break;
        }

    }


    public static final String STATISTICS = "statistics";

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtil.d(TAG, "---onItemClick---");
        int type = 0;
        Intent intent = null;
        Bundle bundle = null;
        switch (position + 1) {
            case Question.TYPE_SINGLE:
                if (count[0] == 0) {
//                    Toast.makeText(RecordActivity.this,"单选题为0个，请重新选择!!!",Toast.LENGTH_SHORT).show();
                    ToastUtil.showToast(RecordActivity.this, "单选题为0个，请重新选择!!!");
                    return;
                }
                intent = new Intent(RecordActivity.this, MainActivity.class);
//                intent.putExtra(Question.TYPE,Question.TYPE_SINGLE);
                bundle = new Bundle();
                bundle.putInt(STATISTICS, this.statistics.ordinal());
                bundle.putInt(Question.TYPE, Question.TYPE_SINGLE);
                LogUtil.d(TAG, "---Question.TYPE_MUTIL---" + this.statistics.ordinal());
                intent.putExtras(bundle);

                break;

//            测试下多选题
            case Question.TYPE_MUTIL:
                if (count[1] == 0) {
//                    Toast.makeText(RecordActivity.this,"多选题为0个，请重新选择!!!",Toast.LENGTH_SHORT).show();
                    ToastUtil.showToast(RecordActivity.this, "多选题为0个，请重新选择!!!");
                    return;
                }

                intent = new Intent(RecordActivity.this, MainActivity.class);
//                intent.putExtra(Question.TYPE,Question.TYPE_MUTIL);
                bundle = new Bundle();
                bundle.putInt(STATISTICS, this.statistics.ordinal());
                bundle.putInt(Question.TYPE, Question.TYPE_MUTIL);
                LogUtil.d(TAG, "---Question.TYPE_MUTIL---" + this.statistics.ordinal());
                intent.putExtras(bundle);
                break;
            case Question.TYPE_CHECKING:
                if (count[2] == 0) {
//                    Toast.makeText(RecordActivity.this,"多选题为0个，请重新选择!!!",Toast.LENGTH_SHORT).show();
                    ToastUtil.showToast(RecordActivity.this, "判断题为0个，请重新选择!!!");
                    return;
                }
                intent = new Intent(RecordActivity.this, MainActivity.class);
//                intent.putExtra(Question.TYPE,Question.TYPE_CHECKING);
                bundle = new Bundle();
                bundle.putInt(STATISTICS, this.statistics.ordinal());
                bundle.putInt(Question.TYPE, Question.TYPE_CHECKING);
                LogUtil.d(TAG, "---Question.TYPE_MUTIL---" + this.statistics.ordinal());
                intent.putExtras(bundle);
                break;
        }
        startActivity(intent);
    }


    class MyAdapter extends BaseAdapter {
        private List<Item> list;

        public MyAdapter(List<Item> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ItemViewHold viewHold;
            if (convertView == null) {
                view = LayoutInflater.from(RecordActivity.this).inflate(R.layout.item_record, parent, false);
                viewHold = new ItemViewHold();
                viewHold.id = (Button) view.findViewById(R.id.record_item_id);
                viewHold.info = (TextView) view.findViewById(R.id.record_item_texview);
                viewHold.count = (Button) view.findViewById(R.id.record_item_count);
                view.setTag(viewHold);
            } else {
                view = convertView;
                viewHold = (ItemViewHold) view.getTag();
            }
            viewHold.id.setText(list.get(position).getId());
            viewHold.count.setText(list.get(position).getCount());
            viewHold.info.setText(list.get(position).getInfo());
            return view;
        }


    }


    private class ItemViewHold {
        public Button id;
        public TextView info;
        public Button count;
    }

    //    获取数据
    public List<Item> getDataResourse(String[] info, int[] count) {
        List<Item> list;
        list = new ArrayList<>();
        int min = Math.min(info.length, count.length);

        int index = 0;
        for (int i = 0; i < min; i++) {
            index = i + 1;
            Item item = new Item("" + index, info[i], "" + count[i]);
            list.add(item);
        }

        return list;
    }

    //条目
    public class Item {
        private String Id;
        private String Info;
        private String count;

        public Item(String id, String info, String count) {
            Id = id;
            Info = info;
            this.count = count;
        }

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getInfo() {
            return Info;
        }

        public void setInfo(String info) {
            Info = info;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

    }


    //    初始化界面
    private void initSurface() {

        Intent intent = this.getIntent();
        int index = intent.getIntExtra(TORECORDACTIVITY, 0);//从index中取出是错题、收藏、还是删除
        LogUtil.d(TAG, "----index---" + index);
//        Setting.Statistics statistics =
        Setting.Statistics[] statisticss = Setting.Statistics.values();
        statistics = statisticss[index];
        switch (statistics) {
            case CollectionQuestion:

                recordTitle.setText("我的收藏");
                recordAllTitle.setText("所有我收藏的题");
                recordReset.setText("清空我的收藏");

                count[0] = DBUtil.newInstance().countEveryStatistic(Setting.Statistics.CollectionQuestion, Question.TYPE_SINGLE);
                count[1] = DBUtil.newInstance().countEveryStatistic(Setting.Statistics.CollectionQuestion, Question.TYPE_MUTIL);
                count[2] = DBUtil.newInstance().countEveryStatistic(Setting.Statistics.CollectionQuestion, Question.TYPE_CHECKING);
                break;
            case NoDoQuestion:
                recordTitle.setText("未做的题");
                recordAllTitle.setText("所有我未做的题");
//                recordReset.setText("清空我的收藏");
                recordReset.setVisibility(View.INVISIBLE);

                count[0] = DBUtil.newInstance().countEveryStatistic(Setting.Statistics.NoDoQuestion, Question.TYPE_SINGLE);
                count[1] = DBUtil.newInstance().countEveryStatistic(Setting.Statistics.NoDoQuestion, Question.TYPE_MUTIL);
                count[2] = DBUtil.newInstance().countEveryStatistic(Setting.Statistics.NoDoQuestion, Question.TYPE_CHECKING);
                break;

            case DeleteQuestion:
                recordTitle.setText("排除的题");
                recordAllTitle.setText("所有我删除的题");
                recordReset.setText("恢复排除的题");
                count[0] = DBUtil.newInstance().countEveryStatistic(Setting.Statistics.DeleteQuestion, Question.TYPE_SINGLE);
                count[1] = DBUtil.newInstance().countEveryStatistic(Setting.Statistics.DeleteQuestion, Question.TYPE_MUTIL);
                count[2] = DBUtil.newInstance().countEveryStatistic(Setting.Statistics.DeleteQuestion, Question.TYPE_CHECKING);

                break;

            case ErrorQuestion:
                recordTitle.setText("我的错题");
                recordAllTitle.setText("所有我做错的题");
                recordReset.setText("清空我的错题");

                count[0] = DBUtil.newInstance().countEveryStatistic(Setting.Statistics.ErrorQuestion, Question.TYPE_SINGLE);
                count[1] = DBUtil.newInstance().countEveryStatistic(Setting.Statistics.ErrorQuestion, Question.TYPE_MUTIL);
                count[2] = DBUtil.newInstance().countEveryStatistic(Setting.Statistics.ErrorQuestion, Question.TYPE_CHECKING);

                break;

            case HaveDoQuestion:
                recordTitle.setText("已做的题");
                recordAllTitle.setText("所有我已做的题");
                recordReset.setText("清空已做的题");

                count[0] = DBUtil.newInstance().countEveryStatistic(Setting.Statistics.HaveDoQuestion, Question.TYPE_SINGLE);
                count[1] = DBUtil.newInstance().countEveryStatistic(Setting.Statistics.HaveDoQuestion, Question.TYPE_MUTIL);
                count[2] = DBUtil.newInstance().countEveryStatistic(Setting.Statistics.HaveDoQuestion, Question.TYPE_CHECKING);
                break;


        }


        int total = 0;
        for (int i = 0; i < count.length; i++) {
            total += count[i];
        }

//        设置标题中的总个数
        recordAllCount.setText("" + total);

    }


}
