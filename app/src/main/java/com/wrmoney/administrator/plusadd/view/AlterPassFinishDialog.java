package com.wrmoney.administrator.plusadd.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.loginview.activitys.PhoneActivity;

/**
 * 修改密码成功
 * Created by Administrator on 2015/11/12.
 */
public class AlterPassFinishDialog extends Dialog {


    private Button btn_finish;
    private Context context;

    public AlterPassFinishDialog(Context context, int theme) {
        super(context, theme);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_alterpass_succeed_dialog);
//        btn_finish=(Button)this.findViewById(R.id.btn_finish);
//        btn_finish.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(context, PhoneActivity.class);
//                context.startActivity(intent);
////                context.fileList();
//
//            }
//        });
    }


}
