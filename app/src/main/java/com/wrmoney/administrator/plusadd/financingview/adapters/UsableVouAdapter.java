package com.wrmoney.administrator.plusadd.financingview.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.bean.VoucherBean;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/12/15.
 */
public class UsableVouAdapter extends BaseAdapter {
    private List<VoucherBean> list;
    private Context context;
    public  String str;
    public int tagCheck;
    public int tagGet;
    HashMap<String, Boolean> states = new HashMap<String, Boolean>();
    public UsableVouAdapter(Context context,List<VoucherBean> list,int tagGet) {
        this.list = list;
        this.context = context;
        this.tagGet=tagGet;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_voucher_usable_item,parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        final ViewHolder holder = (ViewHolder) convertView.getTag();
        VoucherBean bean=list.get(position);
        holder.tv_vname.setText(bean.getLotteryTitle());
        if(bean.getLotteryTitle().equals(bean.getTagCheck())){
            holder.cb_check.setChecked(true);
        }
       // holder.cb_check.setChecked(list.get(position));
        //黑体部分为实现单选功能部分
        final CheckBox radio=(CheckBox) convertView.findViewById(R.id.cb_check);
        holder.cb_check = radio;
        holder.cb_check.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // 重置，确保最多只有一项被选中
                for (String key : states.keySet()) {
                    states.put(key, false);

                }
                states.put(String.valueOf(position), radio.isChecked());
                UsableVouAdapter.this.notifyDataSetChanged();
                str=list.get(position).getLotteryTitle();
                tagCheck=position;
            }
        });

        boolean res = false;
        if ((states.get(String.valueOf(position)) == null
                || states.get(String.valueOf(position)) == false)&&tagGet!=position) {
            res = false;
            states.put(String.valueOf(position), false);
        } else {
            res = true;
            if(tagGet==position){
                tagGet=-1;
            }
        }
        holder.cb_check.setChecked(res);
        return convertView;
    }

    public static class ViewHolder {
        private TextView tv_vname;//标题
        public CheckBox cb_check;//预期收益率


        public ViewHolder(View itemView) {
            this.tv_vname = (TextView) itemView.findViewById(R.id.tv_vname);
            this.cb_check = (CheckBox) itemView.findViewById(R.id.cb_check);
        }
    }
}
