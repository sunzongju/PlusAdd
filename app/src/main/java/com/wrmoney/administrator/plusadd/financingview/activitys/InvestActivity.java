package com.wrmoney.administrator.plusadd.financingview.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.wrmoney.administrator.plusadd.bean.FinancingDetailBean;
import com.wrmoney.administrator.plusadd.bean.FinancingPlanBean;
import com.wrmoney.administrator.plusadd.encode.FinancingParams;
import com.wrmoney.administrator.plusadd.financingview.adapters.FinancingPlanAdapter;
import com.wrmoney.administrator.plusadd.homeview.activitys.HomeSecondActivity;
import com.wrmoney.administrator.plusadd.loginview.activitys.PhoneActivity;
import com.wrmoney.administrator.plusadd.tools.ActionBarSet;
import com.wrmoney.administrator.plusadd.tools.CheckNetTool;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.FormatTool;
import com.wrmoney.administrator.plusadd.tools.SingleUserIdTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;
import com.wrmoney.administrator.plusadd.view.ClaculatorDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Administrator on 2015/11/3.
 */
public class InvestActivity extends BaseActivity implements View.OnClickListener{
    private Button btn_detail;
    private Button btn_join;
    private ImageView iv_cal;
    private String  planID;
    private Intent intent;
    private HttpUtils httpUtils;
    private TextView tv_maxFinancing;
    private TextView tv_expectedRate;
    private TextView tv_lockTime;
    private TextView tv_joinCondition;
    private TextView tv_interestDate;
    private TextView tv_repayType;
    private TextView tv_safeguardMode;
    private TextView tv_safelable;
    private FinancingDetailBean bean;
    private String userid;
    private TextView tv_restAmount;
    private String flag;
    private DecimalFormat df = new DecimalFormat("#.##");
   private  Handler handler=new Handler() {
       @Override
       public void handleMessage(Message msg) {
           super.handleMessage(msg);
             final String maxFinancing=msg.getData().getString("MSA1");
             final String lockTime=msg.getData().getString("MSA2");
             final String expectedRate=msg.getData().getString("MSA3");
             iv_cal.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   try {
                       //Log.i("========haha",maxFinancing);
                      // Toast.makeText(InvestActivity.this,"哈哈"+maxFinancing,LENGTH_SHORT).show();
                       final ClaculatorDialog dialog=new ClaculatorDialog(InvestActivity.this,R.style.dialog);
                       dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
                       dialog.show();
                       final EditText et_maxFinancing =(EditText)dialog.findViewById(R.id.et_maxFinancing);
                       et_maxFinancing.setFocusable(true);
                       et_maxFinancing.setFocusableInTouchMode(true);
                       et_maxFinancing.requestFocus();
                       et_maxFinancing.clearFocus();//失去焦点
                       //et_maxFinancing.requestFocus();//获取焦点

                      final EditText et_lockTime=(EditText)dialog.findViewById(R.id.et_lockTime);
                       final EditText et_expectedRate =(EditText)dialog.findViewById(R.id.et_expectedRate);
                        final TextView tv_income=(TextView)dialog.findViewById(R.id.tv_income);
                       ImageView iv_dismis=(ImageView)dialog.findViewById(R.id.iv_dismis);
                       iv_dismis.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               dialog.dismiss();

                           }
                       });

