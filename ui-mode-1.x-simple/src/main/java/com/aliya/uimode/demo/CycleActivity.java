package com.aliya.uimode.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.aliya.uimode.demo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CycleActivity extends BaseActivity {

    @BindView(R.id.btn_cycle)
    Button mBtnCycle;
    @BindView(R.id.list_view)
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle);
        ButterKnife.bind(this);

        mListView.setAdapter(new CycleAdapter());

    }

    @OnClick(R.id.btn_cycle)
    public void onViewClicked() {
        startActivity(new Intent(this, MainActivity.class));
    }


    class CycleAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 50;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_item_listview, parent, false);
            }
            TextView tv = (TextView) convertView.findViewById(R.id.tv_item);
            tv.setText("item " + position);
            return convertView;
        }
    }

}
