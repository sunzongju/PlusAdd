package com.wrmoney.administrator.plusadd.accountview.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.bean.MessageBean;

import java.util.Collection;
import java.util.List;

import static com.wrmoney.administrator.plusadd.R.color.gray0;

/**
 * Created by Administrator on 2015/11/17.
 */
public class MessageAdapter extends BaseAdapter {
    private Context context;
    private List<MessageBean> list;

    public MessageAdapter(Context context, List<MessageBean> list) {
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

    public void addAll(Collection<? extends MessageBean> collection){
        list.addAll(collection);
        notifyDataSetChanged();
    }

    public void notiFY(){
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.account_message_item, parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        MessageBean bean=list.get(position);

        if("Y".equals(bean.getIsRead())){
            holder.tv_msgTitle.setTextColor(context.getResources().getColor(R.color.gray5));
            holder.tv_sendTime.setTextColor(context.getResources().getColor(R.color.gray5));
            holder.tv_msgContent.setTextColor(context.getResources().getColor(R.color.gray5));
        }else if("N".equals(bean.getIsRead())){
            holder.tv_msgTitle.setTextColor(context.getResources().getColor(R.color.black0));
            holder.tv_sendTime.setTextColor(context.getResources().getColor(R.color.gray7));
            holder.tv_msgContent.setTextColor(context.getResources().getColor(R.color.gray7));
        }
        holder.tv_msgTitle.setText(bean.getMsgTitle());
        holder.tv_sendTime.setText(bean.getSendTime());
        holder.tv_msgContent.setText(bean.getMsgContent());
        return convertView;
    }

    public static class ViewHolder {
        private TextView tv_msgTitle;//消息标题
        private TextView tv_sendTime;//消息时间
        private TextView tv_msgContent;//消息内容
        public ViewHolder(View itemView) {
            this.tv_msgTitle = (TextView) itemView.findViewById(R.id.tv_msgTitle);
            this.tv_sendTime=(TextView)itemView.findViewById(R.id.tv_sendTime);
            this.tv_msgContent=(TextView)itemView.findViewById(R.id.tv_msgContent);
        }
    }
}
