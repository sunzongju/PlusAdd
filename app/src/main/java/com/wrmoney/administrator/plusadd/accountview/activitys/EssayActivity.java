package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
public class EssayActivity extends BaseActivity implements View.OnClickListener{
    private String userid;
    private EditText et_essay;
    private HttpUtils utils;
    private EditText et_captcha;
    private WebView wv_essay;
    private String url2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_essay);
        TextView tv_banner=(TextView)this.findViewById(R.id.tv_banner);
        tv_banner.setText("取现");
        ActionBarSet.setHelpBar(this);
        userid= SingleUserIdTool.newInstance().getUserid();
        wv_essay=(WebView)this.findViewById(R.id.wv_essay);
        WebSettings webSettings = wv_essay.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        wv_essay.setWebChromeClient(new WebChromeClient());
        //wv_essay.clearCache(true);
        wv_essay.setWebViewClient(new WebViewClient() {
            ProgressDialog prDialog;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //prDialog = ProgressDialog.show(EssayActivity.this, null, "数据加载中...");
                if(UrlTool.userCenterUrl.equals(url)){
                    finish();
                }
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i("=====取现55", UrlTool.essayUrl + userid);
                // prDialog.dismiss();
                if(UrlTool.userCenterUrl.equals(url)){
                    finish();
                }
                super.onPageFinished(view, url);
            }
        });

       // Log.i("=====取现",UrlTool.essayUrl + userid);
        wv_essay.loadUrl(UrlTool.essayUrl + userid);
        ImageView iv_return=(ImageView)this.findViewById(R.id.iv_return);
        iv_return.setOnClickListener(this);
        LinearLayout viewById = (LinearLayout) this.findViewById(R.id.lv_return);
        viewById.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        url2 = wv_essay.getUrl();
        if (url2.equals(UrlTool.essayUrl + userid)) {
            finish();
        } else {
           // Log.i("=========网址", url2);
            wv_essay.loadUrl(UrlTool.essayUrl+userid);
        }
    }
}
