package com.steven.demo.base.exception;

/**
 * Created by Steven on 16/5/22.
 */
public class ViewException extends RuntimeException {

    public ViewException() {
        super("View错误");
    }

    public ViewException(String msg) {
        super(msg);
    }
}
