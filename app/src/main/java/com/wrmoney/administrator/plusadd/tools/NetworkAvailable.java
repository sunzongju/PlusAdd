package com.wrmoney.administrator.plusadd.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Administrator on 2015/12/17.
 */
public class NetworkAvailable {

    /*
     * 判断是否有网络
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        } else {
            // 打印所有的网络状态
            NetworkInfo[] infos = cm.getAllNetworkInfo();
            if (infos != null) {
                for (int i = 0; i < infos.length; i++) {
                    // Log.d(TAG, "isNetworkAvailable - info: " +
                    // infos[i].toString());
                    if (infos[i].getState() == NetworkInfo.State.CONNECTED) {
                        //Log.d("========网络1", "isNetworkAvailable -  I " + i);
                    }
                }
            }

            // 如果仅仅是用来判断网络连接　　　　　　
            // 则可以使用 cm.getActiveNetworkInfo().isAvailable();
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null) {
                //Log.d("========网络2",
                        //"isNetworkAvailable - 是否有网络： "
                             //   + networkInfo.isAvailable());
            } else {
               // Log.d("========网络3", "isNetworkAvailable - 完成没有网络！");
                return false;
            }

            // 1、判断是否有3G网络
            if (networkInfo != null
                    && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
              //  Log.d("========网络4", "isNetworkAvailable - 有3G网络");
                return true;
            } else {
              //  Log.d("========网络5", "isNetworkAvailable - 没有3G网络");
            }

            // 2、判断是否有wifi连接
            if (networkInfo != null
                    && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
              //  Log.d("========网络6", "isNetworkAvailable - 有wifi连接");
                return true;
            } else {
              //  Log.d("========网络7", "isNetworkAvailable - 没有wifi连接");
            }
        }
        return false;
    }
}
