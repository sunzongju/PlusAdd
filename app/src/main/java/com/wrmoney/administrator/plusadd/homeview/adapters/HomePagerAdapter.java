package com.wrmoney.administrator.plusadd.homeview.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wrmoney.administrator.plusadd.bean.BannerBean;
import com.wrmoney.administrator.plusadd.homeview.activitys.ActivityDetailActivity;
import com.wrmoney.administrator.plusadd.tools.BitmapHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015/11/2.
 */
public class HomePagerAdapter extends android.support.v4.view.PagerAdapter {

    private Context context;
    private List<ImageView> mList=new ArrayList<ImageView>();

    public HomePagerAdapter(List<ImageView> mViews,Context context) {
        this.mList = mViews;
        this.context=context;

    }
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        ImageView imageView = mList.get(position % mList.size());
//        ViewGroup parent = (ViewGroup) imageView.getParent();
//        if (parent != null) {
//            parent.removeView(imageView);
//        }
//        if(mList.size()>0){
     //           container.addView(mList.get(position % mList.size()));
//                mList.get(position % mList.size()).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                    }
//                });
        try {
            container.addView(mList.get(position % mList.size()));
        }catch (Exception e){
            container.removeView(mList.get(position % mList.size()));
            container.addView(mList.get(position % mList.size()));
        }
                return mList.get(position%mList.size());
//            }else {
//                return  null;
//            }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       // container.removeView((View)object);  ;
           // container.removeView(mList.get(position%mList.size()));
    }

}
