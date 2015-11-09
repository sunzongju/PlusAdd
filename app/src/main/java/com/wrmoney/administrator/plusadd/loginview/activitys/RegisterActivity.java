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
        tv_phone.setText("�����Ѿ����Ͷ�����֤����"+str_phone+"���������������д��֤�룬��δ�յ������ĵȴ�");
        cb_sure = (CheckBox)this.findViewById(R.id.cb_sure);
        // bt_ok = (Button)this.findViewById(R.id.bt_ok);
        bt_timer=(Button)this.findViewById(R.id.bt_timer);
        time = new TimeCount(60000, 1000);//����CountDownTimer����
        bt_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params2= IdentifyParams.getSendIdentifyCode(str_phone, "1");
                utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params2, new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        time.start();  //��ʱ��ʼ
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
      String captcha=et_captcha.getText().toString();//��֤��?????
      String password=et_password.getText().toString();//����????
      final String repassword=et_repassword.getText().toString();//ȷ������?
      String invitCode=et_invitCode.getText().toString();//������ ??????
      String sure=cb_sure.getText().toString();//ͬ��Э�顢//
      if("".equals(captcha)){
       Toast.makeText(this,"��֤�벻��Ϊ��",Toast.LENGTH_SHORT).show();
      // ?????
      }else{
          if("".equals(password)||"".equals(repassword)){
              Toast.makeText(this,"���벻��Ϊ��",Toast.LENGTH_SHORT).show();
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
                                  Toast.makeText(RegisterActivity.this, strDe + "�ɹ�", Toast.LENGTH_SHORT).show();
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
                              Toast.makeText(RegisterActivity.this, "ע��ʧ��", Toast.LENGTH_SHORT).show();
                          }
                      });
                  }else {
                      Toast.makeText(this,"���벻һ��",Toast.LENGTH_SHORT).show();
                  }
              }else {
                  finish();
              }

          }

      }

  }
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//��������Ϊ��ʱ��,�ͼ�ʱ��ʱ����
        }
        @Override
        public void onFinish() {//��ʱ���ʱ����
            bt_timer.setText("������֤");
            bt_timer.setClickable(true);
        }
        @Override
        public void onTick(long millisUntilFinished){//��ʱ������ʾ
            bt_timer.setClickable(false);
            bt_timer.setText(millisUntilFinished /1000+"������·���");
        }
    }
}
