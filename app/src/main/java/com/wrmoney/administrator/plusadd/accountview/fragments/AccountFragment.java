package com.wrmoney.administrator.plusadd.accountview.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.wrmoney.administrator.plusadd.accountview.activitys.ActivityCenterActivity;
import com.wrmoney.administrator.plusadd.accountview.activitys.ActivityFDetailActivity;
import com.wrmoney.administrator.plusadd.accountview.activitys.EssayActivity;
import com.wrmoney.administrator.plusadd.accountview.activitys.InvestMentActivity;
import com.wrmoney.administrator.plusadd.accountview.activitys.InvitationActivity;
import com.wrmoney.administrator.plusadd.accountview.activitys.MessageActivity;
import com.wrmoney.administrator.plusadd.accountview.activitys.MoneyWaterActivity;
import com.wrmoney.administrator.plusadd.accountview.activitys.RechargeActivity;
import com.wrmoney.administrator.plusadd.accountview.activitys.RedPacketActivity;
import com.wrmoney.administrator.plusadd.accountview.activitys.VoucherActivity;
import com.wrmoney.administrator.plusadd.accountview.adapters.AccountMenuAdapter;
import com.wrmoney.administrator.plusadd.bean.MessageBean;
import com.wrmoney.administrator.plusadd.bean.PictureBean;
import com.wrmoney.administrator.plusadd.encode.UserCenterParams;
import com.wrmoney.administrator.plusadd.homeview.activitys.ActivityDetailActivity;
import com.wrmoney.administrator.plusadd.tools.CheckNetTool;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.FormatTool;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.SingleUserIdTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;
import com.wrmoney.administrator.plusadd.view.AutoScrollTextView;
import com.wrmoney.administrator.plusadd.view.BadgeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/2.
 */
public class AccountFragment extends BaseFragment implements View.OnClickListener{

    private View view;
    private FragmentActivity activity;
    private TextView tv_money;
    private TextView tv_addup;
    private TextView tv_count;
    private TextView tv_balance;
    private String userid;
    private Button btn_recharge;
    private HttpUtils utils;
    private GridView gv_menu;
    private List<PictureBean> list=new ArrayList<PictureBean>();
    private int[] pic={R.drawable.account_01,R.drawable.account_02,R.drawable.account_03,R.drawable.account_04,
    R.drawable.account_06,R.drawable.account_07,R.drawable.account_08,0,0};
    private String[] tle={"投资管理","资金流水","活动专区","邀请机制","抵用券",
    "充值","提现","",""};
    private ImageView iv_news;
    private BadgeView badge1;
    private TranslateAnimation mHiddenAction;
    private String activityTitle;
    private AutoScrollTextView autoScrollTextView;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                    0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
            mHiddenAction.setDuration(500);
            layout_activity.startAnimation(mHiddenAction);
            layout_activity.setVisibility(View.INVISIBLE);
            autoScrollTextView.stopScroll();

        }
    };
    private Bundle bundleWhole=new Bundle();
    private ImageView iv_activity;
    private LinearLayout layout_activity;
    private Thread thread;
    private String jumpUrl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Log.i("=======", "AccountCreateView");
        view=inflater.inflate(R.layout.fragment_account, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        init();
    }

    public static AccountFragment newInstance(){
        AccountFragment fragment=new AccountFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        layout_activity.setVisibility(View.VISIBLE);
        autoScrollTextView = (com.wrmoney.administrator.plusadd.view.AutoScrollTextView)view.findViewById(R.id.TextViewNotice);
        autoScrollTextView.startScroll();
        autoScrollTextView.init(activity.getWindowManager());
        autoScrollTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!"".equals(jumpUrl) && jumpUrl != null) {
                    Intent intent = new Intent(activity, ActivityFDetailActivity.class);
                    intent.putExtra("URL", jumpUrl);
                    startActivity(intent);
                }
            }
        });
        if (thread!=null){
            thread.interrupt();
        }
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    Message msg = new Message();
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        //throw new NumberFormatException();
        thread.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        //thread.isDaemon();
    }

    private void init() {
        activity = getActivity();
        tv_money = (TextView) view.findViewById(R.id.tv_money);
        tv_addup = (TextView) view.findViewById(R.id.tv_addup);
        tv_count = (TextView) view.findViewById(R.id.tv_count);
        tv_balance=(TextView)view.findViewById(R.id.tv_balance);
        iv_news=(ImageView)view.findViewById(R.id.iv_news);
        iv_news.setOnClickListener(this);

        iv_activity=(ImageView)view.findViewById(R.id.iv_activity);
        iv_activity.setOnClickListener(this);
        layout_activity=(LinearLayout)view.findViewById(R.id.layout_activity);
        utils= HttpXutilTool.getUtils();
        userid = SingleUserIdTool.newInstance().getUserid();
        int len=pic.length;
        list.clear();
        for (int i=0;i<len;i++){
            PictureBean bean=new PictureBean();
            bean.setImageId(pic[i]);
            bean.setTitle(tle[i]);
            list.add(bean);
        }
        gv_menu=(GridView)view.findViewById(R.id.gv_menu);
        AccountMenuAdapter adapter=new AccountMenuAdapter(activity,list);
        gv_menu.setAdapter(adapter);

        gv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PictureBean bean = list.get(position);
                switch (bean.getImageId()) {
                    case R.drawable.account_01://投资管理
                        Intent intent1 = new Intent(activity, InvestMentActivity.class);
                        intent1.putExtras(bundleWhole);
                        startActivity(intent1);
                        break;
                    case R.drawable.account_02://资金流水
                        Intent intent2 = new Intent(activity, MoneyWaterActivity.class);
                        intent2.putExtras(bundleWhole);
                        startActivity(intent2);
                        break;
                    case R.drawable.account_03://活动专区
                        Intent intent3 = new Intent(activity, ActivityCenterActivity.class);
                        intent3.putExtra("USERID", userid);
                        startActivity(intent3);
                        break;
                    case R.drawable.account_04://邀请机制
                        Intent intent4 = new Intent(activity, InvitationActivity.class);
                        intent4.putExtra("USERID", userid);
                        startActivity(intent4);
                        break;

//                    case R.drawable.account_05://红包
//                        Toast.makeText(activity,"充值",Toast.LENGTH_SHORT).show();
//                        Intent intent5 = new Intent(activity,RedPacketActivity .class );
//                        startActivity(intent5);
//                        break;
                    case R.drawable.account_06://抵用券

                        Intent intent6 = new Intent(activity, VoucherActivity.class);
                        startActivity(intent6);
                        break;
                    case R.drawable.account_07://充值
                        Intent intent7 = new Intent(activity, RechargeActivity.class);
                        startActivity(intent7);
                        break;
                    case R.drawable.account_08://取现
                        Intent intent8 = new Intent(activity, EssayActivity.class);
                        startActivity(intent8);
                        break;
                    default:
                        break;
                }
            }
        });

        badge1 = new BadgeView(activity, iv_news);//创建一个BadgeView对象，view为你需要显示提醒信息的控件
        badge1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);//显示的位置.中间，还有其他位置属性
        badge1.setTextColor(Color.WHITE);  //文本颜色
        badge1.setBadgeBackgroundColor(Color.RED); //背景颜色
        badge1.setTextSize(10); //文本大小
        badge1.setBadgeMargin(0, 0); //水平和竖直方向的间距
