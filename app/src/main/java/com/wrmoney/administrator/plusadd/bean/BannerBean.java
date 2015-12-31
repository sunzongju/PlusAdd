package com.wrmoney.administrator.plusadd.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2015/11/25.
 */
@Table(name = "BannerBean")
public class BannerBean {
    @NoAutoIncrement
    @Id(column = "id")
    private String id;
    @Column(column = "title")
    private String title;
    @Column(column = "descrition")
    private String descrition;
    @Column(column = "url")
    private String url;
    @Column(column = "jumpurl")
    private String jumpurl;

    public String getJumpurl() {
        return jumpurl;
    }

    public void setJumpurl(String jumpurl) {
        this.jumpurl = jumpurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrition() {
        return descrition;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
