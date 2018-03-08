package com.xbm.expandgridlist;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.xbm.expandgridlist.adapter.BaseRVAdapter;
import com.xbm.expandgridlist.adapter.MovieRVAdapter;
import com.xbm.expandgridlist.bean.MovieBean;
import com.xbm.expandgridlist.decoration.HeaderGridSpacingItemDecoration;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BaseRVAdapter.ItemClickListener {

    private ArrayList<MovieBean> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
