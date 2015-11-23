package com.wrmoney.administrator.plusadd.financingview.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wrmoney.administrator.plusadd.BaseFragment;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.bean.FinancingPlanBean;
import com.wrmoney.administrator.plusadd.bean.RedPacketBean;
import com.wrmoney.administrator.plusadd.financingview.activitys.InvestActivity;
import com.wrmoney.administrator.plusadd.financingview.activitys.InvestDetailActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015/11/3.
 */
public class FinancingPlanAdapter extends BaseAdapter {
   private List<FinancingPlanBean> list;
    private Context context;
    private FinancingPlanBean planBean;

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
       // list.clear();
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
        final ViewHolder holder = (ViewHolder) convertView.getTag();

        planBean = list.get(position);
       // Log.i("=======", planBean.getId() + "展示的ID");
        switch (planBean.getType()){
            case 1:
                holder.tv_type.setText("新手");
                break;
            case 2:
                holder.tv_type.setText("90天");
                break;
            case 3:
                holder.tv_type.setText("180天");
                break;
            case 4:
                holder.tv_type.setText("360天");
                break;
            default:
                break;

        }
        holder.tv_rate.setText(planBean.getExpectedRate());
        holder.pro_rate.setProgress(planBean.getProgress());
        holder.tv_content.setText("投资期限" + planBean.getBaseLockPeriod() + "天 项目规模" + planBean.getMinBuyerAmount());
        holder.tv_repaytype.setText(planBean.getRepayType());
        holder.btn_invest.setTag(planBean.getId());
        if("Y".equals(planBean.getEnableBuy())){
            holder.btn_invest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Log.i("========", holder.btn_invest.getTag() + "adapter里的id");
                    Intent intent=new Intent(context, InvestActivity.class);
                    intent.putExtra("PLANID",holder.btn_invest.getTag().toString());
                    context.startActivity(intent);

                }
            });
        }else {
           holder.btn_invest.setText("售罄");
        }


        return convertView;
    }

    public static class ViewHolder {
        private  int id;//计划ID
        private TextView tv_type;//标题
        private TextView tv_rate;//预期收益率
         private ProgressBar pro_rate;//进度条
        private TextView tv_content;//投资内容
        private TextView tv_repaytype;//
        private Button btn_invest;//投资按钮
        public ViewHolder(View itemView) {
            this.tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            this.tv_rate = (TextView) itemView.findViewById(R.id.tv_expectedRate);
            this.pro_rate=(ProgressBar)itemView.findViewById(R.id.pro_rate);
            this.tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            this.tv_repaytype=(TextView)itemView.findViewById(R.id.tv_repaytype);
            this.btn_invest=(Button)itemView.findViewById(R.id.btn_invest);
        }
    }
}
