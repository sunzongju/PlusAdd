package com.wrmoney.administrator.plusadd.encode;

import com.lidroid.xutils.http.RequestParams;
import com.wrmoney.administrator.plusadd.tools.DES3Util;

/**
 * 登录和注册
 * Created by Administrator on 2015/9/10.
 */
public class LoginParams {
    /**
     * 输入手机号
     *
     * @param number
     * @return
     */
    public static RequestParams getPhoneCode(String number) {
        String json = "{ iface:'WRMI100001',mobile:'" + number + "'}";
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
     * 注册
     *
     * @return
     */
    public static RequestParams getRegisCode(String mobilec, String passwordc, String captchac, String invitCodec) {
        String json = "{ iface:'WRMI100002',mobile:'" + mobilec + "',password:'" + passwordc + "',captcha:'" + captchac + "',invitCode:'" + invitCodec + "'}";
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
     * 登录
     *
     * @param mobilec
     * @param passwordc
     * @return
     */
    public static RequestParams getLoginCode(String mobilec, String passwordc) {
        String json = "{ iface:'WRMI100003',mobile:'" + mobilec + "',password:'" + passwordc + "'}";
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
     * 找回密码
     *
     * @param mobilec
     * @param passwordc
     * @return
     */
    public static RequestParams getFindCode(String mobilec, String passwordc) {
        String json = "{ iface:'WRMI100004',mobile:'" + mobilec + "',password:'" + passwordc + "'}";
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
     * 验证码
     *
     * @param mobilec
     * @param type
     * @return
     */
    public static RequestParams getIdentifyCode(String mobilec, String type) {
        String json = "{ inface:'WRMI100005',mobile:" + mobilec + ",type:" + type + "}";
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
