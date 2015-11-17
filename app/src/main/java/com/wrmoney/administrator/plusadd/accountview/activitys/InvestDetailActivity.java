package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.os.Bundle;
import android.widget.ListView;

import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.accountview.adapters.CreditorAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/13.
 */
public class InvestDetailActivity extends BaseActivity{
    private ListView lv_invest;
    private List<String> list=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_invest);
        init();
    }

    public void init(){
       lv_invest=(ListView)this.findViewById(R.id.lv_creditor);
        for(int i=0;i<5;i++){
        list.add("");
        }
        CreditorAdapter adapter=new CreditorAdapter(list,this);
       lv_invest.setAdapter(adapter);

    }
}
