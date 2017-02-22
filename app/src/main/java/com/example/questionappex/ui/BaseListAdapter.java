package com.example.questionappex.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by qiaojiange on 2017/2/17.
 */

public class BaseListAdapter<T>extends BaseAdapter {
    public List<T> mainDatas;
    public Context context;

    public BaseListAdapter(List<T> mainDatas,Context context) {
        this.mainDatas = mainDatas;
        this.context = context;
    }


    @Override
    public int getCount() {
        return mainDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mainDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return null;
    }
}
