package com.wrmoney.administrator.plusadd.loginview.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wrmoney.administrator.plusadd.CommnActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.encode.IdentifyParams;
import com.wrmoney.administrator.plusadd.encode.LoginParams;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.SingleUserIdTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;
import com.wrmoney.administrator.plusadd.view.DiaLog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/9/21.
 */
public class LoginActivity extends Activity {
    private String phone;
    private EditText et_password;
    private HttpUtils utils;
    private TextView tv_findword;
    private ProgressBar pro_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_login);
        pro_bar=(ProgressBar)this.findViewById(R.id.pro_bar);
        init();
    }

    private void init() {
        pro_bar.setVisibility(View.VISIBLE);
        utils = HttpXutilTool.getUtils();
        phone = getIntent().getStringExtra("PHONE");
        et_password = (EditText) this.findViewById(R.id.et_password);
        tv_findword = (TextView) this.findViewById(R.id.tv_findword);
        tv_findword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = IdentifyParams.getSendIdentifyCode(phone, "2");
                utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Intent intent = new Intent(LoginActivity.this, FindwordActivity.class);
                        intent.putExtra("MOBILE", phone);
                        startActivity(intent);
                        tv_findword.setClickable(false);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    public void click(View view) {
        String password = et_password.getText().toString();
        RequestParams params = LoginParams.getLoginCode(phone, password);
        utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                try {
                    JSONObject object = new JSONObject(result);
                    String strResponse = object.getString("argEncPara");
                    String strDe = DES3Util.decode(strResponse);
                    //Toast.makeText(LoginActivity.this, strDe, Toast.LENGTH_SHORT).show();
                    JSONObject obj2 = new JSONObject(strDe);
                    String rescode = obj2.getString("rescode");
                    if ("0000".equals(rescode)) {
                        String userID = obj2.getString("ID");
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        SingleUserIdTool.newInstance().setUserid(userID);
                        Intent intent = new Intent(LoginActivity.this, CommnActivity.class);
                        //intent.putExtra("ID",userID);
                        startActivity(intent);
                        finish();
                    } else if ("0001".equals(rescode)) {
                        DiaLog.showDialog(LoginActivity.this, "您输入密码错误，请重新输入");
                        //Toast.makeText(LoginActivity.this,"账号或者密码错误，请重新输入",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                pro_bar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                pro_bar.setVisibility(View.GONE);
            }
        });
    }
}
