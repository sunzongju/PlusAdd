package com.wrmoney.administrator.plusadd.encode;

import com.lidroid.xutils.http.RequestParams;
import com.wrmoney.administrator.plusadd.tools.DES3Util;

/**
 * 充值提现
 * Created by Administrator on 2015/9/10.
 */
public class RechargeParams {
    /**
     * 开通三方--开户行地区（省）
     * @return
     */
    public static RequestParams getOpenAreaCode(){
        String json="{ inface:'WRMI1000028'}";
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
     * 开通三方--开户行地区(城市)
     * @param provCdc
     * @return
     */
    public static RequestParams getOpenCityCode(String provCdc){
        String json="{ inface:'WRMI1000029',provCd:"+provCdc+"}";
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
     * 开通三方--开户行
     * @return
     */
    public static RequestParams getOpenBankCode(){
        String json="{ inface:'WRMI1000030'}";
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
     * 开通三方
     * @param userIdc
     * @param realNamec
     * @param idCardc
     * @param loginPwdc
     * @param drawalPwdc
     * @param cityCdc
     * @param bankCdc
     * @param bankCardc
     * @return
     */
    public static RequestParams getOpenThirdCode(String userIdc,String realNamec,String idCardc,String loginPwdc,String drawalPwdc,String cityCdc,String bankCdc,String bankCardc){
        String json="{ inface:'WRMI100031',userId:"+userIdc+",realName:"+realNamec+",idCard:"+idCardc+",loginPwd:"+loginPwdc+"drawalPwd:"+drawalPwdc+"cityCd:"+cityCdc+"bankCd:"+bankCdc+"bankCard:"+bankCardc+"}";
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
     * 充值
     * @param userIdc
     * @param rechargeAmountc
     * @return
     */
    public static RequestParams getRechargeCode(String userIdc,String rechargeAmountc){
        String json="{ iface:'WRMI1000032',userId:'"+userIdc+"',rechargeAmount:'"+rechargeAmountc+"'}";
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
     * 提现
     * @param userIdc
     * @param drawalAmountc
     * @param captchac
     * @return
     */
    public static RequestParams getWithdrawCashCode(String userIdc,String drawalAmountc,String captchac){
        String json="{ iface:'WRMI1000033',userId:'"+userIdc+"',drawalAmount:'"+drawalAmountc+"',captcha:'"+captchac+"'}";
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
