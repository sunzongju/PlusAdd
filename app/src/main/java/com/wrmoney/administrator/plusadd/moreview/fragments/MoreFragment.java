package com.wrmoney.administrator.plusadd.moreview.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
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
import com.wrmoney.administrator.plusadd.moreview.activitys.AlterPassActivity;
import com.wrmoney.administrator.plusadd.tools.CutBitmap;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.SingleUserIdTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        btn_finish=(Button)view.findViewById(R.id.btn_finish);//退出
        btn_finish.setOnClickListener(this);
        iv_photo = (ImageView)view.findViewById(R.id.iv_photo);//更换头像
        iv_photo.setOnClickListener(this);
        tv_mobile = (TextView) view.findViewById(R.id.tv_mobile);
        tv_idCard = (TextView) view.findViewById(R.id.tv_idCard);
        tv_invitCode = (TextView) view.findViewById(R.id.tv_invitCode);
        userid = SingleUserIdTool.newInstance().getUserid();
        utils = HttpXutilTool.getUtils();
       // userId = SingleUserId.newInstance().getUserid();
        RequestParams params= SetUpParams.getMysetCode(userid);
//        utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                String result = responseInfo.result;
//                try {
//                    JSONObject obj = new JSONObject(result);
//                    String strResponse = obj.getString("argEncPara");
//                    String strDe = DES3Util.decode(strResponse);
//                    Toast.makeText(activity, strDe, Toast.LENGTH_SHORT).show();
//                    JSONObject obj2 = new JSONObject(strDe);
//
//                    String mobile = obj2.getString("mobile");//手机号
//                    tv_mobile.setText(mobile);
//                    if (obj2.has("idCard")) {
//                        String idCard = obj2.optString("idCard");//身份证
//                        if (idCard.length() > 0) {
//                            tv_idCard.setText(idCard);
//                        }
//                    }
//                    String invitCode = obj2.getString("invitCode");//邀请码
//                    if (invitCode != null && !invitCode.equals("")) {
//                        tv_invitCode.setText(invitCode);
//                    }
//                    //obj2.getString("idCard");//身份证
////                            Toast.makeText(AlterPassActivity.this,"请求失败",Toast.LENGTH_SHORT).show();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//                //e.getExceptionCode();
//                e.printStackTrace();
//                Toast.makeText(activity, "请求成功", Toast.LENGTH_SHORT).show();
//            }
//        });
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

        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");//从Sd中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
//            Drawable drawable = new BitmapDrawable(bt);//转换成drawable
//            iv_photo.setImageDrawable(drawable);
                    Bitmap bitmap = CutBitmap.cutImage(bt);
            iv_photo.setImageBitmap(bitmap);//用ImageView显示出来
        } else {
            /**
             *	如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
             *
             */
        }
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
        callback.setActionBar(3);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_alterpass://修改登录密码
                Intent intent2 = new Intent(activity, AlterPassActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_finish://退出程序
                SingleUserIdTool.newInstance().setUserid(null);
                Intent intent = new Intent(activity, CommnActivity.class);
                startActivity(intent);
                activity.finish();
                break;

            case R.id.iv_photo://更换头像
                activity.setTheme(R.style.ActionSheetStyleiOS7);
                showActionSheet();
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
