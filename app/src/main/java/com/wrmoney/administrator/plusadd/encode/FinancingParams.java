package com.wrmoney.administrator.plusadd.encode;

import com.lidroid.xutils.http.RequestParams;
import com.wrmoney.administrator.plusadd.tools.DES3Util;

/**
 * 投资理财
 * Created by Administrator on 2015/9/10.
 */
public class FinancingParams {
    /**
     * 理财计划--计划列表
     *
     * @return
     */
    public static RequestParams getPlanListCode(String pageIndexc, String pageSizec){
        String json="{ iface:'WRMI100022',pageIndex:'" + pageIndexc + "',pageSize:'" + pageSizec + "'}";
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
     * 首页
     *
     * @return
     */
    public static RequestParams homeListCode(){
        String json="{ iface:'WRMI100034'}";
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
     * 理财计划--投资详情
     *
     * @param planIdc
     * @return
     */
    public static RequestParams getPlanDetailsCode(String planIdc) {
        String json = "{ iface:'WRMI100023',planId:'" + planIdc + "'}";
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
     * 理财计划--购买产品
     *
     * @param userIdc
     * @param planIdc
     * @return
     */
    public static RequestParams getBuyGoodsCode(String userIdc, String planIdc) {
        String json = "{ inface:'WRMI1000024',userId:" + userIdc + ",planId:" + planIdc + "}";
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
     * 理财计划--确认购买
     *
     * @param userIdc
     * @param planIdc
     * @return
     */
    public static RequestParams getSureBuyCode(String userIdc, String planIdc) {
        String json = "{ inface:'WRMI1000025',userId:" + userIdc + ",planId:" + planIdc + "}";
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
     * 理财计划--可用红包
     *
     * @param userIdc
     * @param planIdc
     * @return
     */
    public static RequestParams getAvailableRedCode(String userIdc, String planIdc) {
        String json = "{ inface:'WRMI1000026',userId:" + userIdc + ",planId:" + planIdc + "}";
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
     * 理财计划是否开通第三方
     *
     * @param userIdc
     * @return
     */
    public static RequestParams getOpenThridPartyCode(String userIdc) {
        String json = "{ inface:'WRMI1000027',userId:" + userIdc + "}";
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
