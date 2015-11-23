package com.wrmoney.administrator.plusadd.bean;

/**
 * Created by Administrator on 2015/11/13.
 */
public class InvestMentBean {
    private String orderId;//计划ID
    private String planName;//投资类型
    private String investDate;//日期
    private String expectedRate;//预期收益率
    private String investAmount;//买入金额
    private String lockTime;//锁定期

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getInvestDate() {
        return investDate;
    }

    public void setInvestDate(String investDate) {
        this.investDate = investDate;
    }

    public String getExpectedRate() {
        return expectedRate;
    }

    public void setExpectedRate(String expectedRate) {
        this.expectedRate = expectedRate;
    }

    public String getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(String investAmount) {
        this.investAmount = investAmount;
    }

    public String getLockTime() {
        return lockTime;
    }

    public void setLockTime(String lockTime) {
        this.lockTime = lockTime;
    }
}
