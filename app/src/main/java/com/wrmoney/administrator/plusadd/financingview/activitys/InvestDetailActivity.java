package com.wrmoney.administrator.plusadd.financingview.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.bean.FinancingDetailBean;
import com.wrmoney.administrator.plusadd.bean.PlanBean;
import com.wrmoney.administrator.plusadd.financingview.adapters.PlanDeatailAdapter;
import com.wrmoney.administrator.plusadd.tools.ActionBarSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/3.
 */
public class InvestDetailActivity extends BaseActivity {
    private Button btn_detail;
    private Button btn_join;
    List<PlanBean> listitem = new ArrayList<PlanBean>();
    private String[] title=new String[]{
            "名称","介绍","投资内容","加入条件","计划周期","还款方式","退出","退出到帐时间","费用"
    };
//    private String[] content=new String[]{
//      "财富B计划001期","财富B计划001期，产品是plus0推出的100%本息担保的优势借款项目","机构担保标，实际认证标",
//            "100天起，100天整数倍递增","180天","到期还本付息",
//            "锁定期内不得退出，锁定期到期后可选择解除锁定，如无申请本息自动复投","T+3",
//            "加入费率:0.0% 退出费率0.0%"
//
//    };
    private ListView lv_product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financing_invest_detail);
        ActionBarSet.setActionBar(this);
        TextView tv_banner=(TextView)this.findViewById(R.id.tv_banner);
        tv_banner.setText("产品详情");
        ImageView iv_answer=(ImageView)this.findViewById(R.id.iv_answer);
        iv_answer.setVisibility(View.INVISIBLE);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        FinancingDetailBean bean = bundle.getParcelable("BEAN");
        lv_product=(ListView)this.findViewById(R.id.lv_product);
         String[] content=new String[]{bean.getPlanName(),bean.getIntroduce(),bean.getInvestContent(),bean.getJoinCondition(),bean.getLockTime(),
         bean.getRepayType(),bean.getExitMemo(),bean.getExitArriveDate(),bean.getCost()};
        int len=title.length;
        for (int i = 0; i < len; i++) {
            PlanBean planBean=new PlanBean();
            planBean.setTitle(title[i]);
            planBean.setContent(content[i]);
            listitem.add(planBean);
        }
        PlanDeatailAdapter adapter=new PlanDeatailAdapter(listitem,this);
       lv_product.setAdapter(adapter);
    }


}
