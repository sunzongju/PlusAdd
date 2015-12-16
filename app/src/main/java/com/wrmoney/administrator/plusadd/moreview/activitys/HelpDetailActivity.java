package com.wrmoney.administrator.plusadd.moreview.activitys;

import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.moreview.adapters.ExpandableAdapter;
import com.wrmoney.administrator.plusadd.tools.ActionBarSet;

/**
 * Created by Administrator on 2015/11/17.
 */
public class HelpDetailActivity  extends BaseActivity{
    private ExpandableListView ep_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_help_detail);
        ActionBarSet.setActionBar(this);
        TextView tv_banner=(TextView)this.findViewById(R.id.tv_banner);
        tv_banner.setText("帮助中心");
        init();
    }

    public void init(){

        ep_listview = (ExpandableListView)this.findViewById(R.id.ep_list);
//        initDate();
        ep_listview.setGroupIndicator(null);
        ExpandableAdapter adapter=new ExpandableAdapter(this);
        ep_listview.setAdapter(adapter);

    }
}
