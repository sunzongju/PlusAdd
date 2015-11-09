package com.wrmoney.administrator.plusadd.tools;

import com.lidroid.xutils.HttpUtils;

/**
 * Created by Administrator on 2015/11/2.
 */
public class HttpXutilTool {

    private static HttpUtils httpUtils;

    public static void init() {
        httpUtils = new HttpUtils(10000);
    }

    public static HttpUtils getUtils() {

        return httpUtils;
    }
}
