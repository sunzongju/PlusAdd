package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
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
import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.accountview.adapters.MoneyWaterAdapter;
import com.wrmoney.administrator.plusadd.accountview.fragments.RedAllFragment;
import com.wrmoney.administrator.plusadd.accountview.fragments.RedAvailableFragment;
import com.wrmoney.administrator.plusadd.accountview.fragments.RedExpiredFragment;
import com.wrmoney.administrator.plusadd.accountview.fragments.RedUsedFragment;
import com.wrmoney.administrator.plusadd.accountview.fragments.WaterAddFragment;
import com.wrmoney.administrator.plusadd.accountview.fragments.WaterAllFragment;
import com.wrmoney.administrator.plusadd.accountview.fragments.WaterCutFragment;
import com.wrmoney.administrator.plusadd.accountview.fragments.WaterInvestFragment;
import com.wrmoney.administrator.plusadd.accountview.fragments.WaterRechargeFragment;
import com.wrmoney.administrator.plusadd.encode.UserCenterParams;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 资金流水界面
 * Created by Administrator on 2015/9/22.
 */
public class MoneyWaterActivity extends BaseActivity {
    private String userid;
    private HttpUtils utils;
    private ListView lv_water;
    private List<String> list=new ArrayList<String>();
    private MoneyWaterAdapter adapter;
    private LinearLayout prg1;

    private RadioGroup rg_water;
    private FragmentTransaction transaction;
    private WaterAddFragment waterAddFragment;
    private WaterAllFragment waterAllFragment;
    private WaterCutFragment waterCutFragment;
    private WaterRechargeFragment waterRechargeFragment;
    private WaterInvestFragment waterInvestFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_water);
//        lv_water = (ListView)this.findViewById(R.id.lv_water);
//        TextView tv=(TextView)this.findViewById(R.id.zhnawei);
//        lv_water.setEmptyView(tv);
        init();
    }

    private void init() {
        rg_water = (RadioGroup) this.findViewById(R.id.rg_water);
        //indicator = (TextView)this.findViewById(R.id.indicator);
        transaction = getSupportFragmentManager().beginTransaction();
        waterAddFragment=new WaterAddFragment();
        waterAllFragment=new WaterAllFragment();
        waterCutFragment=new WaterCutFragment();
        waterRechargeFragment=new WaterRechargeFragment();
        waterInvestFragment=new WaterInvestFragment();
        transaction.add(R.id.container, waterAllFragment);
        transaction.commit();
//        RadioButton radioBtn = (RadioButton) rg_water.getChildAt(0);
//        radioBtn.setBackgroundColor(0xffff6600);
//        radioBtn.setTextColor(Color.WHITE);
        rg_water.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                //选中的RadioButton播放动画
                ScaleAnimation sAnim = new ScaleAnimation(1, 1.1f, 1, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                sAnim.setDuration(2000);
                sAnim.setFillAfter(true);

                //遍历所有的RadioButton
//                for (int i = 0; i < group.getChildCount(); i++) {
//                    RadioButton radioBtn = (RadioButton) group.getChildAt(i);
//                    if (radioBtn.isChecked()) {
//                        //radioBtn.startAnimation(sAnim);
//                        //radioBtn.setBackgroundColor(0xff660000);
//                        radioBtn.setBackgroundColor(0xffff6600);
//                        radioBtn.setTextColor(Color.WHITE);
//                    } else {
//                        //radioBtn.clearAnimation();
//                        //radioBtn.setBackground(border2);
//                        //radioBtn.setBackground(border2);
//                        //radioBtn.setBackgroundResource(0xffff6600);
//                        radioBtn.setBackgroundColor(0xffffffff);
//                        radioBtn.setTextColor(Color.GRAY);
//                    }
//                }
//                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) indicator
//                        .getLayoutParams();
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                switch (checkedId) {
                    case R.id.btn_all:
//                        params.leftMargin = (int) ((0.5) * params.width);
//                        indicator.setLayoutParams(params);
                        if (!waterAllFragment.isAdded()) {
                            transaction2.replace(R.id.container, waterAllFragment);
                        } else {
                            transaction2.show(waterAllFragment);
                        }
                        transaction2.commit();
                        break;
                    case R.id.btn_add:
//                        params.leftMargin = (int) ((2.5) * params.width);
//                        indicator.setLayoutParams(params);
                        if (!waterAddFragment.isAdded()) {
                            transaction2.replace(R.id.container, waterAddFragment);
                        } else {
                            transaction2.show(waterAddFragment);
                        }
                        transaction2.commit();
                        break;
                    case R.id.btn_cut:
//                        params.leftMargin = (int) ((4) * params.width);
//                        indicator.setLayoutParams(params);
                        if (!waterCutFragment.isAdded()) {
                            transaction2.replace(R.id.container, waterCutFragment);
                        } else {
                            transaction2.show(waterCutFragment);
                        }
                        transaction2.commit();
                        break;
                    case R.id.btn_recharge:
//                        params.leftMargin = (int) ((5.5) * params.width);
//                        indicator.setLayoutParams(params);
                        if (!waterRechargeFragment.isAdded()) {
                            transaction2.replace(R.id.container,  waterRechargeFragment);
                        } else {
                            transaction2.show( waterRechargeFragment);
                        }
                        transaction2.commit();
                        break;
                    case R.id.btn_invest:
//                        params.leftMargin = (int) ((5.5) * params.width);
//                        indicator.setLayoutParams(params);
                        if (! waterInvestFragment.isAdded()) {
                            transaction2.replace(R.id.container,  waterInvestFragment);
                        } else {
                            transaction2.show( waterInvestFragment);
                        }
                        transaction2.commit();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
