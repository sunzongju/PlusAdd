package com.wrmoney.administrator.plusadd.accountview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.bean.InvestMentBean;
import com.wrmoney.administrator.plusadd.bean.MoneyWaterBean;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015/11/13.
 */
public class InvestMentAdapter extends BaseAdapter{

    private List<InvestMentBean> list;
    private Context context;

    public InvestMentAdapter(List<InvestMentBean> list, Context context) {
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

    public void addAll(Collection<? extends InvestMentBean> listBean){
        list.addAll(listBean);
        notifyDataSetChanged();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.account_invest_ment_item, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        InvestMentBean bean = list.get(position);
//        holder.tv_lotteryStatus.setText(redPacketBean.getLotteryStatus());
//        holder.tv_lotteryValidTime.setText(redPacketBean.getLotteryValidTime());
//        holder.tv_lotteryAmount.setText(redPacketBean.getLotteryAmount());
        return convertView;
    }

    public static class ViewHolder {
        private TextView tv_title;//红包金额
        public ViewHolder(View itemView) {
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}
