package com.wrmoney.administrator.plusadd.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.wrmoney.administrator.plusadd.R;

/**
 * Created by Administrator on 2015/11/13.
 */
public class MoneyShorDialog extends Dialog {
    public MoneyShorDialog(Context context) {
        super(context);
    }

    public MoneyShorDialog (Context context, int theme) {
        super(context, theme);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.custom_money_short_dialog);
    }
}
