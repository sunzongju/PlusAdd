package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

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
import com.wrmoney.administrator.plusadd.tools.BitmapHelper;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
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
public class ActivityCenterActivity extends BaseActivity {

    private HttpUtils utils;
    private String userid;
    private List<ActivityListBean> list = new ArrayList<ActivityListBean>();
    private ActivityCenterAdapter adapter;
    private int[] pics={R.drawable.wallpaper_1,R.drawable.wallpaper_2,R.drawable.wallpaper_3};
    private Integer currentPage=0;
    private Integer pageSize=10;
    private PullToRefreshListView lv_activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_activity);
        init();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void init() {
        BitmapHelper.init(this);
        userid=getIntent().getStringExtra("USERID");
        lv_activity = (PullToRefreshListView) this.findViewById(R.id.lv_activity);
        adapter = new ActivityCenterAdapter(list, this);
        lv_activity.setAdapter(adapter);
        userid = SingleUserIdTool.newInstance().getUserid();
        utils = HttpXutilTool.getUtils();
        RequestParams params = UserCenterParams.getActivityListCode(currentPage+"",pageSize+"");
        utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                JSONObject object = null;
                try {
                    object = new JSONObject(result);
                    String strResponse = object.getString("argEncPara");
                    String strDe = DES3Util.decode(strResponse);
                    Toast.makeText(ActivityCenterActivity.this, strDe, Toast.LENGTH_SHORT).show();

                    JSONObject object1=new JSONObject(strDe);
                    JSONArray array=object1.getJSONArray("msgList");
                    int len=array.length();
                    List<ActivityListBean> list = new ArrayList<ActivityListBean>();
                    for(int i=0;i<len;i++){
                        JSONObject object2=array.getJSONObject(i);
                        ActivityListBean bean=new ActivityListBean();
                        String activityId=object2.getString("activityId");
                        String activityImgUrl=object2.getString("activityImgUrl");
                        String activityStatus=object2.getString("activityStatus");
                        String activityTime=object2.getString("activityTime");
                       bean.setActivityId(activityId);
                        bean.setActivityImgUrl(activityImgUrl);
                        bean.setActivityStatus(activityStatus);
                        bean.setActivityTime(activityTime);
                        list.add(bean);
                    }
                    adapter.addAll(list);
//                    for (int i = 0; i < 10; i++) {
//                        ActivityListBean bean = new ActivityListBean();
//                        bean.setTitle("活动");
//                        bean.setTime("2015-08-16 14:30");
//                       // bean.setContent("����������������������������������������");
//                        //list.add(bean);
//                    }
                   // adapter.addAll(list);
                    //JSONObject obj2=new JSONObject(strDe);
                    // String rescode=obj2.getString("rescode");
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
