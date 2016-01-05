package com.wrmoney.administrator.plusadd.moreview.checkversion;

/**
 * Created by Administrator on 2015/12/21.
 */
public class UpdataInfo {
    private String isNewVer;//是否有新版本，1：是，0：否

    private String newVerNo;//最新版本号

    private String downloadUrl;//安装包下载的URL地址

    private String newVerSize;//新版本大小

    private String updateContent;//更新内容

    private String updateType;//更新类型： 1-无需升级，2-可选升级,3-必须升级

    public String getIsNewVer() {
        return isNewVer;
    }

    public void setIsNewVer(String isNewVer) {
        this.isNewVer = isNewVer;
    }

    public String getNewVerNo() {
        return newVerNo;
    }

    public void setNewVerNo(String newVerNo) {
        this.newVerNo = newVerNo;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getNewVerSize() {
        return newVerSize;
    }

    public void setNewVerSize(String newVerSize) {
        this.newVerSize = newVerSize;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }
}
