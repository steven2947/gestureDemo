package com.steven.demo.gesture;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibrary;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.steven.demo.base.clean.presentation.BaseActivity;
import com.steven.demo.base.clean.presentation.adapter.BaseViewHolder;
import com.steven.demo.base.clean.presentation.adapter.DelegatesAdapter;
import com.steven.demo.base.clean.presentation.adapter.delegate.adapterdelegates2.AdapterDelegate;
import com.steven.demo.base.control.ToastControl;
import com.steven.demo.base.control.listview.ListViewControl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Steven.He
 * @date 2017/4/28
 */

public class ListGestureActivity extends BaseActivity {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.btn_test)
    Button btnTest;

    private GestureLibrary gLib;

    ListViewControl listViewControl;
    DelegatesAdapter<GestureViewModel> adapter;

    private ArrayList<GestureViewModel> mGestureList = new ArrayList<>();

    private String mCurrentGestureName, navuName;

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, ListGestureActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_gesture);
        ButterKnife.bind(this);

        listViewControl = ListViewControl.initializeListView(this, rvList, ListViewControl.VERTICAL);
        listViewControl.addItemDefaultDecoration(this);
        adapter = new DelegatesAdapter(this);
        adapter.addDelegate(new ListGestureDelegate(getLayoutInflater()));
        listViewControl.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @OnClick({R.id.btn_add, R.id.btn_test})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                startActivity(SaveGestureActivity.getCallingIntent(this));
                break;
            case R.id.btn_test:
                startActivity(GestureActivity.getCallingIntent(this));
                break;
        }
    }

    public void update() {
        //当手势数据变化了需要重新获取
        gLib = GestureManager.getGestureLibrary(this);
        setData();
        adapter.set(mGestureList);
    }

    private void setData() {
        mGestureList.clear();
        Set<String> gestureSet = gLib.getGestureEntries();
        for (String gestureNaam : gestureSet) {
            ArrayList<Gesture> list = gLib.getGestures(gestureNaam);
            for (Gesture g : list) {
                mGestureList.add(new GestureViewModel(g, gestureNaam));
            }
        }
    }

    class ListGestureDelegate implements AdapterDelegate<List<GestureViewModel>> {

        LayoutInflater inflater;

        public ListGestureDelegate(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        @Override
        public boolean isForViewType(@NonNull List<GestureViewModel> items, int position) {
            return true;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
            return new ListGestureViewHolder(inflater.inflate(R.layout.adapter_item_gesture, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull List<GestureViewModel> items, int position, @NonNull RecyclerView.ViewHolder holder, List<Object> payloads) {
            ListGestureViewHolder viewHolder = (ListGestureViewHolder) holder;
            GestureViewModel viewModel = items.get(position);

            viewHolder.tvGestureName.setText(viewModel.getGestureName());
            viewHolder.ivGesture.setImageBitmap(viewModel.getGesture().toBitmap(30, 30, 3, Color.YELLOW));
        }

        @Override
        public void destroy() {

        }

        class ListGestureViewHolder extends BaseViewHolder {

            @BindView(R.id.iv_gesture)
            ImageView ivGesture;
            @BindView(R.id.tv_gesture_name)
            TextView tvGestureName;

            public ListGestureViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mCurrentGestureName = adapter.getItem(getAdapterPosition()).getGestureName();

                        PopupMenu popup = new PopupMenu(ListGestureActivity.this, v);
                        popup.getMenuInflater().inflate(R.menu.gesture_item_options, popup.getMenu());
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                if (item.getItemId() == R.id.rename_gesture) {
                                    renameGesture();
                                } else if (item.getItemId() == R.id.delete_gesture) {
                                    deleteGesture();
                                }
                                return true;
                            }
                        });
                        popup.show();

                        return true;
                    }
                });
            }

            private void deleteGesture() {
                gLib.removeEntry(mCurrentGestureName);
                gLib.save();
                mCurrentGestureName = "";
                update();
            }

            private void renameGesture() {
                AlertDialog.Builder namePopup = new AlertDialog.Builder(ListGestureActivity.this);
                namePopup.setTitle("重命名");

                final EditText nameField = new EditText(ListGestureActivity.this);
                namePopup.setView(nameField);

                namePopup.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!nameField.getText().toString().matches("")) {
                            navuName = nameField.getText().toString();
                            saveGesture();
                        } else {
                            renameGesture();  //TODO : validation
                            ToastControl.showToast(ListGestureActivity.this,"请输入");
                        }
                    }
                });
                namePopup.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        navuName = "";
                        mCurrentGestureName = "";
                        return;
                    }
                });

                namePopup.show();
            }


            private void saveGesture() {
                ArrayList<Gesture> list = gLib.getGestures(mCurrentGestureName);
                if (list.size() > 0) {
                    gLib.removeEntry(mCurrentGestureName);
                    gLib.addGesture(navuName, list.get(0));
                    if (gLib.save()) {
                       update();
                    }
                }
                navuName = "";
            }
        }

    }
}
