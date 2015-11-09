package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.encode.UserCenterParams;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 投资管理界面
 * Created by Administrator on 2015/9/22.
 */
public class InvestMentActivity extends BaseActivity{
    private String userid;
    private HttpUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_invest);
        init();
    }

    private void init() {
        userid = getIntent().getStringExtra("USERID");
        utils = HttpXutilTool.getUtils();
        RequestParams params = UserCenterParams.getInvestManagerCode(userid, "0", "1", "10");
//        utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                String result = responseInfo.result;
//                JSONObject object = null;
//                try {
//                    object = new JSONObject(result);
//                    String strResponse = object.getString("argEncPara");
//                    String strDe = DES3Util.decode(strResponse);
//                    Toast.makeText(InvestMentActivity.this, strDe, Toast.LENGTH_SHORT).show();
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
//                Toast.makeText(InvestMentActivity.this, "失败", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
