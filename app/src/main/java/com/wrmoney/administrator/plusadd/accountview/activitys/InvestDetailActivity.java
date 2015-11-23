package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.SingleUserIdTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;
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
public class InvestDetailActivity extends BaseActivity{
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_invest);
        Intent intent=getIntent();
        orderId=intent.getStringExtra("ORDERID");
        init();
    }

    public void init(){
        userid = SingleUserIdTool.newInstance().getUserid();
        utils = HttpXutilTool.getUtils();

        tv_investDate=(TextView)this.findViewById(R.id.tv_investDate);//日期
        tv_planName=(TextView)this.findViewById(R.id.tv_planName);//标题
        tv_expectedRate=(TextView)this.findViewById(R.id.tv_expectedRate);//预期收益率
        tv_investAmount=(TextView)this.findViewById(R.id.tv_investAmount);//买入金额
        tv_lockTime=(TextView)this.findViewById(R.id.tv_lockTime);//锁定期
       lv_invest=(ListView)this.findViewById(R.id.lv_creditor);

        adapter=new CreditorAdapter(list,this);
       lv_invest.setAdapter(adapter);
        dataRequest();
        lv_invest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CreditorListBean bean=list.get(position);
                 dialog2 = new CreditorDetailDialog(InvestDetailActivity.this, R.style.dialog);
                offLineAgreementCd=(TextView)dialog2.findViewById(R.id.tv_offLineAgreementCd);//原始借款合同编号
                borrowerName=(TextView)dialog2.findViewById(R.id.tv_borrowerName);//借款人姓名
                borrowerIdCard=(TextView)dialog2.findViewById(R.id.tv_borrowerIdCard);//借款人身份证号
                creditAmount=(TextView)dialog2.findViewById(R.id.tv_creditAmount);//出借金额
                creditCashValue=(TextView)dialog2.findViewById(R.id.tv_creditCashValue);//债权现金价值
                dataRequest2(bean.getCreditId());
                dialog2.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
                dialog2.show();
            }
        });
    }

    /**
     * 数据请求
     */
    public void dataRequest() {
       // Log.i("=====", "userid" + userid + "orderID" + orderId);
        RequestParams params = UserCenterParams.getOrderBondsManagerCode(userid,orderId,"1","10");
        utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                JSONObject object = null;
                List<CreditorListBean> list = new ArrayList<CreditorListBean>();
                try {
                    object = new JSONObject(result);
                    String strResponse = object.getString("argEncPara");
                    String strDe = DES3Util.decode(strResponse);
                    Log.i("======债权列表", strDe);
                    JSONObject object1 = new JSONObject(strDe);
                    JSONArray array = object1.getJSONArray("creditList");
                    int len = array.length();
                    for (int i = 0; i < len; i++) {
                        CreditorListBean bean = new CreditorListBean();
                        JSONObject object2 = array.getJSONObject(i);
                        bean.setRN(object2.getString("RN"));
                        bean.setCreditId(object2.getString("creditId"));
                        bean.setOffLineAgreementCd(object2.getString("offLineAgreementCd"));
                        list.add(bean);
                    }
                    adapter.addAll(list);
                    tv_investDate.setText(object1.getString("investDate"));
                    tv_planName.setText(object1.getString("planName"));
                    tv_expectedRate.setText("预期收益率：" + object1.getString("expectedRate"));
                    tv_investAmount.setText(object1.getString("investAmount"));
                    tv_lockTime.setText(object1.getString("lockTime" + "天"));
                    // Toast.makeText(InvestMentActivity.this, strDe, Toast.LENGTH_SHORT).show();
                    //JSONObject obj2=new JSONObject(strDe);
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
                Toast.makeText(InvestDetailActivity.this, "失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 数据请求
     */
    public void dataRequest2(String creditId) {
        Log.i("=====","userid"+userid+"orderID"+orderId);
        RequestParams params = UserCenterParams.getInvestManagerDetailCode(userid,creditId);
        utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                JSONObject object = null;
                List<CreditorListBean> list = new ArrayList<CreditorListBean>();
                try {
                    object = new JSONObject(result);
                    String strResponse = object.getString("argEncPara");
                    String strDe = DES3Util.decode(strResponse);
                    Log.i("======债权明细", strDe);
                    JSONObject object1=new JSONObject(strDe);
                    offLineAgreementCd.setText(object1.getString("offLineAgreementCd"));
                    borrowerName.setText(object1.getString("borrowerName"));
                    borrowerIdCard.setText(object1.getString("borrowerIdCard"));
                    creditAmount.setText(object1.getString("creditAmount"));
                    creditCashValue.setText(object1.getString("creditCashValue"));
                    dialog2.notifyAll();
                    // Toast.makeText(InvestMentActivity.this, strDe, Toast.LENGTH_SHORT).show();
                    //JSONObject obj2=new JSONObject(strDe);
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
                Toast.makeText(InvestDetailActivity.this, "失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void click(View view){
        switch (view.getId()){
            case R.id.btn_quit:
                 dialog=new QuitPlanDialog(InvestDetailActivity.this,R.style.dialog);
                dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
                dialog.show();
                Button btn=(Button)dialog.findViewById(R.id.btn_ok);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      dialog.dismiss();
                    }
                });
                break;
            case R.id.btn_http:

                break;

            default:
                break;
        }

    }
}
