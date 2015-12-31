package com.wrmoney.administrator.plusadd.loginview.activitys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.bean.PlanBean;
import com.wrmoney.administrator.plusadd.encode.IdentifyParams;
import com.wrmoney.administrator.plusadd.encode.LoginParams;
import com.wrmoney.administrator.plusadd.tools.ActionBarSet;
import com.wrmoney.administrator.plusadd.tools.CheckNetTool;
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
public class PhoneActivity extends BaseActivity {
    private EditText et_phone;
    private HttpUtils utils;
    private RequestParams params;
    private String strPhone;
    private ProgressBar pro_bar;
    private String planID;
    private ProgressDialog pd;
    private ProgressBar dd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);
        ActionBarSet.setActionBar(this);
        TextView tv_banner=(TextView)this.findViewById(R.id.tv_banner);
        tv_banner.setText("输入手机号");
        HttpXutilTool.init();
        et_phone = (EditText) this.findViewById(R.id.et_phone);
        utils = HttpXutilTool.getUtils();
        params = new RequestParams();
        //pro_bar=(ProgressBar)this.findViewById(R.id.pro_bar);
        String str = null;
        Intent intent=getIntent();
        planID=intent.getStringExtra("PLANID");
        pd=new ProgressDialog(this);

    }

    public void click(View view) {

        Boolean b = CheckNetTool.checkNet(this);
        if(b){
            pd=ProgressDialog.show(this,"","数据加载中...");
            strPhone = et_phone.getText().toString().trim();
            //strPhone="18500236430";
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
                                //  Toast.makeText(PhoneActivity.this, strDe, Toast.LENGTH_SHORT).show();
                                //Log.i("===========错误手机号", strDe);
                                JSONObject obj2 = new JSONObject(strDe);
                                if("0000".equals(obj2.getString("rescode"))){
                                    String type = obj2.getString("isRegFlag");
                                    if ("1".equals(type)) {
                                        Intent intent = new Intent(PhoneActivity.this, LoginActivity.class);
                                        intent.putExtra("PHONE", strPhone);
                                        intent.putExtra("PLANID", planID);
                                        startActivityForResult(intent, 300);
                                        pd.dismiss();
                                        //finish();
                                    } else if ("0".equals(type)) {
                                        Intent intent = new Intent(PhoneActivity.this, RegisterActivity.class);
                                        intent.putExtra("PHONE", strPhone);
                                        //startActivity(intent);
                                        startActivityForResult(intent, 400);
                                        pd.dismiss();
                                    }

                                }else {
                                    pd.dismiss();
                                    DiaLog.showDialog(PhoneActivity.this, obj2.getString("resmsg"));
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
                            pd.dismiss();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    pd.dismiss();
                }
            } else {
                DiaLog.showDialog(PhoneActivity.this, "您输入的手机号有误,请重新输入");
                pd.dismiss();
            }
        }
    }
}
