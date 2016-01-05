package com.wrmoney.administrator.plusadd.tools;

import android.content.Context;

import com.wrmoney.administrator.plusadd.view.DiaLog;

/**
 * Created by Administrator on 2015/12/30.
 */
public class CheckNetTool {
    public static Boolean checkNet(Context context){
        if (!NetworkAvailable.isNetworkAvailable(context)){
            DiaLog.showDialog(context, "您当前网络不好，请检查网络");
            return false;
        }else{
            return true;
        }
    }
}
