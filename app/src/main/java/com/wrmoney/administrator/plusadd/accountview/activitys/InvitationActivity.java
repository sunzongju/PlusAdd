package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.actionsheet.ActionSheet;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.bean.ActivityListBean;
import com.wrmoney.administrator.plusadd.bean.InvitationBean;
import com.wrmoney.administrator.plusadd.encode.UserCenterParams;
import com.wrmoney.administrator.plusadd.tools.ActionBarSet;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.SingleUserIdTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;
import com.wrmoney.administrator.plusadd.view.InviteFriendsDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 邀请机制���
 * Created by Administrator on 2015/11/2.
 */
public class    InvitationActivity extends BaseActivity {
    private TextView tv_invitation;
    private TextView tv_indetail;
    private String userid;
    private HttpUtils utils;
    private TextView tv_inviteCount;
    private TextView tv_backedCommsAmount;
    private TextView tv_unbackCommsAmount;
    private EditText et_invitationCode;
    private InvitationBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_invitation);
        ActionBarSet.setActionBar(this);
        TextView tv_banner=(TextView)this.findViewById(R.id.tv_banner);
        tv_banner.setText("邀请好友");
        init();
    }

    public void init(){
        tv_invitation=(TextView)this.findViewById(R.id.tv_invitation);//邀请
        tv_indetail=(TextView)this.findViewById(R.id.tv_indetail);//邀请详情

        tv_inviteCount=(TextView)this.findViewById(R.id.tv_inviteCount);//邀请人数
        tv_backedCommsAmount=(TextView)this.findViewById(R.id.tv_backedCommsAmount);//已返金额
        tv_unbackCommsAmount=(TextView)this.findViewById(R.id.tv_unbackCommsAmount);//待返金额；
        et_invitationCode=(EditText)this.findViewById(R.id.et_invitationCode);//我的邀请码


        userid = SingleUserIdTool.newInstance().getUserid();
        utils = HttpXutilTool.getUtils();
        dataRequest();

    }

    /**
     * 数据请求
     */
    public void dataRequest(){
        RequestParams params = UserCenterParams.getInviteCode(userid);
        utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                JSONObject object = null;
                try {
                    List<ActivityListBean> list2 = new ArrayList<ActivityListBean>();
                    object = new JSONObject(result);
                    String strResponse = object.getString("argEncPara");
                    String strDe = DES3Util.decode(strResponse);
                    Log.i("========邀请好友1", strDe);
//                    Toast.makeText(ActivityCenterActivity.this, strDe, Toast.LENGTH_SHORT).show();
                    JSONObject object1 = new JSONObject(strDe);
                    String str = object1.getString("intiteCodeRecord");
                    JSONObject object2 = new JSONObject(str);
                    if ("0000".equals(object1.getString("rescode"))) {
                        bean = new InvitationBean();
                        bean.setInviteCount(object2.getString("inviteCount"));
                        bean.setBackedCommsAmount(object2.getString("backedCommsAmount"));
                        bean.setUnbackCommsAmount(object2.getString("unbackCommsAmount"));
                        bean.setInvitationCode(object2.getString("invitationCode"));
                        bean.setBindInviteCode(object2.getString("bindInviteCode"));
                        // Log.i("========邀请好友", "人数" + bean.getInviteCount() + "已返金额" + bean.getBackedCommsAmount());
                        tv_inviteCount.setText(bean.getInviteCount());
                        tv_backedCommsAmount.setText(bean.getBackedCommsAmount());
                        tv_unbackCommsAmount.setText(bean.getUnbackCommsAmount());
                        et_invitationCode.setText(bean.getInvitationCode());
                    } else {
                        // Toast.makeText(InvitationActivity.this,"请求失败",Toast.LENGTH_SHORT).show();
                    }
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
            }
        });
    }


    public void click(View view){
        switch (view.getId()){
            case R.id.tv_invitation:
                if(bean!=null){
                    InviteFriendsDialog dialog=new InviteFriendsDialog(this,R.style.dialog);
                    dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
                    dialog.show();
//                    Intent intent0=new Intent(this,InvitationFriendActivity.class);
//                    startActivity(intent0);
                    //this.setTheme(R.style.ActionSheetStyleiOS7);
                    //showActionSheet();
                }
                break;
            case R.id.tv_indetail:
                if(bean!=null){
                    Intent intent=new Intent(this,InvitationDetailActivity.class);
                    startActivity(intent);
                }
                break;
            default:
                break;

        }

    }

}
