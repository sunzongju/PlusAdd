package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.encode.RechargeParams;
import com.wrmoney.administrator.plusadd.tools.ActionBarSet;
import com.wrmoney.administrator.plusadd.tools.CheckNetTool;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.SingleUserIdTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * ȡ�ֽ���
 * Created by Administrator on 2015/11/2.
 */
public class EssayActivity extends BaseActivity{
    private String userid;
    private EditText et_essay;
    private HttpUtils utils;
    private EditText et_captcha;
    private WebView wv_essay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_essay);
        ActionBarSet.setActionBar(this);
        TextView tv_banner=(TextView)this.findViewById(R.id.tv_banner);
        tv_banner.setText("取现");
        userid= SingleUserIdTool.newInstance().getUserid();
        wv_essay=(WebView)this.findViewById(R.id.wv_essay);
        WebSettings webSettings = wv_essay.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        wv_essay.setWebViewClient(new WebViewClient() {
            ProgressDialog prDialog;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //prDialog = ProgressDialog.show(EssayActivity.this, null, "数据加载中...");
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
               // prDialog.dismiss();
                super.onPageFinished(view, url);
            }
        });
        wv_essay.loadUrl(UrlTool.essayUrl+userid);

    }

}
