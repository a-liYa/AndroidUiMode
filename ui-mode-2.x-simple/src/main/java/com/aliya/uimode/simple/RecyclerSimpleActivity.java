package com.aliya.uimode.simple;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aliya.uimode.simple.base.BaseActivity;

/**
 * RecyclerView相关的UiMode使用示例页
 *
 * @author a_liYa
 * @date 2018/5/14 上午10:22.
 */
public class RecyclerSimpleActivity extends BaseActivity {

    private RecyclerView mRecycler;
    private Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_simple);

        mRecycler = (RecyclerView) findViewById(R.id.recycler);

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter = new Adapter());

    }

    class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bindData(position);
        }

        @Override
        public int getItemCount() {
            return 40;
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_list_view_item, parent, false));
        }

        public void bindData(int position) {
            ((TextView) itemView).setText("item " + position);
        }

    }

}
