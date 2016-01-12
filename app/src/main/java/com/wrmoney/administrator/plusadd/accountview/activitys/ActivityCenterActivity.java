package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.PullToRefreshWebView2;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.accountview.adapters.ActivityCenterAdapter;
import com.wrmoney.administrator.plusadd.bean.ActivityListBean;
import com.wrmoney.administrator.plusadd.encode.UserCenterParams;
import com.wrmoney.administrator.plusadd.tools.ActionBarSet;
import com.wrmoney.administrator.plusadd.tools.BitmapHelper;
import com.wrmoney.administrator.plusadd.tools.CheckNetTool;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.NetworkAvailable;
import com.wrmoney.administrator.plusadd.tools.SingleUserIdTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * �活动专区�
 * Created by Administrator on 2015/11/2.
 */
public class ActivityCenterActivity extends BaseActivity implements View.OnClickListener{

    private HttpUtils utils;
    private String userid;
    private List<ActivityListBean> list = new ArrayList<ActivityListBean>();
    private ActivityCenterAdapter adapter;
    private int[] pics={R.drawable.wallpaper_1,R.drawable.wallpaper_2,R.drawable.wallpaper_3};
    private Integer currentPage=0;
    private Integer pageSize=10;
    private PullToRefreshListView lv_activity;
    private int current=1;
    private WebView wv_center_activity;
    private String url2;
    private ILoadingLayout loadingLayoutProxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_activity2);
        TextView tv_banner=(TextView)this.findViewById(R.id.tv_banner);
        tv_banner.setText("活动专区");
        ActionBarSet.setHelpBar(this);
        wv_center_activity=(WebView)this.findViewById(R.id.wv_center_activity);
        WebSettings webSettings = wv_center_activity.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        wv_center_activity.setWebChromeClient(new WebChromeClient());
        wv_center_activity.setWebViewClient(new WebViewClient() {
            ProgressDialog prDialog;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                prDialog = ProgressDialog.show(ActivityCenterActivity.this, null, "数据加载中...");
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if(prDialog.isShowing()){
                    prDialog.dismiss();
                }
                super.onPageFinished(view, url);
            }
        });
        wv_center_activity.loadUrl(UrlTool.activityUrl);
        ImageView iv_return=(ImageView)this.findViewById(R.id.iv_return);
        iv_return.setOnClickListener(this);
        LinearLayout viewById = (LinearLayout) this.findViewById(R.id.lv_return);
        viewById.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        url2 =wv_center_activity.getUrl();
        if (url2.equals(UrlTool.activityUrl)) {
            finish();
        } else {
            wv_center_activity.loadUrl(UrlTool.activityUrl);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void init() {
        BitmapHelper.init(this);
        utils = HttpXutilTool.getUtils();
        userid=getIntent().getStringExtra("USERID");
        dataRequest(current);
        lv_activity = (PullToRefreshListView) this.findViewById(R.id.lv_activity);
        View v= LayoutInflater.from(this).inflate(R.layout.empty_view,null);
        lv_activity.setEmptyView(v);
        adapter = new ActivityCenterAdapter(list, this);
        lv_activity.setAdapter(adapter);
        loadingLayoutProxy = lv_activity.getLoadingLayoutProxy();
        loadingLayoutProxy.setPullLabel("");
        lv_activity.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                current=1;
                list.clear();
                dataRequest(current);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                current++;
                dataRequest(current);
            }
        });
        userid = SingleUserIdTool.newInstance().getUserid();


    }

    /**
     * 数据请求
     */
    public void dataRequest(int current){
        Boolean b=CheckNetTool.checkNet(this);
        if(b){
            RequestParams params = UserCenterParams.getActivityListCode(current+"",pageSize+"");
            utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    String result = responseInfo.result;
                    JSONObject object = null;
                    try {
                        List<ActivityListBean> list2 = new ArrayList<ActivityListBean>();
                        object = new JSONObject(result);
                        String strResponse = object.getString("argEncPara");
                        String strDe = DES3Util.decode(strResponse);
//                    Toast.makeText(ActivityCenterActivity.this, strDe, Toast.LENGTH_SHORT).show();
                        // Log.i("========图片",strDe);
                        JSONObject object1=new JSONObject(strDe);
                        JSONArray array=object1.getJSONArray("msgList");
                        int len=array.length();
                        for(int i=0;i<len;i++){
                            JSONObject object2=array.getJSONObject(i);
                            ActivityListBean bean=new ActivityListBean();
                            String activityId=object2.getString("activityId");
                            String activityImgUrl=object2.getString("activityImgUrl");
                            String activityStatus=object2.getString("activityStatus");
                            String activityTime=object2.getString("activityTime");
                            bean.setBeginTimeStr(object2.getString("beginTimeStr"));
                            bean.setEndTimeStr(object2.getString("endTimeStr"));
                            bean.setActivityId(activityId);
                            bean.setActivityImgUrl(activityImgUrl);
                            bean.setActivityStatus(activityStatus);
                            bean.setActivityTime(activityTime);
                            list2.add(bean);
                        }
                        adapter.addAll(list2);
                        lv_activity.onRefreshComplete();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(LoginActivity.this, strDe, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    e.printStackTrace();
                }
            });
        }
    }


}
