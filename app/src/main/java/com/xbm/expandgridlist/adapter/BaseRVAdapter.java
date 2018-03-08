package com.xbm.expandgridlist.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by xbm on 2018/3/8.
 */
public abstract class BaseRVAdapter<T> extends RecyclerView.Adapter<BaseRVAdapter.MBaseViewHolder> {

    @NonNull
    public List<T> mData;
    @NonNull
    private int layoutId;

    public BaseRVAdapter(@NonNull int layoutId) {
        this.layoutId = layoutId;
        mData = new ArrayList<>();
    }

    public BaseRVAdapter(@NonNull List<T> mData, @NonNull int layoutId) {
        this.mData = mData;
        this.layoutId = layoutId;
    }

    @Override
    public MBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return MBaseViewHolder.get(inflate);
    }

    @Override
    public void onBindViewHolder(MBaseViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new RVClickListener(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void replaceData(@NonNull Collection<? extends T> data) {
        // 不是同一个引用才清空列表
        if (data != mData) {
            mData.clear();
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    static class MBaseViewHolder extends RecyclerView.ViewHolder {

        private SparseArray<View> mViews;

        public MBaseViewHolder(View itemView) {
            super(itemView);
            mViews = new SparseArray<View>();
        }

        public static MBaseViewHolder get(View itemView) {
            return new MBaseViewHolder(itemView);
        }

        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }
    }

    private ItemClickListener itemClickListener;//点击事件的监听

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public interface ItemClickListener {
        void itemClick(BaseRVAdapter adapter, View view, int position);
    }

    class RVClickListener implements View.OnClickListener {

        int position;

        public RVClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.itemClick(BaseRVAdapter.this, v, position);
            }
        }
    }
}
