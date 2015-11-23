package com.wrmoney.administrator.plusadd.tools;

import android.content.Context;

import com.lidroid.xutils.BitmapUtils;
import com.wrmoney.administrator.plusadd.R;

/**
 * Created by Administrator on 2015/11/21.
 */
public class BitmapHelper {

    private static BitmapUtils utils;
    public static void init(Context context)
    {
        utils=new BitmapUtils(context,null,0.25f,10<<20);
        utils.configDefaultLoadingImage(R.mipmap.ic_launcher);
        utils.configDefaultLoadFailedImage(R.mipmap.ic_launcher);
        utils.configThreadPoolSize(5);
    }
    public static BitmapUtils getUtils()
    {
        return utils;
    }
}
