package com.wrmoney.administrator.plusadd.accountview.adapters;

import android.content.Context;
import android.graphics.Picture;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.bean.ActivityListBean;
import com.wrmoney.administrator.plusadd.bean.PictureBean;

import java.util.List;

/**
 * Created by Administrator on 2015/11/12.
 */
public class AccountMenuAdapter extends BaseAdapter {
    private Context context;
    private List<PictureBean> list;

    public AccountMenuAdapter(Context context, List<PictureBean> list) {
        this.context = context;
        this.list = list;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.account_menu_item,parent,false);

            convertView.setTag(new ViewHolder(convertView));

        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        PictureBean bean = list.get(position);
        holder.iv_pic.setImageResource(bean.getImageId());
        holder.tv_tle.setText(bean.getTitle());
        return convertView;
    }

    public static class ViewHolder {
        private ImageView iv_pic;//红包金额
        private TextView tv_tle;//红包状态

        public ViewHolder(View itemView) {
            this.iv_pic = (ImageView) itemView.findViewById(R.id.iv_pic);
            this.tv_tle = (TextView) itemView.findViewById(R.id.tv_tle);
        }
    }


}
