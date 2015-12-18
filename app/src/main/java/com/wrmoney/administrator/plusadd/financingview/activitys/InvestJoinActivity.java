package com.wrmoney.administrator.plusadd.financingview.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.MainActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.accountview.adapters.VoucherAdapter;
import com.wrmoney.administrator.plusadd.bean.FinancingDetailBean;
import com.wrmoney.administrator.plusadd.bean.FinancingPlanBean;
import com.wrmoney.administrator.plusadd.bean.UsableVoucherBean;
import com.wrmoney.administrator.plusadd.bean.VoucherBean;
import com.wrmoney.administrator.plusadd.encode.FinancingParams;
import com.wrmoney.administrator.plusadd.financingview.adapters.UsableVouAdapter;
import com.wrmoney.administrator.plusadd.tools.ActionBarSet;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.SingleUserIdTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;
import com.wrmoney.administrator.plusadd.view.ClaculatorDialog;
import com.wrmoney.administrator.plusadd.view.VoucherDialog;

import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Administrator on 2015/11/3.
 */
public class InvestJoinActivity extends BaseActivity {
    private TextView tv_redpacket;
    private TextView tv_nate;
    private ListView lv_vou;
    private List<String> list = new ArrayList<String>();
    private String[] str={};
    private Button btn_sure;
    private HttpUtils httpUtils;
    private String userid;
    private String planId;
    private List<VoucherBean> beanList=new ArrayList<VoucherBean>();
    private TextView tv_surplusAmount;
    private TextView tv_acct_balance;
    private TextView tv_income;
    private EditText et_sum;
    private  Handler handler=new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                 String expectedRate=msg.getData().getString("MSA1");
                 String baseLockPeriod=msg.getData().getString("MSA2");
               final double expecte=Double.parseDouble(expectedRate);
               final double lockTime=Double.parseDouble(baseLockPeriod);
                double count=(100*expecte*lockTime)/365;
                String result = String.format("%.2f", count);
                tv_income.setText(result);
                et_sum.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String str=et_sum.getText().toString();
                        if(!"".equals(str)&&str!=null){
                            double sum=Double.parseDouble(str);
                            double count=(sum*expecte*lockTime)/36500;
                            String result = String.format("%.2f", count);
                            tv_income.setText(result);
                        }else {
                            tv_income.setText("0.00");
                        }

                    }
                });

            }
    };
    private Integer tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financing_invest_join);
        ActionBarSet.setActionBar(this);
        TextView tv_banner=(TextView)this.findViewById(R.id.tv_banner);
        tv_banner.setText("买入产品名称");
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
       // FinancingDetailBean bean = bundle.getParcelable("BEAN");
        planId=bundle.getString("PLANID");
        init();

        tv_redpacket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tv_redpacket.getTag()!=null){
                    tag = (Integer)tv_redpacket.getTag();
                }else{
                    tag=-1;
                }
                final VoucherDialog dialog = new VoucherDialog(InvestJoinActivity.this, R.style.dialog);
                dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
                dialog.show();
                beanList.clear();
                for (int i = 0; i < 8; i++) {
                    VoucherBean bean = new VoucherBean();
                    bean.setLotteryTitle("抵用券名称"+i);
                    beanList.add(bean);
                }
                lv_vou = (ListView) dialog.findViewById(R.id.lv_vou);
               final UsableVouAdapter adapter = new UsableVouAdapter(InvestJoinActivity.this, beanList,tag);
                lv_vou.setAdapter(adapter);
              //  lv_vou.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                btn_sure=(Button)dialog.findViewById(R.id.btn_sure);
                btn_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tv_redpacket.setText(adapter.str);
                        tv_redpacket.setTag(adapter.tagCheck);
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    public void init(){
        userid=SingleUserIdTool.newInstance().getUserid();
        httpUtils = new HttpUtils(10000);
        tv_surplusAmount=(TextView)this.findViewById(R.id.tv_surplusAmount);//剩余可投金额
        tv_acct_balance=(TextView)this.findViewById(R.id.tv_acct_balance);//可用余额
        tv_income=(TextView)this.findViewById(R.id.tv_income);//预计收益
        et_sum=(EditText)this.findViewById(R.id.et_sum);//金额
        dataRequest();
        voucherRequest();
        tv_redpacket= (TextView)this.findViewById(R.id.tv_redpacket);
        tv_nate=(TextView)this.findViewById(R.id.tv_nate);

    }
    /**
     * 数据请求
     */
    public void dataRequest(){
        //Log.i("==========JOIN","用户ID："+userid+"计划ID"+planId);
        RequestParams params = FinancingParams.getBuyGoodsCode2(userid,planId);
        httpUtils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                // Toast.makeText(activity,"成功", LENGTH_SHORT).show();
                String result = responseInfo.result;
                JSONObject object = null;
                try {
                    List<FinancingPlanBean> list = new ArrayList<FinancingPlanBean>();
                    object = new JSONObject(result);
                    String strResponse = object.getString("argEncPara");
                    String strDe = DES3Util.decode(strResponse);
                //    Log.i("=======购买产品", strDe);
                    JSONObject object1=new JSONObject(strDe);
                    tv_surplusAmount.setText(object1.getString("surplusAmount"));
                    tv_acct_balance.setText(object1.getString("acct_balance"));
                    final String expectedRate=object1.getString("expectedRate");
                    final String baseLockPeriod=object1.getString("baseLockPeriod");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("MSA1", expectedRate);
                            bundle.putString("MSA2", baseLockPeriod);
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }
                    }).start();
//                    double expectedRate=Double.parseDouble();
//                    double baseLockPeriod=Double.parseDouble(object1.getString("baseLockPeriod"));
//                    double sum=Double.parseDouble(et_sum.getText().toString());
//                    Log.i("=======购买产品","利率："+expectedRate+"期限"+baseLockPeriod);
//                    double income=expectedRate*baseLockPeriod*100/365;
//                    Log.i("============预计收益：",income+"");
//                    tv_income.setText(income + "");

//                    setBean(strDe);
//                    setData();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                //Toast.makeText(InvestJoinActivity.this, "失败", LENGTH_SHORT).show();
            }
        });

    }

    public void voucherRequest(){
       //  Log.i("==========JOIN","用户ID："+userid+"计划ID"+planId);
        RequestParams params = FinancingParams.getSureBuyCode(userid,planId);
        httpUtils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                // Toast.makeText(activity,"成功", LENGTH_SHORT).show();
                String result = responseInfo.result;
                JSONObject object = null;
                try {
                    List<UsableVoucherBean> list = new ArrayList<UsableVoucherBean>();
                    object = new JSONObject(result);
                    String strResponse = object.getString("argEncPara");
                    String strDe = DES3Util.decode(strResponse);
                 //   Log.i("=======可用抵用券", strDe);
//                    setBean(strDe);
//                    setData();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
              //  Toast.makeText(InvestJoinActivity.this, "失败", LENGTH_SHORT).show();
            }
        });
    }

    private void showPopupWindow(View view) {
        //Toast.makeText(this,"红包",Toast.LENGTH_SHORT).show();
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.financing_join_red_packet, null);

         PopupWindow popupWindow = new PopupWindow(contentView,
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);

        popupWindow.setOutsideTouchable(true); // 设置是否允许在外点击使其消失，到底有用没？

//        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                return false;
//                // 这里如果返回true的话，touch事件将被拦截
//                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
//            }
//        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.wallpaper_2));
//
//        // 设置好参数之后再show
        popupWindow.showAsDropDown(tv_nate,100,100);//设置显示位置
        popupWindow.update();
        popupWindow.showAsDropDown(view);
//
//    }
}
}
