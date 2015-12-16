package com.wrmoney.administrator.plusadd.bean;

/**
 * 活动列表
 * Created by Administrator on 2015/11/3.
 */
public class ActivityListBean  {
    private String activityId;//活动ID
    private String activityStatus;//活动状态
    private String activityTime;//活动时间
    private String beginTimeStr;//开始时间
    private String endTimeStr;//结束时间
    private String activityImgUrl;//活动图片地址
    public String getBeginTimeStr() {
        return beginTimeStr;
    }

    public void setBeginTimeStr(String beginTimeStr) {
        this.beginTimeStr = beginTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public String getActivityImgUrl() {
        return activityImgUrl;
    }

    public void setActivityImgUrl(String activityImgUrl) {
        this.activityImgUrl = activityImgUrl;
    }
}
