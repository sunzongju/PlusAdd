package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.accountview.adapters.InvestMentAdapter;
import com.wrmoney.administrator.plusadd.bean.InvestMentBean;
import com.wrmoney.administrator.plusadd.encode.UserCenterParams;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 投资管理界面
 * Created by Administrator on 2015/9/22.
 */
public class InvestMentActivity extends BaseActivity{
    private String userid;
    private HttpUtils utils;
    private RadioGroup rg_invest;
    private ListView lv_invest;
    private List<InvestMentBean> list=new ArrayList<InvestMentBean>();
    private InvestMentAdapter adapter;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_invest_manage);
        init();
        lv_invest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(list.size()>0){
                    InvestMentBean bean=list.get(position);
                    Intent intent=new Intent(InvestMentActivity.this,InvestDetailActivity.class);
                    String orderId=bean.getOrderId();
                    intent.putExtra("ORDERID",orderId);
                    startActivity(intent);
                }
            }
        });
    }

    private void init() {
        rg_invest=(RadioGroup) this.findViewById(R.id.rg_invest);
        lv_invest = (ListView) this.findViewById(R.id.lv_invest);
        userid = getIntent().getStringExtra("USERID");
        utils = HttpXutilTool.getUtils();
        dataRequest("0");
       adapter=new InvestMentAdapter(list,this);
        lv_invest.setAdapter(adapter);
        rg_invest.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //选中的RadioButton播放动画
                ScaleAnimation sAnim = new ScaleAnimation(1, 1.1f, 1, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                sAnim.setDuration(2000);
                sAnim.setFillAfter(true);
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                switch (checkedId) {
                    case R.id.btn_all:
                        list.clear();
                       dataRequest("0");
                        break;
                    case R.id.btn_locked:
                        list.clear();
                        dataRequest("1");
                        break;
                    case R.id.btn_unlock:
                        list.clear();
                        dataRequest("2");
                        break;
                    case R.id.btn_quit:
                        list.clear();
                        dataRequest("3");
                        break;
                    case R.id.btn_quited:
                        list.clear();
                        dataRequest("4");
                        break;
                    default:
                        break;
                }
            }
        });

    }
        /**
         * 数据请求
         */
    public void dataRequest(String type) {
        RequestParams params = UserCenterParams.getInvestManagerCode(userid, type, "1", "10");
        utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                JSONObject object = null;
                List<InvestMentBean> list=new ArrayList<InvestMentBean>();
                try {
                    object = new JSONObject(result);
                    String strResponse = object.getString("argEncPara");
                    String strDe = DES3Util.decode(strResponse);
                    //Log.i("======投资管理",strDe);
                    JSONObject object1=new JSONObject(strDe);
                    JSONArray array=object1.getJSONArray("investList");
                    int len=array.length();
                    for(int i=0;i<len;i++){
                        InvestMentBean bean=new InvestMentBean();
                        JSONObject object2=array.getJSONObject(i);
                        bean.setOrderId(object2.getString("orderId"));
                        bean.setInvestDate(object2.getString("investDate"));
                        bean.setPlanName(object2.getString("planName"));
                        bean.setExpectedRate(object2.getString("expectedRate"));
                        bean.setInvestAmount(object2.getString("investAmount"));
                        bean.setLockTime(object2.getString("lockTime"));
                        list.add(bean);
                    }
                    adapter.addAll(list);
                   // Toast.makeText(InvestMentActivity.this, strDe, Toast.LENGTH_SHORT).show();
                    //JSONObject obj2=new JSONObject(strDe);
                    // String rescode=obj2.getString("rescode");
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
                Toast.makeText(InvestMentActivity.this, "失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
