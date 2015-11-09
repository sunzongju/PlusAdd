package com.wrmoney.administrator.plusadd.tools;

/**
 * Created by Administrator on 2015/11/2.
 */
public class SingleUserIdTool {

    private static SingleUserIdTool instance = null;
    private String userid = null;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    private SingleUserIdTool() {
    }

    public static SingleUserIdTool newInstance() {
        if (instance == null) {
            instance = new SingleUserIdTool();
        }
        return instance;
    }
}
