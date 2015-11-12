package com.wrmoney.administrator.plusadd.view;

import android.app.Dialog;
import android.content.Context;

import com.wrmoney.administrator.plusadd.R;

/**
 * Created by Administrator on 2015/11/12.
 */
public class AlterPassFinishDialog extends Dialog {


    public AlterPassFinishDialog(Context context, int theme) {
        super(context, theme);
    }

    public AlterPassFinishDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        setContentView(R.layout.custom_alterpass_succeed_dialog);

    }


}
