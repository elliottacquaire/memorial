package com.exae.memorialapp.view;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.exae.memorialapp.R;
import com.lxj.xpopup.core.BottomPopupView;

public class CustomEditTextBottomPopup extends BottomPopupView {
    public CustomEditTextBottomPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_edittext_bottom_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        findViewById(R.id.btn_finish).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
//
//        setOnKeyListener(new OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if(keyCode==KeyEvent.KEYCODE_BACK){
//                    ToastUtils.showShort("自定义弹窗设置了KeyListener");
//                    return true;
//                }
//                return true;
//            }
//        });
    }

    @Override
    protected void onShow() {
        super.onShow();
    }

    @Override
    protected void onDismiss() {
        super.onDismiss();
//        Log.e("tag", "CustomEditTextBottomPopup  onDismiss");
    }

    public String getComment(){
        EditText et = findViewById(R.id.et_comment);
        return et.getText().toString();
    }

    public void setComment(String comment) {
        EditText et = findViewById(R.id.et_comment);
//        et.setText(comment);
    }

//    @Override
//    protected int getMaxHeight() {
//        return (int) (XPopupUtils.getWindowHeight(getContext())*0.75);
//    }
}
