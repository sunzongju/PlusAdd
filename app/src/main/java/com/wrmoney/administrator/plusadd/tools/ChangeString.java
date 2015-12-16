package com.wrmoney.administrator.plusadd.tools;

/**
 * Created by Administrator on 2015/12/16.
 */
public class ChangeString {

    //把一个字符串中的大写转为小写，小写转换为大写：思路1
    public static String exChange(String str){
        StringBuffer sb = new StringBuffer();
        if(str!=null){
            for(int i=0;i<str.length();i++){
                char c = str.charAt(i);
               if(Character.isLowerCase(c)){
                    sb.append(Character.toUpperCase(c));
                }
            }
        }

        return sb.toString();
    }
}
