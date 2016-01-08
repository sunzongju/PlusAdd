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

    public void addAll(Collection<? extends ImageView> collection){
        mList.addAll(collection);
        notifyDataSetChanged();
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
    public Object instantiateItem(ViewGroup container,final int position) {
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
        mList.get(position % mList.size()).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       if(mList.size()>0){
                           String str=(String)mList.get(position % mList.size()).getTag();
                           if(!"".equals(str)&&str!=null){
                               Intent intent=new Intent(context,ActivityDetailActivity.class);
                               intent.putExtra("URL",str);
                               context.startActivity(intent);
                           }
                          // Log.i("======= ==图片", (String) mList.get(position % mList.size()).getTag());
                       }
                    }
                });
        return mList.get(position%mList.size());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       // container.removeView((View)object);  ;
           // container.removeView(mList.get(position%mList.size()));
    }

}
