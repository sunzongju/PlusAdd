package com.wrmoney.administrator.plusadd.financingview.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wrmoney.administrator.plusadd.BaseFragment;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.bean.FinancingPlanBean;
import com.wrmoney.administrator.plusadd.bean.HomeContentBean;
import com.wrmoney.administrator.plusadd.encode.FinancingParams;
import com.wrmoney.administrator.plusadd.financingview.activitys.InvestActivity;
import com.wrmoney.administrator.plusadd.financingview.adapters.FinancingPlanAdapter;
import com.wrmoney.administrator.plusadd.tools.CheckNetTool;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.DbHelper;
import com.wrmoney.administrator.plusadd.tools.NetworkAvailable;
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
    private DbUtils dbUtils;
    private ILoadingLayout loadingLayoutProxy;
//    private ListView refreshableView;
//    private int footerViewsCount;
    private TextView finishView;
    private ListView lv;
    private TextView tv;

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
         dbUtils = DbHelper.getUtils();
         pro_bar=(ProgressBar)view.findViewById(R.id.pro_bar);
         finishView=(TextView)view.findViewById(R.id.tv_finish);
        lv_plan=(PullToRefreshListView)view.findViewById(R.id.lv_money_plan);
        lv = lv_plan.getRefreshableView();
        tv=new TextView(activity);
        tv.setGravity(Gravity.CENTER);
        tv.setText("数据加载完毕");


        //lv.addHeaderView(header);
//        refreshableView = lv_plan.getRefreshableView();
//        footerViewsCount = refreshableView.getFooterViewsCount();
//         finishView = new TextView(activity);
//         finishView.setText("数据加载完成");
        lv_plan.setEmptyView(pro_bar);
        adapter=new FinancingPlanAdapter(list,activity);
        lv_plan.setAdapter(adapter);

//        dataRequest(current);


        lv_plan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(activity,list.get(position).getId(),LENGTH_SHORT).show();
                int pos = position - 1;
                //Log.i("====adfa",list.get(pos).getId()+"");
                Intent intent = new Intent(activity, InvestActivity.class);
                intent.putExtra("PLANID", list.get(pos).getId() + "");
                activity.startActivity(intent);


            }
        });
//        lv_plan.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                synchronized (this) {
//                    if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
//                        // 判断是否滚动到底部
//                        if (view.getLastVisiblePosition() == view
//                                .getCount() - 1) {
//                            //JLog.e("LOADMORE==========", "loading...");
//                            //在这里加载数据
//                            current++;
//                            dataRequest(current);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//            }
//        });

    }

    @Override
    public void onResume() {
        super.onResume();
        checkNetWorkInfo();
        loadingLayoutProxy = lv_plan.getLoadingLayoutProxy();
        loadingLayoutProxy.setPullLabel("");
//        if(){
//            lv_plan.setMode(null);
//        }else {
//            lv_plan.setMode(PullToRefreshBase.Mode.BOTH);
//        }

        lv_plan.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    //Toast.makeText(activity,"下拉刷新",LENGTH_SHORT).show();
                    lv.removeFooterView(tv);
                    finishView.setVisibility(View.GONE);
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

        // Log.i("========FinmentonResume","11111111111");
    }

    /**
     * 检查是否有网络
     */

    private void checkNetWorkInfo() {
        if (!NetworkAvailable.isNetworkAvailable(activity)) {
            try {
                List<FinancingPlanBean> all = dbUtils.findAll(FinancingPlanBean.class);
//                Log.i("========all",all.get(0).getName());
                if(all!=null){
                    adapter.addAll(all);
                }
            } catch (DbException e) {
                e.printStackTrace();
            }
        }else{
            list.clear();
            dataRequest(current);
        }
    }


    /**
     * 数据请求
     */
    public void dataRequest(int current){
        Boolean b = CheckNetTool.checkNet(activity);
        if(b){
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
                        Log.i("=======理财页",strDe);
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
                    if(list2.size()<10){
                        int footerViewsCount = lv.getFooterViewsCount();
                        Log.i("=====个数1", footerViewsCount + "");
                        if(footerViewsCount<2){
                            lv.addFooterView(tv);
                        }
//                        finishView.setVisibility(View.VISIBLE);
//                        Log.i("======个数",footerViewsCount+"");
//                        if(footerViewsCount<2){
//                            refreshableView.addFooterView(finishView);
//                        }
                    }
                        dbUtils.dropTable(FinancingPlanBean.class);
                        dbUtils.saveOrUpdateAll(list2);
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
    }

    public static FinancingFragment newInstance(){
        FinancingFragment fragment=new FinancingFragment();
        return fragment;
    }

}
