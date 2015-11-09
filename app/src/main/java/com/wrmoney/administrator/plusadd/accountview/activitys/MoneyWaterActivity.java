package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.wrmoney.administrator.plusadd.accountview.adapters.MoneyWaterAdapter;
import com.wrmoney.administrator.plusadd.encode.UserCenterParams;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 资金流水界面
 * Created by Administrator on 2015/9/22.
 */
public class MoneyWaterActivity extends BaseActivity {
    private String userid;
    private HttpUtils utils;
    private ListView lv_water;
    private List<String> list=new ArrayList<String>();
    private MoneyWaterAdapter adapter;
    private LinearLayout prg1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_water);
        lv_water = (ListView)this.findViewById(R.id.lv_water);
        TextView tv=(TextView)this.findViewById(R.id.zhnawei);
       //TextView tv=new TextView(this);
        //prg1 = ((LinearLayout) this.findViewById(R.id.draw));
        lv_water.setEmptyView(tv);
//        adapter=new MoneyWaterAdapter(list,this);
//        lv_water.setAdapter(adapter);
        init();
    }

    private void init() {
        userid = getIntent().getStringExtra("USERID");
        utils = HttpXutilTool.getUtils();
        RequestParams params = UserCenterParams.getFundRunningCode(userid, "0", "1", "10");
//        utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                String result = responseInfo.result;
//                JSONObject object = null;
//                try {
//                    object = new JSONObject(result);
//                    String strResponse = object.getString("argEncPara");
//                    String strDe = DES3Util.decode(strResponse);
//                    Toast.makeText(MoneyWaterActivity.this, strDe, Toast.LENGTH_SHORT).show();
//                    //JSONObject obj2=new JSONObject(strDe);
//                    // String rescode=obj2.getString("rescode");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                //Toast.makeText(LoginActivity.this, strDe, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//                e.printStackTrace();
//                Toast.makeText(MoneyWaterActivity.this, "失败", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
