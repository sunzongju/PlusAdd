package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.os.Bundle;
import android.view.View;
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
import com.wrmoney.administrator.plusadd.encode.RechargeParams;
import com.wrmoney.administrator.plusadd.tools.ActionBarSet;
import com.wrmoney.administrator.plusadd.tools.CheckNetTool;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.SingleUserIdTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 充值界面ֵ����
 * Created by Administrator on 2015/11/2.
 */
public class RechargeActivity extends BaseActivity {

    private EditText et_recharge;
    private HttpUtils utils;
    private String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_recharge);
        ActionBarSet.setActionBar(this);
        TextView tv_banner=(TextView)this.findViewById(R.id.tv_banner);
        tv_banner.setText("充值");
        userid = SingleUserIdTool.newInstance().getUserid();
        et_recharge = (EditText) this.findViewById(R.id.et_recharge);
        utils = HttpXutilTool.getUtils();
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                Boolean b = CheckNetTool.checkNet(this);
                if(b){
                    String rechargeAmount = et_recharge.getText().toString();
                    RequestParams params = RechargeParams.getRechargeCode(userid, rechargeAmount);
                    utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            String result = responseInfo.result;
                            JSONObject object = null;
                            try {
                                object = new JSONObject(result);
                                String strResponse = object.getString("argEncPara");
                                String strDe = DES3Util.decode(strResponse);
                                //  Toast.makeText(RechargeActivity.this, strDe, Toast.LENGTH_SHORT).show();
                                // JSONObject obj2 = new JSONObject(strDe);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(HttpException e, String s) {
                            //Toast.makeText(RechargeActivity.this, "��ֵʧ��", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    });
                }
                break;
        }


    }
}
