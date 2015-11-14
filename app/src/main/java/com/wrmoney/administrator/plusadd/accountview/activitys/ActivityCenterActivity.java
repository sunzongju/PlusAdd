package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

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
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.SingleUserIdTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * �活动专区�
 * Created by Administrator on 2015/11/2.
 */
public class ActivityCenterActivity extends BaseActivity {
    private ListView lv_news;
    private HttpUtils utils;
    private String userid;
    private List<ActivityListBean> list = new ArrayList<ActivityListBean>();
    private ActivityCenterAdapter adapter;
    private int[] pics={R.drawable.wallpaper_1,R.drawable.wallpaper_2,R.drawable.wallpaper_3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_activity);
        init();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        if(){
//
//        }
    }

    private void init() {
        getIntent().getStringExtra("USERID");
        for(int i=0;i<3;i++){
            ActivityListBean bean=new ActivityListBean();
            bean.setPic(pics[i]);
            list.add(bean);

        }
        lv_news = (ListView) this.findViewById(R.id.lv_news);
        adapter = new ActivityCenterAdapter(list, this);
        lv_news.setAdapter(adapter);
        userid = SingleUserIdTool.newInstance().getUserid();
        utils = HttpXutilTool.getUtils();
        RequestParams params = UserCenterParams.getMessageListCode(userid, "1", "10");
//        utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                String result = responseInfo.result;
//                JSONObject object = null;
//                try {
//                    object = new JSONObject(result);
//                    String strResponse = object.getString("argEncPara");
//                    String strDe = DES3Util.decode(strResponse);
//                    Toast.makeText(ActivityCenterActivity.this, strDe, Toast.LENGTH_SHORT).show();
//                    for (int i = 0; i < 10; i++) {
//                        ActivityListBean bean = new ActivityListBean();
//                        bean.setTitle("活动");
//                        bean.setTime("2015-08-16 14:30");
//                        bean.setContent("����������������������������������������");
//                        list.add(bean);
//                    }
//                    adapter.addAll(list);
//                    //JSONObject obj2=new JSONObject(strDe);
//                    // String rescode=obj2.getString("rescode");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                //Toast.makeText(LoginActivity.this, strDe, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//                e.printStackTrace();
//            }
//        });
    }
}
