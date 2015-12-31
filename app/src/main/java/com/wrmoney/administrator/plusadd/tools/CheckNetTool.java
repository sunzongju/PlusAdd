package com.wrmoney.administrator.plusadd.tools;

import android.content.Context;

import com.wrmoney.administrator.plusadd.view.DiaLog;

/**
 * Created by Administrator on 2015/12/30.
 */
public class CheckNetTool {
    public static Boolean checkNet(Context context){
        if (!NetworkAvailable.isNetworkAvailable(context)){
            DiaLog.showDialog(context, "网速不给力");
            return false;
        }else{
            return true;
        }
    }
}
