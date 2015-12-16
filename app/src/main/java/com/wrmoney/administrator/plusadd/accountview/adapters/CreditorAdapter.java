package com.wrmoney.administrator.plusadd.accountview.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.bean.CreditorListBean;
import com.wrmoney.administrator.plusadd.view.CreditorDetailDialog;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015/11/17.
 */
public class CreditorAdapter  extends BaseAdapter{
    private List<CreditorListBean> list;
    private Context context;
    public CreditorAdapter(List<CreditorListBean> list, Context context) {
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

    public void addAll(Collection<? extends CreditorListBean> add){
        list.addAll(add);
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.account_creditor_item, parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        final CreditorListBean  bean=list.get(position);

        if(bean!=null){
            holder.tv_offLineAgreementCd.setText(bean.getOffLineAgreementCd());
            holder.tv_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CreditorDetailDialog dialog=new CreditorDetailDialog(context,R.style.dialog);
                    dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
                    dialog.show();
                    TextView tv_offLineAgreementCd=(TextView)dialog.findViewById(R.id.tv_offLineAgreementCd);
                    TextView tv_borrowerName=(TextView)dialog.findViewById(R.id.tv_borrowerName);
                    TextView tv_borrowerIdCard=(TextView)dialog.findViewById(R.id.tv_borrowerIdCard);
                    TextView tv_creditAmount=(TextView)dialog.findViewById(R.id.tv_creditAmount);
                    TextView tv_creditCashValue=(TextView)dialog.findViewById(R.id.tv_creditCashValue);
                   // Log.i("========合同号",bean.getOffLineAgreementCd());
                    tv_offLineAgreementCd.setText(bean.getOffLineAgreementCd());
                    String name=bean.getBorrowerName();
                    String idStart=bean.getBorrowerIdCard().substring(0,5);
                    String idEnd=bean.getBorrowerIdCard().substring(16,18);
//                    tv_borrowerName.setText(bean.getBorrowerName());
//                    tv_borrowerIdCard.setText(bean.getBorrowerIdCard());
                    tv_borrowerName.setText("**"+name.charAt(name.length()-1));
                    tv_borrowerIdCard.setText(idStart+"***********"+idEnd);
                    tv_creditAmount.setText(bean.getCreditAmount());
                    tv_creditCashValue.setText(bean.getCreditCashValue());
                }
            });
        }
        return convertView;
    }

    public static class ViewHolder {
        private TextView tv_offLineAgreementCd;//原始借款合同编号
        private TextView tv_detail;//明细
        public ViewHolder(View itemView) {
            this.tv_offLineAgreementCd = (TextView) itemView.findViewById(R.id.tv_offLineAgreementCd);
            this.tv_detail=(TextView)itemView.findViewById(R.id.tv_detail);
        }
    }
}
