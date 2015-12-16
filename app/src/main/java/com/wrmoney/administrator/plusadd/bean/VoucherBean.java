package com.wrmoney.administrator.plusadd.bean;

/**
 * Created by Administrator on 2015/11/16.
 */
public class VoucherBean  {
    private String lotteryId;//奖券iD
    private String lotteryAmount;//奖券金额
    private String lotteryStatus;//使用状态
    private String lotteryValidTime;//有效期
    private String lotteryComent;//奖券描述
    private String lotteryTitle;//奖券标题
    private String tagCheck;

    public String getTagCheck() {
        return tagCheck;
    }

    public void setTagCheck(String tagCheck) {
        this.tagCheck = tagCheck;
    }

    public String getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(String lotteryId) {
        this.lotteryId = lotteryId;
    }

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

    public String getLotteryComent() {
        return lotteryComent;
    }

    public void setLotteryComent(String lotteryComent) {
        this.lotteryComent = lotteryComent;
    }

    public String getLotteryTitle() {
        return lotteryTitle;
    }

    public void setLotteryTitle(String lotteryTitle) {
        this.lotteryTitle = lotteryTitle;
    }
}
