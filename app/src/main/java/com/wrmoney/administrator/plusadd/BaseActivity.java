package com.wrmoney.administrator.plusadd;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wrmoney.administrator.plusadd.tools.ActivityCollectorTool;

/**
 * Created by Administrator on 2015/11/2.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollectorTool.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorTool.removeActivity(this);
    }
}
