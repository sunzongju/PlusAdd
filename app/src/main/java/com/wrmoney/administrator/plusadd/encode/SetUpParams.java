package com.wrmoney.administrator.plusadd.encode;

import android.util.Log;

import com.lidroid.xutils.http.RequestParams;
import com.wrmoney.administrator.plusadd.tools.DES3Util;

/**
 * 设置
 * Created by Administrator on 2015/9/10.
 */
public class SetUpParams {
    /**
     * 我的设置
     *
     * @param useridc
     * @return
     */
    public static RequestParams getMysetCode(String useridc) {
        String json = "{ iface:'WRMI100007',userId:'" + useridc + "'}";
        RequestParams params = new RequestParams();
        String str;
        try {
            str = DES3Util.encode(json);
            params.addQueryStringParameter("argEncPara", str);
            return params;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改登录密码
     *
     * @param userIdc
     * @param oldPwdc
     * @param password
     * @return
     */
    public static RequestParams getLoginCode(String userIdc, String oldPwdc, String password) {
        String json = "{ iface:'WRMI100008',userId:'" + userIdc + "',oldPwd:'" + oldPwdc + "',password:'" + password + "'}";
        Log.i("===============", json);
        RequestParams params = new RequestParams();
        String str;
        try {
            str = DES3Util.encode(json);
            params.addQueryStringParameter("argEncPara", str);
            return params;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检查更新
     *
     * @param appVerc
     * @param osc
     * @return
     */
    public static RequestParams getUpdateCode(String appVerc, String osc) {
        String json = "{ inface:'WRMI100009',appVer:" + appVerc + ",os:" + osc + "}";
        RequestParams params = new RequestParams();
        String str;
        try {
            str = DES3Util.encode(json);
            params.addQueryStringParameter("argEncPara", str);
            return params;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
