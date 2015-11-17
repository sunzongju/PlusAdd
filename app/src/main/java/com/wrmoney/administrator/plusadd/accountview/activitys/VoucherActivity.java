package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.accountview.adapters.VoucherAdapter;
import com.wrmoney.administrator.plusadd.bean.VoucherBean;

import java.util.ArrayList;
import java.util.List;

/**
 *抵用券界面����
 * Created by Administrator on 2015/11/2.
 */
public class VoucherActivity extends BaseActivity {
    private ListView lv_red;
    private List<VoucherBean> list=new ArrayList<VoucherBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_voucher);

        init();
    }

    public void init(){
        lv_red=(ListView)this.findViewById(R.id.lv_red);
        for(int i=0;i<2;i++){
            VoucherBean bean=new VoucherBean();
            list.add(bean);
        }
        VoucherAdapter adapter=new VoucherAdapter(list,this);
       lv_red.setAdapter(adapter);



    }
}
