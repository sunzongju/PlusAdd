package com.wrmoney.administrator.plusadd.bean;

/**
 * Created by Administrator on 2015/11/23.
 */
public class CreditorListBean {
    private String borrowerName;
    private String creditId;//债权ID
    private String offLineAgreementCd;//
    private String borrowerIdCard;
    private String creditCashValue;
    private String creditAmount;
    private String status;//订单状态

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getOffLineAgreementCd() {
        return offLineAgreementCd;
    }

    public void setOffLineAgreementCd(String offLineAgreementCd) {
        this.offLineAgreementCd = offLineAgreementCd;
    }

    public String getBorrowerIdCard() {
        return borrowerIdCard;
    }

    public void setBorrowerIdCard(String borrowerIdCard) {
        this.borrowerIdCard = borrowerIdCard;
    }

    public String getCreditCashValue() {
        return creditCashValue;
    }

    public void setCreditCashValue(String creditCashValue) {
        this.creditCashValue = creditCashValue;
    }

    public String getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(String creditAmount) {
        this.creditAmount = creditAmount;
    }
}
