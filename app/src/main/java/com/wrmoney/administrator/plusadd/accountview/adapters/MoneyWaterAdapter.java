package com.wrmoney.administrator.plusadd.accountview.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.bean.MoneyWaterBean;
import com.wrmoney.administrator.plusadd.bean.RedPacketBean;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015/10/16.
 */
public class MoneyWaterAdapter extends BaseAdapter {
    private List<MoneyWaterBean> list;
    private Context context;

    public MoneyWaterAdapter(List<MoneyWaterBean> list, Context context) {
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

    public void addAll(Collection<? extends MoneyWaterBean> listBean){
        list.addAll(listBean);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.account_money_water_item,parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        MoneyWaterBean bean = list.get(position);
        holder.tv_transDate.setText(bean.getTransDate());
       holder.tv_transComent.setText(bean.getTransComent());
        holder.tv_transAmount.setText(bean.getTransAmount());
        int index=bean.getTransAmount().indexOf("+");
       if(index!=-1){
           holder.tv_transAmount.setTextColor(Color.RED);
       }else {
           holder.tv_transAmount.setTextColor(Color.GREEN);
       }
        return convertView;
    }

    public static class ViewHolder {
        private TextView tv_transDate;//红包金额
        private TextView tv_transComent;//红包状态
        private TextView tv_transAmount;//有效期

        public ViewHolder(View itemView) {
            this.tv_transDate = (TextView) itemView.findViewById(R.id.tv_transDate);
            this.tv_transComent = (TextView) itemView.findViewById(R.id.tv_transComent);
            this.tv_transAmount = (TextView) itemView.findViewById(R.id.tv_transAmount);
        }
    }
}
