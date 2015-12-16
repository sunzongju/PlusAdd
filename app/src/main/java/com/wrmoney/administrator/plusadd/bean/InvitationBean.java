package com.wrmoney.administrator.plusadd.bean;

/**
 * Created by Administrator on 2015/11/14.
 */
public class InvitationBean {
    private String inviteCount;//邀请人数
    private String backedCommsAmount;//已返金额
    private String unbackCommsAmount;//待反金额
    private String invitationCode;//自己的邀请码
    private String bindInviteCode;//绑定邀请码
    private String commissionTimeStr;
    private String createTimeStr;
    private String regTimeStr;

    public String getInviteCount() {
        return inviteCount;
    }

    public void setInviteCount(String inviteCount) {
        this.inviteCount = inviteCount;
    }

    public String getBackedCommsAmount() {
        return backedCommsAmount;
    }

    public void setBackedCommsAmount(String backedCommsAmount) {
        this.backedCommsAmount = backedCommsAmount;
    }

    public String getUnbackCommsAmount() {
        return unbackCommsAmount;
    }

    public void setUnbackCommsAmount(String unbackCommsAmount) {
        this.unbackCommsAmount = unbackCommsAmount;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getBindInviteCode() {
        return bindInviteCode;
    }

    public void setBindInviteCode(String bindInviteCode) {
        this.bindInviteCode = bindInviteCode;
    }

    public String getCommissionTimeStr() {
        return commissionTimeStr;
    }

    public void setCommissionTimeStr(String commissionTimeStr) {
        this.commissionTimeStr = commissionTimeStr;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getRegTimeStr() {
        return regTimeStr;
    }

    public void setRegTimeStr(String regTimeStr) {
        this.regTimeStr = regTimeStr;
    }
}
