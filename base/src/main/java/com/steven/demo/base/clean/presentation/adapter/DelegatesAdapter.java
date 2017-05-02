package com.steven.demo.base.clean.presentation.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.steven.demo.base.clean.presentation.adapter.delegate.adapterdelegates2.AdapterDelegate;
import com.steven.demo.base.clean.presentation.adapter.delegate.adapterdelegates2.AdapterDelegatesManager;

import java.util.List;

/**
 * @author Steven.He
 * @date 16/7/1
 */

public class DelegatesAdapter<T> extends BaseAdapter<T, RecyclerView.ViewHolder> {

    protected Context appContext;
    private AdapterDelegatesManager<List<T>> delegatesManager;

    public DelegatesAdapter(Activity activity) {
        this.appContext = activity.getApplicationContext();
        this.delegatesManager = new AdapterDelegatesManager<>();
    }

    public DelegatesAdapter(Context context) {
        this.appContext = context.getApplicationContext();
        this.delegatesManager = new AdapterDelegatesManager<>();
    }


    /**
     * 释放资源方法
     */
    public void destroy() {
        this.appContext = null;
        this.delegatesManager.destroy();
        this.delegatesManager = null;
    }

    /**
     * 判断View 类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(items, position);
    }

    /**
     * 创建视图
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegatesManager.onCreateViewHolder(parent, viewType);
    }


    /**
     * 视图绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        delegatesManager.onBindViewHolder(items, position, holder, null);
    }

    /**
     * 视图绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        delegatesManager.onBindViewHolder(items, position, holder, payloads);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addDelegate(AdapterDelegate adapterDelegate) {
        if (!delegatesManager.contains(adapterDelegate))
            this.delegatesManager.addDelegate(adapterDelegate);
    }

    public String getString(int resId) {
        return appContext.getString(resId);
    }
}
