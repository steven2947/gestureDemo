package com.steven.demo.base.clean.presentation.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven.He
 * @date 2017/4/21
 */

public abstract class BasePagerAdapter<M> extends PagerAdapter {

    public ArrayList<M> dataList = new ArrayList();// 列表数据源

    protected abstract View bindData(int position);

    @Override
    public int getItemPosition(Object object) {
        if (dataList != null && dataList.size() == 0) {
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return dataList != null ? dataList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 创建指定位置的页面视图
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View view = bindData(position);

        view.setTag(position);

        ((ViewPager) container).addView(view);
        return view;
    }


    //清除滑出的资源
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void init(List<M> items) {
        this.dataList.addAll(items);
    }

    public void set(List<M> items) {
        clear();
        init(items);
        notifyDataSetChanged();
    }

    public void clear() {
        dataList.clear();
        notifyDataSetChanged();
    }

    public void add(M item) {
        dataList.add(item);
        notifyDataSetChanged();
    }

    public void add(int position, M item) {
        dataList.add(position, item);
        notifyDataSetChanged();
    }

    public void add(List<M> items) {
        this.dataList.addAll(items);
        notifyDataSetChanged();
    }


    public void remove(Object item) {
        dataList.remove(item);
        notifyDataSetChanged();
    }

    public M getItem(int position) {
        return dataList.get(position);
    }

}