                       final  Button btn_count=(Button)dialog.findViewById(R.id.btn_count);
                       double expectedRate0 = Double.parseDouble(lockTime);
                       double lockTime0=Double.parseDouble(expectedRate);
                       double count=(100*expectedRate0*lockTime0)/365;
                       String result = String.format("%.2f", count);
                      // String str=df.format(count+"");
                       tv_income.setText(result);
                       et_maxFinancing.setText("10000");
                       et_lockTime.setText(lockTime);
                       et_expectedRate.setText(expectedRate);
                       btn_count.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               et_maxFinancing.setFocusableInTouchMode(false);
                               et_maxFinancing.setFocusable(false);
                               try {
                                   double maxFinancing = Double.parseDouble(et_maxFinancing.getText().toString());
                                   double expectedRate = Double.parseDouble(et_expectedRate.getText().toString());
                                   double lockTime = Double.parseDouble(et_lockTime.getText().toString());
                                   double count = (maxFinancing * expectedRate * lockTime) / 36500;
                                   String result = String.format("%.2f", count);
                                   tv_income.setText(result);
                               } catch (Exception e) {
                                   e.printStackTrace();
                               }

                           }
                       });
                       et_maxFinancing.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               //  viewById.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
                               v.setFocusable(true);
                               v.setFocusableInTouchMode(true);
                               v.requestFocus();
                               et_maxFinancing.setText("");
                           }
                       });
                   }catch (Exception e){
                       e.printStackTrace();
                   }
               }
           });

       }
   };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financing_invest);
        ActionBarSet.setActionBar(this);
        TextView tv_banner=(TextView)this.findViewById(R.id.tv_banner);
        tv_banner.setText("产品详情");
        btn_detail=(Button)this.findViewById(R.id.btn_detail);
        btn_join=(Button)this.findViewById(R.id.btn_join);
        iv_cal=(ImageView)this.findViewById(R.id.iv_cal);
        tv_safelable=(TextView)this.findViewById(R.id.tv_safelable);

       // iv_cal.setOnClickListener(this);
        btn_detail.setOnClickListener(this);
        btn_join.setOnClickListener(this);
        tv_safelable.setOnClickListener(this);
        intent=getIntent();
        planID=intent.getStringExtra("PLANID");
        flag=intent.getStringExtra("FLAG");
        if("Y".equals(flag)){
            btn_join.setClickable(false);
        }else {
            btn_join.setText("立即加入");
            btn_join.setClickable(true);
            btn_join.setBackgroundResource(R.drawable.button_press);
        }
        //Log.i("=========",planID+"用户ID");
        init();
    }


    public void init(){
        userid= SingleUserIdTool.newInstance().getUserid();
        if(SingleUserIdTool.newInstance().getUserid()!=null){
           // Log.i("=====UserID2",userid);
        }
        tv_maxFinancing=(TextView)this.findViewById(R.id.tv_maxFinancing);//项目金额
        tv_expectedRate=(TextView)this.findViewById(R.id.tv_expectedRate);//预期年化收益率
        tv_lockTime=(TextView)this.findViewById(R.id.tv_lockTime);//锁定期限
        tv_restAmount=(TextView)this.findViewById(R.id.tv_restAmount);//剩余可投金额
        tv_joinCondition=(TextView)this.findViewById(R.id.tv_joinCondition);//加入条件
        tv_interestDate=(TextView)this.findViewById(R.id.tv_interestDate);//计息方式
        tv_repayType=(TextView)this.findViewById(R.id.tv_repayType);//还款方式
        tv_safeguardMode=(TextView)this.findViewById(R.id.tv_safeguardMode);//安全保障
        tv_safeguardMode.setOnClickListener(this);
        httpUtils = new HttpUtils(10000);
        dataRequest();
    }

    /**
     * 数据请求
     */
    public void dataRequest(){
        Boolean b = CheckNetTool.checkNet(this);
        if(b){
            RequestParams params = FinancingParams.getPlanDetailsCode(planID);
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
                        //Log.i("=======详情", strDe);
                        setBean(strDe);
                        setData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(HttpException e, String s) {
                    e.printStackTrace();
                    //  Toast.makeText(InvestActivity.this, "失败", LENGTH_SHORT).show();
                }
            });
        }
    }

    public void setBean(String str){
        try {
            JSONObject object1=new JSONObject(str);
           bean=new FinancingDetailBean();
            bean.setPlanId(object1.getString("planId"));
            bean.setPlanName(object1.getString("planName"));
            bean.setMaxFinancing(object1.getString("maxFinancing"));
            bean.setExpectedRate(object1.getString("expectedRate"));
            bean.setLockTime(object1.getString("lockTime"));
            bean.setJoinTimes(object1.getString("joinTimes"));
            bean.setRestAmount(object1.getString("restAmount"));
            bean.setJoinCondition(object1.getString("joinCondition"));
            bean.setInterestDate(object1.getString("interestDate"));
            bean.setRepayType(object1.getString("repayType"));
            bean.setSafeguardMode(object1.getString("safeguardMode"));
            bean.setIntroduce(object1.getString("introduce"));
            bean.setInvestContent(object1.getString("investContent"));
            bean.setCooperativeOrg(object1.getString("cooperativeOrg"));
            bean.setExitMemo(object1.getString("exitMemo"));
            bean.setExitArriveDate(object1.getString("exitArriveDate"));
            bean.setCost(object1.getString("cost"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setData(){
        String str1=FormatTool.amtFormat(bean.getMaxFinancing());
        tv_maxFinancing.setText(str1);
        tv_expectedRate.setText(bean.getExpectedRate()+"%");
        tv_lockTime.setText(bean.getLockTime());
        String str2=FormatTool.amtFormat(bean.getRestAmount());
        if("Y".equals(flag)){
            tv_restAmount.setText("剩余可投金额0.00元");
        }else {
            tv_restAmount.setText("剩余可投金额"+str2+"元");
        }
        tv_joinCondition.setText(bean.getJoinCondition());
        tv_interestDate.setText(bean.getInterestDate());
        tv_repayType.setText(bean.getRepayType());
        tv_safeguardMode.setText(bean.getSafeguardMode());
        final String maxFinancing = bean.getMaxFinancing();
        final String lockTime = bean.getLockTime();
        final String expectedRate = bean.getExpectedRate();
      //  Log.i("========比例",expectedRate);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("MSA1", maxFinancing);
                bundle.putString("MSA2", lockTime);
                bundle.putString("MSA3", expectedRate);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }).start();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_detail:
                Intent intent0=new Intent(this,InvestDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelable("BEAN",bean);
                intent0.putExtras(bundle);
                startActivity(intent0);
                break;
            case R.id.btn_join:
                if (userid == null) {
                    Intent intent11 = new Intent(this, PhoneActivity.class);
                    intent11.putExtra("PLANID",planID);
                    startActivity(intent11);
                     //startActivity(intent11);
                   // finish();
                } else {
                   // Log.i("=======InvestUserid",SingleUserIdTool.newInstance().getUserid());
                    SingleUserIdTool.newInstance().setUserid(SingleUserIdTool.newInstance().getUserid());
                    Intent intent1=new Intent(this,InvestWebJoinActivity.class);
                    Bundle bundle2=new Bundle();
//                    bundle2.putParcelable("BEAN",bean);
                    bundle2.putString("PLANID",bean.getPlanId());
                    intent1.putExtras(bundle2);
                    startActivity(intent1);
                }

                break;
            case R.id.iv_cal:

//                ClaculatorDialog dialog = new ClaculatorDialog(InvestActivity.this, R.style.dialog);
//                dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
//                dialog.show();
//                TextView tv_title =(TextView)dialog.findViewById(R.id.tv_title);
//                tv_title.setText("哈哈");
                break;
            case R.id.tv_safelable:
            case R.id.tv_safeguardMode:
                Intent intent=new Intent(this, HomeSecondActivity.class);
                startActivity(intent);
                break;
            default:
                break;

        }
    }
}
