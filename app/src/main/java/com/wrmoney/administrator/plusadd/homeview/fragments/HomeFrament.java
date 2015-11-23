package com.wrmoney.administrator.plusadd.homeview.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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

import com.lidroid.xutils.HttpUtils;
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
import com.wrmoney.administrator.plusadd.financingview.adapters.FinancingPlanAdapter;
import com.wrmoney.administrator.plusadd.homeview.adapters.HomeContentAdapter;
import com.wrmoney.administrator.plusadd.homeview.adapters.HomePagerAdapter;
import com.wrmoney.administrator.plusadd.loginview.activitys.PhoneActivity;
import com.wrmoney.administrator.plusadd.moreview.activitys.AlterPassActivity;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;
import com.wrmoney.administrator.plusadd.view.AlterPassFinishDialog;
import com.wrmoney.administrator.plusadd.view.CheckVersionDialog;
import com.wrmoney.administrator.plusadd.view.ClaculatorDialog;
import com.wrmoney.administrator.plusadd.view.CreditorDetailDialog;
import com.wrmoney.administrator.plusadd.view.DiaLog;
import com.wrmoney.administrator.plusadd.view.MoneyShorDialog;
import com.wrmoney.administrator.plusadd.view.MyDialog;
import com.wrmoney.administrator.plusadd.view.QuitPlanDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

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
    private List<HomeContentBean> listBean=new ArrayList<HomeContentBean>();
    private Button btn1;
    private HttpUtils httpUtils;
    private HomeContentAdapter adapter1;
//    private AlterPassFinishDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // Log.i("=======","HomeCreateView");
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
        lv_plan=(ListView)view.findViewById(R.id.lv_plan);
        adapter1=new HomeContentAdapter(listBean,activity);
        lv_plan.setAdapter(adapter1);
        httpUtils = new HttpUtils(10000);
        rg_index = (RadioGroup) view.findViewById(R.id.rg_index);
        vp_index= (ViewPager) view.findViewById(R.id.pager_index);
        btn1=(Button)view.findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CheckVersionDialog dialog=new CheckVersionDialog(activity,R.style.dialog);
               // QuitPlanDialog dialog = new QuitPlanDialog(activity, R.style.dialog);
                dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
                dialog.show();
            }
        });
       dataRequest();
       bannerSet();
    }

    /**
     * 数据请求
     */
    public void dataRequest(){
        RequestParams params = FinancingParams.homeListCode();
        httpUtils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL,params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                // Toast.makeText(activity,"成功", LENGTH_SHORT).show();
                String result = responseInfo.result;
                JSONObject object = null;
                try {
                    object = new JSONObject(result);
                    String strResponse = object.getString("argEncPara");
                    String strDe = DES3Util.decode(strResponse);
                    JSONObject object1=new JSONObject(strDe);
                    String str=object1.getString("newHand");
                    //Toast.makeText(activity,str, LENGTH_SHORT).show();
                    JSONObject object2=new JSONObject(str);
                    List<HomeContentBean> listBean=new ArrayList<HomeContentBean>();
                    HomeContentBean bean=new HomeContentBean();
                    String name=object2.getString("name");//标题
                    Toast.makeText(activity,name, LENGTH_SHORT).show();
                    String expectedRate=object2.getString("expectedRate");//收益率
                    Integer progress=object2.getInt("progress");//进度条
                    String baseLockPeriod=object2.getString("baseLockPeriod");//期限
                    String maxFinaning=object2.getString("maxFinancing");//项目规模
                    String repayType=object2.getString("repayType");//还款方式
                    String enablleBuy=object2.getString("enableBuy");//是否售罄
                    bean.setName(name);
                    bean.setExpectedRate(expectedRate);
                    bean.setProgress(progress);
                    bean.setBaseLockPeriod(baseLockPeriod);
                    bean.setMaxFinaning(maxFinaning);
                    bean.setRepayType(repayType);
                    bean.setEnablleBuy(enablleBuy);
                    listBean.add(bean);
                    //Log.i("==========", listBean.size()+"");
                    adapter1.addAll(listBean);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                Toast.makeText(activity,"失败", LENGTH_SHORT).show();
            }
        });

    }
    /**
     * 首页标题图
     */
    public void bannerSet(){
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
    }
    //final MyDialog selectDialog = new MyDialog(activity,R.style.dialog);//创建Dialog并设置样式主题
    // AlterPassFinishDialog dialog=new AlterPassFinishDialog(activity,R.style.dialog);
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
    //MoneyShorDialog dialog=new MoneyShorDialog(activity);
//                CreditorDetailDialog dialog=new CreditorDetailDialog(activity,R.style.dialog);
//                //dialog=new AlterPassFinishDialog(activity,R.style.dialog);
}
