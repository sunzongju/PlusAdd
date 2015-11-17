package com.wrmoney.administrator.plusadd.accountview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.R;

import java.util.List;

/**
 * Created by Administrator on 2015/11/17.
 */
public class CreditorAdapter  extends BaseAdapter{
    private List<String> list;
    private Context context;

    public CreditorAdapter(List<String> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.account_creditor_item, parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        return convertView;
    }

    public static class ViewHolder {
        private TextView tv_detail;//债券明细
        public ViewHolder(View itemView) {
            this.tv_detail = (TextView) itemView.findViewById(R.id.tv_detail);
        }
    }
}
