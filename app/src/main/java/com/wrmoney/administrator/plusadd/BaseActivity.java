package com.wrmoney.administrator.plusadd;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wrmoney.administrator.plusadd.moreview.activitys.HelpCenterActivity;
import com.wrmoney.administrator.plusadd.tools.ActionBarSet;
import com.wrmoney.administrator.plusadd.tools.ActivityCollectorTool;

/**
 * Created by Administrator on 2015/11/2.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollectorTool.addActivity(this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
        //(ImageView)this.findViewById(R.id.iv_return);
    }
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorTool.removeActivity(this);
    }
//    public void click(View view){
//        switch (view.getId()){
//            case R.id.iv_return:
//               // finish();
//               // onKeyDown()
//                break;
//            default:
//                break;
//
//
//        }
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);

    }
}
