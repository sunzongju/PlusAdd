package com.wrmoney.administrator.plusadd.financingview.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.R;

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
    List<Map<String, String>> listitem = new ArrayList<Map<String, String>>();
    private String[] title=new String[]{
            "名称","介绍","投资内容","加入条件","计划周期","还款方式","退出","退出到帐时间","费用"
    };
    private String[] content=new String[]{
      "财富B计划001期","财富B计划001期，产品是plus0推出的100%本息担保的优势借款项目","机构担保标，实际认证标",
            "100天起，100天整数倍递增","180天","到期还本付息",
            "锁定期内不得退出，锁定期到期后可选择解除锁定，如无申请本息自动复投","T+3",
            "加入费率:0.0% 退出费率0.0%"

    };
    private ListView lv_product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financing_invest_detail);
        lv_product=(ListView)this.findViewById(R.id.lv_product);
        // 将上述资源转化为list集合
        int len=title.length;
        for (int i = 0; i < len; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("title", title[i]);
            map.put("content",content[i] );
            listitem.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                listitem,
                R.layout.financing_product_item,
                new String[] { "title", "content" },
                new int[] {R.id.tv_title, R.id.tv_content });
       lv_product.setAdapter(adapter);

//        btn_detail=(Button)this.findViewById(R.id.btn_detail);
//        btn_join=(Button)this.findViewById(R.id.btn_join);
//        btn_detail.setOnClickListener(this);
//        btn_join.setOnClickListener(this);
    }


}
