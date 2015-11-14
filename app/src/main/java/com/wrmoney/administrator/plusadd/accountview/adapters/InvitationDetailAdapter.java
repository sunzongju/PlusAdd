package com.wrmoney.administrator.plusadd.accountview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.bean.InvitationBean;
import com.wrmoney.administrator.plusadd.bean.MoneyWaterBean;

import java.util.List;

/**
 * Created by Administrator on 2015/11/14.
 */
public class InvitationDetailAdapter extends BaseAdapter{
    private Context context;
    private List<InvitationBean> list;

    public InvitationDetailAdapter(Context context, List<InvitationBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.account_invitation_detail_item, parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
//        MoneyWaterBean bean = list.get(position);
//        holder.tv_lotteryStatus.setText(redPacketBean.getLotteryStatus());
//        holder.tv_lotteryValidTime.setText(redPacketBean.getLotteryValidTime());
//        holder.tv_lotteryAmount.setText(redPacketBean.getLotteryAmount());
        return convertView;
    }

    public static class ViewHolder {
        private TextView tv_name;//红包金额

        public ViewHolder(View itemView) {
            this.tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
