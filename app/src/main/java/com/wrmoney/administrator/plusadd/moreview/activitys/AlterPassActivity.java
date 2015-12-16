package com.wrmoney.administrator.plusadd.moreview.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.wrmoney.administrator.plusadd.CommnActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.encode.SetUpParams;
import com.wrmoney.administrator.plusadd.tools.ActionBarSet;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.SingleUserIdTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;
import com.wrmoney.administrator.plusadd.view.AlterPassFinishDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/10/10.
 */
public class AlterPassActivity extends BaseActivity {
    private RequestParams params;
    private HttpUtils utils;
    private EditText old_pwd;
    private EditText new_pwd;
    private String userId;
    private String oldPwd;
    private String password;
    private AlterPassFinishDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_alter_pass);
        ActionBarSet.setActionBar(this);
        TextView tv_banner=(TextView)this.findViewById(R.id.tv_banner);
        tv_banner.setText("修改登录密码");
        utils = HttpXutilTool.getUtils();
        params = new RequestParams();
        old_pwd = (EditText) this.findViewById(R.id.et_oldpwd);
        new_pwd = (EditText) this.findViewById(R.id.et_newpwd);
        userId = SingleUserIdTool.newInstance().getUserid();


    }

    public void click(View view) {
        oldPwd = old_pwd.getText().toString();
        password = new_pwd.getText().toString();
        try {
            // String json="{ inface:'WRMI100001',mobile:'13651087998'}";
            //String str = DES3Util.encode(json);
            params = SetUpParams.getLoginCode(userId, oldPwd, password);
            //params.addQueryStringParameter("argEncPara", str);
            utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    String result = responseInfo.result;
                    try {
                        JSONObject obj = new JSONObject(result);
                        String strResponse = obj.getString("argEncPara");
                        String strDe = DES3Util.decode(strResponse);
                      //  Toast.makeText(AlterPassActivity.this, strDe, Toast.LENGTH_SHORT).show();
                        JSONObject obj2=new JSONObject(strDe);
                        String rescode = obj2.getString("rescode");
                        if ("0000".equals(rescode)) {
                            dialog=new AlterPassFinishDialog(AlterPassActivity.this,R.style.dialog);
                            dialog.setCanceledOnTouchOutside(true);//���õ��Dialog�ⲿ��������ر�Dialog
                            dialog.show();
//                DiaLog.AlterPassFinishDialog(this,"");
                            Button btn_finsish=(Button)dialog.findViewById(R.id.btn_finish);
                            TextView textView=(TextView)dialog.findViewById(R.id.tv_title);
                            textView.setText("密码修改成功");
                            btn_finsish.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
//                                    Intent intent = new Intent(AlterPassActivity.this, CommnActivity.class);
//                                    AlterPassActivity.this.startActivity(intent);
                                    AlterPassActivity.this.finish();
                                }
                            });
                        } else {
                            // Toast.makeText(FindwordActivity.this, "???", Toast.LENGTH_SHORT).show();
                        }
//                            String type=obj2.getString("isRegFlag");
//                            Toast.makeText(AlterPassActivity.this,"����ʧ��",Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    //e.getExceptionCode();
                    e.printStackTrace();
                 //   Toast.makeText(AlterPassActivity.this, "����ɹ�", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
