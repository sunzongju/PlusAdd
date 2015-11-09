package com.wrmoney.administrator.plusadd.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wrmoney.administrator.plusadd.R;

/**
 * Created by Administrator on 2015/10/8.
 */
public class DiaLog {
    public static void showDialog(Context context, String str) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        //dialog.setTitle("ÄãºÃ");
        View view1 = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null);
        TextView tv = (TextView) view1.findViewById(R.id.tv_content);
        tv.setText(str);
        dialog.setView(view1);
        Button btn = (Button) view1.findViewById(R.id.bt_dialog);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
