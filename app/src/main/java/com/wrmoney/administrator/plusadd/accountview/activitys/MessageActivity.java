package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.os.Bundle;
import android.widget.ListView;

import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.accountview.adapters.MessageAdapter;
import com.wrmoney.administrator.plusadd.bean.MessageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/16.
 */
public class MessageActivity extends BaseActivity{
    private ListView lv_news;
    private List<MessageBean> list=new ArrayList<MessageBean>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_message);
        init();
    }

    public void init(){
        lv_news=(ListView)this.findViewById(R.id.lv_news);
        for(int i=0;i<10;i++){
            MessageBean bean=new MessageBean();
            list.add(bean);
        }
        MessageAdapter adapter=new MessageAdapter(this,list);
        lv_news.setAdapter(adapter);
    }

}
