package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.wrmoney.administrator.plusadd.accountview.adapters.CreditorAdapter;
import com.wrmoney.administrator.plusadd.bean.CreditorListBean;
import com.wrmoney.administrator.plusadd.bean.InvestMentBean;
import com.wrmoney.administrator.plusadd.encode.UserCenterParams;
import com.wrmoney.administrator.plusadd.tools.ActionBarSet;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.SingleUserIdTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;
import com.wrmoney.administrator.plusadd.view.ClaculatorDialog;
import com.wrmoney.administrator.plusadd.view.CreditorDetailDialog;
import com.wrmoney.administrator.plusadd.view.QuitPlanDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/13.
 */
public class InvestDetailActivity extends BaseActivity implements View.OnClickListener{
    private ListView lv_invest;
    private List<CreditorListBean> list=new ArrayList<CreditorListBean>();
    private String orderId;
    private HttpUtils utils;
    private CreditorAdapter adapter;
    private String userid;
    private TextView tv_investDate;
    private TextView tv_planName;
    private TextView tv_expectedRate;
    private TextView tv_investAmount;
    private TextView tv_lockTime;
    private TextView offLineAgreementCd;
    private TextView borrowerName;
    private TextView borrowerIdCard;
    private TextView creditAmount;
    private TextView creditCashValue;
    private CreditorDetailDialog dialog2;
    private QuitPlanDialog dialog;
    private String exitFlag;
    private Button btn_quit;
    private Button btn_http;
    private String agreementUrl;
    private RelativeLayout tv_empty;
    private TextView tv_status;

    ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_invest);
        ActionBarSet.setActionBar(this);
        TextView tv_banner=(TextView)this.findViewById(R.id.tv_banner);
        tv_banner.setText("投资管理");
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        orderId=bundle.getString("ORDERID");
        exitFlag=bundle.getString("EXITFLAG");
        init();
    }
    public void init(){
        userid = SingleUserIdTool.newInstance().getUserid();
        utils = HttpXutilTool.getUtils();
        ImageView iv_index=(ImageView)this.findViewById(R.id.iv_index);
        iv_index.setVisibility(View.GONE);
        tv_investDate=(TextView)this.findViewById(R.id.tv_investDate);//日期
        tv_planName=(TextView)this.findViewById(R.id.tv_planName);//标题
        tv_expectedRate=(TextView)this.findViewById(R.id.tv_expectedRate);//预期收益率
        tv_investAmount=(TextView)this.findViewById(R.id.tv_investAmount);//买入金额
        tv_lockTime=(TextView)this.findViewById(R.id.tv_lockTime);//锁定期
        tv_status=(TextView)this.findViewById(R.id.tv_status);
         btn_http=(Button)this.findViewById(R.id.btn_http);

        btn_quit=(Button)this.findViewById(R.id.btn_quit);
       // Log.i("=======标志111111",exitFlag);
        if("0".equals(exitFlag)){
        }else if("1".equals(exitFlag)){
            btn_quit.setVisibility(View.VISIBLE);
            btn_quit.setOnClickListener(this);
        }
        lv_invest=(ListView)this.findViewById(R.id.lv_creditor);
        tv_empty=(RelativeLayout)this.findViewById(R.id.tv_empty);
//        TextView emptyView = new TextView(this);
//        emptyView.setText("暂无数据");
//        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        addContentView(emptyView, params);
//        lv_invest.setEmptyView(emptyView);
        adapter=new CreditorAdapter(list,this);
        lv_invest.setAdapter(adapter);
        dataRequest();
    }

    /**
     * 数据请求
     */
    public void dataRequest() {
       // Log.i("=====", "userid" + userid + "orderID" + orderId);
        RequestParams params = UserCenterParams.getOrderBondsManagerCode(userid, orderId, "1", "10");
        utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                JSONObject object = null;
                 List<CreditorListBean> list2 = new ArrayList<CreditorListBean>();
                try {
                    object = new JSONObject(result);
                    String strResponse = object.getString("argEncPara");
                    String strDe = DES3Util.decode(strResponse);
                   // Log.i("======债权明细列表", strDe);
                    JSONObject object1 = new JSONObject(strDe);
                    boolean agree = object1.has("agreementUrl");
                    if(agree){
                        agreementUrl =object1.getString("agreementUrl");
                        btn_http.setClickable(true);
                        btn_http.setText("借款协议");
                       // btn_http.setBackgroundColor(getResources().getColor(R.color.orange));
                       // btn_http.setBackground(R.drawable.button_press);
                        btn_http.setBackgroundResource(R.drawable.button_press);
                        btn_http.setOnClickListener(InvestDetailActivity.this);
                    }
                    JSONArray array = null;
                    array = object1.getJSONArray("creditList");
                    int len = array.length();
                    for (int i = 0; i < len; i++) {
                        CreditorListBean bean = new CreditorListBean();
                        JSONObject object2 = array.getJSONObject(i);
                        bean.setCreditId(object2.getString("creditId"));
                        bean.setOffLineAgreementCd(object2.getString("offLineAgreementCd"));
                        bean.setBorrowerIdCard(object2.getString("borrowerIdCard"));
                        bean.setBorrowerName(object2.getString("borrowerName"));
                        bean.setCreditAmount(object2.getString("creditAmount"));
                        bean.setCreditCashValue(object2.getString("creditCashValue"));
                        //bean.setStatus(object2.getString("status"));
                        list2.add(bean);
                    }
                    if(list2.size()>0){
                        lv_invest.setVisibility(View.VISIBLE);
                        adapter.addAll(list2);
                        tv_empty.setVisibility(View.GONE);
                    }else {
                        tv_empty.setVisibility(View.VISIBLE);
                    }
                    tv_investDate.setText(object1.getString("investDate"));
                    tv_planName.setText(object1.getString("planName"));
                    tv_expectedRate.setText("预期收益率：" + object1.getString("expectedRate"));
                    tv_investAmount.setText(object1.getString("investAmount"));
                    tv_lockTime.setText(object1.getString("lockTime")+"天");
                    tv_status.setText(object1.getString("status"));

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
                //Toast.makeText(InvestDetailActivity.this, "失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 退出计划
     */
    public void exitPlan() {
       // Log.i("=====","userid"+userid+"orderID"+orderId);
        RequestParams params = UserCenterParams.getMessageQuitCode(userid,orderId);
        utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
//                String result = responseInfo.result;
//                JSONObject object = null;
//                List<CreditorListBean> list = new ArrayList<CreditorListBean>();
//                try {
//                    object = new JSONObject(result);
//                    String strResponse = object.getString("argEncPara");
//                    String strDe = DES3Util.decode(strResponse);
//                    Log.i("======退出计划", strDe);
//                    JSONObject object1=new JSONObject(strDe);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
               // Toast.makeText(InvestDetailActivity.this, "退出成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
               // Toast.makeText(InvestDetailActivity.this, "退出失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_quit:
                dialog=new QuitPlanDialog(InvestDetailActivity.this,R.style.dialog);
                dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
                dialog.show();
                Button btn_ok=(Button)dialog.findViewById(R.id.btn_ok);
                Button btn_cancle=(Button)dialog.findViewById(R.id.btn_cancle);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exitPlan();
                        dialog.dismiss();
                    }
                });
                btn_cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.btn_http:
                   Intent intent=new Intent(InvestDetailActivity.this,AgreeActivity.class);
                   intent.putExtra("URL",agreementUrl);
                   startActivity(intent);
                break;
            default:
                break;
        }
    }
}
