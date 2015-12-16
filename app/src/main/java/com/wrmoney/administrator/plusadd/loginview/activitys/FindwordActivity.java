package com.wrmoney.administrator.plusadd.loginview.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.wrmoney.administrator.plusadd.encode.IdentifyParams;
import com.wrmoney.administrator.plusadd.tools.ActionBarSet;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 找回密码�����
 * Created by Administrator on 2015/9/21.
 */
public class FindwordActivity extends BaseActivity {
    private TextView bt_timer;
    private TextView tv_phone;
    private String mobile;
    private EditText et_captcha;
    private HttpUtils utils;
    private String captcha;
    private TimeCount time;
    private String resultStart;
    private String resultEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_findword);
        ActionBarSet.setActionBar(this);
        TextView tv_banner=(TextView)this.findViewById(R.id.tv_banner);
        tv_banner.setText("找回密码");
        init();
    }

    private void init() {
        utils = HttpXutilTool.getUtils();
        Intent intent = getIntent();
        mobile = getIntent().getStringExtra("MOBILE");
        resultStart=mobile.substring(0, 3);
        resultEnd= mobile.substring(7,11);
        et_captcha = (EditText) this.findViewById(R.id.et_captcha);
        tv_phone = (TextView) this.findViewById(R.id.tv_phone);
        bt_timer = (TextView) this.findViewById(R.id.bt_timer);
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
//        bt_timer.callOnClick();
//        time.start();
        bt_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_phone.setText("我们已经发送短信验证码至" + resultStart+"****"+resultEnd + "，请在输入框内填写验证码，若未收到请耐心等侯。");
                time.start();
                sendCode();
            }
        });
    }

    /**
     * 发送验证码
     */
    public void sendCode(){
        RequestParams params = IdentifyParams.getSendIdentifyCode(mobile, "2");
        utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
               // Toast.makeText(FindwordActivity.this,"验证码发送成功",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(HttpException e, String s) {
               // Toast.makeText(FindwordActivity.this,"验证码发送失败",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });
    }

    public void click(View view) {
        captcha = et_captcha.getText().toString();
        RequestParams params = IdentifyParams.getCheckIdentifyCode(mobile, captcha);
        utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                JSONObject object = null;
                try {
                    object = new JSONObject(result);
                    String strResponse = object.getString("argEncPara");
                    String strDe = DES3Util.decode(strResponse);
                    //Toast.makeText(FindwordActivity.this, strDe, Toast.LENGTH_SHORT).show();
                    JSONObject obj2 = new JSONObject(strDe);
                    String rescode = obj2.getString("rescode");
                    //Toast.makeText(FindwordActivity.this,rescode , Toast.LENGTH_SHORT).show();
                    if ("0000".equals(rescode)) {
                        Intent intent = new Intent(FindwordActivity.this, FindPassActivity.class);
                        intent.putExtra("MOBILE", mobile);
                        //intent.putExtra("CAPTCHA", captcha);
                        startActivity(intent);
                    } else {
                        // Toast.makeText(FindwordActivity.this, "验证码输入错误", Toast.LENGTH_SHORT).show();
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
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔?
        }

        @Override
        public void onFinish() {//计时完毕时触发
            bt_timer.setText("点击获取验证码");
            bt_timer.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//
            bt_timer.setClickable(false);
            bt_timer.setText(millisUntilFinished / 1000 + "秒后重新发送");
        }
    }
}
