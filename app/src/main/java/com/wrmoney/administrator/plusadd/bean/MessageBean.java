package com.wrmoney.administrator.plusadd.bean;

/**
 * Created by Administrator on 2015/11/17.
 */
public class MessageBean {
    private String msgId;//��ϢID
    private String msgTitle;//��Ϣ����
    private String sendTime;//��Ϣʱ��
    private String msgContent;//��Ϣ����
    private String isRead;//�Ƿ��Ѷ�

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }
}
