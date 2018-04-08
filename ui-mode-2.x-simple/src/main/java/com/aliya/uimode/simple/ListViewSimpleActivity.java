package com.aliya.uimode.simple;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aliya.uimode.simple.base.BaseActivity;

/**
 * ListView相关的UiMode使用示例页
 *
 * @author a_liYa
 * @date 2018/2/8 下午3:42.
 */
public class ListViewSimpleActivity extends BaseActivity {

    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_simple);

        mListView = (ListView) findViewById(R.id.list_view);

        mListView.setAdapter(new Adapter());
    }

    class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 40;
        }

        @Override
        public Object getItem(int position) {
            return "item " + position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_list_view_item, parent, false);
            }
            ((TextView) convertView).setText(getItem(position).toString());
            return convertView;
        }
    }

}
