package com.wrmoney.administrator.plusadd.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;

import java.math.BigDecimal;

/**
 * 理财计划列表
 * Created by Administrator on 2015/11/3.
 */
@Table(name = "FinancingPlanBean")
public class FinancingPlanBean  {
    @NoAutoIncrement
    @Id(column = "id")
    private  Integer id;//计划ID
    @Column(column = "baseLockPeriod")
    private Integer baseLockPeriod;//基础锁定期限期限
    @Column(column = "type")
    private Integer type;//计划种类
    @Column(column = "expectedRate")
    private String expectedRate;//预期收益率
    @Column(column = "progress")
    private Integer progress;//进度条
    @Column(column = "minBuyerAmount")
    private String  minBuyerAmount;//购买最小金额
    @Column(column = "maxFinancing")
    private String maxFinancing;//筹措资金上限/计划筹集金额

    public String getMaxFinancing() {
        return maxFinancing;
    }

    public void setMaxFinancing(String maxFinancing) {
        this.maxFinancing = maxFinancing;
    }

    private String repayType;//还款方式
    private String enableBuy;
    private String name;//理财计划名称

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnableBuy() {
        return enableBuy;
    }

    public void setEnableBuy(String enableBuy) {
        this.enableBuy = enableBuy;
    }

    public Integer getBaseLockPeriod() {
        return baseLockPeriod;
    }

    public void setBaseLockPeriod(Integer baseLockPeriod) {
        this.baseLockPeriod = baseLockPeriod;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getExpectedRate() {
        return expectedRate;
    }

    public void setExpectedRate(String expectedRate) {
        this.expectedRate = expectedRate;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getMinBuyerAmount() {
        return minBuyerAmount;
    }

    public void setMinBuyerAmount(String minBuyerAmount) {
        this.minBuyerAmount = minBuyerAmount;
    }

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }
}
