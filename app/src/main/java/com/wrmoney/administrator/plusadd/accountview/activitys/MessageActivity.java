package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.accountview.adapters.MessageAdapter;
import com.wrmoney.administrator.plusadd.bean.InvestMentBean;
import com.wrmoney.administrator.plusadd.bean.MessageBean;
import com.wrmoney.administrator.plusadd.encode.UserCenterParams;
import com.wrmoney.administrator.plusadd.tools.ActionBarSet;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.SingleUserIdTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/16.
 */
public class MessageActivity extends BaseActivity{

    private List<MessageBean> list=new ArrayList<MessageBean>();
    private HttpUtils utils;
    private String userid;
    private MessageAdapter adapter;
    private PullToRefreshListView lv_news;
    private int current=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_message);
        ActionBarSet.setActionBar(this);
        TextView tv_banner=(TextView)this.findViewById(R.id.tv_banner);
        tv_banner.setText("我的消息");
        init();
    }

    public void init(){
        userid = SingleUserIdTool.newInstance().getUserid();
        utils = HttpXutilTool.getUtils();
        lv_news=(PullToRefreshListView)this.findViewById(R.id.lv_news);
        View v= LayoutInflater.from(this).inflate(R.layout.empty_view,null);
        lv_news.setEmptyView(v);
        adapter=new MessageAdapter(this,list);
        lv_news.setAdapter(adapter);
        //Log.i("========onStart","刷新");
        dataRequest(current);
        lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //notify();

                MessageBean bean=list.get(position-1);
                Intent intent=new Intent(MessageActivity.this,MessageDetailActivity.class);
                intent.putExtra("MSGID",bean.getMsgId());
                startActivity(intent);
                current = 1;
                list.clear();
                dataRequest(current);
            }
        });
        lv_news.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                current = 1;
                list.clear();
                dataRequest(current);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                current++;
                dataRequest(current);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    /**
     * �������
     */
    public void dataRequest(int current) {
        //Log.i("userid",userid);
        RequestParams params = UserCenterParams.getMessageListCode(userid,current+"","10");
        utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                JSONObject object = null;
                try {
                    List<MessageBean> list2 = new ArrayList<MessageBean>();
                    object = new JSONObject(result);
                    String strResponse = object.getString("argEncPara");
                    String strDe = DES3Util.decode(strResponse);
                   // Log.i("======消息列表", strDe);
                    JSONObject object1=new JSONObject(strDe);
                    JSONArray array=object1.getJSONArray("msgList");
                    int len=array.length();
                    for(int i=0;i<len;i++){
                        JSONObject object2=array.getJSONObject(i);
                        MessageBean bean=new MessageBean();
                        bean.setMsgId(object2.getString("msgId"));
                        bean.setMsgTitle(object2.getString("msgTitle"));
                        bean.setSendTime(object2.getString("sendTime"));
                        bean.setMsgContent(object2.getString("msgContent"));
                        bean.setIsRead(object2.getString("isRead"));
                        list2.add(bean);
                    }
                  //  Log.i("====长度",list2.size()+"");
                    adapter.addAll(list2);
                    lv_news.onRefreshComplete();
                    // Toast.makeText(InvestMentActivity.this, strDe, Toast.LENGTH_SHORT).show();
                    //JSONObject obj2=new JSONObject(strDe);
                    // String rescode=obj2.getString("rescode");
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Toast.makeText(LoginActivity.this, strDe, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                //Toast.makeText(MessageActivity.this, "失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
