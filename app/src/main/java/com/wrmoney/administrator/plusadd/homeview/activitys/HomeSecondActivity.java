package com.wrmoney.administrator.plusadd.homeview.activitys;

import android.os.Bundle;
import android.widget.TextView;

import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.bean.BannerBean;
import com.wrmoney.administrator.plusadd.tools.ActionBarSet;

/**
 * Created by Administrator on 2016/1/4.
 */
public class HomeSecondActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_safe_02);
        ActionBarSet.setActionBar(this);
        TextView tv_banner=(TextView)this.findViewById(R.id.tv_banner);
        tv_banner.setText("我要安全理财");
    }
}
