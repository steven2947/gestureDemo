package com.steven.demo.base.control.listview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.steven.demo.base.R;
import com.steven.demo.base.clean.presentation.adapter.animators.ScaleInTopAnimator;
import com.steven.demo.base.control.listview.layout_manager.WrapContentLinearLayoutManager;
import com.steven.demo.base.exception.ViewException;
import com.steven.demo.base.util.PreconditionUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;


/**
 * Created by Steven on 16/5/22.
 * 列表控件:recyclerView
 */
public class ListViewControl {

    RecyclerView recyclerView;

    private RecyclerView.OnScrollListener mOnScrollListener;

    public static int VERTICAL = 0; //横向排布
    public static int HORIZONTAL = 1;//纵向排布
    public static int FLEX = 2;//自动换行布局

    private ListViewControl(Context context, RecyclerView view) {

        mOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        };

        this.recyclerView = view;

        // Workaround for dash path effect
        // https://code.google.com/p/android/issues/detail?id=29944
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            recyclerView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        recyclerView.addOnScrollListener(mOnScrollListener);
//        recyclerView.setHasFixedSize(true);

        //添加默认的动画
        setItemAnimation(new ScaleInTopAnimator());
    }

    /**
     * 资源释放
     */
    public void destroy() {
        recyclerView.removeOnScrollListener(mOnScrollListener);
        mOnScrollListener = null;

        recyclerView.setAdapter(null);

        recyclerView.getLayoutManager().removeAllViews();
        recyclerView.setLayoutManager(null);
        /*layoutManager.removeAllViews();
        layoutManager = null;*/

        recyclerView = null;
    }


    /**
     * 初始化列表控制器
     *
     * @param context
     * @param view
     * @param orientation
     * @return
     */
    public static ListViewControl initializeListView(Context context, @NonNull View view, int orientation) {
        if (view instanceof RecyclerView && context != null) {
            RecyclerView recyclerView = (RecyclerView) view;
            ListViewControl listViewControl = new ListViewControl(context, recyclerView);

            listViewControl.setLinearLayoutLayouManager(context, orientation);

            return listViewControl;
        } else {
            throw new ViewException("列表控件初始化失败,列表控件不是RecyclerView");
        }
    }

    /**
     * 初始化列表控制器
     *
     * @param context
     * @param view
     * @return
     */
    public static ListViewControl initializeListView(Context context, @NonNull View view) {
        if (view instanceof RecyclerView && context != null) {
            RecyclerView recyclerView = (RecyclerView) view;
            ListViewControl listViewControl = new ListViewControl(context, recyclerView);
            return listViewControl;
        } else {
            throw new ViewException("列表控件初始化失败,列表控件不是RecyclerView");
        }
    }

    /**
     * 初始化九宫格布局
     *
     * @param context
     * @param view
     * @param orientation
     * @param columnNum
     * @return
     */
    public static ListViewControl initializeGridListView(Context context, @NonNull View view, int orientation, int columnNum) {

        if (view instanceof RecyclerView && context != null) {

            if (columnNum < 0)
                columnNum = 6;//默认6列

            RecyclerView recyclerView = (RecyclerView) view;
            ListViewControl listViewControl = new ListViewControl(context, recyclerView);

            listViewControl.setGridLayoutManager(context, orientation, columnNum);

            return listViewControl;
        } else {
            throw new ViewException("列表控件初始化失败,列表控件不是RecyclerView");
        }
    }

    /**
     * 设置列表布局
     *
     * @param context
     * @param orientation
     */
    public void setLinearLayoutLayouManager(Context context, int orientation) {

        PreconditionUtil.checkNotNull(recyclerView);

        //LinearLayoutManager
        RecyclerView.LayoutManager linearLayoutManager = null;
        if (orientation == HORIZONTAL) {
            linearLayoutManager = new WrapContentLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        } else if (orientation == VERTICAL) {//默认 VERTICAL
            linearLayoutManager = new WrapContentLinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        } else if (orientation == FLEX) {
            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager();

            layoutManager.setFlexWrap(FlexWrap.WRAP);//设置是否换行
            layoutManager.setFlexDirection(FlexDirection.ROW);//设置主轴排列方式
            layoutManager.setJustifyContent(JustifyContent.FLEX_START);
            layoutManager.setAlignItems(AlignItems.FLEX_START);
            linearLayoutManager = layoutManager;
        }
        recyclerView.setLayoutManager(linearLayoutManager);
    }


    /**
     * 设置九宫格布局
     *
     * @param context
     * @param orientation
     * @param columnNum
     */
    public void setGridLayoutManager(Context context, int orientation, int columnNum) {

        PreconditionUtil.checkNotNull(recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, columnNum);

        if (orientation == HORIZONTAL)
            gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        else
            gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(gridLayoutManager);
    }

    @Deprecated
    public void setFlexLayoutManager(Context context) {

        PreconditionUtil.checkNotNull(recyclerView);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager();
        layoutManager.setFlexWrap(FlexWrap.WRAP);//设置是否换行
        layoutManager.setFlexDirection(FlexDirection.ROW);//设置主轴排列方式
        layoutManager.setJustifyContent(JustifyContent.FLEX_END);
        layoutManager.setAlignItems(AlignItems.STRETCH);

        recyclerView.setLayoutManager(layoutManager);
    }

    /**
     * 设置适配器
     *
     * @param obj
     */
    public void setAdapter(Object obj) {
        if (obj instanceof RecyclerView.Adapter) {
            RecyclerView.Adapter mAdapter = (RecyclerView.Adapter) obj;
            recyclerView.setAdapter(mAdapter);
        } else {
            throw new ViewException("列表控件初始化失败,Adapter不是RecyclerView.Adapter");
        }
    }


    /**
     * 添加触摸监听
     *
     * @param listener
     */
    public void setOnTouchListener(View.OnTouchListener listener) {
        recyclerView.setOnTouchListener(listener);
    }

    public void addOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        recyclerView.addOnScrollListener(onScrollListener);
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return recyclerView.getLayoutManager();
    }

    /**
     * 添加间距视图
     *
     * @param itemDecoration
     */
    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        recyclerView.addItemDecoration(itemDecoration);
    }

    /**
     * 添加默认间距视图
     *
     * @param
     */
    public void addItemDefaultDecoration(Context context) {
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(context)
                .sizeResId(R.dimen.view_interspace)
                .build());
    }

    /**
     * 设置九宫格列数
     *
     * @param spanSizeLookup
     */
    public void setSpanSizeLookupByGirdView(GridLayoutManager.SpanSizeLookup spanSizeLookup) {
        if (getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager layoutManager = (GridLayoutManager) getLayoutManager();
            layoutManager.setSpanSizeLookup(spanSizeLookup);
        }
    }

    /**
     * 设置每个项的进入
     *
     * @param animation
     */
    public void setItemAnimation(RecyclerView.ItemAnimator animation) {
        recyclerView.setItemAnimator(animation);
    }

    public void scrollToPosition(int position) {
        recyclerView.scrollToPosition(position);
    }
}
