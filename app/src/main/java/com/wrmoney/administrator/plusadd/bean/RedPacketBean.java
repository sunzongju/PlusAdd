package com.wrmoney.administrator.plusadd.bean;

/**
 * 红包bean
 * Created by Administrator on 2015/9/22.
 */
public class RedPacketBean {
    private String lotteryAmount;//红包金额
    private String lotteryStatus;//红包状态
    private String lotteryValidTime;//有效期

    public String getLotteryAmount() {
        return lotteryAmount;
    }

    public void setLotteryAmount(String lotteryAmount) {
        this.lotteryAmount = lotteryAmount;
    }

    public String getLotteryStatus() {
        return lotteryStatus;
    }

    public void setLotteryStatus(String lotteryStatus) {
        this.lotteryStatus = lotteryStatus;
    }

    public String getLotteryValidTime() {
        return lotteryValidTime;
    }

    public void setLotteryValidTime(String lotteryValidTime) {
        this.lotteryValidTime = lotteryValidTime;
    }
}
