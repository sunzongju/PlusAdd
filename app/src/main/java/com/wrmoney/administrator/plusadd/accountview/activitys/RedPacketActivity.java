package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
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
import com.wrmoney.administrator.plusadd.accountview.adapters.RedPacketAdapter;
import com.wrmoney.administrator.plusadd.accountview.fragments.RedAllFragment;
import com.wrmoney.administrator.plusadd.accountview.fragments.RedAvailableFragment;
import com.wrmoney.administrator.plusadd.accountview.fragments.RedExpiredFragment;
import com.wrmoney.administrator.plusadd.accountview.fragments.RedUsedFragment;
import com.wrmoney.administrator.plusadd.bean.RedPacketBean;
import com.wrmoney.administrator.plusadd.encode.UserCenterParams;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.SingleUserIdTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 红包界面
 * Created by Administrator on 2015/11/2.
 */
public class RedPacketActivity extends BaseActivity {
    private String userid;
    private HttpUtils utils;
    private List<RedPacketBean> list = new ArrayList<RedPacketBean>();
    private ListView lv_redpacket;
    private RedPacketAdapter adapter;
    private RadioGroup rg_group;
    private TextView indicator;
    private FragmentTransaction transaction1;
    private RedAllFragment redAllFragment;
    private RedAvailableFragment redAvailable;
    private RedUsedFragment redUsedFragment;
    private RedExpiredFragment redExpiredFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_red);

        init();
        // lv_redpacket= (ListView)this.findViewById(R.id.lv_redpacket);
        getIntent().getStringExtra("USERID");
        userid = SingleUserIdTool.newInstance().getUserid();
        utils = HttpXutilTool.getUtils();
        adapter = new RedPacketAdapter(list, this);
        // lv_redpacket.setAdapter(adapter);
        RequestParams params = UserCenterParams.getBonusCode(userid, "0", "1", "10");
        utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                JSONObject object = null;
                try {
                    object = new JSONObject(result);
                    String strResponse = object.getString("argEncPara");
                    String strDe = DES3Util.decode(strResponse);
                    Toast.makeText(RedPacketActivity.this, strDe, Toast.LENGTH_SHORT).show();
                    Log.v("======", strDe);
                    JSONObject obj2 = new JSONObject(strDe);
                    JSONArray array = obj2.getJSONArray("lotteryList");
                    int size = array.length();
                    for (int i = 0; i < size; i++) {
                        RedPacketBean bean = new RedPacketBean();
                        JSONObject obj3 = array.getJSONObject(i);
                        bean.setLotteryAmount(obj3.getString("lotteryAmount"));
                        bean.setLotteryStatus(obj3.getString("lotteryStatus"));
                        bean.setLotteryValidTime(obj3.getString("lotteryValidTime"));
                        list.add(bean);
                    }
                    adapter.addAll(list);

                    // String rescode=obj2.getString("rescode");
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Toast.makeText(LoginActivity.this, strDe, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 初始化界面
     */
    public void init() {
        rg_group = (RadioGroup) this.findViewById(R.id.rg);
        //indicator = (TextView)this.findViewById(R.id.indicator);
        transaction1 = getSupportFragmentManager().beginTransaction();
        redAllFragment = new RedAllFragment();
        redAvailable = new RedAvailableFragment();
        redUsedFragment = new RedUsedFragment();
        redExpiredFragment = new RedExpiredFragment();
        transaction1.add(R.id.container, redAllFragment);
        transaction1.commit();
//        RadioButton radioBtn = (RadioButton) rg_group.getChildAt(0);
//        radioBtn.setBackgroundColor(0xffff6600);
//        radioBtn.setTextColor(Color.WHITE);
        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
//                        radioBtn.setBackgroundResource(R.drawable.border2);
//                        radioBtn.setTextColor(Color.GRAY);
//                    }
//                }
//                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) indicator
//                        .getLayoutParams();
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                switch (checkedId) {
                    case R.id.btn1:
//                        params.leftMargin = (int) ((0.5) * params.width);
//                        indicator.setLayoutParams(params);
                        if (!redAllFragment.isAdded()) {
                            transaction2.replace(R.id.container, redAllFragment);
                        } else {
                            transaction2.show(redAllFragment);
                        }
                        transaction2.commit();
                        break;
                    case R.id.btn2:
//                        params.leftMargin = (int) ((2.5) * params.width);
//                        indicator.setLayoutParams(params);
                        if (!redAvailable.isAdded()) {
                            transaction2.replace(R.id.container, redAvailable);
                        } else {
                            transaction2.show(redAvailable);
                        }
                        transaction2.commit();
                        break;
                    case R.id.btn3:
//                        params.leftMargin = (int) ((4) * params.width);
//                        indicator.setLayoutParams(params);
                        if (!redUsedFragment.isAdded()) {
                            transaction2.replace(R.id.container, redUsedFragment);
                        } else {
                            transaction2.show(redUsedFragment);
                        }
                        transaction2.commit();
                        break;
                    case R.id.btn4:
//                        params.leftMargin = (int) ((5.5) * params.width);
//                        indicator.setLayoutParams(params);
                        if (!redExpiredFragment.isAdded()) {
                            transaction2.replace(R.id.container, redExpiredFragment);
                        } else {
                            transaction2.show(redExpiredFragment);
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
