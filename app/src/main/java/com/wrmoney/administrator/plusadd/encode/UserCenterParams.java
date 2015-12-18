package com.wrmoney.administrator.plusadd.encode;

import com.lidroid.xutils.http.RequestParams;
import com.wrmoney.administrator.plusadd.tools.DES3Util;

/**
 * 用户中心
 * Created by Administrator on 2015/9/10.
 */
public class UserCenterParams {
    /**
     * 用户中心
     *
     * @param userIdc
     * @return
     */
    public static RequestParams getUpdateCode(String userIdc) {
        String json = "{ iface:'WRMI100010',userId:'" + userIdc + "'}";
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
     * 账号总览
     *
     * @param userIdc
     * @return
     */
    public static RequestParams getShowCode(String userIdc) {
        String json = "{ inface:'WRMI100011',userId:" + userIdc + "}";
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
     * 消息列表
     *
     * @param userIdc
     * @param currentc
     * @param pageSizec
     * @return
     */
    public static RequestParams getMessageListCode(String userIdc, String currentc, String pageSizec) {
        String json = "{ iface:'WRMI100012',userId:'" + userIdc + "',current:'" + currentc + "',pageSize:'" + pageSizec + "'}";
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
     * 消息详情
     *
     * @param userIdc
     * @param msgIdc
     * @return
     */
    public static RequestParams getMessageDetailCode(String userIdc, String msgIdc) {
        String json = "{ iface:'WRMI100013',userId:'" + userIdc + "',msgId:'" + msgIdc + "'}";
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
     * 投资管理--列表
     *
     * @param userIdc
     * @param typec
     * @param currentc
     * @param pageSizec
     * @return
     */
    public static RequestParams getInvestManagerCode(String userIdc, String typec, String currentc, String pageSizec) {
        String json = "{ iface:'WRMI100014',userId:'" + userIdc + "',type:'" + typec + "',current:'" + currentc + "',pageSize:'" + pageSizec + "'}";
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
     * 投资管理--订单债券列表
     *
     * @param userIdc
     * @param orderIdc
     * @param currentc
     * @param pageSizec
     * @return
     */
    public static RequestParams getOrderBondsManagerCode(String userIdc, String orderIdc, String currentc, String pageSizec) {
        String json = "{ iface:'WRMI100015',userId:'" + userIdc + "',orderId:'" + orderIdc + "',current:'" + currentc + "',pageSize:'" + pageSizec + "'}";
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
     * 投资管理--债券明细
     *
     * @param userIdc
     * @param creditIdc
     * @return
     */
    public static RequestParams getInvestManagerDetailCode(String userIdc, String creditIdc) {
        String json = "{ iface:'WRMI100016',userId:'" + userIdc + "',creditId:'" + creditIdc + "'}";
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
     * 投资管理--退出计划
     *
     * @param userIdc
     * @param orderIdc
     * @return
     */
    public static RequestParams getMessageQuitCode(String userIdc, String orderIdc) {
        String json = "{ iface:'WRMI100017',userId:'" + userIdc + "',orderId:'" + orderIdc + "'}";
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
     * 资金流水
     *
     * @param userIdc
     * @param typec
     * @param currentc
     * @param pageSizec
     * @return
     */
    public static RequestParams getFundRunningCode(String userIdc, String typec, String currentc, String pageSizec) {
        String json = "{ iface:'WRMI100018',userId:'" + userIdc + "',type:'" + typec + "',current:'" + currentc + "',pageSize:'" + pageSizec + "'}";
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
     * 活动专区--活动列表
     *
     * @param currentc
     * @param pageSizec
     * @return
     */
    public static RequestParams getActivityListCode(String currentc, String pageSizec) {
        String json = "{ iface:'WRMI100019',current:'" + currentc + "',pageSize:'" + pageSizec + "'}";
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
     * 活动专区--活动详情
     *
     * @param activityIdc
     * @return
     */
    public static RequestParams getActivityDetailsCode(String activityIdc) {
        String json = "{ inface:'WRMI100020',activityId:" + activityIdc + "}";
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
     * 红包--红包列表
     *
     * @param userIdc
     * @param typec
     * @param currentc
     * @param pageSizec
     * @return
     */

    public static RequestParams getBonusCode(String userIdc, String typec, String currentc, String pageSizec) {
        String json = "{ iface:'WRMI100021',userId:'" + userIdc + "',type:'" + typec + "',current:'" + currentc + "',pageSize:'" + pageSizec + "'}";
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
     * WRMI100037邀请好友
     * @param userId
     * @return
     */
    public static RequestParams getInviteCode(String userId) {
        String json = "{ iface:'WRMI100037',userId:'" + userId + "'}";
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
     * WRMI100038邀请好友详情
     * @return
     */
  public static RequestParams getInviteDetailCode(String invitationCode, String typec, String currentc, String pageSizec){
      String json = "{ iface:'WRMI100038',invitationCode:'" + invitationCode + "',type:'" + typec + "',current:'" + currentc + "',pageSize:'" + pageSizec + "'}";
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
