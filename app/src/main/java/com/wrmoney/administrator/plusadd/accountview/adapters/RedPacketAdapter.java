package com.wrmoney.administrator.plusadd.accountview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.bean.RedPacketBean;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015/11/3.
 */
public class RedPacketAdapter extends BaseAdapter {

    private List<RedPacketBean> list = null;
    private Context context = null;

    public RedPacketAdapter(List<RedPacketBean> list, Context context) {
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

    public void addAll(Collection<? extends RedPacketBean> collection) {
        list.addAll(collection);
        notifyDataSetChanged();
    }

    public void addOne(RedPacketBean bean) {
        list.add(bean);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.account_redpackey_item, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        RedPacketBean redPacketBean = list.get(position);
        holder.tv_lotteryStatus.setText(redPacketBean.getLotteryStatus());
        holder.tv_lotteryValidTime.setText(redPacketBean.getLotteryValidTime());
        holder.tv_lotteryAmount.setText(redPacketBean.getLotteryAmount());
        return convertView;
    }

    public static class ViewHolder {
        private TextView tv_lotteryAmount;//红包金额
        private TextView tv_lotteryStatus;//红包状态
        private TextView tv_lotteryValidTime;//有效期

        public ViewHolder(View itemView) {
            this.tv_lotteryAmount = (TextView) itemView.findViewById(R.id.tv_lotteryAmount);
            this.tv_lotteryStatus = (TextView) itemView.findViewById(R.id.tv_lotteryStatus);
            this.tv_lotteryValidTime = (TextView) itemView.findViewById(R.id.tv_lotteryValidTime);
        }
    }
}
