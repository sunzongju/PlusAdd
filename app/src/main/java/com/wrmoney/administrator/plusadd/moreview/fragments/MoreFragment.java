package com.wrmoney.administrator.plusadd.moreview.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.actionsheet.ActionSheet;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wrmoney.administrator.plusadd.BaseFragment;
import com.wrmoney.administrator.plusadd.CommnActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.allinterface.FragmentCallback;
import com.wrmoney.administrator.plusadd.encode.SetUpParams;
import com.wrmoney.administrator.plusadd.moreview.activitys.AlterBindActivity;
import com.wrmoney.administrator.plusadd.moreview.activitys.AlterPassActivity;
import com.wrmoney.administrator.plusadd.moreview.activitys.BindCodeActivity;
import com.wrmoney.administrator.plusadd.moreview.activitys.ConnectOurActivity;
import com.wrmoney.administrator.plusadd.moreview.activitys.HelpCenterActivity;
import com.wrmoney.administrator.plusadd.tools.CutBitmap;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.SingleUserIdTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;
import com.wrmoney.administrator.plusadd.view.CheckVersionDialog;
import com.wrmoney.administrator.plusadd.view.QuitLoginDialog;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by Administrator on 2015/11/2.
 */
public class MoreFragment extends BaseFragment implements View.OnClickListener,ActionSheet.ActionSheetListener{

    private static String path = "/sdcard/myHead/";//sd路径
    private FragmentCallback callback;
    private RequestParams params;
    private View view;
    private TextView tv_mobile;
    private TextView tv_idCard;
    private TextView tv_invitCode;
    private String userid;
    private HttpUtils utils;
    private FragmentActivity activity;
    private String userId;
    private ImageView btn_alterpass;
    private ImageView iv_photo;
    private Button btn_finish;
    private TextView tv_help;
    private TextView tv_update;
    private TextView tv_connect;
    private TextView tv_Certificate;
    private ImageView iv_bind;
    private TextView tv_bind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Log.i("=======","MoreCreateView");
        view=inflater.inflate(R.layout.fragment_more,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        params = new RequestParams();
        initView();
    }

    private void initView() {
        //初始化控件
        activity=getActivity();
        btn_alterpass=(ImageView)view.findViewById(R.id.btn_alterpass);//修改密码
        btn_alterpass.setOnClickListener(this);
        tv_update=(TextView)view.findViewById(R.id.tv_update);
        tv_update.setOnClickListener(this);
        btn_finish=(Button)view.findViewById(R.id.btn_finish);//退出
        btn_finish.setOnClickListener(this);
        iv_photo = (ImageView)view.findViewById(R.id.iv_photo);//更换头像
       // iv_photo.setOnClickListener(this);
        Bitmap bt2 = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        Bitmap bitmap2 = CutBitmap.cutImage(bt2);
        iv_photo.setImageBitmap(bitmap2);//用ImageView显示出来

        tv_bind=(TextView)view.findViewById(R.id.tv_bind);//
        iv_bind=(ImageView)view.findViewById(R.id.iv_bind);//修改绑定的邀请码
        iv_bind.setOnClickListener(this);
        tv_help=(TextView)view.findViewById(R.id.tv_help);//帮助中心
        tv_help.setOnClickListener(this);
        tv_connect=(TextView)view.findViewById(R.id.tv_connect);//联系我们
        tv_connect.setOnClickListener(this);
        tv_mobile = (TextView) view.findViewById(R.id.tv_mobile);
        tv_idCard = (TextView) view.findViewById(R.id.tv_idCard);
        tv_Certificate=(TextView)view.findViewById(R.id.tv_Certificate);
        tv_invitCode = (TextView) view.findViewById(R.id.tv_invitCode);
        userid = SingleUserIdTool.newInstance().getUserid();
        utils = HttpXutilTool.getUtils();
        userId = SingleUserIdTool.newInstance().getUserid();

//        btn_news=(Button)this.findViewById(R.id.btn_news);
//        btn_activity=(Button)this.findViewById(R.id.btn_activity);
//        btn_red=(Button)this.findViewById(R.id.btn_red);
//        badge1 = new BadgeView(this, btn_news);
//        badge1.setText("15");
//        badge1.setBadgePosition(BadgeView.POSITION_BOTTOM_LEFT);
//        badge1.setTextColor(Color.WHITE);
//        badge1.setBadgeBackgroundColor(Color.RED);
//        badge1.setBadgeMargin(20, 25); //ˮ
//        badge1.setTextSize(12);
//        badge1.toggle();

//        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");//从Sd中找头像，转换成Bitmap
//        if (bt != null) {
//            @SuppressWarnings("deprecation")
////            Drawable drawable = new BitmapDrawable(bt);//转换成drawable
////            iv_photo.setImageDrawable(drawable);
//                    Bitmap bitmap = CutBitmap.cutImage(bt);
//            iv_photo.setImageBitmap(bitmap);//用ImageView显示出来
//        } else {
//            /**
//             *	如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
//             *
//             */
//        }
    }

