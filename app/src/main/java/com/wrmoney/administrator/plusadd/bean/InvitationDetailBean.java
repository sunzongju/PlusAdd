package com.wrmoney.administrator.plusadd.bean;

/**
 * Created by Administrator on 2015/12/17.
 */
public class InvitationDetailBean {
    private String userId;//用户id
    private String invitationCode;//邀请码（加入计划时使用的邀请码）
    private String type;//类型（1、注册2、投资）
    private String productType;//产品类型（计划ID）
    private String commissionAmount;//佣金
    private String commissionAmountStr;//佣金字符串类型
    private String comminssionTimeStr;
    private String returnState;

    public String getComminssionTimeStr() {
        return comminssionTimeStr;
    }

    public void setComminssionTimeStr(String comminssionTimeStr) {
        this.comminssionTimeStr = comminssionTimeStr;
    }

    public String getReturnState() {
        return returnState;
    }

    public void setReturnState(String returnState) {
        this.returnState = returnState;
    }

    private String coment;//备注
    private String regTime;//注册日期
    private String mobile;//手机号
    private String invitedUser;//邀请人
    private String ifOpenAcct;//第三方是否已开户：1 是0否
    private String commissionTime;//返佣时间
    private String orderNum;//订单数
    private String count;//返现次数

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(String commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public String getCommissionAmountStr() {
        return commissionAmountStr;
    }

    public void setCommissionAmountStr(String commissionAmountStr) {
        this.commissionAmountStr = commissionAmountStr;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getInvitedUser() {
        return invitedUser;
    }

    public void setInvitedUser(String invitedUser) {
        this.invitedUser = invitedUser;
    }

    public String getIfOpenAcct() {
        return ifOpenAcct;
    }

    public void setIfOpenAcct(String ifOpenAcct) {
        this.ifOpenAcct = ifOpenAcct;
    }

    public String getCommissionTime() {
        return commissionTime;
    }

    public void setCommissionTime(String commissionTime) {
        this.commissionTime = commissionTime;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
