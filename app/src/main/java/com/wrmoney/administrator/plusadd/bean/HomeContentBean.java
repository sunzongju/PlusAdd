package com.wrmoney.administrator.plusadd.bean;

/**
 * Created by Administrator on 2015/11/21.
 */
public class HomeContentBean {

    private String name;//标题
    private String expectedRate;//收益率
    private int progress;//进度条
    private String baseLockPeriod;//期限
    private String maxFinaning; //项目规模
    private String repayType;//还款方式

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }

    private String  enablleBuy; //是否售罄


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpectedRate() {
        return expectedRate;
    }

    public void setExpectedRate(String expectedRate) {
        this.expectedRate = expectedRate;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getBaseLockPeriod() {
        return baseLockPeriod;
    }

    public void setBaseLockPeriod(String baseLockPeriod) {
        this.baseLockPeriod = baseLockPeriod;
    }

    public String getMaxFinaning() {
        return maxFinaning;
    }

    public void setMaxFinaning(String maxFinaning) {
        this.maxFinaning = maxFinaning;
    }

    public String getEnablleBuy() {
        return enablleBuy;
    }

    public void setEnablleBuy(String enablleBuy) {
        this.enablleBuy = enablleBuy;
    }
}
