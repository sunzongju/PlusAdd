package com.wrmoney.administrator.plusadd.wecomeview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.CommnActivity;
import com.wrmoney.administrator.plusadd.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/2.
 */
public class WelcomeActivity extends BaseActivity {
    private ViewPager vp_wel;
    private List<View> list = new ArrayList<View>();
    private int view[] = {R.drawable.u30, R.drawable.u30, R.drawable.u46};
    private RadioGroup rg_group;
    private Boolean type = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
    }

    private void init() {
        vp_wel = (ViewPager) this.findViewById(R.id.pager_wel);
        list.add(LayoutInflater.from(this).inflate(R.layout.welcom_item_01, null));
        list.add(LayoutInflater.from(this).inflate(R.layout.welcom_item_02, null));
        list.add(LayoutInflater.from(this).inflate(R.layout.welcom_item_03, null));
        list.add(LayoutInflater.from(this).inflate(R.layout.welcom_item_04,null));

        WelcomeAdapter adapter = new WelcomeAdapter(list);
        rg_group = (RadioGroup) this.findViewById(R.id.rg_group);
        rg_group.setVisibility(View.INVISIBLE);
        SharedPreferences pre1 = getSharedPreferences("user", Context.MODE_PRIVATE);
        type = pre1.getBoolean("TYPE", true);
        if (type) {
            vp_wel.setAdapter(adapter);
            SharedPreferences pre2 = getSharedPreferences("user", Context.MODE_PRIVATE);
            SharedPreferences.Editor edt = pre2.edit();
            edt.putBoolean("TYPE", false);
            edt.commit();
        } else {
//            Intent intent = new Intent(this, SecondActivity.class);
            Intent intent = new Intent(this, CommnActivity.class);
            startActivity(intent);
            finish();
        }


        vp_wel.setAdapter(adapter);
        vp_wel.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                rg_group.check(i + 1);
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    /**
     * ����
     *
     * @param view
     */
    public void click(View view) {
        Intent intent = new Intent(WelcomeActivity.this, CommnActivity.class);
        startActivity(intent);
        finish();
    }
}
