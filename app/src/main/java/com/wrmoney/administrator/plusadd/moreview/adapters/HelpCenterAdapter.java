package com.wrmoney.administrator.plusadd.moreview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.bean.InvestMentBean;

import java.util.List;

/**
 * Created by Administrator on 2015/11/16.
 */
public class HelpCenterAdapter  extends BaseAdapter{
    private List<String> list;
    private Context context;

    public HelpCenterAdapter(List<String> list, Context context) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.more_help_center_item,parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.tv_help.setText(list.get(position));

        return convertView;
    }

    public static class ViewHolder {
        private TextView tv_help;//帮助条目
        public ViewHolder(View itemView) {
            this.tv_help= (TextView) itemView.findViewById(R.id.tv_help_item);
        }
    }
}
