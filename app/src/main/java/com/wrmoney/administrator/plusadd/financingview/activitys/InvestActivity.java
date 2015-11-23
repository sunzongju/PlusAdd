package com.wrmoney.administrator.plusadd.financingview.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.wrmoney.administrator.plusadd.bean.FinancingPlanBean;
import com.wrmoney.administrator.plusadd.encode.FinancingParams;
import com.wrmoney.administrator.plusadd.financingview.adapters.FinancingPlanAdapter;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.UrlTool;
import com.wrmoney.administrator.plusadd.view.ClaculatorDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financing_invest);
        btn_detail=(Button)this.findViewById(R.id.btn_detail);
        btn_join=(Button)this.findViewById(R.id.btn_join);
        iv_cal=(ImageView)this.findViewById(R.id.iv_cal);
        iv_cal.setOnClickListener(this);
        btn_detail.setOnClickListener(this);
        btn_join.setOnClickListener(this);
        intent=getIntent();
       planID=intent.getStringExtra("PLANID");
        //Log.i("=========",planID+"用户ID");
        init();
    }
    public void init(){
        tv_maxFinancing=(TextView)this.findViewById(R.id.tv_maxFinancing);//项目金额
        tv_expectedRate=(TextView)this.findViewById(R.id.tv_expectedRate);//预期年化收益率
        tv_lockTime=(TextView)this.findViewById(R.id.tv_lockTime);//锁定期限

        tv_joinCondition=(TextView)this.findViewById(R.id.tv_joinCondition);//加入条件
        tv_interestDate=(TextView)this.findViewById(R.id.tv_interestDate);//计息方式
        tv_repayType=(TextView)this.findViewById(R.id.tv_repayType);//还款方式
        tv_safeguardMode=(TextView)this.findViewById(R.id.tv_safeguardMode);//安全保障
        httpUtils = new HttpUtils(10000);
        dataRequest();
    }

    /**
     * 数据请求
     */
    public void dataRequest(){
        RequestParams params = FinancingParams.getPlanDetailsCode(planID);
        httpUtils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL,params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                // Toast.makeText(activity,"成功", LENGTH_SHORT).show();
                String result = responseInfo.result;
                JSONObject object = null;
                try {
                    List<FinancingPlanBean> list=new ArrayList<FinancingPlanBean>();
                    object = new JSONObject(result);
                    String strResponse = object.getString("argEncPara");
                    String strDe = DES3Util.decode(strResponse);
                    //Log.i("=======",strDe);
                    JSONObject object1=new JSONObject(strDe);
                    tv_maxFinancing.setText(object1.getString("maxFinancing"));
                    tv_expectedRate.setText(object1.getString("expectedRate"));
                    tv_lockTime.setText(object1.getString("lockTime"));
                    tv_joinCondition.setText(object1.getString("joinCondition"));
                    tv_interestDate.setText(object1.getString("interestDate"));
                    tv_repayType.setText(object1.getString("repayType"));
                    tv_safeguardMode.setText(object1.getString("safeguardMode"));

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                Toast.makeText(InvestActivity.this,"失败", LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_detail:
                Intent intent0=new Intent(this,InvestDetailActivity.class);
                startActivity(intent0);
                break;
            case R.id.btn_join:
                Intent intent1=new Intent(this,InvestJoinActivity.class);
                startActivity(intent1);
                break;
            case R.id.iv_cal:
                ClaculatorDialog dialog=new ClaculatorDialog(this,R.style.dialog);
                dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
                dialog.show();
                break;
            default:
                break;

        }
    }
}
