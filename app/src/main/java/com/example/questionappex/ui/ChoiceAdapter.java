package com.example.questionappex.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.questionappex.R;
import com.example.questionappex.model.Choice;
import com.example.questionappex.model.Question;
import com.example.questionappex.util.LogUtil;

import java.util.List;


//选择适配器，继承了BaseListAdapter。实现了OnCheckChangeListener
public class ChoiceAdapter extends ArrayAdapter<Choice> {
    private static final String TAG = "ChoiceAdapter";
    //用户选项
    boolean choices[];
    //    Question question;
    Context context;
    private List<Choice> mChoiceList;

    //    item的布局文件
    private int resourceId;

    public ChoiceAdapter(Context context, int resource, List<Choice> mChoiceList) {
        super(context, resource);
        this.resourceId = resource;
        this.mChoiceList = mChoiceList;
    }

    public ChoiceAdapter(Context context, int resource) {
        super(context, resource);
        this.resourceId = resource;
    }

    private Question question;

    public ChoiceAdapter(Context context, int resource, Question question) {
        super(context, resource);
        LogUtil.d(TAG, "ChoiceAdapter =" + question.toString());
        this.resourceId = resource;
        this.question = question;
        this.mChoiceList = question.getChoices();


//        this.mChoiceList = mChoiceList;
    }

    @Override
    public int getCount() {
        LogUtil.d(TAG, "--------getCount---");
        return mChoiceList.size();
    }

    @Override
    public Choice getItem(int position) {
        LogUtil.d(TAG, "----getItem-----");
        return mChoiceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        LogUtil.d(TAG, "----getItemId-----");
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LogUtil.d(TAG, "----getView----position:" + position);
        Choice choice = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (null == convertView) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.answerTextView = (TextView) view.findViewById(R.id.tv_item);
            viewHolder.choice = (CheckBox) view.findViewById(R.id.cb_choice);
            viewHolder.positionTextView = (TextView) view.findViewById(R.id.tv_position);
            viewHolder.convertView = view;
            viewHolder.position = position;
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.positionTextView.setText(choice.getPosition());
//        viewHolder.choice.setChecked();
        viewHolder.answerTextView.setText(choice.getAnswer());
        //设置背景颜色
        processItemBackground(position, viewHolder);
        return view;
    }

    private void processItemBackground(int position, ViewHolder viewHolder) {
        LogUtil.d(TAG, "----processItemBackground----position:" + position);
        final Choice choice = getItem(position);
        switch (choice.getState()) {
            case ERROR:
                viewHolder.convertView.setBackgroundColor(Color.RED);
                break;
            case RIGHT:
                viewHolder.convertView.setBackgroundColor(Color.GREEN);
                break;
            case NORMAL:
                viewHolder.convertView.setBackgroundColor(Color.WHITE);
                break;
        }
        viewHolder.choice.setOnCheckedChangeListener(null);
        viewHolder.choice.setChecked(choices[position]);

//        将位置放入CheckBox的tag中，之后获取
        viewHolder.choice.setTag(position);

//        viewHolder.convertView.setTag(position);
        viewHolder.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int position = (int)v.getTag();
                ViewHolder vh = (ViewHolder) v.getTag();
                if (Question.TYPE_SINGLE == question.getType() || Question.TYPE_CHECKING == question.getType()) {
                    for (int i = 0; i < choices.length; i++) {
                        choices[i] = false;
                    }
                }
                int position = vh.position;
                choices[position] = !choices[position];
                vh.choice.setChecked(choices[position]);
                notifyDataSetChanged();
//                Toast.makeText(MyApplication.getContext(),"converView.setOnClickListener"+position,Toast.LENGTH_SHORT).show();
            }
        });

//        viewHolder.choice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(question.getType()==Question.TYPE_SINGLE){
//                    for(int i = 0;i<choices.length;i++){
//                        choices[i] = false;
//                    }
//                }
//                int position = (int)buttonView.getTag();
//                choices[position] = isChecked;
//                notifyDataSetChanged();
//            }
//        });

    }

    public void setQuestion(Question question) {
        LogUtil.d(TAG, "----setQuestion----:" + question);
        this.question = question;
        this.mChoiceList = question.getChoices();
        choices = new boolean[this.mChoiceList.size()];
        notifyDataSetChanged();
    }

    //    判断用户选择的是否是正确答案,并修改答案后面的背景颜色
    public void judge() {
//        将选择的答案组成字符串
//        判断字符串是否相等
//        设置每个item的状态

//        都没有选择
        boolean flag = false;
        for (int i = 0; i < choices.length; i++) {
            if (choices[i] == true) {
                flag = true;
            }
        }
        if (!flag) {
            Toast.makeText(MyApplication.getContext(), "请选择选项!!!", Toast.LENGTH_SHORT).show();
            return;
        }

//        修改了这个debug
//无论单选还是多选都应该显示所有正确答案，之后再判定用户输入

        String result = question.getResult();

        for (int i = 0; i < result.length(); i++) {
            char ch = result.charAt(i);
            this.mChoiceList.get(ch - 'A').setState(Choice.State.RIGHT);
        }

        for (int i = 0; i < choices.length; i++) {
            if (choices[i] == true) {
                char ch = (char) ('A' + i);
//                sb.append(ch);
                if (this.question.getResult().indexOf(ch) > -1) {
                    this.mChoiceList.get(i).setState(Choice.State.RIGHT);
                } else {
                    this.mChoiceList.get(i).setState(Choice.State.ERROR);
                    this.question.setHasDo(1);
                    this.question.setError(1);
                }
            }
        }

        //刷新界面
        notifyDataSetChanged();
    }


    public void setChoices(List<Choice> choices) {
        LogUtil.d(TAG, "----setChoices----");
        this.mChoiceList = choices;
        notifyDataSetChanged();
    }

//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//    }


    public class ViewHolder {
        View convertView;
        TextView positionTextView;
        TextView answerTextView;
        CheckBox choice;
        int position;
    }
}
