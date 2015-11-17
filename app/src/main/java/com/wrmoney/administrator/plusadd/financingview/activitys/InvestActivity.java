package com.wrmoney.administrator.plusadd.financingview.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.view.ClaculatorDialog;

/**
 * Created by Administrator on 2015/11/3.
 */
public class InvestActivity extends BaseActivity implements View.OnClickListener{
    private Button btn_detail;
    private Button btn_join;
    private ImageView iv_cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financing_invest);
        btn_detail=(Button)this.findViewById(R.id.btn_detail);
        btn_join=(Button)this.findViewById(R.id.btn_join);
        iv_cal=(ImageView)this.findViewById(R.id.iv_cal);
        iv_cal.setOnClickListener(this);
        btn_detail.setOnClickListener(this);
        btn_join.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_detail:
                Intent intent0=new Intent(this,InvestDetailActivity.class);
                startActivity(intent0);
                break;
            case R.id.btn_join:
                Intent intent1=new Intent(this,InvestJoinActivity.class);
                startActivity(intent1);
                break;
            case R.id.iv_cal:
                ClaculatorDialog dialog=new ClaculatorDialog(this,R.style.dialog);
                dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
                dialog.show();
                break;
            default:
                break;

        }
    }
}
