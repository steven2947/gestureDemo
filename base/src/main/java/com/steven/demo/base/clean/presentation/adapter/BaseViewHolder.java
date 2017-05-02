package com.steven.demo.base.clean.presentation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author Steven.He
 * @date 2017/4/11
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public void destroy() {

    }
}
