package com.wrmoney.administrator.plusadd.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.financingview.activitys.InvestJoinActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/16.
 */
public class VoucherDialog extends Dialog {
    private Button btn_sure;
    private Context context;
    private ListView lv_vou;
    private List<String> list=new ArrayList<String>();

    public VoucherDialog(Context context, int theme) {
        super(context, theme);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_voucher_dialog);

    }
}
