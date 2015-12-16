package com.wrmoney.administrator.plusadd.moreview.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.moreview.adapters.HelpCenterAdapter;
import com.wrmoney.administrator.plusadd.tools.ActionBarSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/16.
 */
public class HelpCenterActivity extends BaseActivity {
    private ListView lv_help;
    private List<String> list=new ArrayList<String>();
    private String[] str={"Plus0是一家怎样的网站？","Plus0管理团队实力如何？","Plus0如何保证投资者本金和收益安全？",
    "Plus0平台提供居间撮合服务的合法性？","融资方出现违约、坏账怎么办？"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_help_center);
        ActionBarSet.setActionBar(this);
        TextView tv_banner=(TextView)this.findViewById(R.id.tv_banner);
        tv_banner.setText("帮助中心");
        init();
    }

    public void init(){
        lv_help=(ListView)this.findViewById(R.id.lv_help);
        int len=str.length;
        for(int i=0;i<len;i++){
            list.add(str[i]);
        }
        HelpCenterAdapter adapter=new HelpCenterAdapter(list,this);
       lv_help.setAdapter(adapter);

    }

    public void click(View view){
        switch (view.getId()){
            case R.id.tv_01:
                Intent intent=new Intent(this,HelpDetailActivity.class);
                startActivity(intent);
                break;
            default:
                break;

        }

    }

}
