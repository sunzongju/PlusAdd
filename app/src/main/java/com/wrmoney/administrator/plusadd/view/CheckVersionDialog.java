package com.wrmoney.administrator.plusadd.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.wrmoney.administrator.plusadd.R;

/**
 * Created by Administrator on 2015/11/23.
 */
public class CheckVersionDialog extends Dialog {

    public CheckVersionDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_version_check);

    }
}
