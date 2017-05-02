package com.steven.demo.gesture;

import android.gesture.Gesture;

/**
 * @author Steven.He
 * @date 2017/5/2
 */

public class GestureViewModel {

    private Gesture gesture;
    private String gestureName;

    public GestureViewModel(Gesture gesture, String gestureName) {
        this.gesture = gesture;
        this.gestureName = gestureName;
    }

    public Gesture getGesture() {
        return gesture;
    }

    public void setGesture(Gesture gesture) {
        this.gesture = gesture;
    }

    public String getGestureName() {
        return gestureName;
    }

    public void setGestureName(String gestureName) {
        this.gestureName = gestureName;
    }
}
