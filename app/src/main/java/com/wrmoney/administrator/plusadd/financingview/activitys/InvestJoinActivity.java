package com.wrmoney.administrator.plusadd.financingview.activitys;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.MainActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.accountview.adapters.VoucherAdapter;
import com.wrmoney.administrator.plusadd.view.VoucherDialog;

import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/3.
 */
public class InvestJoinActivity extends BaseActivity {
    private TextView tv_redpacket;
    private TextView tv_nate;
    private ListView lv_vou;
    private List<String> list = new ArrayList<String>();
    private String[] str={};
    private Button btn_sure;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financing_invest_join);
       tv_redpacket= (TextView)this.findViewById(R.id.tv_redpacket);
        tv_nate=(TextView)this.findViewById(R.id.tv_nate);
        tv_redpacket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showPopupWindow(v);
                VoucherDialog dialog=new VoucherDialog(InvestJoinActivity.this,R.style.dialog);
//                lv_vou=(ListView)dialog.findViewById(R.id.lv_vou);
//
//                for (int i = 0; i <4; i++) {
//                    list.add("抵用券名称");
//                }
               // lv_vou.setAdapter(adapter);
                dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
                dialog.show();

            }
        });

    }


    private void showPopupWindow(View view) {
        Toast.makeText(this,"红包",Toast.LENGTH_SHORT).show();
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.financing_join_red_packet, null);

         PopupWindow popupWindow = new PopupWindow(contentView,
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);

        popupWindow.setOutsideTouchable(true); // 设置是否允许在外点击使其消失，到底有用没？

//        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                return false;
//                // 这里如果返回true的话，touch事件将被拦截
//                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
//            }
//        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.wallpaper_2));
//
//        // 设置好参数之后再show
        popupWindow.showAsDropDown(tv_nate,100,100);//设置显示位置
        popupWindow.update();
        popupWindow.showAsDropDown(view);
//
//    }

}
}
