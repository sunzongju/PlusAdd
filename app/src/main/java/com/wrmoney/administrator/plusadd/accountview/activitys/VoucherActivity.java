package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshAdapterViewBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.accountview.adapters.VoucherAdapter;
import com.wrmoney.administrator.plusadd.accountview.adapters.VouchersAdapter;
import com.wrmoney.administrator.plusadd.bean.ActivityListBean;
import com.wrmoney.administrator.plusadd.bean.VoucherBean;
import com.wrmoney.administrator.plusadd.encode.UserCenterParams;
import com.wrmoney.administrator.plusadd.tools.ActionBarSet;
import com.wrmoney.administrator.plusadd.tools.CheckNetTool;
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
 *抵用券界面����
 * Created by Administrator on 2015/11/2.
 */
public class VoucherActivity extends BaseActivity {
    private ListView lv_red;
    private List<VoucherBean> list=new ArrayList<VoucherBean>();
    private String userid;
    private HttpUtils utils;
    private RadioGroup rg_group;
    private VouchersAdapter adapter;
    private PullToRefreshListView lv_voucher;
    private int current=1;
    private int checked=R.id.btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_red);
        ActionBarSet.setActionBar(this);
        ActionBarSet.setHelpBar(this);
        TextView tv_banner=(TextView)this.findViewById(R.id.tv_banner);
        tv_banner.setText("我的抵用券");
        init();
    }
    /**
     * 初始化界面
     */
    public void init() {
        lv_voucher=(PullToRefreshListView)this.findViewById(R.id.lv_voucher);
        View v= LayoutInflater.from(this).inflate(R.layout.empty_view,null);
        lv_voucher.setEmptyView(v);
        userid = SingleUserIdTool.newInstance().getUserid();
        utils = HttpXutilTool.getUtils();
        //Toast.makeText(this,"抵用券",Toast.LENGTH_SHORT).show();
        rg_group = (RadioGroup) this.findViewById(R.id.rg);
        adapter=new VouchersAdapter(list,this);
        lv_voucher.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        dataRequest("0", current);
        ILoadingLayout loadingLayoutProxy = lv_voucher.getLoadingLayoutProxy();
        loadingLayoutProxy.setPullLabel("");
        lv_voucher.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                current=1;
                list.clear();
                switch (checked) {
                    case R.id.btn1:
                        dataRequest("0", current);
                        break;
                    case R.id.btn2:
                        dataRequest("1",current);
                        break;
                    case R.id.btn3:
                        dataRequest("2",current);
                        break;
                    case R.id.btn4:
                        dataRequest("3",current);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                current++;
                switch (checked) {
                    case R.id.btn1:
                        dataRequest("0", current);
                        break;
                    case R.id.btn2:;
                        dataRequest("1",current);
                        break;
                    case R.id.btn3:
                        dataRequest("2",current);
                        break;
                    case R.id.btn4:
                        dataRequest("3",current);
                        break;
                    default:
                        break;
                }
            }
        });
        //indicator = (TextView)this.findViewById(R.id.indicator);
        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                current = 1;
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
                        checked = R.id.btn1;
                        list.clear();
                        dataRequest("0", current);
                        transaction2.commit();
                        break;
                    case R.id.btn2:
                        checked = R.id.btn2;
                        list.clear();
                        dataRequest("1", current);
                        break;
                    case R.id.btn3:
                        checked = R.id.btn3;
                        list.clear();
                        dataRequest("2", current);
                        break;
                    case R.id.btn4:
                        checked = R.id.btn4;
                        list.clear();
                        dataRequest("3", current);
                        break;
                    default:
                        break;
                }
            }
        });

    }

    /**
     * 数据请求
     */
    public void dataRequest(String type,int current) {
     //   Log.i("========类型",type);
        Boolean b = CheckNetTool.checkNet(this);
        if(b){
            RequestParams params = UserCenterParams.getBonusCode(userid, type, current+"", "10");
            utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    String result = responseInfo.result;
                    JSONObject object = null;
                    try {
                        List<VoucherBean> list2=new ArrayList<VoucherBean>();
                        object = new JSONObject(result);
                        String strResponse = object.getString("argEncPara");
                        String strDe = DES3Util.decode(strResponse);
                        //  Log.v("======抵用券", strDe);
                        JSONObject object1=new JSONObject(strDe);
                        JSONArray array=object1.getJSONArray("lotteryList");
                        int len=array.length();
                        for(int i=0;i<len;i++){
                            VoucherBean bean=new VoucherBean();
                            JSONObject object2=array.getJSONObject(i);
                            bean.setLotteryId(object2.getString("lotteryId"));
                            bean.setLotteryAmount(object2.getString("lotteryAmount"));
                            bean.setLotteryStatus(object2.getString("lotteryStatus"));
                            bean.setLotteryValidTime(object2.getString("lotteryValidTime"));
                            bean.setLotteryComent(object2.getString("lotteryComent"));
                            bean.setLotteryTitle(object2.getString("lotteryTitle"));
                            list2.add(bean);
                        }
                        adapter.addAll(list2);
                        lv_voucher.onRefreshComplete();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    e.printStackTrace();
                }
            });
        }
    }
}
