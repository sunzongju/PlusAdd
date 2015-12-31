package com.wrmoney.administrator.plusadd.homeview.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.bean.FinancingPlanBean;
import com.wrmoney.administrator.plusadd.bean.HomeContentBean;
import com.wrmoney.administrator.plusadd.financingview.activitys.InvestActivity;
import com.wrmoney.administrator.plusadd.financingview.activitys.InvestJoinActivity;
import com.wrmoney.administrator.plusadd.loginview.activitys.PhoneActivity;
import com.wrmoney.administrator.plusadd.tools.DisplayUtil;
import com.wrmoney.administrator.plusadd.tools.SingleUserIdTool;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015/11/20.
 */
public class HomeContentAdapter extends BaseAdapter {
    private List<HomeContentBean> list;
    private static Context context;
    private HomeContentBean planBean;
    private String userid=SingleUserIdTool.newInstance().getUserid();

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
            convertView = LayoutInflater.from(context).inflate(R.layout.home_content_item,null);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
            planBean = list.get(position);
            // holder.tv_title.setText(planBean.getTitle());
            holder.tv_name.setText(planBean.getName());
            holder.tv_rate.setText(planBean.getExpectedRate());

            holder.tv_repaytype.setText(planBean.getRepayType());
            double maxFinaning=Double.parseDouble(planBean.getMaxFinaning());
            double joinAmount=Double.parseDouble(planBean.getJoinAmount());
            Log.i("====数值",maxFinaning+";"+joinAmount);

        if("Y".equals(planBean.getEnablleBuy())){
            if(joinAmount>=maxFinaning){
                holder.btn_invest.setText("售罄");
            }else {
                holder.pro_rate.setProgress(planBean.getProgress());
                holder.btn_invest.setClickable(true);
                holder.btn_invest.setText("投资");
                holder.btn_invest.setBackgroundResource(R.drawable.button_press);
                holder.btn_invest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (userid == null) {
                            Intent intent11 = new Intent(context, PhoneActivity.class);
                            intent11.putExtra("PLANID",planBean.getId()+"");
                            context.startActivity(intent11);
                            //startActivity(intent11);
                            // finish();
                        } else {
                            //  Log.i("=======InvestUserid", SingleUserIdTool.newInstance().getUserid());
                            SingleUserIdTool.newInstance().setUserid(SingleUserIdTool.newInstance().getUserid());
                            Intent intent1=new Intent(context,InvestJoinActivity.class);
                            Bundle bundle2=new Bundle();
                            //bundle2.putParcelable("BEAN",planBean.getId()+"");
                            bundle2.putString("PLANID",planBean.getId()+"");
                            intent1.putExtras(bundle2);
                            context.startActivity(intent1);
                        }
                    }
                });
            }
        }else {
            holder.btn_invest.setText("售罄");
        }
            try {
                float account=(Float.parseFloat(planBean.getMaxFinaning()))/10000;
                int account1=(int)account;
                holder.tv_content.setText("投资期限" + planBean.getBaseLockPeriod() + "天，项目规模" +account1+"万");
            }catch (Exception e){
                e.printStackTrace();
            }
        return convertView;
    }

    public static class ViewHolder {
        private TextView tv_name;//标题
        private TextView tv_rate;//年化收益率
        private ProgressBar pro_rate;//进度条
        private TextView tv_content;//投资内容
        private TextView tv_repaytype;
        private TextView btn_invest;//投资按钮
        private LinearLayout ll_layout;

        public ViewHolder(View itemView) {
            this.tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            this.tv_rate = (TextView) itemView.findViewById(R.id.tv_expectedRate);
            this.pro_rate=(ProgressBar)itemView.findViewById(R.id.pro_rate);
            this.tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            this.tv_repaytype=(TextView)itemView.findViewById(R.id.tv_repaytype);
            this.btn_invest=(TextView)itemView.findViewById(R.id.btn_invest);
            this.ll_layout=(LinearLayout)itemView.findViewById(R.id.ll_layout);
            LinearLayout.LayoutParams layoutParams=(LinearLayout.LayoutParams)this.ll_layout.getLayoutParams();
            DisplayMetrics dm = new DisplayMetrics();
            dm=((Activity)context).getResources().getDisplayMetrics();
            ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
            int  height1= DisplayUtil.dip2px(context, 54);
            layoutParams.height=(dm.heightPixels-(DisplayUtil.dip2px(context,384)))/2;
//            int height2 =dm.heightPixels-height1;  //得到高度
//            layoutParams.height=height2/4;
            this.ll_layout.setLayoutParams(layoutParams);
        }
    }
}
