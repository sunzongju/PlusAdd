package com.wrmoney.administrator.plusadd.accountview.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.bean.InvitationBean;
import com.wrmoney.administrator.plusadd.bean.InvitationDetailBean;
import com.wrmoney.administrator.plusadd.bean.MoneyWaterBean;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015/11/14.
 */
public class InvitationDetailAdapter extends BaseAdapter{
    private Context context;
    private List<InvitationDetailBean> list;

    public InvitationDetailAdapter(Context context, List<InvitationDetailBean> list) {
        this.context = context;
        this.list = list;
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

    public void addAll(Collection<? extends InvitationDetailBean> collection){
        list.addAll(collection);
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.account_invitation_detail_item, parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        InvitationDetailBean bean = list.get(position);
        Log.i("========Type", bean.getType());
        if("1".equals(bean.getIfOpenAcct())){
            holder.tv_invitedUser.setText(bean.getInvitedUser());
           // holder.tv_invitedUser.setText(bean.getMobile());
        }else if("0".equals(bean.getIfOpenAcct())){
            holder.tv_invitedUser.setText(bean.getMobile());
        }
        if("1".equals(bean.getType())){
            holder.btn_type.setText("注");
            holder.tv_coment.setText("邀请成功");
        }else if("2".equals(bean.getType())){
            holder.btn_type.setText("投");
            if("0".equals(bean.getReturnState())){
                holder.tv_coment.setText("购买奖励");
            }else if("1".equals(bean.getReturnState())){
                holder.tv_coment.setText("返佣完成");
            }
        }
        holder.tv_commissionAmount.setText(bean.getCommissionAmountStr());
        holder.tv_commissionTime.setText(bean.getComminssionTimeStr());
        holder.tv_coment.setText(bean.getComent());
        holder.tv_count.setText("共计"+bean.getCount()+"次");
//        holder.tv_lotteryStatus.setText(redPacketBean.getLotteryStatus());
//        holder.tv_lotteryValidTime.setText(redPacketBean.getLotteryValidTime());
//        holder.tv_lotteryAmount.setText(redPacketBean.getLotteryAmount());
        return convertView;
    }

    public static class ViewHolder {
        private TextView tv_invitedUser;//邀请人
        private TextView tv_commissionAmount;//佣金字符串类型
        private TextView tv_commissionTime;//返佣时间
        private TextView tv_coment;//备注
        private TextView tv_count;//返现次数
        private Button btn_type;//类型

        public ViewHolder(View itemView) {
            this.tv_invitedUser = (TextView) itemView.findViewById(R.id.tv_invitedUser);
            this.tv_commissionAmount=(TextView)itemView.findViewById(R.id.tv_commissionAmountStr);
            this.tv_commissionTime=(TextView)itemView.findViewById(R.id.tv_commissionTimeStr);
            this.tv_coment=(TextView)itemView.findViewById(R.id.tv_coment);
            this.tv_count=(TextView)itemView.findViewById(R.id.tv_count);
            this.btn_type=(Button)itemView.findViewById(R.id.btn_type);
        }
    }
}
