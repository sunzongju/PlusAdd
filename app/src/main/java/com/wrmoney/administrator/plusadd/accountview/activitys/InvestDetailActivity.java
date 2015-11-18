package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.accountview.adapters.CreditorAdapter;
import com.wrmoney.administrator.plusadd.view.CreditorDetailDialog;
import com.wrmoney.administrator.plusadd.view.QuitPlanDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/13.
 */
public class InvestDetailActivity extends BaseActivity{
    private ListView lv_invest;
    private List<String> list=new ArrayList<String>();
    private QuitPlanDialog dialog;

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

        lv_invest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CreditorDetailDialog dialog = new CreditorDetailDialog(InvestDetailActivity.this, R.style.dialog);
                //dialog=new AlterPassFinishDialog(activity,R.style.dialog);

                dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
                dialog.show();

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
