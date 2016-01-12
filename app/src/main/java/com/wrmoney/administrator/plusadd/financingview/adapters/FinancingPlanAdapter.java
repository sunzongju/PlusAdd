package com.wrmoney.administrator.plusadd.financingview.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wrmoney.administrator.plusadd.BaseFragment;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.bean.FinancingPlanBean;
import com.wrmoney.administrator.plusadd.bean.RedPacketBean;
import com.wrmoney.administrator.plusadd.financingview.activitys.InvestActivity;
import com.wrmoney.administrator.plusadd.financingview.activitys.InvestDetailActivity;
import com.wrmoney.administrator.plusadd.financingview.activitys.InvestJoinActivity;
import com.wrmoney.administrator.plusadd.financingview.activitys.InvestWebJoinActivity;
import com.wrmoney.administrator.plusadd.loginview.activitys.PhoneActivity;
import com.wrmoney.administrator.plusadd.tools.DisplayUtil;
import com.wrmoney.administrator.plusadd.tools.SingleUserIdTool;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015/11/3.
 */
public class FinancingPlanAdapter extends BaseAdapter {
    private List<FinancingPlanBean> list;
    private static Context context;
    private FinancingPlanBean planBean;
    private String userid= SingleUserIdTool.newInstance().getUserid();
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
       // Log.i("========2",list.size()+"");//获得数据数位10；（此处首次应该为0）
        list.addAll(collection);
        //Log.i("========3", list.size()+"");//获得数据数位20  （此处首次应该为10）
        notifyDataSetChanged();
    }

    public void addOne(FinancingPlanBean bean) {
        list.add(bean);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        planBean = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.financing_plan_item,null);
            convertView.setTag(new ViewHolder1(convertView));
        }
       // Log.i("=======", planBean.getId() + "展示的ID");
        final ViewHolder1 holder = (ViewHolder1) convertView.getTag();
        holder.tv_type.setText(planBean.getName());
        holder.tv_rate.setText(planBean.getExpectedRate());
        holder.pro_rate.setProgress(planBean.getProgress());
        //holder.tv_content.setText("投资期限" + planBean.getBaseLockPeriod() + " 项目规模" + planBean.getMaxFinancing());
        double account=(Double.parseDouble(planBean.getMaxFinancing()))/10000;

        Log.i("=========数据值",account+"");
        int account1=(int)Math.round(account);
        holder.tv_content.setText("投资期限" + planBean.getBaseLockPeriod() + "天");
        holder.tv_content2.setText("项目金额"+account1+"万");
        holder.tv_repaytype.setText(planBean.getRepayType());
        holder.btn_invest.setTag(planBean.getId());
        if("Y".equals(planBean.getEnableBuy())){
            holder.btn_invest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (userid == null) {
                            Intent intent11 = new Intent(context, PhoneActivity.class);
//                            intent11.putExtra("PLANID",planBean.getId());
                            context.startActivity(intent11);
                            //startActivity(intent11);
                            // finish();
                        } else {
                           // Log.i("=======InvestUserid", SingleUserIdTool.newInstance().getUserid());
                            //SingleUserIdTool.newInstance().setUserid(SingleUserIdTool.newInstance().getUserid());
                            Intent intent1=new Intent(context,InvestWebJoinActivity.class);
                            Bundle bundle2=new Bundle();
                            //bundle2.putParcelable("BEAN",planBean);
                            bundle2.putString("PLANID",holder.btn_invest.getTag()+"");
                            bundle2.putString("USERID",userid);
                            intent1.putExtras(bundle2);
                            context.startActivity(intent1);
                        }
                }
            });
        }else {
          // holder.btn_invest.setText("售罄");
        }
        return convertView;
    }

    public static class ViewHolder1 {
        private LinearLayout ll_layout;
        private  int id;//计划ID
        private TextView tv_type;//标题
        private TextView tv_rate;//预期收益率
         private ProgressBar pro_rate;//进度条
        private TextView tv_content;//投资内容
        private TextView tv_content2;
        private TextView tv_repaytype;//
        private TextView btn_invest;//投资按钮
        private LinearLayout  ll;
        public ViewHolder1(View itemView) {
            this.tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            this.tv_rate = (TextView) itemView.findViewById(R.id.tv_expectedRate);
            this.pro_rate=(ProgressBar)itemView.findViewById(R.id.pro_rate);
            this.tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            this.tv_content2=(TextView)itemView.findViewById(R.id.tv_content2);
            this.tv_repaytype=(TextView)itemView.findViewById(R.id.tv_repaytype);
            this.btn_invest=(TextView)itemView.findViewById(R.id.btn_invest);
            this.ll_layout=(LinearLayout)itemView.findViewById(R.id.ll_layout);
            LinearLayout.LayoutParams layoutParams=(LinearLayout.LayoutParams)this.ll_layout.getLayoutParams();
            DisplayMetrics dm = new DisplayMetrics();
            dm=((Activity)context).getResources().getDisplayMetrics();
            ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
            int  height1=DisplayUtil.dip2px(context,54);
            layoutParams.height=(dm.heightPixels-(DisplayUtil.dip2px(context,174)))/4;
//            int height2 =dm.heightPixels-height1;  //得到高度
//            layoutParams.height=height2/4;
            this.ll_layout.setLayoutParams(layoutParams);
        }
    }
    public static class ViewHolder2{
        private TextView tv_all;//加载完毕提示
        public ViewHolder2(View itemView) {
            this.tv_all =(TextView)itemView.findViewById(R.id.tv_all);
        }
    }

}