//        badge1.setHeight(20);
        badge1.toggle();
        dataRequest();
        //messageCount();
    }

    @Override
    public void onStart() {
        super.onStart();
        RequestParams params = UserCenterParams.getUpdateCode(userid);
        utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                JSONObject object = null;
                try {
                    object = new JSONObject(result);
                    String strResponse = object.getString("argEncPara");
                    String strDe = DES3Util.decode(strResponse);
                    JSONObject obj2 = new JSONObject(strDe);
                    String len = obj2.getString("newMsgNum");
                    // badge1.setText("12"); //显示类容
                    int tag=Integer.parseInt(len);
                    if(tag>0){
                        if(tag>99){
                            badge1.setText("99+");
                        }else {
                            badge1.setText(len); //显示类容
                        }
                    }else {
                        badge1.hide();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    /**
     * 数据请求
     */
    public void dataRequest() {
        Boolean b = CheckNetTool.checkNet(activity);
        if(b){
            RequestParams params = UserCenterParams.getUpdateCode(userid);
            utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    String result = responseInfo.result;
                    JSONObject object = null;
                    try {
                        object = new JSONObject(result);
                        String strResponse = object.getString("argEncPara");
                        String strDe = DES3Util.decode(strResponse);
                        Log.i("======个人中心", strDe);
                        JSONObject obj2 = new JSONObject(strDe);
                        String allAmount = obj2.getString("allAmount");//总资产
                        Log.i("======allAmount",allAmount);
                        if("".equals(allAmount)||allAmount==null||"0".equals(allAmount)){
                            tv_money.setText("0.00");
                        }else {
                            tv_money.setText(allAmount);
                        }
                        String incomeAmount = obj2.getString("incomeAmount");//累计收益
                        if("".equals(incomeAmount)||incomeAmount==null||"0".equals(incomeAmount)){
                            tv_addup.setText("0.00");

                        }else {
                            tv_addup.setText(incomeAmount);
                        }
                        String acctBalance=obj2.getString("acctBalance");//账号可用余额
                        if("".equals(acctBalance)||acctBalance==null||"0".equals(acctBalance)){
                            tv_balance.setText("0.00");
                        }else {
                            tv_balance.setText(acctBalance);
                        }
                        String investAmount = obj2.getString("investAmount");//投资总额
                        if("".equals(investAmount)||investAmount==null||"0".equals(investAmount)){
                            tv_count.setText("0.00");
                        }else {
                            tv_count.setText(investAmount);
                        }
                        //tv_count.setText(FormatTool.amtFormat(investAmount));
                        bundleWhole.putString("allAmount", allAmount);//总额
                        bundleWhole.putString("incomeAmount",incomeAmount);//累计收益
                        bundleWhole.putString("acctBalance",acctBalance);
                        JSONArray array=obj2.getJSONArray("listActive");
                        if(array.length()>0){
                            JSONObject object3=array.getJSONObject(0);
                            activityTitle=object3.getString("title");
                            jumpUrl=object3.getString("jumpUrl");

                        }
                        if(!"".equals(activityTitle)&&activityTitle!=null){
                            //  Log.i("=====活动标题",activityTitle);
                            //autoScrollTextView.setText(activityTitle);
                            autoScrollTextView.setText2(activityTitle);
                            autoScrollTextView.startScroll();


                        }
                        // p.setInvestAmount(obj2.getString("investAmount"));
                        //String activityTitle=obj2.getString("activityTitle");//活动标题
                        // p.setActivityTitle(obj2.getString("activityTitle"));
                        // Toast.makeText(FinancingActivity.this, ""+allAmount+"累计"+incomeAmount+"投资"+investAmount, Toast.LENGTH_SHORT).show()
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(HttpException e, String s) {

                }
            });
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.iv_news:
                Intent intent=new Intent(activity, MessageActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_activity:
                layout_activity.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
