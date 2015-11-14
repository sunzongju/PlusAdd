package com.wrmoney.administrator.plusadd.loginview.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/9/7.
 */
public class RegisterActivity extends Activity {
    private EditText et_captcha;
    private EditText et_password;
    private EditText et_repassword;
    private CheckBox cb_sure;
    private Button bt_ok;
    private String str_phone;
    private HttpUtils utils;
    private EditText et_invitCode;
    private Button bt_timer;
    private TimeCount time;
    private TextView tv_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        init();
    }

    private void init() {
        utils = HttpXutilTool.getUtils();
        str_phone=getIntent().getStringExtra("PHONE");//????
        et_captcha = (EditText)this.findViewById(R.id.et_captcha);
        et_password = (EditText)this.findViewById(R.id.et_password);
        et_repassword = (EditText)this.findViewById(R.id.et_repassword);
        et_invitCode=(EditText)this.findViewById(R.id.et_invitCode);
        tv_phone=(TextView)this.findViewById(R.id.tv_phone);
        tv_phone.setText("我们已经发送短信验证码至"+str_phone+"，请在输入框内填写验证码，若未收到请耐心等待");
        cb_sure = (CheckBox)this.findViewById(R.id.cb_sure);
        // bt_ok = (Button)this.findViewById(R.id.bt_ok);
        bt_timer=(Button)this.findViewById(R.id.bt_timer);
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象���
        bt_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params2= IdentifyParams.getSendIdentifyCode(str_phone, "1");
                utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params2, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        time.start();  //计时开始
                    }
                    @Override
                    public void onFailure(HttpException e, String s) {
                        e.printStackTrace();
                    }
                });

            }
        });
    }

    public void click(View view){
      String captcha=et_captcha.getText().toString();//验证码?????
      String password=et_password.getText().toString();//密码????
      final String repassword=et_repassword.getText().toString();//确认密码?
      String invitCode=et_invitCode.getText().toString();//邀请码 ??????
      String sure=cb_sure.getText().toString();//同意协议、//
      if("".equals(captcha)){
       Toast.makeText(this,"验证码不能为空",Toast.LENGTH_SHORT).show();
      // ?????
      }else{
          if("".equals(password)||"".equals(repassword)){
              Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
          }else{
              if(cb_sure.isChecked()){
                  if(password.equals(repassword)){
                      RequestParams params= LoginParams.getRegisCode(str_phone, password, captcha, invitCode);
                      utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
                          @Override
                          public void onSuccess(ResponseInfo<String> responseInfo) {
                              String result = responseInfo.result;
                              JSONObject obj = null;
                              try {
                                  obj = new JSONObject(result);
                                  String strResponse = obj.getString("argEncPara");
                                  String strDe = DES3Util.decode(strResponse);
                                  Toast.makeText(RegisterActivity.this, strDe + "成功", Toast.LENGTH_SHORT).show();
//                      Intent intent=new Intent(PhoneActivity.this, RegisterActivity.class);
//                      intent.putExtra("PHONE",strPhone);
                                  //   startActivity(intent);
                              } catch (JSONException e) {
                                  e.printStackTrace();
                              } catch (Exception e) {
                                  e.printStackTrace();
                              }
                          }

                          @Override
                          public void onFailure(HttpException e, String s) {
                              e.printStackTrace();
                              Toast.makeText(RegisterActivity.this, "ע注册失败", Toast.LENGTH_SHORT).show();
                          }
                      });
                  }else {
                      Toast.makeText(this,"密码不一致",Toast.LENGTH_SHORT).show();
                  }
              }else {
                  finish();
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
            bt_timer.setText("重新验证֤");
            bt_timer.setClickable(true);
        }
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示����ʾ
            bt_timer.setClickable(false);
            bt_timer.setText(millisUntilFinished /1000+"秒后重新发送");
        }
    }
}