    public static MoreFragment newInstance(){
        MoreFragment fragment=new MoreFragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callback = (FragmentCallback) activity;
    }

    @Override
    public void onResume() {
        super.onResume();
       // Log.i("onResume", "======111111");
        RequestParams params= SetUpParams.getMysetCode(userid);
        utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                try {
                    JSONObject obj = new JSONObject(result);
                    String strResponse = obj.getString("argEncPara");
                    String strDe = DES3Util.decode(strResponse);
                    //Toast.makeText(activity, strDe, Toast.LENGTH_SHORT).show();
                    // Log.i("========更多",strDe);;
                    JSONObject obj2 = new JSONObject(strDe);

                    String mobile = obj2.getString("mobile");//手机号
                    String idcardValidate = obj2.getString("idcardValidate");//是否认证
                    tv_mobile.setText(mobile);
                    if ("Y".equals(idcardValidate)) {
                        tv_Certificate.setText("(已认证)");
                    } else {
                        tv_Certificate.setText("(未认证)");
                    }
                    if (obj2.has("idCard")) {
                        String idCard = obj2.optString("idCard");//身份证
                        if (idCard.length() > 0) {
                            tv_idCard.setText(idCard);
                        }
                    }
                    String invitCode = obj2.getString("invitCode");//邀请码
                    if (invitCode != null && !invitCode.equals("")) {
                        tv_invitCode.setText(invitCode);
                    }
                    String bindCode = obj2.getString("bindCode");//我绑定的邀请码
                    if (bindCode != null && !bindCode.equals("")) {
                        tv_bind.setText(bindCode);
                    }
                    //obj2.getString("idCard");//身份证
//                            Toast.makeText(AlterPassActivity.this,"请求失败",Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                //e.getExceptionCode();
                e.printStackTrace();
                //Toast.makeText(activity, "请求成功", Toast.LENGTH_SHORT).show();
            }
        });
        callback.setActionBar(3);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_alterpass://修改登录密码
                Intent intent2 = new Intent(activity, AlterPassActivity.class);
                startActivity(intent2);
                break;
            case R.id.tv_update:
//                PackageManager pm = getS
//                PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);//getPackageName()是你当前类的包名，0代表是获取版本信息
//                String name = pi.versionName;
//                int code = pi.versionCode;

                CheckVersionDialog dialog1=new CheckVersionDialog(activity,R.style.dialog);
                dialog1.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
                dialog1.show();
                break;
            case R.id.btn_finish://退出程序

                final QuitLoginDialog  dialog2=new QuitLoginDialog(activity,R.style.dialog);
                dialog2.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
                dialog2.show();
                Button btn_ok=(Button)dialog2.findViewById(R.id.btn_ok);
                Button btn_cancle=(Button)dialog2.findViewById(R.id.btn_cancle);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SingleUserIdTool.newInstance().setUserid(null);
                        Intent intent = new Intent(activity, CommnActivity.class);
                        startActivity(intent);
                        activity.finish();
                    }
                });
                btn_cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog2.dismiss();
                    }
                });
                break;

            case R.id.iv_photo://更换头像
                activity.setTheme(R.style.ActionSheetStyleiOS7);
                showActionSheet();
                break;
            case R.id.iv_bind:
                String str=tv_bind.getText().toString();
                Intent intent4=new Intent(activity, AlterBindActivity.class);
                if(!"".equals(str)&&str!=null){
                    intent4.putExtra("CODE",str);
                }
                intent4.putExtra("INVIT",tv_invitCode.getText().toString());
                startActivity(intent4);
                break;
            case R.id.tv_help://帮助中心
                Intent intent1=new Intent(activity, HelpCenterActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_connect://联系我们
               Intent intent3=new Intent(activity, ConnectOurActivity.class);
                startActivity(intent3);
            default:
                break;

        }
    }
    public void showActionSheet() {
        ActionSheet.createBuilder(activity, activity.getSupportFragmentManager())
                .setCancelButtonTitle("Cancel")
                .setOtherButtonTitles("从相册取", "手机拍照")
                .setCancelableOnTouchOutside(true).setListener(this).show();
    }

    @Override
    public void onDismiss(ActionSheet actionSheet, boolean b) {

    }

    @Override
    public void onOtherButtonClick(ActionSheet actionSheet, int i) {
        switch (i) {
            case 0://从相册里面取图片
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                activity.startActivityForResult(intent1, 1);
                break;
            case 1://手机拍照
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                        "head.jpg")));
                activity.startActivityForResult(intent2, 2);//采用ForResult打开
                break;

        }
    }




}
