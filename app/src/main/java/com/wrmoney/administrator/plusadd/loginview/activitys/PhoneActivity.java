package com.wrmoney.administrator.plusadd.loginview.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.encode.IdentifyParams;
import com.wrmoney.administrator.plusadd.encode.LoginParams;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;
import com.wrmoney.administrator.plusadd.view.DiaLog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 输入手机号进行验证����֤
 * Created by Administrator on 2015/9/7.
 */
public class PhoneActivity extends Activity {
    private EditText et_phone;
    private HttpUtils utils;
    private RequestParams params;
    private String strPhone;
    private ProgressBar pro_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);
        //Log.i("========","phoneCreate");
        HttpXutilTool.init();
        et_phone = (EditText) this.findViewById(R.id.et_phone);
        utils = HttpXutilTool.getUtils();
        params = new RequestParams();
        pro_bar=(ProgressBar)this.findViewById(R.id.pro_bar);
        String str = null;
    }

    public void click(View view) {
        pro_bar.setVisibility(View.VISIBLE);
        strPhone = et_phone.getText().toString().trim();
        int i = strPhone.length();
        if (i == 11) {
            try {
                // String json="{ inface:'WRMI100001',mobile:'13651087998'}";
                //String str = DES3Util.encode(json);
                params = LoginParams.getPhoneCode(strPhone);
                //params.addQueryStringParameter("argEncPara", str);
                utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL,params, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        try {
                            JSONObject obj = new JSONObject(result);
                            String strResponse = obj.getString("argEncPara");
                            String strDe = DES3Util.decode(strResponse);
                            Toast.makeText(PhoneActivity.this, strDe, Toast.LENGTH_SHORT).show();
                            JSONObject obj2 = new JSONObject(strDe);
                            String type = obj2.getString("isRegFlag");
                            Toast.makeText(PhoneActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            if ("1".equals(type)) {
                                Intent intent = new Intent(PhoneActivity.this, LoginActivity.class);
                                intent.putExtra("PHONE", strPhone);
                                startActivityForResult(intent,300);
                                finish();
                            } else if ("0".equals(type)) {
                                RequestParams params2 = IdentifyParams.getSendIdentifyCode(strPhone, "1");
                                utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params2, new RequestCallBack<String>() {
                                    @Override
                                    public void onSuccess(ResponseInfo<String> responseInfo) {
                                        Intent intent = new Intent(PhoneActivity.this, RegisterActivity.class);
                                        intent.putExtra("PHONE", strPhone);
                                        //startActivity(intent);
                                        startActivityForResult(intent,400);
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(HttpException e, String s) {
                                        e.printStackTrace();
                                    }
                                });
                            } else {
                                // Toast.makeText(PhoneActivity.this, "ʧ��", Toast.LENGTH_SHORT).show();
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
                        //e.getExceptionCode();
                        e.printStackTrace();
                        Toast.makeText(PhoneActivity.this, "链接失败", Toast.LENGTH_SHORT).show();
                        pro_bar.setVisibility(View.GONE);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
//            AlertDialog.Builder builder=new AlertDialog.Builder(this);
//            builder.setTitle("��ʾ");
//            builder.setMessage("��������ֻ����������������");
//            builder.show();
            DiaLog.showDialog(PhoneActivity.this, "您输入的手机号有误");
            // Toast.makeText(this,i+"������ֻ����������������",Toast.LENGTH_SHORT).show();
        }

    }
}
