package com.wrmoney.administrator.plusadd.tools;

import android.content.Context;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.wrmoney.administrator.plusadd.bean.HomeContentBean;

/**
 * Created by Administrator on 2015/7/28.
 */
public class DbHelper {
    public static DbUtils utils;

    public static DbUtils getUtils() {
        return utils;
    }

    public static void setUtils(DbUtils utils) {
        DbHelper.utils = utils;
    }

    public static void init(Context context)
    {
        DbUtils.DaoConfig config = new DbUtils.DaoConfig(context);
        config.setDbName("xUtils-demo"); //db名
        config.setDbVersion(1);  //db版本
        //DbUtils db = DbUtils.create(config);//db还有其他的一些构造方法，比如含有更新表版本的监听器的
        utils=DbUtils.create(config);
        utils.configAllowTransaction(true);//开启事物
        utils.configDebug(true);//开启调试模式
        try {
            utils.createTableIfNotExist(HomeContentBean.class); //创建一个表User
            utils.save(new HomeContentBean());//在表中保存一个user对象。最初执行保存动作时，也会创建User表
        } catch (DbException e) {
            e.printStackTrace();
        }

    }
}
