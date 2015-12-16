package com.wrmoney.administrator.plusadd.loginview.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
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
import com.wrmoney.administrator.plusadd.encode.LoginParams;
import com.wrmoney.administrator.plusadd.tools.ActionBarSet;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;
import com.wrmoney.administrator.plusadd.view.AlterPassFinishDialog;
import com.wrmoney.administrator.plusadd.view.DiaLog;

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
    private EditText et_pass;
    private EditText et_repass;
    private AlterPassFinishDialog dialog;
    private String pass;
    private String pass2;

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
        resultEnd= mobile.substring(7, 11);
        et_pass=(EditText)this.findViewById(R.id.et_pass);
        et_repass=(EditText)this.findViewById(R.id.et_repass);
        et_captcha = (EditText) this.findViewById(R.id.et_captcha);
        tv_phone = (TextView) this.findViewById(R.id.tv_phone);
        bt_timer = (TextView) this.findViewById(R.id.bt_timer);
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
//        bt_timer.callOnClick();
//        time.start();
        bt_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_phone.setText("我们已经发送短信验证码至" + resultStart + "****" + resultEnd + "，请在输入框内填写验证码，若未收到请耐心等侯。");
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
        pass=et_pass.getText().toString();
        pass2=et_repass.getText().toString();

        if(captcha==null||"".equals(captcha)) {
            DiaLog.showDialog(FindwordActivity.this, "验证码不能为空");
        }else {
            if(pass==null||"".equals(pass)){
                DiaLog.showDialog(FindwordActivity.this, "密码不能为空");
            }else{
                if(pass2==null||"".equals(pass2)){
                    DiaLog.showDialog(FindwordActivity.this, "重置密码不能为空");
                }else{
                    if(!pass2.equals(pass)){
                        DiaLog.showDialog(FindwordActivity.this, "两次输入的密码不一致");
                    }else{
                        codeCorrect(captcha);
                    }
                }
            }
        }

    }
    /**
     * 判断验证码是否正确
     */
    public void codeCorrect(String captcha){
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
//                        Intent intent = new Intent(FindwordActivity.this, FindPassActivity.class);
//                        intent.putExtra("MOBILE", mobile);
//                        //intent.putExtra("CAPTCHA", captcha);
//                        startActivity(intent);
                        dataRequest(pass);
                    } else {
                        DiaLog.showDialog(FindwordActivity.this, "验证码错误");
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
    /**
     * 请求数据
     * @param pass
     */
    public void dataRequest(String pass){
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
                    // Toast.makeText(FindPassActivity.this,strDe,Toast.LENGTH_SHORT).show();
                    JSONObject obj2 = new JSONObject(strDe);
                    String rescode = obj2.getString("rescode");
                    //Toast.makeText(FindwordActivity.this,rescode , Toast.LENGTH_SHORT).show();
                    if ("0000".equals(rescode)) {
//                            Intent intent = new Intent(FindPassActivity.this, CommnActivity.class);
//                            //intent.putExtra("MOBILE", mobile);
//                            //intent.putExtra("CAPTCHA", captcha);
//                            startActivity(intent);

                        dialog=new AlterPassFinishDialog(FindwordActivity.this,R.style.dialog);
                        dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
                        dialog.show();
//                DiaLog.AlterPassFinishDialog(this,"");
                        Button btn_finsish=(Button)dialog.findViewById(R.id.btn_finish);
                        btn_finsish.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                // Intent intent = new Intent(FindPassActivity.this, CommnActivity.class);
                                Intent intent = new Intent(FindwordActivity.this, LoginActivity.class);
                                intent.putExtra("PHONE", mobile);
                                intent.putExtra("TAG","tag");
                                FindwordActivity.this.startActivity(intent);
                            }
                        });
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
