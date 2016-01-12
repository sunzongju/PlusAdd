package com.wrmoney.administrator.plusadd.accountview.activitys;

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
import com.wrmoney.administrator.plusadd.CommnActivity;
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
 * 充值界面ֵ����
 * Created by Administrator on 2015/11/2.
 */
public class RechargeActivity extends BaseActivity implements View.OnClickListener{

    private EditText et_recharge;
    private HttpUtils utils;
    private String userid;
    private WebView wv_recharge;
    private String url2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_recharge);
        ActionBarSet.setActionBar(this);
        TextView tv_banner=(TextView)this.findViewById(R.id.tv_banner);
        tv_banner.setText("充值");
        ActionBarSet.setHelpBar(this);
        userid= SingleUserIdTool.newInstance().getUserid();
        wv_recharge=(WebView)this.findViewById(R.id.wv_recharge);
        WebSettings webSettings = wv_recharge.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        wv_recharge.setWebChromeClient(new WebChromeClient());
        //wv_recharge.clearCache(true);
        wv_recharge.setWebViewClient(new WebViewClient() {
            ProgressDialog prDialog;

//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Log.i("=======网址1111",url);
//                if(UrlTool.userCenterUrl.equals(url)){
//                    Intent intent1 = new Intent(RechargeActivity.this, CommnActivity.class);
//                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent1.putExtra("FLAG", "userCenterUrl");
//                    startActivity(intent1);
//                }
//                return true;
//            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if(UrlTool.userCenterUrl.equals(url)){
//                    Intent intent1 = new Intent(RechargeActivity.this, CommnActivity.class);
//                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent1.putExtra("FLAG", "userCenterUrl");
//                    startActivity(intent1);
                    finish();
                }
//                prDialog = ProgressDialog.show(RechargeActivity.this, null, "数据加载中...");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i("=====充值55", UrlTool.rechargeUrl + userid);
                if(UrlTool.userCenterUrl.equals(url)){
//                    Intent intent1 = new Intent(RechargeActivity.this, CommnActivity.class);
//                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent1.putExtra("FLAG", "userCenterUrl");
//                    startActivity(intent1);
                    finish();
                }
                    super.onPageFinished(view, url);
            }
        });
        //Log.i("=====充值", UrlTool.rechargeUrl + userid);
        wv_recharge.loadUrl(UrlTool.rechargeUrl + userid);
        ImageView iv_return=(ImageView)this.findViewById(R.id.iv_return);
        iv_return.setOnClickListener(this);
        LinearLayout viewById = (LinearLayout) this.findViewById(R.id.lv_return);
        viewById.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        url2 = wv_recharge.getUrl();
        if (url2.equals(UrlTool.rechargeUrl + userid)) {
            finish();
        } else {
            //Log.i("=========网址", url2);
            wv_recharge.loadUrl(UrlTool.rechargeUrl + userid);
        }
    }
}
