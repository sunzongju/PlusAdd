package com.wrmoney.administrator.plusadd.accountview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.bean.VoucherBean;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015/11/17.
 */
public class VouchersAdapter extends BaseAdapter {
    private List<VoucherBean> list;
    private Context context;

    public VouchersAdapter(List<VoucherBean> list, Context context) {
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

    public void addAll(Collection<? extends VoucherBean> listbean){
        list.addAll(listbean);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.account_voucher_items, parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        VoucherBean bean=list.get(position);
        holder.tv_lotteryAmount.setText(bean.getLotteryAmount());
        holder.tv_lotteryTitle.setText(bean.getLotteryTitle());
        holder.tv_lotteryComent.setText(bean.getLotteryComent());
        //holder.tv_lotteryStatus.setText(bean.getLotteryStatus());
        holder.tv_lotteryValidTime.setText(bean.getLotteryValidTime());
        if("1".equals(bean.getLotteryStatus())){
            holder.tv_name.setTextColor(context.getResources().getColor(R.color.red));
            holder.tv_lotteryTitle.setTextColor(context.getResources().getColor(R.color.gray3));
            holder.tv_lotteryComent.setTextColor(context.getResources().getColor(R.color.gray3));
            holder.tv_lotteryValidTime.setTextColor(context.getResources().getColor(R.color.gray3));
            holder.iv_voucher.setVisibility(View.INVISIBLE);
            holder.lay_01.setBackgroundColor(context.getResources().getColor(R.color.red));
        }else if("2".equals(bean.getLotteryStatus())){
            holder.tv_timeTitle.setTextColor(context.getResources().getColor(R.color.gray0));
            holder.tv_name.setTextColor(context.getResources().getColor(R.color.gray0));
            holder.tv_lotteryTitle.setTextColor(context.getResources().getColor(R.color.gray0));
            holder.tv_lotteryComent.setTextColor(context.getResources().getColor(R.color.gray0));
            holder.tv_lotteryValidTime.setTextColor(context.getResources().getColor(R.color.gray0));
            holder.iv_voucher.setVisibility(View.VISIBLE);
            holder.iv_voucher.setImageResource(R.drawable.isuse_pic);
            holder.lay_01.setBackgroundColor(context.getResources().getColor(R.color.gray0));
        }else if("3".equals(bean.getLotteryStatus())){
            holder.tv_timeTitle.setTextColor(context.getResources().getColor(R.color.gray0));
            holder.tv_name.setTextColor(context.getResources().getColor(R.color.gray0));
            holder.tv_lotteryTitle.setTextColor(context.getResources().getColor(R.color.gray0));
            holder.tv_lotteryComent.setTextColor(context.getResources().getColor(R.color.gray0));
            holder.tv_lotteryValidTime.setTextColor(context.getResources().getColor(R.color.gray0));
            holder.iv_voucher.setVisibility(View.VISIBLE);
            holder.iv_voucher.setImageResource(R.drawable.isstale_pic);
            holder.lay_01.setBackgroundColor(context.getResources().getColor(R.color.gray0));
        }

        return convertView;
    }

    public static class ViewHolder {
        private TextView tv_lotteryAmount;//抵用券金额
        private TextView tv_lotteryTitle;//奖券标题
        private TextView tv_lotteryComent;//奖券描述
        private TextView tv_lotteryStatus;//奖券状态
        private TextView tv_lotteryValidTime;//
        private TextView tv_name;
        private ImageView iv_voucher;
        private LinearLayout lay_01;
        private TextView tv_timeTitle;

        public ViewHolder(View itemView) {
            this.tv_lotteryAmount = (TextView) itemView.findViewById(R.id.tv_lotteryAmount);
            this.tv_lotteryTitle=(TextView)itemView.findViewById(R.id.tv_lotteryTitle);
            this.tv_lotteryComent=(TextView)itemView.findViewById(R.id.tv_lotteryComent);
            this.tv_lotteryStatus=(TextView)itemView.findViewById(R.id.tv_lotteryStatus);
            this.tv_lotteryValidTime=(TextView)itemView.findViewById(R.id.tv_lotteryValidTime);
            this.tv_name=(TextView)itemView.findViewById(R.id.tv_name);
            this.iv_voucher=(ImageView)itemView.findViewById(R.id.iv_voucher);
            this.lay_01=(LinearLayout)itemView.findViewById(R.id.lay_01);
            this.tv_timeTitle=(TextView)itemView.findViewById(R.id.tv_timeTitle);
        }
    }
}
