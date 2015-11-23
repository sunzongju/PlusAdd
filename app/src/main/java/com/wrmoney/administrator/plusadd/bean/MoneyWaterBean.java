package com.wrmoney.administrator.plusadd.bean;

/**
 * Created by Administrator on 2015/11/11.
 */
public class MoneyWaterBean {
    private String transDate;//交易日期
    private String transComent;//交易描述
    private String transAmount;//交易金额

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public String getTransComent() {
        return transComent;
    }

    public void setTransComent(String transComent) {
        this.transComent = transComent;
    }

    public String getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }
}
