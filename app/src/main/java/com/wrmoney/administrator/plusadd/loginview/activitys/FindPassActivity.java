package com.wrmoney.administrator.plusadd.loginview.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wrmoney.administrator.plusadd.CommnActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.encode.LoginParams;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * Created by Administrator on 2015/9/21.
 */
public class FindPassActivity extends Activity {
    private EditText et_pass;
    private EditText et_repass;
    private HttpUtils utils;
    private String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_findpass);
        utils = HttpXutilTool.getUtils();
         mobile=getIntent().getStringExtra("MOBILE");
        et_pass=(EditText)this.findViewById(R.id.et_pass);
        et_repass=(EditText)this.findViewById(R.id.et_repass);
    }
    public void click(View view){
        String pass=et_pass.getText().toString();
        String repass=et_repass.getText().toString();
        if(pass.equals(repass)){
            RequestParams params= LoginParams.getFindCode(mobile, pass);
            utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String >() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    String result=responseInfo.result;
                    JSONObject obj= null;
                    try {
                        obj = new JSONObject(result);
                        String strResponse=obj.getString("argEncPara");
                        String strDe= DES3Util.decode(strResponse);
                        Toast.makeText(FindPassActivity.this,strDe,Toast.LENGTH_SHORT).show();
                        JSONObject obj2 = new JSONObject(strDe);
                        String rescode = obj2.getString("rescode");
                        //Toast.makeText(FindwordActivity.this,rescode , Toast.LENGTH_SHORT).show();
                        if ("0000".equals(rescode)) {
                            Intent intent = new Intent(FindPassActivity.this, CommnActivity.class);
                            //intent.putExtra("MOBILE", mobile);
                            //intent.putExtra("CAPTCHA", captcha);
                            startActivity(intent);
                        } else {
                            // Toast.makeText(FindwordActivity.this, "???", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onFailure(HttpException e, String s) {
                         e.printStackTrace();
                }
            });
        }else {
            Toast.makeText(this,"Ê§°Ü",Toast.LENGTH_SHORT).show();
        }

    }
}
