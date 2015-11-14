package com.wrmoney.administrator.plusadd.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.wrmoney.administrator.plusadd.R;

/**
 * 修改密码成功
 * Created by Administrator on 2015/11/12.
 */
public class AlterPassFinishDialog extends Dialog {


    public AlterPassFinishDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_alterpass_succeed_dialog);
    }

}
