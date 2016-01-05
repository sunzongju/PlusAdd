package com.wrmoney.administrator.plusadd.tools;

import com.lidroid.xutils.HttpUtils;

/**
 * Created by Administrator on 2015/12/31.
 */
public class CheckFlag {

    private static Boolean cFlag=false;
    public static Boolean getcFlag() {
        return cFlag;
    }
    public static void setcFlag(Boolean cFlag) {
        CheckFlag.cFlag = cFlag;
    }
}
