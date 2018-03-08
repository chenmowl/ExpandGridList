package com.xbm.expandgridlist.adapter;

import android.view.View;
import android.widget.TextView;

import com.xbm.expandgridlist.R;
import com.xbm.expandgridlist.bean.MovieBean;


/**
 * Created by xbm on 2018/3/8.
 */

public class MovieRVAdapter extends BaseRVAdapter<MovieBean> {

    int criticalCount;//伸缩临界值

    boolean isHide;

    private MovieBean bean;

    public MovieRVAdapter(int criticalCount) {
        super(R.layout.item_movie_turn);
        isHide = true;
        this.criticalCount = criticalCount;
        bean = new MovieBean("", "", true);
    }

    @Override
    public void onBindViewHolder(MBaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        TextView tvContent = holder.getView(R.id.tv_content);
        View toggle = holder.getView(R.id.toggle);
        MovieBean bean = mData.get(position);
        if (bean.isToggle()) {
            tvContent.setText("");
            toggle.setVisibility(View.VISIBLE);
            toggle.setSelected(!isHide);
        } else {
            toggle.setVisibility(View.GONE);
            tvContent.setText(bean.getTurn());
        }
    }

    @Override
    public int getItemCount() {
        int max = criticalCount;
        int size = mData.size();//真实数据的数量
        size = mData.contains(bean) ? size - 1 : size;//去除非数据项
        if (size > max) {
            //这是根据状态值隐藏
            if (isHide) {
                if (!mData.contains(bean)) {
                    mData.add(max - 1, bean);
                } else {
                    int i = mData.indexOf(bean);
                    if (i != max - 1) {
                        mData.remove(bean);
                        mData.add(max - 1, bean);
                    }
                }
                return max;
            } else {
                if (mData.contains(bean)) {
                    int i = mData.indexOf(bean);
                    if (i != size) {
                        mData.remove(bean);
                        mData.add(bean);
                    }
                } else {
                    mData.add(bean);
                }
                return mData.size();
            }
        } else {
            if (mData.contains(bean)) {
                mData.remove(bean);
            }
            return mData.size();
        }
    }

    /**
     * 切换剧集伸缩的方法
     */
    public void toggle() {
        isHide = !isHide;
        notifyDataSetChanged();
    }
}
