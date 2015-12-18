package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.R;

/**
 * Created by Administrator on 2015/12/18.
 */
public class ActivityFDetailActivity extends BaseActivity{
    private String url;
    private WebView wv_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_activity_detail);
        url=getIntent().getStringExtra("URL");
        wv_activity=(WebView)this.findViewById(R.id.wv_activity);
        wv_activity.loadUrl(url);

    }
}
