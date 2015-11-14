package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.R;

/**
 * 邀请机制���
 * Created by Administrator on 2015/11/2.
 */
public class InvitationActivity extends BaseActivity {
    private TextView tv_invitation;
    private TextView tv_indetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_invitation);
        init();
    }

    public void init(){
        tv_invitation=(TextView)this.findViewById(R.id.tv_invitation);//邀请
        tv_indetail=(TextView)this.findViewById(R.id.tv_indetail);//邀请详情
    }

    public void click(View view){
        switch (view.getId()){
            case R.id.tv_invitation:
                Intent intent0=new Intent(this,InvitationFriendActivity.class);
                startActivity(intent0);
                break;
            case R.id.tv_indetail:
                Intent intent=new Intent(this,InvitationDetailActivity.class);
                startActivity(intent);
                break;
            default:
                break;



        }

    }
}
