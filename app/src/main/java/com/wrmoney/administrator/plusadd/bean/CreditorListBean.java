package com.wrmoney.administrator.plusadd.bean;

/**
 * Created by Administrator on 2015/11/23.
 */
public class CreditorListBean {
    private String RN;
    private String creditId;//债权ID
    private String offLineAgreementCd;//

    public String getRN() {
        return RN;
    }

    public void setRN(String RN) {
        this.RN = RN;
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
}
