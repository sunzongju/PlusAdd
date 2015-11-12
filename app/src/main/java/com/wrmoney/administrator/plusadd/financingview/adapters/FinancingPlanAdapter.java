package com.wrmoney.administrator.plusadd.financingview.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wrmoney.administrator.plusadd.BaseFragment;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.bean.FinancingPlanBean;
import com.wrmoney.administrator.plusadd.bean.RedPacketBean;
import com.wrmoney.administrator.plusadd.financingview.activitys.InvestActivity;
import com.wrmoney.administrator.plusadd.financingview.activitys.InvestDetailActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015/11/3.
 */
public class FinancingPlanAdapter extends BaseAdapter {
   private List<FinancingPlanBean> list;
    private Context context;

    public FinancingPlanAdapter(List<FinancingPlanBean> list, Context context) {
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

    public void addAll(Collection<? extends FinancingPlanBean> collection) {
        list.addAll(collection);
        notifyDataSetChanged();
    }

    public void addOne(FinancingPlanBean bean) {
        list.add(bean);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.financing_plan_item,parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        FinancingPlanBean planBean = list.get(position);
//        holder.tv_lotteryStatus.setText(redPacketBean.getLotteryStatus());
//        holder.tv_lotteryValidTime.setText(redPacketBean.getLotteryValidTime());
//        holder.tv_lotteryAmount.setText(redPacketBean.getLotteryAmount());
        holder.tv_title.setText(planBean.getTitle());
        holder.btn_invest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, InvestActivity.class);
                context.startActivity(intent);

            }
        });
        return convertView;
    }

    public static class ViewHolder {
        private TextView tv_title;//标题
        private TextView tv_rate;//年化收益率
       // private TextView pro_rate;//进度条
        private TextView tv_content;//投资内容
        private Button btn_invest;//投资按钮

        public ViewHolder(View itemView) {
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.tv_rate = (TextView) itemView.findViewById(R.id.tv_rate);
            this.tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            this.btn_invest=(Button)itemView.findViewById(R.id.btn_invest);
        }
    }
}
