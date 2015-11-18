package com.wrmoney.administrator.plusadd.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.wrmoney.administrator.plusadd.R;

/**
 * 债权明细对话框
 * Created by Administrator on 2015/11/18.
 */
public class CreditorDetailDialog extends Dialog{
    public CreditorDetailDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_creditor_detail_dialog);

    }
}
