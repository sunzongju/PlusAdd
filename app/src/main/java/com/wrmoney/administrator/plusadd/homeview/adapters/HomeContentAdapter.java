package com.wrmoney.administrator.plusadd.homeview.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.bean.FinancingPlanBean;
import com.wrmoney.administrator.plusadd.bean.HomeContentBean;
import com.wrmoney.administrator.plusadd.financingview.activitys.InvestActivity;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015/11/20.
 */
public class HomeContentAdapter extends BaseAdapter {
    private List<HomeContentBean> list;
    private Context context;

    public HomeContentAdapter(List<HomeContentBean> list, Context context) {
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

    public void addAll(Collection<? extends HomeContentBean> collection) {
        // list.clear();
        list.addAll(collection);
        notifyDataSetChanged();
    }

    public void addOne(HomeContentBean bean) {
        list.add(bean);
        notifyDataSetChanged();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.home_content_item,parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        HomeContentBean planBean = list.get(position);
        // holder.tv_title.setText(planBean.getTitle());
        holder.tv_name.setText(planBean.getName());
        holder.tv_rate.setText(planBean.getExpectedRate());
        holder.pro_rate.setProgress(planBean.getProgress());
        holder.tv_content.setText("投资期限" + planBean.getBaseLockPeriod() + "天 项目规模" + planBean.getMaxFinaning());
        holder.tv_repaytype.setText(planBean.getRepayType());
        if("Y".equals(planBean.getEnablleBuy())){
            holder.btn_invest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, InvestActivity.class);
                    context.startActivity(intent);

                }
            });
        }else {
            holder.btn_invest.setText("售罄");
        }

        return convertView;
    }

    public static class ViewHolder {
        private TextView tv_name;//标题
        private TextView tv_rate;//年化收益率
        private ProgressBar pro_rate;//进度条
        private TextView tv_content;//投资内容
        private TextView tv_repaytype;
        private Button btn_invest;//投资按钮

        public ViewHolder(View itemView) {
            this.tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            this.tv_rate = (TextView) itemView.findViewById(R.id.tv_expectedRate);
            this.pro_rate=(ProgressBar)itemView.findViewById(R.id.pro_rate);
            this.tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            this.tv_repaytype=(TextView)itemView.findViewById(R.id.tv_repaytype);
            this.btn_invest=(Button)itemView.findViewById(R.id.btn_invest);
        }
    }
}
