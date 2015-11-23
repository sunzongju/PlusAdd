package com.wrmoney.administrator.plusadd.accountview.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wrmoney.administrator.plusadd.BaseFragment;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.accountview.adapters.MoneyWaterAdapter;
import com.wrmoney.administrator.plusadd.bean.MoneyWaterBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/11.
 */
public class WaterAllFragment extends BaseFragment {

    private View view;

    private List<MoneyWaterBean> list=new ArrayList<MoneyWaterBean>();
    private MoneyWaterAdapter adapter;
    private ListView lv_water;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_account_water_all,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    public void init(){
//        for (int i=0;i<20;i++){
//            MoneyWaterBean bean=new MoneyWaterBean();
//            bean.setMoney("");
//            list.add(bean);
//        }
        lv_water=(ListView)view.findViewById(R.id.lv_water);
        adapter=new MoneyWaterAdapter(list,getActivity());
        lv_water.setAdapter(adapter);

    }
}
