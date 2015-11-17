package com.wrmoney.administrator.plusadd.accountview.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
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
import com.wrmoney.administrator.plusadd.accountview.activitys.EssayActivity;
import com.wrmoney.administrator.plusadd.accountview.activitys.InvestMentActivity;
import com.wrmoney.administrator.plusadd.accountview.activitys.InvitationActivity;
import com.wrmoney.administrator.plusadd.accountview.activitys.MessageActivity;
import com.wrmoney.administrator.plusadd.accountview.activitys.MoneyWaterActivity;
import com.wrmoney.administrator.plusadd.accountview.activitys.RechargeActivity;
import com.wrmoney.administrator.plusadd.accountview.activitys.RedPacketActivity;
import com.wrmoney.administrator.plusadd.accountview.activitys.VoucherActivity;
import com.wrmoney.administrator.plusadd.accountview.adapters.AccountMenuAdapter;
import com.wrmoney.administrator.plusadd.bean.PictureBean;
import com.wrmoney.administrator.plusadd.encode.UserCenterParams;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.SingleUserIdTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;
import com.wrmoney.administrator.plusadd.view.BadgeView;

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
    R.drawable.account_05,R.drawable.account_06,R.drawable.account_07,R.drawable.account_08,0};
    private String[] tle={"投资管理","资金流水","活动专区","邀请机制","红包","抵用券",
    "充值","取现",""};
    private ImageView iv_news;
    private BadgeView badge1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

    private void init() {
        activity = getActivity();
        tv_money = (TextView) view.findViewById(R.id.tv_money);
        tv_addup = (TextView) view.findViewById(R.id.tv_addup);
        tv_count = (TextView) view.findViewById(R.id.tv_count);
        tv_balance=(TextView)view.findViewById(R.id.tv_balance);
        iv_news=(ImageView)view.findViewById(R.id.iv_news);
       badge1 = new BadgeView(activity, iv_news);//创建一个BadgeView对象，view为你需要显示提醒信息的控件
//BadgeViewbadge1 = new BadgeView(this, view,0); 在使用tabhost使用这个，view为
//TabWidget，0表示tabhost里面的第几个TabWidget
        badge1.setText("12"); //显示类容
        badge1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);//显示的位置.中间，还有其他位置属性
        badge1.setTextColor(Color.WHITE);  //文本颜色
        badge1.setBadgeBackgroundColor(Color.RED); //背景颜色
        badge1.setTextSize(12); //文本大小
        badge1.setBadgeMargin(0, 0); //水平和竖直方向的间距
        badge1.toggle();

        iv_news.setOnClickListener(this);
        userid = SingleUserIdTool.newInstance().getUserid();
//        btn_recharge=(Button)view.findViewById(R.id.btn_recharge);
//        btn_recharge.setOnClickListener(this);
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
               PictureBean bean= list.get(position);
                switch (bean.getImageId()){
                    case R.drawable.account_01://投资管理
                        Intent intent1 = new Intent(activity, InvestMentActivity.class);
                        intent1.putExtra("USERID", userid);
                        startActivity(intent1);
                        break;
                    case R.drawable.account_02://资金流水
                        Intent intent2 = new Intent(activity, MoneyWaterActivity.class);
                        intent2.putExtra("USERID", userid);
                        startActivity(intent2);
                        break;
                    case R.drawable.account_03://活动专区
//             Intent intent6=new Intent(this, ActivityListActivity.class);
//             startActivity(intent6);
                        Intent intent3 = new Intent(activity, ActivityCenterActivity.class);
                        intent3.putExtra("USERID", userid);
                        startActivity(intent3);
                        break;
                    case R.drawable.account_04://邀请机制
//             Intent intent6=new Intent(this, ActivityListActivity.class);
//             startActivity(intent6);

                        Intent intent4 = new Intent(activity,InvitationActivity.class);
                        intent4.putExtra("USERID", userid);
                        startActivity(intent4);
                        break;

                    case R.drawable.account_06://红包

                        Toast.makeText(activity,"充值",Toast.LENGTH_SHORT).show();
                        Intent intent5 = new Intent(activity,RedPacketActivity .class );
                        startActivity(intent5);
                        break;
                    case R.drawable.account_05://抵用券

                        Intent intent6 = new Intent(activity, VoucherActivity.class);
                        startActivity(intent6);
                        break;
                    case R.drawable.account_07://充值
                        Intent intent7 = new Intent(activity, RechargeActivity.class);
                        startActivity(intent7);
                        break;
                    case R.drawable.account_08://取现
                        Intent intent8 = new Intent(activity,EssayActivity.class );
                        startActivity(intent8);
                        break;
                    default:
                        break;
                }
            }
        });

//        view.findViewById(R.id.btn_investment).setOnClickListener(this);//投资管理
//        view.findViewById(R.id.btn_moneywater).setOnClickListener(this);//资金流水
//        view.findViewById(R.id.btn_activitycenter).setOnClickListener(this);//活动专区
//        view.findViewById(R.id.btn_voucher).setOnClickListener(this);//抵用券
//
//        view.findViewById(R.id.btn_recharge).setOnClickListener(this);//充值
//        view.findViewById(R.id.btn_essay).setOnClickListener(this);//取现
//        view.findViewById(R.id.btn_invitation).setOnClickListener(this);//邀请机制
//        view.findViewById(R.id.btn_red).setOnClickListener(this);//红包

        utils= HttpXutilTool.getUtils();

        // Toast.makeText(this, userid, Toast.LENGTH_SHORT).show();


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
                    Toast.makeText(activity, strDe, Toast.LENGTH_SHORT).show();
                    JSONObject obj2 = new JSONObject(strDe);
                    tv_money.setText("qqqqqqq");
                    // p=new Person();
                    // p.setNewMsgNum(obj2.getString("newMsgNum"));//未读消息数
                    String allAmount = obj2.getString("allAmount");//总资产
                    tv_money.setText(allAmount);
                    //p.setAllAmount(obj2.getString("allAmount"));
                    String incomeAmount = obj2.getString("incomeAmount");//累计收益
                    tv_addup.setText(incomeAmount);
                    // p.setIncomeAmount(obj2.getString("incomeAmount"));
                    String acctBalance=obj2.getString("acctBalance");//账号可用余额
                    // p.setAcctBalance(obj2.getString("acctBalance"));
                    tv_balance.setText(acctBalance);
                    String investAmount = obj2.getString("investAmount");//投资总额
                    tv_count.setText(investAmount);
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

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.iv_news:
                badge1.hide();
            Intent intent=new Intent(activity, MessageActivity.class);
                startActivity(intent);
                break;

        }
    }
}
