package com.wrmoney.administrator.plusadd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

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
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorTool.removeActivity(this);
    }
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_return:
                finish();
                break;
            default:
                break;


        }
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);



    }
}
