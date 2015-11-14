package com.wrmoney.administrator.plusadd.homeview.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wrmoney.administrator.plusadd.BaseFragment;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.bean.FinancingPlanBean;
import com.wrmoney.administrator.plusadd.financingview.adapters.FinancingPlanAdapter;
import com.wrmoney.administrator.plusadd.homeview.adapters.HomePagerAdapter;
import com.wrmoney.administrator.plusadd.moreview.activitys.AlterPassActivity;
import com.wrmoney.administrator.plusadd.view.AlterPassFinishDialog;
import com.wrmoney.administrator.plusadd.view.ClaculatorDialog;
import com.wrmoney.administrator.plusadd.view.DiaLog;
import com.wrmoney.administrator.plusadd.view.MoneyShorDialog;
import com.wrmoney.administrator.plusadd.view.MyDialog;

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
    private Button btn1;

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
        btn1=(Button)view.findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //final MyDialog selectDialog = new MyDialog(activity,R.style.dialog);//创建Dialog并设置样式主题
               // AlterPassFinishDialog dialog=new AlterPassFinishDialog(activity,R.style.dialog);
                MoneyShorDialog dialog=new MoneyShorDialog(activity);
//                Window win = selectDialog.getWindow();
//                WindowManager.LayoutParams params = new WindowManager.LayoutParams();
//                params.x = -80;//设置x坐标
//                params.y = -60;//设置y坐标
//                win.setAttributes(params);
//                TextView tv=(TextView)selectDialog.findViewById(R.id.tv_title);
//                tv.setText("哈哈");
//                LinearLayout layout=(LinearLayout)LayoutInflater.from(activity).inflate(R.layout.custom_alterpass_succeed_dialog,null);
//                Button button=(Button)layout.findViewById(R.id.btn_finish);
//                selectDialog.getWindow().setContentView(layout);
                dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
                dialog.show();
//                button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        selectDialog.dismiss();
//                    }
//                });
//                DiaLog.AlterPassFinishDialog(this,"");

            }
        });


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
