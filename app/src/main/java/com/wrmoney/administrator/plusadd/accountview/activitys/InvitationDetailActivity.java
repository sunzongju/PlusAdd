package com.wrmoney.administrator.plusadd.accountview.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.accountview.adapters.InvitationDetailAdapter;
import com.wrmoney.administrator.plusadd.bean.InvitationBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/14.
 */
public class InvitationDetailActivity extends BaseActivity {

    private RadioGroup rg_invitation;
    private ListView lv_invitation;
    private List<InvitationBean> list=new ArrayList<InvitationBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_detail);
        init();
    }

    public void init(){
       rg_invitation= (RadioGroup)this.findViewById(R.id.rg_invitation);
       lv_invitation= (ListView)this.findViewById(R.id.lv_invitation);
        for(int i=0;i<3;i++){
            InvitationBean bean=new InvitationBean();
            bean.setName("");
            list.add(bean);
        }
        RadioButton radioBtn = (RadioButton) rg_invitation.getChildAt(0);
        radioBtn.setBackgroundColor(0xffff6600);
        radioBtn.setTextColor(Color.WHITE);
        InvitationDetailAdapter adapter=new InvitationDetailAdapter(this,list);
        lv_invitation.setAdapter(adapter);

        rg_invitation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //选中的RadioButton播放动画
                ScaleAnimation sAnim = new ScaleAnimation(1, 1.1f, 1, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                sAnim.setDuration(2000);
                sAnim.setFillAfter(true);
                //遍历所有的RadioButton
//                for (int i = 0; i < group.getChildCount(); i++) {
//                    RadioButton radioBtn = (RadioButton) group.getChildAt(i);
//                    if (radioBtn.isChecked()) {
//                        //radioBtn.startAnimation(sAnim);
//                        //radioBtn.setBackgroundColor(0xff660000);
//                        radioBtn.setBackgroundColor(0xffff6600);
//                        radioBtn.setTextColor(Color.WHITE);
//                    } else {
//                        //radioBtn.clearAnimation();
//                        //radioBtn.setBackground(border2);
//                        //radioBtn.setBackground(border2);
//                        //radioBtn.setBackgroundResource(0xffff6600);
//                        radioBtn.setBackgroundColor(0xffffffff);
//                        radioBtn.setTextColor(Color.GRAY);
//                    }
//                }
//                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) indicator
//                        .getLayoutParams();
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                switch (checkedId) {
                    case R.id.btn_all://全部
                        break;
                    case R.id.btn_reg://邀请注册
                        break;
                    case R.id.btn_invest://注册投资
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
