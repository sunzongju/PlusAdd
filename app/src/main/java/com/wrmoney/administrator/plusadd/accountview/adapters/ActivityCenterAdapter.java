package com.wrmoney.administrator.plusadd.accountview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.bean.ActivityListBean;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015/11/3.
 */
public class ActivityCenterAdapter extends BaseAdapter {

    private List<ActivityListBean> list;
    private Context context;

    public ActivityCenterAdapter(List<ActivityListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addAll(Collection<? extends ActivityListBean> collection) {
        list.addAll(collection);
        notifyDataSetChanged();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.account_activity_item, null);
            convertView.setTag(new ViewHolder(convertView));

        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        ActivityListBean news = list.get(position);
        holder.iv_imag.setImageResource(news.getPic());
//        holder.tv_title.setText(news.getTitle());
//        holder.tv_time.setText(news.getTime());
//        holder.tv_content.setText(news.getContent());
        return convertView;
    }

    public static class ViewHolder {
        private ImageView iv_imag;


        public ViewHolder(View itemView) {
           this.iv_imag = (ImageView) itemView.findViewById(R.id.iv_image);
//            this.tv_time = (TextView) itemView.findViewById(R.id.tv_time);
//            this.tv_content = (TextView) itemView.findViewById(R.id.tv_content);
//            this.tv_btn = (TextView) itemView.findViewById(R.id.tv_btn);
        }
    }

}
