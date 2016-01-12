package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
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
import com.wrmoney.administrator.plusadd.accountview.adapters.InvitationDetailAdapter;
import com.wrmoney.administrator.plusadd.bean.InvitationBean;
import com.wrmoney.administrator.plusadd.bean.InvitationDetailBean;
import com.wrmoney.administrator.plusadd.bean.MoneyWaterBean;
import com.wrmoney.administrator.plusadd.encode.UserCenterParams;
import com.wrmoney.administrator.plusadd.tools.ActionBarSet;
import com.wrmoney.administrator.plusadd.tools.CheckNetTool;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.SingleUserIdTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/14.
 */
public class InvitationDetailActivity extends BaseActivity {

    private RadioGroup rg_invitation;

    private List<InvitationDetailBean> list=new ArrayList<InvitationDetailBean>();
    private PullToRefreshListView lv_invitation;
    private String userid;
    private HttpUtils httpUtils;
    private InvitationDetailAdapter adapter;
    private String invitationCode;
    private int current=1;
    private int checked=R.id.btn_all;
    private TextView tv_finish;
    private ListView lv;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_detail);
        ActionBarSet.setActionBar(this);
        TextView tv_banner=(TextView)this.findViewById(R.id.tv_banner);
        tv_banner.setText("邀请明细");
        init();
    }
    public void init(){
        userid= SingleUserIdTool.newInstance().getUserid();
        httpUtils = new HttpUtils(10000);
        invitationCode=getIntent().getStringExtra("CODE");
       rg_invitation= (RadioGroup)this.findViewById(R.id.rg_invitation);
       lv_invitation= (PullToRefreshListView)this.findViewById(R.id.lv_invitation);
        lv = lv_invitation.getRefreshableView();
        tv=new TextView(this);
        tv.setGravity(Gravity.CENTER);
        tv.setText("仅显示近30天的数据");

        View v= LayoutInflater.from(this).inflate(R.layout.empty_view2, null);
        lv_invitation.setEmptyView(v);
        adapter=new InvitationDetailAdapter(this,list);
        lv_invitation.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        dataRequest(invitationCode, "0", current);
        ILoadingLayout loadingLayoutProxy = lv_invitation.getLoadingLayoutProxy();
        loadingLayoutProxy.setPullLabel("");
        lv_invitation.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
               // tv_finish.setVisibility(View.GONE);
                lv.removeFooterView(tv);
                current=1;
                list.clear();
                switch (checked) {
                    case R.id.btn_all:
                        dataRequest(invitationCode,"0",current);
                        break;
                    case R.id.btn_reg:
                        dataRequest(invitationCode,"1",current);
                        break;
                    case R.id.btn_invest:
                        dataRequest(invitationCode,"2",current);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                current++;
                switch (checked) {
                    case R.id.btn_all:
                        dataRequest(invitationCode,"0",current);
                        break;
                    case R.id.btn_reg:
                        dataRequest(invitationCode,"1",current);
                        break;
                    case R.id.btn_invest:
                        dataRequest(invitationCode,"2",current);
                        break;
                    default:
                        break;
                }
            }
        });


        rg_invitation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //选中的RadioButton播放动画
                lv.removeFooterView(tv);
                ScaleAnimation sAnim = new ScaleAnimation(1, 1.1f, 1, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                sAnim.setDuration(2000);
                sAnim.setFillAfter(true);
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                switch (checkedId) {
                    case R.id.btn_all://全部
                        checked = R.id.btn_all;
                        list.clear();
                        dataRequest(invitationCode, "0", current);
                        break;
                    case R.id.btn_reg://邀请注册
                        checked = R.id.btn_reg;
                        list.clear();
                        dataRequest(invitationCode, "1", current);
                        break;
                    case R.id.btn_invest://注册投资
                        checked = R.id.btn_invest;
                        list.clear();
                        dataRequest(invitationCode, "2", current);
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
    public void dataRequest(String invitationCode, String type, int current){
        Boolean b = CheckNetTool.checkNet(this);
        if(b){
            RequestParams params = UserCenterParams.getInviteDetailCode(invitationCode,type,current+"","10");
            httpUtils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    // Toast.makeText(activity,"成功", LENGTH_SHORT).show();
                    String result = responseInfo.result;
                    JSONObject object = null;
                    try {
                        List<InvitationDetailBean> list2=new ArrayList<InvitationDetailBean>();
                        object = new JSONObject(result);
                        String strResponse = object.getString("argEncPara");
                        String strDe = DES3Util.decode(strResponse);
                         Log.i("=======邀请好友详情", strDe);
                        JSONObject object1=new JSONObject(strDe);
                        JSONArray array=object1.getJSONArray("reList");
                        int len=array.length();
                        for(int i=0;i<len;i++){
                            JSONObject object2=array.getJSONObject(i);
                            InvitationDetailBean bean=new InvitationDetailBean();
                            bean.setCommissionAmountStr(object2.getString("commissionAmountStr"));
                            bean.setComminssionTimeStr(object2.getString("commissionTimeStr"));
                            bean.setReturnState(object2.getString("returnState"));
                            bean.setType(object2.getString("type"));
                            bean.setProductType(object2.getString("productType"));
                            bean.setCommissionAmount(object2.getString("commissionAmount"));
                            bean.setComent(object2.getString("coment"));
                            bean.setRegTime(object2.getString("regTime"));
                            bean.setMobile(object2.getString("mobile"));
                            bean.setInvitedUser(object2.getString("invitedUser"));
                            bean.setIfOpenAcct(object2.getString("ifOpenAcct"));
                            if(object2.has("commissionTime")){
                                bean.setCommissionTime(object2.getString("commissionTime"));
                            }
                            bean.setOrderNum(object2.getString("orderNum"));
                            bean.setCount(object2.getString("count"));
                            list2.add(bean);
                        }
                        if(list2.size()<10){
                            int footerViewsCount = lv.getFooterViewsCount();
                            if(footerViewsCount<2){
                                lv.addFooterView(tv);
                            }
                        }
                        adapter.addAll(list2);
                        lv_invitation.onRefreshComplete();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                @Override
                public void onFailure(HttpException e, String s) {
                    e.printStackTrace();
                    //Toast.makeText(MoneyWaterActivity.this, "失败", LENGTH_SHORT).show();
                    lv_invitation.onRefreshComplete();
                }
            });
        }
    }
}
