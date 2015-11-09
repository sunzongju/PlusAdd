package com.wrmoney.administrator.plusadd.homeview.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wrmoney.administrator.plusadd.BaseFragment;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.bean.FinancingPlanBean;
import com.wrmoney.administrator.plusadd.financingview.adapters.FinancingPlanAdapter;
import com.wrmoney.administrator.plusadd.homeview.adapters.HomePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页fragment
 * Created by Administrator on 2015/11/2.
 */
public class HomeFrament extends BaseFragment {

    private View view;
    private List<View> list = new ArrayList<View>();
    private Activity activity;
    private ViewPager vp_index;
    private RadioGroup rg_index;
    private ListView lv_plan;
    private List<FinancingPlanBean> listBean=new ArrayList<FinancingPlanBean>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();
        view=inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

    }

    public static HomeFrament newInstance(){
        HomeFrament fragment=new HomeFrament();
        return fragment;
    }

    public void init(){

//        rb_join = (Button) view.findViewById(R.id.rb_join);
//        rb_join.setOnClickListener(this);
        rg_index = (RadioGroup) view.findViewById(R.id.rg_index);
        vp_index= (ViewPager) view.findViewById(R.id.pager_index);
        list.clear();
        list.add(LayoutInflater.from(activity).inflate(R.layout.home_index_pager_01, null));
        list.add(LayoutInflater.from(activity).inflate(R.layout.home_index_pager_02, null));
        list.add(LayoutInflater.from(activity).inflate(R.layout.home_index_pager_03, null));
//        Toast.makeText(activity,list.size()+"",Toast.LENGTH_SHORT).show();
        HomePagerAdapter adapter = new HomePagerAdapter(list);
        vp_index.setAdapter(adapter);
        vp_index.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                //rg_index.check(i + 1);
                RadioButton btn = (RadioButton) rg_index.getChildAt(i);
                if (btn != null) {
                    btn.setChecked(true);
                }

                // Toast.makeText(SecondActivity.this,i+"",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageSelected(int i) {
                //Toast.makeText(getActivity(),i,Toast.LENGTH_SHORT).show();
                //rb2.isChecked();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        lv_plan=(ListView)view.findViewById(R.id.lv_plan);
        listBean.clear();
        for(int i=0;i<2;i++){
            FinancingPlanBean bean=new FinancingPlanBean();
            bean.setTitle("标题");
            listBean.add(bean);
        }
        FinancingPlanAdapter adapter1=new FinancingPlanAdapter(listBean,activity);
        lv_plan.setAdapter(adapter1);
    }
}
