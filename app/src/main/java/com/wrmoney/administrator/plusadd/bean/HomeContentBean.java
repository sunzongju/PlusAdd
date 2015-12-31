package com.wrmoney.administrator.plusadd.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2015/11/21.
 */
@Table(name="HomeContentBean")
public class HomeContentBean {
    @NoAutoIncrement
    @Id(column = "id")
    private Integer id;//计划ID
    @Column(column = "name")
    private String name;//标题
    @Column(column = "expectedRate")
    private String expectedRate;//收益率
    @Column(column = "progress")
    private int progress;//进度条
    @Column(column = "baseLockPeriod")
    private String baseLockPeriod;//期限
    @Column(column = "maxFinaning")
    private String maxFinaning; //项目规模
    @Column(column = "repayType")
    private String repayType;//还款方式
    @Column(column = "joinAmount")
    private String joinAmount;//

    public String getJoinAmount() {
        return joinAmount;
    }

    public void setJoinAmount(String joinAmount) {
        this.joinAmount = joinAmount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
