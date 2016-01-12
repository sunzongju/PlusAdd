package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshAdapterViewBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
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
import com.wrmoney.administrator.plusadd.loginview.activitys.LoginActivity2;

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
 * 投资管理界面
 * Created by Administrator on 2015/9/22.
 */
public class InvestMentActivity extends BaseActivity{
    private String userid;
    private HttpUtils utils;
    private RadioGroup rg_invest;

    private List<InvestMentBean> list=new ArrayList<InvestMentBean>();
    private InvestMentAdapter adapter;
    private String type;
    private PullToRefreshAdapterViewBase lv_invest;
    private int current=1;
    private int checked=R.id.btn_all;
    private Bundle bundle;
    private TextView tv_finish;
    private TextView tv;
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_invest_manage);
        ActionBarSet.setActionBar(this);
        ActionBarSet.setHelpBar(this);
        TextView tv_banner=(TextView)this.findViewById(R.id.tv_banner);
        tv_banner.setText("投资管理");
        init();

        lv_invest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(list.size()>0){
                    InvestMentBean bean=list.get(position-1);
                    String orderId=bean.getOrderId();
                    String exitFlag=bean.getExitFlag();
                  //  Log.i("=======标志",exitFlag);
//                    Intent intent=new Intent(InvestMentActivity.this,PhoneActivity2.class);
//                    Bundle bundle=new Bundle();
//                    bundle.putString("ORDERID",orderId);
//                    bundle.putString("EXITFLAG",exitFlag);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
                    Intent intent = new Intent(InvestMentActivity.this, LoginActivity2.class);
                    intent.putExtra("PHONE",  SingleUserIdTool.newInstance().getPhoneNum());
                   // intent.putExtra("PLANID", planID);
                    intent.putExtra("ORDERID", orderId);
                    intent.putExtra("EXITFLAG", exitFlag);
                    startActivity(intent);
                }
            }
        });
    }

    private void init() {
        bundle = getIntent().getExtras();
        TextView tv_allAmount=(TextView)this.findViewById(R.id.tv_allAmount);
       TextView tv_acctBalance=(TextView)this.findViewById(R.id.tv_acctBalance);
       TextView tv_incomeAmount=(TextView)this.findViewById(R.id.tv_incomeAmount);
       TextView tv_lookwater=(TextView)this.findViewById(R.id.tv_lookwater);
        tv_lookwater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(InvestMentActivity.this, MoneyWaterActivity.class);
                //Bundle bundle=new Bundle();
                intent2.putExtras(bundle);
                startActivity(intent2);
            }
        });
        Bundle bundle = getIntent().getExtras();
        String allAmount=bundle.getString("allAmount");
        if("".equals(allAmount)||allAmount==null||"0".equals(allAmount)){
            tv_allAmount.setText("0.00");
        }else {
            tv_allAmount.setText(allAmount);//总额
        }
        String acctBalance=bundle.getString("acctBalance");
        if("".equals(acctBalance)||acctBalance==null||"0".equals(acctBalance)){
            tv_acctBalance.setText("0.00");
        }else {
            tv_acctBalance.setText(acctBalance);//累计收益
        }
        String incomeAmount=bundle.getString("incomeAmount");
        if("".equals(incomeAmount)||incomeAmount==null||"0".equals(incomeAmount)){
            tv_incomeAmount.setText("0.00");
        }else {
            tv_incomeAmount.setText(bundle.getString("incomeAmount"));//可用余额
        }
        rg_invest=(RadioGroup) this.findViewById(R.id.rg_invest);
        lv_invest = (PullToRefreshListView) this.findViewById(R.id.lv_invest);
        lv = (ListView) lv_invest.getRefreshableView();
        tv=new TextView(this);
        tv.setGravity(Gravity.CENTER);
        tv.setText("数据加载完毕");

        tv_finish=(TextView)this.findViewById(R.id.tv_finish);
        View v=LayoutInflater.from(this).inflate(R.layout.empty_view, null);
        lv_invest.setEmptyView(v);
        userid = SingleUserIdTool.newInstance().getUserid();
        utils = HttpXutilTool.getUtils();
       adapter=new InvestMentAdapter(list,this);
        lv_invest.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        dataRequest("0", current);
        ILoadingLayout loadingLayoutProxy = lv_invest.getLoadingLayoutProxy();
        loadingLayoutProxy.setPullLabel("");
        lv_invest.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase refreshView) {
                tv_finish.setVisibility(View.GONE);
                lv.removeFooterView(tv);
                current=1;
                list.clear();
                switch (checked){
                    case R.id.btn_all:
                        dataRequest("0",current);
                        break;
                    case R.id.btn_locked:
                        dataRequest("1",current);
                        break;
                    case R.id.btn_unlock:
                        dataRequest("2",current);
                        break;
                    case R.id.btn_quit:
                        dataRequest("3",current);
                        break;
                    case R.id.btn_quited:
                        dataRequest("4",current);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase refreshView) {
                current++;
                switch (checked) {
                    case R.id.btn_all:
                        dataRequest("0",current);
                        break;
                    case R.id.btn_locked:
                        dataRequest("1",current);
                        break;
                    case R.id.btn_unlock:
                        dataRequest("2",current);
                        break;
                    case R.id.btn_quit:
                        dataRequest("3",current);
                        break;
                    case R.id.btn_quited:
                        dataRequest("4",current);
                        break;
                    default:
                        break;
                }
            }
        });
        rg_invest.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //选中的RadioButton播放动画
                lv.removeFooterView(tv);
                current = 1;
                ScaleAnimation sAnim = new ScaleAnimation(1, 1.1f, 1, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                sAnim.setDuration(2000);
                sAnim.setFillAfter(true);
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                switch (checkedId) {
                    case R.id.btn_all:
                        checked = R.id.btn_all;
                        list.clear();
                        dataRequest("0", current);
                        break;
                    case R.id.btn_locked:
                        checked = R.id.btn_locked;
                        list.clear();
                        dataRequest("1", current);
                        break;
                    case R.id.btn_unlock:
                        checked = R.id.btn_unlock;
                        list.clear();
                        dataRequest("2", current);
                        break;
                    case R.id.btn_quit:
                        checked = R.id.btn_quit;
                        list.clear();
                        dataRequest("3", current);
                        break;
                    case R.id.btn_quited:
                        checked = R.id.btn_quited;
                        list.clear();
                        dataRequest("4", current);
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
    public void dataRequest(String type,int current) {
        Boolean b = CheckNetTool.checkNet(this);
        if(b){
            RequestParams params = UserCenterParams.getInvestManagerCode(userid, type, current+"", "10");
            utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    String result = responseInfo.result;
                    JSONObject object = null;
                    try {
                        List<InvestMentBean> list2=new ArrayList<InvestMentBean>();
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
                            bean.setStatus(object2.getString("status"));
                            bean.setInvestDate(object2.getString("investDate"));
                            bean.setPlanName(object2.getString("planName"));
                            bean.setExpectedRate(object2.getString("expectedRate"));
                            bean.setInvestAmount(object2.getString("investAmount"));
                            bean.setLockTime(object2.getString("lockTime"));
                            bean.setExitFlag(object2.getString("exitFlag"));
                            list2.add(bean);
                        }
                        if(list2.size()<10){
                            //tv_finish.setVisibility(View.VISIBLE);
                            int footerViewsCount = lv.getFooterViewsCount();
                            if(footerViewsCount<2){
                                lv.addFooterView(tv);
                            }
                        }
                        adapter.addAll(list2);
                        lv_invest.onRefreshComplete();
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
                    // Toast.makeText(InvestMentActivity.this, "失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
