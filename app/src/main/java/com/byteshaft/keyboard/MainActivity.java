package com.byteshaft.keyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;


public class MainActivity extends InputMethodService
        implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView keyboardView;
    private Keyboard keyboard;
    private boolean caps = false;



    @Override
    public View onCreateInputView() {
        keyboardView = (KeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this, R.xml.alpha);
        keyboardView.setOnKeyboardActionListener(this);
        return keyboardView;
    }


    @Override
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInput(attribute, restarting);
        switch (attribute.inputType&EditorInfo.TYPE_MASK_CLASS) {
            case EditorInfo.TYPE_CLASS_NUMBER:
            case EditorInfo.TYPE_CLASS_PHONE:
                keyboard = new Keyboard(this, R.xml.numeric);
                break;
            case EditorInfo.TYPE_CLASS_TEXT:
                keyboard = new Keyboard(this, R.xml.alpha);
                break;

        }
    }

    @Override
    public void onStartInputView(EditorInfo info, boolean restarting) {
        keyboardView.setKeyboard(keyboard);
        keyboardView.closing();


    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        switch(primaryCode){
            case Keyboard.KEYCODE_DELETE :
                ic.deleteSurroundingText(1, 0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                caps = !caps;
                keyboard.setShifted(caps);
                keyboardView.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            default:
                char code = (char)primaryCode;
                if(Character.isLetter(code) && caps){
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code),1);
        }
    }

    @Override
    public void onPress(int primaryCode) {
        System.out.println(primaryCode);
    }

    @Override
    public void onRelease(int primaryCode) {
        System.out.println("primarycode");
    }

    @Override
    public void onText(CharSequence text) {
        System.out.println("charsequence");
    }

    @Override
    public void swipeDown() {
        System.out.println("swipeDown");
    }

    @Override
    public void swipeLeft() {
        System.out.println("swipeLeft");
    }

    @Override
    public void swipeRight() {
        System.out.println("swiperight");
    }

    @Override
    public void swipeUp() {
        System.out.println("swipeup");
    }
}