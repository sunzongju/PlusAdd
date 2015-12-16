package com.wrmoney.administrator.plusadd.financingview.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wrmoney.administrator.plusadd.BaseFragment;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.bean.FinancingPlanBean;
import com.wrmoney.administrator.plusadd.encode.FinancingParams;
import com.wrmoney.administrator.plusadd.financingview.activitys.InvestActivity;
import com.wrmoney.administrator.plusadd.financingview.adapters.FinancingPlanAdapter;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.UrlTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * 理财主fragment
 * Created by Administrator on 2015/11/2.
 */
public class FinancingFragment extends BaseFragment{

    private View view;
//    private ListView lv_plan;
    private FragmentActivity activity;
    private List<FinancingPlanBean> list=new ArrayList<FinancingPlanBean>();
    private FinancingPlanAdapter adapter;
    private HttpUtils httpUtils;
    private PullToRefreshListView lv_plan;
    private int current=1;
    private ProgressBar pro_bar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // Log.i("=======", "FinancingCreateView");
        view=inflater.inflate(R.layout.fragment_financing, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        init();
    }

    public void init(){
        httpUtils = new HttpUtils(10000);
        activity = getActivity();
         pro_bar=(ProgressBar)view.findViewById(R.id.pro_bar);
        lv_plan=(PullToRefreshListView)view.findViewById(R.id.lv_money_plan);
//        View v= LayoutInflater.from(activity).inflate(R.layout.empty_view,null);
//        lv_plan.setEmptyView(v);
        lv_plan.setEmptyView(pro_bar);
        adapter=new FinancingPlanAdapter(list,activity);
        lv_plan.setAdapter(adapter);
        dataRequest(current);
        lv_plan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(activity,list.get(position).getId(),LENGTH_SHORT).show();
               int pos=position-1;
                //Log.i("====adfa",list.get(pos).getId()+"");
                Intent intent=new Intent(activity, InvestActivity.class);
                intent.putExtra("PLANID",list.get(pos).getId()+"");
                activity.startActivity(intent);


            }
        });
        lv_plan.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //Toast.makeText(activity,"下拉刷新",LENGTH_SHORT).show();
                current=1;
                list.clear();
                dataRequest(current);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
               //Toast.makeText(activity,"上拉加载",LENGTH_SHORT).show();
                current++;
                dataRequest(current);
            }
        });
    }

    /**
     * 数据请求
     */
    public void dataRequest(int current){
        RequestParams params = FinancingParams.getPlanListCode(current+"","10");
        httpUtils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL,params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
               // Toast.makeText(activity,"成功", LENGTH_SHORT).show();
                String result = responseInfo.result;
                JSONObject object = null;
                try {
                    List<FinancingPlanBean> list2=new ArrayList<FinancingPlanBean>();
                    object = new JSONObject(result);
                    String strResponse = object.getString("argEncPara");
                    String strDe = DES3Util.decode(strResponse);
                    //Toast.makeText(activity,str, LENGTH_SHORT).show();
                   // Log.i("=======理财页",strDe);
                    JSONObject object1=new JSONObject(strDe);
                    JSONArray array=object1.getJSONArray("result");
                    int len=array.length();
                    for(int i=0;i<len;i++){
                        JSONObject object2=array.getJSONObject(i);
                        FinancingPlanBean bean=new FinancingPlanBean();
                        Integer id=object2.getInt("id");
                        //Log.i("======",id+"请求到的id");
                        Integer type=object2.getInt("type");
                        String expectedRate=object2.getString("expectedRate");
                        Integer progress=object2.getInt("progress");
                        Integer baseLockPeriod=object2.getInt("baseLockPeriod");
                        String minBuyerAmount=object2.getString("minBuyerAmount");
                        String maxFinancing=object2.getString("maxFinancing");
                        String repayType=object2.getString("repayType");
                        String enableBuy=object2.getString("enableBuy");
                        String name=object2.getString("name");
                        bean.setId(id);
                        bean.setType(type);
                        bean.setExpectedRate(expectedRate);
                        bean.setProgress(progress);
                        bean.setBaseLockPeriod(baseLockPeriod);
                        bean.setMinBuyerAmount(minBuyerAmount);
                        bean.setMaxFinancing(maxFinancing);
                        bean.setRepayType(repayType);
                        bean.setEnableBuy(enableBuy);
                        bean.setName(name);
                        list2.add(bean);
                    }
                    //adapter.clear();
                    //Log.i("========1",list.size()+"");//这里获取的list长度是10；
                    adapter.addAll(list2);
                    lv_plan.onRefreshComplete();
                    //Toast.makeText(activity,len+"", LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
               // Toast.makeText(activity,"失败", LENGTH_SHORT).show();
                pro_bar.setVisibility(View.GONE);
            }
        });

    }

    public static FinancingFragment newInstance(){
        FinancingFragment fragment=new FinancingFragment();
        return fragment;
    }

}
