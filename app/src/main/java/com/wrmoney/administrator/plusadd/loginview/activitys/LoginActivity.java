package com.wrmoney.administrator.plusadd.loginview.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.CommnActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.encode.IdentifyParams;
import com.wrmoney.administrator.plusadd.encode.LoginParams;
import com.wrmoney.administrator.plusadd.financingview.activitys.InvestActivity;
import com.wrmoney.administrator.plusadd.tools.ActionBarSet;
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
public class LoginActivity extends BaseActivity {
    private String phone;
    private EditText et_password;
    private HttpUtils utils;
    private TextView tv_findword;
    private ProgressBar pro_bar;
    private String planId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_login);
        ActionBarSet.setActionBar(this);
        TextView tv_banner=(TextView)this.findViewById(R.id.tv_banner);
        tv_banner.setText("输入登录密码");
        pro_bar=(ProgressBar)this.findViewById(R.id.pro_bar);
        //init();
    }

    private void init() {
        utils = HttpXutilTool.getUtils();
        phone = getIntent().getStringExtra("PHONE");
        planId=getIntent().getStringExtra("PLANID");
        et_password = (EditText) this.findViewById(R.id.et_password);
        tv_findword = (TextView) this.findViewById(R.id.tv_findword);
        String tag=getIntent().getStringExtra("TAG");
        if("tag".equals(tag)){
            tv_findword.setVisibility(View.INVISIBLE);
        }
        tv_findword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FindwordActivity.class);
                intent.putExtra("MOBILE", phone);
                startActivity(intent);
                tv_findword.setClickable(false);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    public void click(View view) {
        pro_bar.setVisibility(View.VISIBLE);
        String password = et_password.getText().toString();
       // password="123456";
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
                        SingleUserIdTool tool=SingleUserIdTool.newInstance();
                        tool.setUserid(userID);
                        tool.setPhoneNum(phone);
                        if(planId==null){
                            Intent intent = new Intent(LoginActivity.this, CommnActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }else{
                            Intent intent1=new Intent(LoginActivity.this, InvestActivity.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent1.putExtra("PLANID",planId);
                            startActivity(intent1);
                        }
                       // LoginActivity.this.finish();
                        finish();
                    } else if ("0001".equals(rescode)) {
                        DiaLog.showDialog(LoginActivity.this, "您输入的密码有误请重新输入");
                        //Toast.makeText(LoginActivity.this,"�˺Ż��������������������",Toast.LENGTH_SHORT).show();
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
