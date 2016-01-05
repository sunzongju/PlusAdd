package com.wrmoney.administrator.plusadd.homeview.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
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

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
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
import com.wrmoney.administrator.plusadd.homeview.activitys.HomeSecondActivity;
import com.wrmoney.administrator.plusadd.homeview.activitys.HomeThirdActivity;
import com.wrmoney.administrator.plusadd.homeview.activitys.HomefirstActivity;
import com.wrmoney.administrator.plusadd.homeview.adapters.AdapterCycle;
import com.wrmoney.administrator.plusadd.homeview.adapters.HomeContentAdapter;
import com.wrmoney.administrator.plusadd.homeview.adapters.HomePagerAdapter;
import com.wrmoney.administrator.plusadd.loginview.activitys.PhoneActivity;
import com.wrmoney.administrator.plusadd.moreview.activitys.AlterPassActivity;
import com.wrmoney.administrator.plusadd.tools.BitmapHelper;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.DbHelper;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.NetworkAvailable;
import com.wrmoney.administrator.plusadd.tools.UrlTool;
import com.wrmoney.administrator.plusadd.view.AlterPassFinishDialog;
import com.wrmoney.administrator.plusadd.view.CheckVersionDialog;
import com.wrmoney.administrator.plusadd.view.ClaculatorDialog;
import com.wrmoney.administrator.plusadd.view.CreditorDetailDialog;
import com.wrmoney.administrator.plusadd.view.DiaLog;
import com.wrmoney.administrator.plusadd.view.MoneyShorDialog;
import com.wrmoney.administrator.plusadd.view.MyDialog;
import com.wrmoney.administrator.plusadd.view.QuitPlanDialog;
import com.wrmoney.administrator.plusadd.wxapi.WXEntryActivity;

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
    private List<ImageView> listImage=new ArrayList<ImageView>();
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
    private DbUtils dbUtils;
    private BitmapUtils helper;
    private ImageView iv_btn1;
    private ImageView iv_btn2;
    private ImageView iv_btn3;


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
        init();
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
        helper=BitmapHelper.getUtils();
        DbHelper.init(activity);
        httpUtils = new HttpUtils(10000);
        dbUtils = DbHelper.getUtils();
        lv_plan=(ListView)view.findViewById(R.id.lv_plan);
        ll_points=(LinearLayout)view.findViewById(R.id.ll_points);
        adapter1=new HomeContentAdapter(listBean,activity);
        lv_plan.setAdapter(adapter1);
        vp_index= (ViewPager) view.findViewById(R.id.pager_index);
        checkNetWorkInfo();
            lv_plan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(activity,list.get(position).getId(),LENGTH_SHORT).show();
                    // Log.i("=========长度",listBean.get(position).getId()+"");
                    // int pos = position - 1;
                    //Log.i("====adfa",list.get(pos).getId()+"");
                    Intent intent = new Intent(activity, InvestActivity.class);
                    intent.putExtra("PLANID",listBean.get(position).getId()+"");
                    if("N".equals(listBean.get(position).getEnablleBuy())){
                        intent.putExtra("FLAG","Y");
                    }else {
                      double str1=Double.parseDouble(listBean.get(position).getMaxFinaning());
                        double str2=Double.parseDouble(listBean.get(position).getJoinAmount());
                        if(str2>=str1){
                            intent.putExtra("FLAG","Y");
                        }
                    }
                    activity.startActivity(intent);

                }
            });
        //rg_index = (RadioGroup) view.findViewById(R.id.rg_index);
        iv_btn1=(ImageView)view.findViewById(R.id.iv_btn1);
        iv_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent intent=new Intent(activity, HomefirstActivity.class);
                startActivity(intent);
            }
        });
        iv_btn2=(ImageView)view.findViewById(R.id.iv_btn2);
        iv_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, HomeSecondActivity.class);
                startActivity(intent);

            }
        });
        iv_btn3=(ImageView)view.findViewById(R.id.iv_btn3);
        iv_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, HomeThirdActivity.class);
                startActivity(intent);

            }
        });
    }

    /**
     * 检查是否有网络
     */

    private void checkNetWorkInfo() {
        if (!NetworkAvailable.isNetworkAvailable(activity)) {
            try {
                List<HomeContentBean> all = dbUtils.findAll(HomeContentBean.class);
                List<BannerBean> listB=dbUtils.findAll(BannerBean.class);
//                Log.i("========all",all.get(0).getName());
                if(all!=null){
                    adapter1.addAll(all);
                }
                if(listB!=null){
                      //Log.i("========图片的URL",listB.get(0).getUrl());
                     List<ImageView> imageViews = setData(listB);
                     // Log.i("===========长度",imageViews.size()+"");
                    bannerSet(imageViews);
            }
                new AlertDialog.Builder(activity)
                        .setTitle("提示!")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setMessage("检测到你还没开启网络，请开启")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activity.finish();
                            }
                        })
                        .setPositiveButton("开启",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        startActivity(new Intent(
                                                Settings.ACTION_WIRELESS_SETTINGS));// 进入无线网络配置界面



//                                    startActivity(new Intent(
//                                            Settings.ACTION_WIFI_SETTINGS)); // 进入手机中的wifi网络设置界面
                                        // finish();
                                    }
                                }).show();

            } catch (DbException e) {
                e.printStackTrace();
            }
        }else{
            dataRequest();
        }
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
                    Log.i("====主页", strDe);
                    JSONObject object1 = new JSONObject(strDe);
                    JSONArray array = object1.getJSONArray("bannerList");
                    int len = array.length();
                    dbUtils.dropTable(HomeContentBean.class);
                    for (int i = 0; i < len; i++) {
                        JSONObject object3 = array.getJSONObject(i);
                        BannerBean bean = new BannerBean();
                        bean.setId(object3.getString("id"));
                        bean.setTitle(object3.getString("title"));
                        bean.setDescrition(object3.getString("descrition"));
                        bean.setUrl(object3.getString("url"));
                        bean.setJumpurl(object3.getString("jump_url"));
                        list.add(bean);
                    }
                    dbUtils.saveOrUpdateAll(list);
                    List<ImageView> listImage = setData(list);
                    bannerSet(listImage);
                    List<HomeContentBean> listBean = new ArrayList<HomeContentBean>();
                    dbUtils.dropTable(HomeContentBean.class);
                    String str = object1.getString("newHand");
                    contentSet(str,listBean);
                    String str2=object1.getString("seven");
                    contentSet(str2,listBean);
                    //listBean.add();
                    // Log.i("==========", listBean.size()+"");
                    dbUtils.saveOrUpdateAll(listBean);
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
        adapter2 = new HomePagerAdapter(listImage,activity);
        vp_index.setAdapter(adapter2);
        //vp_index.setCurrentItem(Integer.MAX_VALUE / 2 - 4 - (Integer.MAX_VALUE / 2) % 4);
       // vp_index.setCurrentItem(Integer.MAX_VALUE/2);
       // vp_index.setAdapter(adapter3);
        //实现自动切换界面
        //在外层，将mViewPager初始位置设置为1即可
        //adapter2.addAll(listImage);
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
            String joinAmount=object2.getString("joinAmount");
            String enablleBuy = object2.getString("enableBuy");//是否售罄
            Integer id=object2.getInt("id");//计划的ID
            boolean has = object2.has("repayType");
            if(has){
                String repayType= object2.getString("repayType");//还款方式
                bean.setRepayType(repayType);
            }
            bean.setJoinAmount(joinAmount);
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
            String jump_url=bannerBeans.get(i).getJumpurl();
            iv.setTag(jump_url);
            helper.display(iv, bannerBeans.get(i).getUrl());
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
