package com.steven.demo.base.control.listview;

import android.view.MotionEvent;
import android.view.View;

/**
 * @author Steven.He
 * @date 2016/12/16
 */

public interface RecyclerHeadersTouchListener {
    void onHeaderClick(View header, int position, long headerId, MotionEvent event);
}
