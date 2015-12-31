package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.wrmoney.administrator.plusadd.accountview.adapters.RedPacketAdapter;
import com.wrmoney.administrator.plusadd.accountview.adapters.VouchersAdapter;
import com.wrmoney.administrator.plusadd.accountview.fragments.RedAllFragment;
import com.wrmoney.administrator.plusadd.accountview.fragments.RedAvailableFragment;
import com.wrmoney.administrator.plusadd.accountview.fragments.RedExpiredFragment;
import com.wrmoney.administrator.plusadd.accountview.fragments.RedUsedFragment;
import com.wrmoney.administrator.plusadd.bean.RedPacketBean;
import com.wrmoney.administrator.plusadd.bean.VoucherBean;
import com.wrmoney.administrator.plusadd.encode.UserCenterParams;
import com.wrmoney.administrator.plusadd.tools.ActionBarSet;
import com.wrmoney.administrator.plusadd.tools.CheckNetTool;
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
 * 红包界面
 * Created by Administrator on 2015/11/2.
 */
public class RedPacketActivity extends BaseActivity {
    private String userid;
    private HttpUtils utils;
    private List<VoucherBean> list = new ArrayList<VoucherBean>();
    private ListView lv_redpacket;
    private RedPacketAdapter adapter;
    private RadioGroup rg_group;
    private TextView indicator;
    private FragmentTransaction transaction1;
    private RedAllFragment redAllFragment;
    private RedAvailableFragment redAvailable;
    private RedUsedFragment redUsedFragment;
    private RedExpiredFragment redExpiredFragment;
    private ListView lv_voucher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_voucher);
        ActionBarSet.setActionBar(this);

        //init();
        getIntent().getStringExtra("USERID");

    }



    /**
     * 数据请求
     */
    public void dataRequest(String type) {
        Boolean b = CheckNetTool.checkNet(this);
        if(b){
            RequestParams params = UserCenterParams.getBonusCode(userid, "0", "1", "10");
            utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    String result = responseInfo.result;
                    JSONObject object = null;
                    try {
                        object = new JSONObject(result);
                        String strResponse = object.getString("argEncPara");
                        String strDe = DES3Util.decode(strResponse);
                        // Log.v("======红包", strDe);
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
    }
}
