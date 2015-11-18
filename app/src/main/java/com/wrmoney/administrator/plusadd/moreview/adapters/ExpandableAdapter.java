package com.wrmoney.administrator.plusadd.moreview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.wrmoney.administrator.plusadd.R;

/**
 * Created by Administrator on 2015/11/17.
 */
public class ExpandableAdapter extends BaseExpandableListAdapter {
    public String[] groups={"Plus0是一家怎样的网站？","Plus0管理团队实力如何？","Plus0如何保证投资者的本金和收益安全？",
            "Plus0平台提供居间撮合服务的合法性？","融资方出现违约、坏账怎么办？"};
    public String[][] childrens={{"内容内容内容内容内容内容内容内容内容内容内容内容Plus0如何保证投资者的本金和收益安全"},
            {"内容内容内容内容内容内容内容内容内容内容内容内容Plus0如何保证投资者的本金和收益安全"},
            {"内容内容内容内容内容内容内容内容内容内容内容内容Plus0如何保证投资者的本金和收益安全"},
            {"内容内容内容内容内容内容内容内容内容内容内容内容Plus0如何保证投资者的本金和收益安全"},
            {"内容内容内容内容内容内容内容内容内容内容内容内容Plus0如何保证投资者的本金和收益安全"}};
    private Context context;

    public ExpandableAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return groups.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childrens[groupPosition].length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childrens[groupPosition][childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//        TextView textView = getGenericView();
//        textView.setText(getGroup(groupPosition).toString());
//        LinearLayout parentLayout=(LinearLayout) View.i
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.more_help_parent,parent,false);
            convertView.setTag(new ViewHolderp(convertView));
        }
        ViewHolderp holder = (ViewHolderp) convertView.getTag();
        holder.tv_title.setText(groups[groupPosition]);
//        TextView parentTextView=(TextView) convertView.findViewById(R.id.parent_textview);
//        parentTextView.setText(groups[groupPosition]);
//        ImageView parentImageViw = (ImageView) convertView.findViewById(R.id.iv_pic);
//        if(isExpanded){
//           parentImageViw.setBackgroundResource(R.drawable.account_01);
//
//        }else{
//            parentImageViw.setBackgroundResource(R.drawable.account_02);
//
//        }
        return convertView;
    }


    public static  class ViewHolderp{
        private TextView tv_title;

        public ViewHolderp(View itemView) {
            this.tv_title = (TextView) itemView.findViewById(R.id.parent_textview);
        }
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.more_help_children,parent,false);
            convertView.setTag(new ViewHolder2(convertView));
        }
        ViewHolder2 holder = (ViewHolder2) convertView.getTag();
        holder.tv_content.setText(childrens[groupPosition][childPosition]);
        return convertView;
    }
    public static class ViewHolder2{
        private TextView tv_content;
        public ViewHolder2(View itemView) {
            this.tv_content = (TextView) itemView.findViewById(R.id.second_textview);
        }
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
