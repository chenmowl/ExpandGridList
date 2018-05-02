package com.xbm.expandgridlist;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.xbm.expandgridlist.adapter.BaseRVAdapter;
import com.xbm.expandgridlist.adapter.ExpandAdapter;
import com.xbm.expandgridlist.adapter.MovieRVAdapter;
import com.xbm.expandgridlist.bean.MovieBean;
import com.xbm.expandgridlist.decoration.HeaderGridSpacingItemDecoration;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BaseRVAdapter.ItemClickListener {

    private ArrayList<MovieBean> movies;
    private ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //第一种方式
        RecyclerView rvList = findViewById(R.id.rv_content);
        int spanCount = 6;//跟布局里面的spanCount属性是一致的
        GridLayoutManager mgr = new GridLayoutManager(this, spanCount);
        rvList.setLayoutManager(mgr);
        rvList.setPadding(dip2px(this, 5), 0, dip2px(this, 5), 0);
        int spacing = dip2px(this, 5);//每一个矩形的间距
        boolean includeEdge = true;//如果设置成false那边缘地带就没有间距
        //设置每个item间距
        rvList.addItemDecoration(new HeaderGridSpacingItemDecoration(spanCount, spacing, includeEdge, 0));
        movies = new ArrayList<>();
        for (int i = 1; i < 15; i++) {
            movies.add(new MovieBean("Movie", String.valueOf(i)));
        }
        MovieRVAdapter mAdapter = new MovieRVAdapter(12);
        rvList.setAdapter(mAdapter);
        mAdapter.replaceData(movies);
        mAdapter.setItemClickListener(this);

        //第二种方式的伸展布局，相比于第一种不依赖javabean  略解耦
        RecyclerView rvList02 = findViewById(R.id.rv_content2);
        GridLayoutManager mgr02 = new GridLayoutManager(this, spanCount);
        rvList02.setLayoutManager(mgr02);
        rvList02.setPadding(dip2px(this, 5), 0, dip2px(this, 5), 0);
        //设置每个item间距
        rvList02.addItemDecoration(new HeaderGridSpacingItemDecoration(spanCount, spacing, includeEdge, 0));
        data = new ArrayList<>();
        for (int i = 1; i < 25; i++) {
            data.add("turn = " + i);
        }
        ExpandAdapter mAdapter02 = new ExpandAdapter(18);
        View expandView = LayoutInflater.from(this).inflate(R.layout.item_toggle, null, false);
        mAdapter02.setExpandView(expandView);
        mAdapter02.setItemClickListener(new ExpandClickListener());
        rvList02.setAdapter(mAdapter02);
        mAdapter02.replaceData(data);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    public void itemClick(BaseRVAdapter adapter, View view, int position) {
        MovieBean bean = (MovieBean) adapter.mData.get(position);
        if (bean.isToggle()) {
            ((MovieRVAdapter) adapter).toggle();
        } else {
            Toast.makeText(MainActivity.this, "turn= " + bean.getTurn() + "   content= " + bean.getName(), Toast.LENGTH_SHORT).show();
        }
    }

    public class ExpandClickListener implements BaseRVAdapter.ItemClickListener {

        @Override
        public void itemClick(BaseRVAdapter adapter, View view, int position) {
            Toast.makeText(MainActivity.this, data.get(position), Toast.LENGTH_SHORT).show();
        }
    }
}
