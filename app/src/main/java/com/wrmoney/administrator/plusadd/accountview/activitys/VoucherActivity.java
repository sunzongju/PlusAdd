package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.os.Bundle;
import android.util.Log;
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
import com.wrmoney.administrator.plusadd.accountview.adapters.VoucherAdapter;
import com.wrmoney.administrator.plusadd.bean.ActivityListBean;
import com.wrmoney.administrator.plusadd.bean.VoucherBean;
import com.wrmoney.administrator.plusadd.encode.UserCenterParams;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.SingleUserIdTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 *抵用券界面����
 * Created by Administrator on 2015/11/2.
 */
public class VoucherActivity extends BaseActivity {
    private ListView lv_red;
    private List<VoucherBean> list=new ArrayList<VoucherBean>();
    private String userid;
    private HttpUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_voucher);

        init();
    }

    public void init(){
        lv_red=(ListView)this.findViewById(R.id.lv_red);
        for(int i=0;i<2;i++){
            VoucherBean bean=new VoucherBean();
            list.add(bean);
        }
        VoucherAdapter adapter=new VoucherAdapter(list,this);
       lv_red.setAdapter(adapter);
        userid = SingleUserIdTool.newInstance().getUserid();
        utils = HttpXutilTool.getUtils();
        RequestParams params = UserCenterParams.getBonusCode(userid,"0","1","10");
        utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                JSONObject object = null;
                try {
                    object = new JSONObject(result);
                    String strResponse = object.getString("argEncPara");
                    String strDe = DES3Util.decode(strResponse);
                   // Toast.makeText(VoucherActivity.this, strDe, Toast.LENGTH_SHORT).show();
                    Log.i("=========",strDe);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Toast.makeText(LoginActivity.this, strDe, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
            }
        });



    }
}
