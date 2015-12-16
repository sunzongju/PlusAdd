package com.wrmoney.administrator.plusadd.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.wrmoney.administrator.plusadd.R;

/**
 * 计算器弹出框
 * Created by Administrator on 2015/11/13.
 */
public class ClaculatorDialog extends Dialog {
    public ClaculatorDialog(Context context, int theme) {
        super(context, theme);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_caculator_dialog);
    }
}
