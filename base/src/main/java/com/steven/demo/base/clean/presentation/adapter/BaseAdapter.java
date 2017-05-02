package com.steven.demo.base.clean.presentation.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by Steven on 16/4/17.
 * 默认 RecycleView adapter
 */
public abstract class BaseAdapter<M,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<M> items = new ArrayList<>();

    public void onDestroy() {
        if (items != null)
            items.clear();
        items = null;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void add(M object) {
        if (object != null) {
            items.add(object);
            notifyItemInserted(items.indexOf(object));
        }
    }

    public void add(int index, M object) {
        items.add(index, object);
        notifyItemInserted(index);
    }

    public void addAll(Collection<? extends M> collection) {
        if (collection != null) {
            items.addAll(collection);
            notifyDataSetChanged();
        }
    }

    public void addAll(List<M> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void remove(M object) {
        if (items.contains(object)) {
            int i = items.indexOf(object);
            items.remove(object);
            notifyItemRemoved(i);
        }
    }

    public void set(List<M> items) {
        if (this.items != items && items != null) {
            this.items.clear();
            this.items.addAll(items);
        }
        notifyDataSetChanged();
    }

    public M getItem(int position) {
        if (items.size() > position) {
            return items.get(position);
        } else {
            return null;
        }
    }

    public List<M> getItems() {
        return items;
    }
}
