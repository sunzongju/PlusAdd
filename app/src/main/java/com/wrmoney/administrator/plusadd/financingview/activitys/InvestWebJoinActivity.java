package com.wrmoney.administrator.plusadd.financingview.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.CommnActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.homeview.activitys.HomefirstActivity;
import com.wrmoney.administrator.plusadd.tools.ActionBarSet;
import com.wrmoney.administrator.plusadd.tools.SingleUserIdTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;

/**
 * Created by Administrator on 2016/1/4.
 */
public class InvestWebJoinActivity  extends BaseActivity implements View.OnClickListener{
    private String userid;
    private WebView wv_gobuy;
    private String url1;
    private String url2;
    private String planId;
    private ProgressDialog prDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invest_web_join);
        TextView tv_banner=(TextView)this.findViewById(R.id.tv_banner);
        tv_banner.setText("买入产品名称");
        ActionBarSet.setHelpBar(this);
//        userid= SingleUserIdTool.newInstance().getUserid();
        final Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        // FinancingDetailBean bean = bundle.getParcelable("BEAN");
        planId=bundle.getString("PLANID");
        userid=bundle.getString("USERID");
        wv_gobuy=(WebView)this.findViewById(R.id.wv_gobuy);
        WebSettings webSettings = wv_gobuy.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        wv_gobuy.setWebChromeClient(new WebChromeClient());
        wv_gobuy.setWebViewClient(new WebViewClient() {
            // ProgressDialog prDialog;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // prDialog = ProgressDialog.show(InvestWebJoinActivity.this, null, "数据加载中...");
                Log.i("====网址111", wv_gobuy.getUrl());
                if (UrlTool.financeUrl.equals(url)) {
                    Intent intent1 = new Intent(InvestWebJoinActivity.this, CommnActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent1.putExtra("FLAG", "financeUrl");
                    startActivity(intent1);
                } else if (UrlTool.indexUrl.equals(url)) {
                    Intent intent1 = new Intent(InvestWebJoinActivity.this, CommnActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent1.putExtra("FLAG", "indexUrl");
                    startActivity(intent1);
                }
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i("====网址222", wv_gobuy.getUrl());
                if (UrlTool.financeUrl.equals(url)) {
                    Intent intent1 = new Intent(InvestWebJoinActivity.this, CommnActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent1.putExtra("FLAG", "financeUrl");
                    startActivity(intent1);
                } else if (UrlTool.indexUrl.equals(url)) {
                    Intent intent1 = new Intent(InvestWebJoinActivity.this, CommnActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent1.putExtra("FLAG", "indexUrl");
                    startActivity(intent1);
                }
                Log.i("====网址222", wv_gobuy.getUrl());
                super.onPageFinished(view, url);
            }
        });

        url1=UrlTool.buyUrl+userid+"&id="+planId;
        Log.i("========网络",url1);
        wv_gobuy.loadUrl(url1);
        ImageView iv_return=(ImageView)this.findViewById(R.id.iv_return);
        iv_return.setOnClickListener(this);
        LinearLayout viewById = (LinearLayout) this.findViewById(R.id.lv_return);
        viewById.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        url2 = wv_gobuy.getUrl();
        if (url2.equals(url1)) {
            finish();
        } else {
            Log.i("=========网址",url2);
            wv_gobuy.loadUrl(url1);
        }
    }
}
