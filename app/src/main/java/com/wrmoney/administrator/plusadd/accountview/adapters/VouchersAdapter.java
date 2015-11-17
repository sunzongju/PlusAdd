package com.wrmoney.administrator.plusadd.accountview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.bean.VoucherBean;

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.account_voucher_items, parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        return convertView;
    }

    public static class ViewHolder {
        private TextView tv_voucher;//红包金额
        public ViewHolder(View itemView) {
            this.tv_voucher = (TextView) itemView.findViewById(R.id.tv_voucher);
        }
    }
}
