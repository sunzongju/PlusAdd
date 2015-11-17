package com.wrmoney.administrator.plusadd.financingview.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wrmoney.administrator.plusadd.BaseFragment;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.bean.FinancingPlanBean;
import com.wrmoney.administrator.plusadd.financingview.adapters.FinancingPlanAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 理财主fragment
 * Created by Administrator on 2015/11/2.
 */
public class FinancingFragment extends BaseFragment{

    private View view;
    private ListView lv_plan;
    private FragmentActivity activity;
    private List<FinancingPlanBean> list=new ArrayList<FinancingPlanBean>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_financing, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        init();
    }

    public void init(){
        activity = getActivity();
        list.clear();
        for (int i=0;i<10;i++){
            FinancingPlanBean planBean=new FinancingPlanBean();
            planBean.setTitle("标题");
            list.add(planBean);
        }
        lv_plan=(ListView)view.findViewById(R.id.lv_money_plan);
        FinancingPlanAdapter adapter=new FinancingPlanAdapter(list,activity);
        lv_plan.setAdapter(adapter);
//        lv_plan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//
//            }
//        });
    }


    public static FinancingFragment newInstance(){
        FinancingFragment fragment=new FinancingFragment();
        return fragment;
    }

}
