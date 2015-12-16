package com.wrmoney.administrator.plusadd.homeview.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
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
import com.wrmoney.administrator.plusadd.bean.BannerBean;
import com.wrmoney.administrator.plusadd.bean.FinancingPlanBean;
import com.wrmoney.administrator.plusadd.bean.HomeContentBean;
import com.wrmoney.administrator.plusadd.encode.FinancingParams;
import com.wrmoney.administrator.plusadd.financingview.activitys.InvestActivity;
import com.wrmoney.administrator.plusadd.financingview.adapters.FinancingPlanAdapter;
import com.wrmoney.administrator.plusadd.homeview.adapters.AdapterCycle;
import com.wrmoney.administrator.plusadd.homeview.adapters.HomeContentAdapter;
import com.wrmoney.administrator.plusadd.homeview.adapters.HomePagerAdapter;
import com.wrmoney.administrator.plusadd.loginview.activitys.PhoneActivity;
import com.wrmoney.administrator.plusadd.moreview.activitys.AlterPassActivity;
import com.wrmoney.administrator.plusadd.tools.BitmapHelper;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;


import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * 首页fragment
 * Created by Administrator on 2015/11/2.
 */
public class HomeFrament extends BaseFragment {

    private View view;
    private List<BannerBean> list = new ArrayList<BannerBean>();
    private Activity activity;

    private RadioGroup rg_index;
    private ListView lv_plan;
    private List<HomeContentBean> listBean=new ArrayList<HomeContentBean>();
    private Button btn1;
    private HttpUtils httpUtils;
    private HomeContentAdapter adapter1;
    private HomePagerAdapter adapter2;
    private ViewPager vp_index;
    private LinearLayout ll_points;
    //用来控制循环的自动标志位
    private boolean isloop = true;
    private int preposition = 0;
    private AdapterCycle adapter3;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
//                    //获取viewpager当前正在展示的pager索引
//                    int currentItem = vp_index.getCurrentItem();
//                    //切换到viewpager界面
//                    vp_index.setCurrentItem(currentItem+1);
                    int count = adapter2.getCount();
                    if (count > 2) { // 实际上，多于1个，就多于3个
                        int index = vp_index.getCurrentItem();
                        index = index % (count - 2) + 1; //这里修改过
                        vp_index.setCurrentItem(index, true);
                    }
                    break;
            }
        };
    };
    private Thread thread;


//    private AlterPassFinishDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // Log.i("=======","HomeCreateView");
        activity = getActivity();
        view=inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (isloop) {
                    //系统时钟的睡眠方法---->电量的消耗很少。
                    SystemClock.sleep(5000);
                    handler.sendEmptyMessage(1);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //thread.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        thread.isDaemon();
    }

    public static HomeFrament newInstance(){
        HomeFrament fragment=new HomeFrament();
        return fragment;
    }
    public void init(){
        BitmapHelper.init(activity);
        httpUtils = new HttpUtils(10000);
        lv_plan=(ListView)view.findViewById(R.id.lv_plan);
        ll_points=(LinearLayout)view.findViewById(R.id.ll_points);
        adapter1=new HomeContentAdapter(listBean,activity);
        lv_plan.setAdapter(adapter1);
        dataRequest();
        lv_plan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(activity,list.get(position).getId(),LENGTH_SHORT).show();
               // Log.i("=========长度",listBean.get(position).getId()+"");
               // int pos = position - 1;
                //Log.i("====adfa",list.get(pos).getId()+"");
                Intent intent = new Intent(activity, InvestActivity.class);
                intent.putExtra("PLANID",listBean.get(position).getId()+"");
                activity.startActivity(intent);


            }
        });
        //rg_index = (RadioGroup) view.findViewById(R.id.rg_index);
        vp_index= (ViewPager) view.findViewById(R.id.pager_index);
        btn1=(Button)view.findViewById(R.id.btn1);
