package com.wrmoney.administrator.plusadd.loginview.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.wrmoney.administrator.plusadd.tools.ChangeString;
import com.wrmoney.administrator.plusadd.tools.CheckNetTool;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.SingleUserIdTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;
import com.wrmoney.administrator.plusadd.view.DiaLog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/9/7.
 */
public class RegisterActivity extends BaseActivity {
    private EditText et_captcha;
    private EditText et_password;
    private EditText et_repassword;
    private CheckBox cb_sure;
    private Button bt_ok;
    private String str_phone;
    private HttpUtils utils;
    private EditText et_invitCode;
    private TextView bt_timer;
    private TimeCount time;
    private TextView tv_phone;
    private RequestParams params2;
    private String invitCode2;
    private String resultStart;
    private String resultEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        ActionBarSet.setActionBar(this);
        TextView tv_banner=(TextView)this.findViewById(R.id.tv_banner);
        tv_banner.setText("注册");
        init();
    }

    private void init() {
        utils = HttpXutilTool.getUtils();
        str_phone=getIntent().getStringExtra("PHONE");//
        resultStart=str_phone.substring(0, 3);
        resultEnd= str_phone.substring(7, 11);
        params2 = IdentifyParams.getSendIdentifyCode(str_phone, "1");
        et_captcha = (EditText)this.findViewById(R.id.et_captcha);
        et_password = (EditText)this.findViewById(R.id.et_password);
        et_repassword = (EditText)this.findViewById(R.id.et_repassword);
        et_invitCode=(EditText)this.findViewById(R.id.et_invitCode);
        tv_phone=(TextView)this.findViewById(R.id.tv_phone);
        cb_sure = (CheckBox)this.findViewById(R.id.cb_sure);
        bt_timer=(TextView)this.findViewById(R.id.bt_timer);
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象���
        bt_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_phone.setText("我们已经发送短信验证码至" + resultStart + "****" + resultEnd + "，请在输入框内填写验证码，若未收到请耐心等待");
                time.start();
                sendCode();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cb_sure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(cb_sure.isChecked()){
                    Button bt_ok=(Button)RegisterActivity.this.findViewById(R.id.bt_ok);
                    bt_ok.setBackgroundResource(R.drawable.button_press);
                    bt_ok.setClickable(true);
                }else {
                    Button bt_ok=(Button)RegisterActivity.this.findViewById(R.id.bt_ok);
                    bt_ok.setBackgroundResource(R.color.gray);
                    bt_ok.setClickable(false);
                }
            }
        });
    }

    public void sendCode(){
        utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params2, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                 //计时开始
            }
            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
            }
        });

    }

    public void click(View view){

        Boolean b = CheckNetTool.checkNet(this);
        if(b){

            String captcha=et_captcha.getText().toString();//验证码
            String password=et_password.getText().toString();//密码
            final String repassword=et_repassword.getText().toString();//确认密码
            String invitCode=et_invitCode.getText().toString();//邀请码
            if(!"".equals(invitCode)&&invitCode!=null){
                // invitCode2=ChangeString.exChange(invitCode);
                invitCode2=invitCode;
            }else{
                invitCode2="";
            }
            //  Log.i("=======邀请码",invitCode2);
            String sure=cb_sure.getText().toString();//同意协议、//
            if("".equals(captcha)){
                DiaLog.showDialog(RegisterActivity.this, "验证码不能为空");
            }else{
                if(password==null||"".equals(password)){
                    DiaLog.showDialog(RegisterActivity.this, "密码不能为空");
                }else {
                    if(repassword==null||"".equals(repassword)){
                        DiaLog.showDialog(RegisterActivity.this, "重复密码不能为空");
                    }else {
                        if(password.equals(repassword)){
                            if(cb_sure.isChecked()){
                                RequestParams params= LoginParams.getRegisCode(str_phone, password, captcha, invitCode2);
                                utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
                                    @Override
                                    public void onSuccess(ResponseInfo<String> responseInfo) {
                                        String result = responseInfo.result;
                                        JSONObject obj = null;
                                        try {
                                            obj = new JSONObject(result);
                                            String strResponse = obj.getString("argEncPara");
                                            String strDe = DES3Util.decode(strResponse);
                                            //Log.i("=======注册成功",strDe);
                                            JSONObject object=new JSONObject(strDe);
                                            String str=object.getString("rescode");
                                            if("0000".equals(str)){
                                                String userId=object.getString("userId");
                                                SingleUserIdTool.newInstance().setUserid(userId);
                                                Intent intent=new Intent(RegisterActivity.this,LoginSuccessActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }else {
                                                DiaLog.showDialog(RegisterActivity.this, object.getString("resmsg"));
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
                                        DiaLog.showDialog(RegisterActivity.this, "注册失败");
                                    }
                                });
                            }else {

                            }
                        }else {
                            DiaLog.showDialog(RegisterActivity.this, "两次输入的密码不一致");
                        }
                    }
                }
            }

        }
  }
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔���
        }
        @Override
        public void onFinish() {//计时完毕时触发
            bt_timer.setBackgroundColor(getResources().getColor(R.color.orange));
            bt_timer.setText("点击发送验证码֤");
            bt_timer.setClickable(true);
        }
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示��
            bt_timer.setBackgroundColor(getResources().getColor(R.color.gray));// ��ʾ
            bt_timer.setClickable(false);
            bt_timer.setText(millisUntilFinished /1000+"秒后重新发送");
        }
    }
}
