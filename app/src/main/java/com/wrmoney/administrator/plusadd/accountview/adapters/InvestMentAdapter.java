package com.wrmoney.administrator.plusadd.accountview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.account_invest_ment_item, parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        InvestMentBean bean = list.get(position);
        String status=bean.getStatus();
        holder.status.setText(status);
        if("已锁定".equals(status)){
            holder.lockPic.setImageResource(R.drawable.locked);
        }else{
            holder.lockPic.setImageResource(R.drawable.unlocked);
        }
        holder.planName.setText(bean.getPlanName());
        holder.investDate.setText(bean.getInvestDate());
        holder.expectedRate.setText("预期收益率"+bean.getExpectedRate());
        holder.investAmount.setText(bean.getInvestAmount());
        holder.lockTime.setText(bean.getLockTime()+"天");
        return convertView;
    }

    public static class ViewHolder {
        private String orderId;//计划ID
        private TextView status;//订单状态
        private TextView planName;//投资类型
        private TextView investDate;//日期
        private TextView expectedRate;//预期收益率
        private TextView investAmount;//买入金额
        private TextView lockTime;//锁定期
        private ImageView lockPic;//小锁图标
        public ViewHolder(View itemView) {
            this.planName = (TextView) itemView.findViewById(R.id.tv_planName);
            this.investDate=(TextView)itemView.findViewById(R.id.tv_investDate);
            this.expectedRate=(TextView)itemView.findViewById(R.id.tv_expectedRate);
            this.investAmount=(TextView)itemView.findViewById(R.id.tv_investAmount);
            this.lockTime=(TextView)itemView.findViewById(R.id.tv_lockTime);
            this.status=(TextView)itemView.findViewById(R.id.tv_status);
            this.lockPic=(ImageView)itemView.findViewById(R.id.iv_lock);
        }
    }
}
