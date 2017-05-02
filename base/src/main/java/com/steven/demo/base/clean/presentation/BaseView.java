package com.steven.demo.base.clean.presentation;

import android.content.Context;

/**
 * @author Steven.He
 * @date 2017/4/10
 */

public interface BaseView {

    Context context();

    void showMessage(String message);

    void showLoading();

    void hideLoading();
}
