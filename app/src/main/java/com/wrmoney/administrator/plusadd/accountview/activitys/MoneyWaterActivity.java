package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.ILoadingLayout;
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
import com.wrmoney.administrator.plusadd.accountview.adapters.MoneyWaterAdapter;
import com.wrmoney.administrator.plusadd.accountview.fragments.RedAllFragment;
import com.wrmoney.administrator.plusadd.accountview.fragments.RedAvailableFragment;
import com.wrmoney.administrator.plusadd.accountview.fragments.RedExpiredFragment;
import com.wrmoney.administrator.plusadd.accountview.fragments.RedUsedFragment;
import com.wrmoney.administrator.plusadd.accountview.fragments.WaterAddFragment;
import com.wrmoney.administrator.plusadd.accountview.fragments.WaterAllFragment;
import com.wrmoney.administrator.plusadd.accountview.fragments.WaterCutFragment;
import com.wrmoney.administrator.plusadd.accountview.fragments.WaterInvestFragment;
import com.wrmoney.administrator.plusadd.accountview.fragments.WaterRechargeFragment;
import com.wrmoney.administrator.plusadd.bean.FinancingPlanBean;
import com.wrmoney.administrator.plusadd.bean.MoneyWaterBean;
import com.wrmoney.administrator.plusadd.encode.FinancingParams;
import com.wrmoney.administrator.plusadd.encode.UserCenterParams;
import com.wrmoney.administrator.plusadd.tools.ActionBarSet;
import com.wrmoney.administrator.plusadd.tools.CheckNetTool;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.FormatTool;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.SingleUserIdTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * 资金流水界面
 * Created by Administrator on 2015/9/22.
 */
public class MoneyWaterActivity extends BaseActivity {
    private String userid;
    private HttpUtils utils;
    private List<MoneyWaterBean> list=new ArrayList<MoneyWaterBean>();
    private LinearLayout prg1;

    private RadioGroup rg_water;
    private FragmentTransaction transaction;
    private WaterAddFragment waterAddFragment;
    private WaterAllFragment waterAllFragment;
    private WaterCutFragment waterCutFragment;
    private WaterRechargeFragment waterRechargeFragment;
    private WaterInvestFragment waterInvestFragment;
    private HttpUtils httpUtils;
    private MoneyWaterAdapter adapter;
    private PullToRefreshListView lv_water;
    private int current=1;
    private int checked=R.id.btn_all;
    private ListView lv;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_water);
        ActionBarSet.setHelpBar(this);
        ActionBarSet.setActionBar(this);
        TextView tv_banner=(TextView)this.findViewById(R.id.tv_banner);
        tv_banner.setText("资金流水");
        init();
    }

    private void init() {
        TextView tv_allAmount=(TextView)this.findViewById(R.id.tv_allAmount);
        Bundle bundle = getIntent().getExtras();
        String allAmount=bundle.getString("allAmount");
        if("".equals(allAmount)||allAmount==null||"0".equals(allAmount)){
            tv_allAmount.setText("0.00");
        }else {
            tv_allAmount.setText(allAmount);//总额
        }
        userid= SingleUserIdTool.newInstance().getUserid();
        httpUtils = new HttpUtils(10000);
        rg_water = (RadioGroup) this.findViewById(R.id.rg_water);
        lv_water=(PullToRefreshListView)this.findViewById(R.id.lv_water);
        lv = lv_water.getRefreshableView();
        tv=new TextView(this);
        tv.setGravity(Gravity.CENTER);
        tv.setText("数据加载完毕");

        View v= LayoutInflater.from(this).inflate(R.layout.empty_view, null);
        lv_water.setEmptyView(v);
       adapter=new MoneyWaterAdapter(list,this);
       lv_water.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        dataRequest("0", current);
        ILoadingLayout loadingLayoutProxy = lv_water.getLoadingLayoutProxy();
        loadingLayoutProxy.setPullLabel("");
        lv_water.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                lv.removeFooterView(tv);
                current=1;
                list.clear();
                switch (checked) {
                    case R.id.btn_all:
                        dataRequest("0",current);
                        break;
                    case R.id.btn_add:
                        dataRequest("1",current);
                        break;
                    case R.id.btn_cut:
                        dataRequest("2",current);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                current++;
                switch (checked) {
                    case R.id.btn_all:
                        dataRequest("0",current);
                        break;
                    case R.id.btn_add:
                        dataRequest("1",current);
                        break;
                    case R.id.btn_cut:
                        dataRequest("2",current);
                        break;
                    default:
                        break;
                }
            }
        });
        rg_water.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                lv.removeFooterView(tv);
                current = 1;
                //选中的RadioButton播放动画
                ScaleAnimation sAnim = new ScaleAnimation(1, 1.1f, 1, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                sAnim.setDuration(2000);
                sAnim.setFillAfter(true);
                switch (checkedId) {
                    case R.id.btn_all:
                        checked = R.id.btn_all;
                        list.clear();
                        dataRequest("0", current);
                        break;
                    case R.id.btn_add:
                        checked = R.id.btn_add;
                        list.clear();
                        dataRequest("1", current);
                        break;
                    case R.id.btn_cut:
                        checked = R.id.btn_cut;
                        list.clear();
                        dataRequest("2", current);
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
    public void dataRequest(String type,int current){
        Boolean b = CheckNetTool.checkNet(this);
        if(b){
            RequestParams params = UserCenterParams.getFundRunningCode(userid,type,current+"","10");
            httpUtils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    // Toast.makeText(activity,"成功", LENGTH_SHORT).show();
                    String result = responseInfo.result;
                    JSONObject object = null;
                    try {
                        List<MoneyWaterBean> list2=new ArrayList<MoneyWaterBean>();
                        object = new JSONObject(result);
                        String strResponse = object.getString("argEncPara");
                        String strDe = DES3Util.decode(strResponse);
                        //Log.i("=======资金流水", strDe);
                        JSONObject object1=new JSONObject(strDe);
                        JSONArray array=object1.getJSONArray("flowList");
                        int len=array.length();
                        for(int i=0;i<len;i++){
                            JSONObject object2=array.getJSONObject(i);
                            MoneyWaterBean bean=new MoneyWaterBean();
                            bean.setTransDate(object2.getString("transDate"));
                            bean.setTransComent(object2.getString("transComent"));
                            bean.setTransAmount(object2.getString("transAmount"));
                            list2.add(bean);
                        }

                        if(list2.size()<10){
                            int footerViewsCount = lv.getFooterViewsCount();
                            if(footerViewsCount<2){
                                lv.addFooterView(tv);
                            }
                        }


                        adapter.addAll(list2);
                        lv_water.onRefreshComplete();
                        // JSONObject object1 = new JSONObject(strDe);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(HttpException e, String s) {
                    e.printStackTrace();
                    //Toast.makeText(MoneyWaterActivity.this, "失败", LENGTH_SHORT).show();
                }
            });
        }
    }
}
