package com.wrmoney.administrator.plusadd.encode;

import com.lidroid.xutils.http.RequestParams;
import com.wrmoney.administrator.plusadd.tools.DES3Util;


/**
 * 验证码
 * Created by Administrator on 2015/9/10.
 */
public class IdentifyParams {
    /**
     * 发送验证码
     * @param mobilec
     * @param type
     * @return
     */
    public static RequestParams getSendIdentifyCode(String mobilec,String type){
        String json="{ iface:'WRMI100005',mobile:'"+mobilec+"',type:'"+type+"'}";
        RequestParams params=new RequestParams();
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
     * 校验验证码
     * @param mobilec
     * @param captchac
     * @return
     */
    public static RequestParams getCheckIdentifyCode(String mobilec,String captchac){
        String json="{ iface:'WRMI100006',mobile:'"+mobilec+"',captcha:'"+captchac+"'}";
        RequestParams params=new RequestParams();
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