//        btn1.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                CheckVersionDialog dialog = new CheckVersionDialog(activity, R.style.dialog);
//                // QuitPlanDialog dialog = new QuitPlanDialog(activity, R.style.dialog);
//                dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
//                dialog.show();
//            }
//        });

    }

    /**
     * 数据请求
     */
    public void dataRequest(){
        RequestParams params = FinancingParams.homeListCode();
        httpUtils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //Toast.makeText(activity,"成功", LENGTH_SHORT).show();
                String result = responseInfo.result;
                JSONObject object = null;
                try {
                    object = new JSONObject(result);
                    String strResponse = object.getString("argEncPara");
                    String strDe = DES3Util.decode(strResponse);
                    //Log.i("====主页", strDe);
                    JSONObject object1 = new JSONObject(strDe);
                    JSONArray array = object1.getJSONArray("bannerList");
                    int len = array.length();
                    for (int i = 0; i < len; i++) {
                        JSONObject object3 = array.getJSONObject(i);
                        BannerBean bean = new BannerBean();
                        bean.setId(object3.getString("id"));
                        bean.setTitle(object3.getString("title"));
                        bean.setDescrition(object3.getString("descrition"));
                        bean.setUrl(object3.getString("url"));
                        list.add(bean);
                    }
                    List<ImageView> listImage = setData(list);
                    bannerSet(listImage);
                    List<HomeContentBean> listBean = new ArrayList<HomeContentBean>();
                    String str = object1.getString("newHand");
                    contentSet(str,listBean);
                    String str2=object1.getString("seven");
                    contentSet(str2,listBean);
                    //listBean.add();
                    // Log.i("==========", listBean.size()+"");
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
                //Toast.makeText(activity, "失败", LENGTH_SHORT).show();
            }
        });

    }
    /**
     * 首页标题图
     */


    int imag2[]={R.drawable.wallpaper_1,R.drawable.wallpaper_2,R.drawable.wallpaper_3};
    List<ImageView> listt=new ArrayList<>();
    public void bannerSet(final List<ImageView> listImage){
//        for(int i=0;i<3;i++){
//            ImageView iv1=new ImageView(activity);
//            iv1.setImageResource(imag2[i]);
//            listt.add(iv1);
//        }
//       Toast.makeText(activity,list.size()+"",Toast.LENGTH_SHORT).show();
        adapter2 = new HomePagerAdapter(listImage,activity);
         //adapter3= new AdapterCycle(activity,vp_index,listt);
        vp_index.setAdapter(adapter2);
        //vp_index.setCurrentItem(Integer.MAX_VALUE / 2 - 4 - (Integer.MAX_VALUE / 2) % 4);
       // vp_index.setCurrentItem(Integer.MAX_VALUE/2);
       // vp_index.setAdapter(adapter3);
        //实现自动切换界面
        //在外层，将mViewPager初始位置设置为1即可
        if (adapter2.getCount() > 1) { //多于1个，才循环并开启定时器
            vp_index.setCurrentItem(1); //将mViewPager初始位置设置为1
           thread.start(); //startTimer(); //开启定时器，定时切换页面
        }

        vp_index.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }
            @Override
            public void onPageSelected(int i) {
                //得到容器中指定位置的子视图
                try {
                    View childAt = ll_points.getChildAt(i % listImage.size());
                    childAt.setBackgroundResource(R.drawable.button_b_c);
                    //将前一个小圆点颜色还原
                    ll_points.getChildAt(preposition).setBackgroundResource(R.drawable.button_b);
                    //改变perposition的值
                    preposition = i % listImage.size();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public void contentSet(String str,List<HomeContentBean> listBean){

        JSONObject object2 = null;
        try {
            object2 = new JSONObject(str);
            HomeContentBean bean = new HomeContentBean();
            String name = object2.getString("name");//标题
            String expectedRate = object2.getString("expectedRate");//收益率
            Integer progress = object2.getInt("progress");//进度条
            String baseLockPeriod = object2.getString("baseLockPeriod");//期限
            String maxFinaning = object2.getString("maxFinancing");//项目规模
            String enablleBuy = object2.getString("enableBuy");//是否售罄
            Integer id=object2.getInt("id");//计划的ID
            boolean has = object2.has("repayType");
            if(has){
                String repayType= object2.getString("repayType");//还款方式
               bean.setRepayType(repayType);
            }
            bean.setName(name);
            bean.setExpectedRate(expectedRate);
            bean.setProgress(progress);
            bean.setBaseLockPeriod(baseLockPeriod);
            bean.setMaxFinaning(maxFinaning);
            bean.setId(id);
            bean.setEnablleBuy(enablleBuy);
            listBean.add(bean);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    int imag[]={R.drawable.wallpaper_1,R.drawable.wallpaper_2,R.drawable.wallpaper_3};
    //设置数据源
    private List<ImageView> setData(List<BannerBean> bannerBeans) {
        List<ImageView>mList = new ArrayList<ImageView>();
        for(int i=0;i<bannerBeans.size();i++){
            ImageView iv = new ImageView(activity);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
           // iv.setImageResource(imag[i]);
            int id=Integer.parseInt(bannerBeans.get(i).getId());;
            iv.setId(id);
            BitmapHelper.getUtils().display(iv,bannerBeans.get(i).getUrl());
            mList.add(iv);
            //动态的生成小圆点，然后添加到线性布局中
            View view = new View(activity);
            view.setBackgroundResource(R.drawable.button_b);
            //设置布局参数，定义view的宽和高
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
            params.leftMargin = 10;
            view.setLayoutParams(params);
            ll_points.addView(view);
        }
        ll_points.getChildAt(0).setBackgroundResource(R.drawable.button_b_c);
        return mList;
    }
}
