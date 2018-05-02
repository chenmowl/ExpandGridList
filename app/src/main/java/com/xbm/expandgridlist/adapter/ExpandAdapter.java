package com.xbm.expandgridlist.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xbm.expandgridlist.R;

/**
 * 第二种方式的伸展布局适配器，相比于第一种不依赖javabean
 */
public class ExpandAdapter extends BaseRVAdapter<String> implements View.OnClickListener {

    private static final int TYPE_TOGGLE = 1;

    private View expandView;//回缩按钮

    private int criticalCount;//伸缩临界值

    private boolean hide;//是否处于回缩状态

    public ExpandAdapter() {
        super(R.layout.item_movie_turn);
        criticalCount = 10;
    }

    public ExpandAdapter(int criticalCount) {
        super(R.layout.item_movie_turn);
        this.criticalCount = criticalCount;
        hide = true;
    }

    /**
     * 设置toggle 控件
     *
     * @param expandView
     */
    public void setExpandView(View expandView) {
        this.expandView = expandView;
    }

    public void setCriticalCount(int criticalCount) {
        this.criticalCount = criticalCount;
    }

    @Override
    public MBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TOGGLE) {
            if (expandView == null) {
                throw new IllegalArgumentException("ExpandAdapter中 expandView 不能为null");
            } else {
                return MBaseViewHolder.get(expandView);
            }
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(MBaseViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == TYPE_TOGGLE) {
            // TODO: 2018/5/2 这里处理toggle点击事件
            if (expandView != null) {
                expandView.setOnClickListener(this);
            }
        } else {
            super.onBindViewHolder(holder, position);
            TextView tvContent = holder.getView(R.id.tv_content);
            tvContent.setText(String.valueOf(position));
        }
    }

    @Override
    public int getItemCount() {
        int dataLength = mData.size();
        if (dataLength >= criticalCount) {
            //超过临界值，添加footer
            if (hide) {
                return criticalCount;
            } else {
                return dataLength + 1;
            }
        }
        return super.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        int dataLength = mData.size();
        if (dataLength >= criticalCount) {
            if ((hide && position == (criticalCount - 1)) || (!hide && position == dataLength)) {
                return TYPE_TOGGLE;
            } else {
                return super.getItemViewType(position);
            }
        } else {
            return super.getItemViewType(position);
        }
    }

    public void toggle() {
        hide = !hide;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        toggle();
        if (expandView != null) {
            expandView.setSelected(!hide);
        }
    }
}
