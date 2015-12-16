package com.wrmoney.administrator.plusadd;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.wrmoney.administrator.plusadd.accountview.fragments.AccountFragment;
import com.wrmoney.administrator.plusadd.allinterface.FragmentCallback;
import com.wrmoney.administrator.plusadd.encode.UserCenterParams;
import com.wrmoney.administrator.plusadd.financingview.fragments.FinancingFragment;
import com.wrmoney.administrator.plusadd.homeview.fragments.HomeFrament;
import com.wrmoney.administrator.plusadd.loginview.activitys.PhoneActivity;
import com.wrmoney.administrator.plusadd.moreview.fragments.MoreFragment;
import com.wrmoney.administrator.plusadd.tools.CutBitmap;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.SingleUserIdTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;
import com.wrmoney.administrator.plusadd.view.BadgeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2015/11/2.
 */
public class CommnActivity extends BaseActivity implements View.OnClickListener,FragmentCallback {
    private FragmentTransaction transaction2;
    private HomeFrament homeFragment;
    private AccountFragment accountFragment;
    private MoreFragment moreFragment;

    private FinancingFragment finacingFragment;
    private android.support.v7.app.ActionBar actionBar;
    private ImageView iv_photo;
    private Bitmap head;//头像Bitmap
    private static String path = "/sdcard/myHead/";//sd路径
    private int checkId;
    private RadioGroup rg_menu;
//    private RadioButton radio_home;
//    private RadioButton radio_financing;
//    private RadioButton radio_account;
//    private RadioButton radio_more;
//    private RadioButton radiotype;
    private Fragment mContent;
    private String userid;
    private HttpUtils utils;
    private BadgeView badge1;
    private TextView tv_banner;
    private CheckBox radio_home;
    private CheckBox radio_financing;
    private CheckBox radio_account;
    private CheckBox radio_more;
    private CheckBox radiotype;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commn);
        tv_banner=(TextView)this.findViewById(R.id.tv_banner);
        tv_banner.setText("Plus0乘10理财");
        ImageView iv_return=(ImageView)this.findViewById(R.id.iv_return);
        iv_return.setVisibility(View.GONE);
        rg_menu=(RadioGroup)findViewById(R.id.rg_menu);
        radio_home = ((CheckBox) findViewById(R.id.radio_home));
        //radio_home.setCompoundDrawables(10,10,10,10);
        radiotype=radio_home;
        radio_financing = ((CheckBox) findViewById(R.id.radio_financing));
        radio_account = ((CheckBox) findViewById(R.id.radio_account));
        radio_more = ((CheckBox) findViewById(R.id.radio_more));

        radio_home.setOnClickListener(this);
        radio_financing.setOnClickListener(this);
        radio_account.setOnClickListener(this);
        radio_more.setOnClickListener(this);
        homeFragment=(HomeFrament)HomeFrament.newInstance();
        finacingFragment =  (FinancingFragment) FinancingFragment.newInstance();
        accountFragment =  (AccountFragment) AccountFragment.newInstance();
        moreFragment =  (MoreFragment) MoreFragment.newInstance();
//        if(radio_home.isChecked()){
        if(savedInstanceState==null){
            transaction2 = getSupportFragmentManager().beginTransaction();
//            transaction2.add(R.id.frag_container,finacingFragment).hide(finacingFragment);
//            transaction2.add(R.id.frag_container,accountFragment).hide(accountFragment);
//            transaction2.add(R.id.frag_container,moreFragment).hide(accountFragment);
            transaction2.add(R.id.frag_container, homeFragment);
            mContent=homeFragment;
            transaction2.commit();
        }else{
//            homeFragment=getFragmentManager().findFragmentById(R.id.)
//            getSupportFragmentManager().beginTransaction().show()
        }
        init();
    }

    public void init(){
        utils = HttpXutilTool.getUtils();
        userid = SingleUserIdTool.newInstance().getUserid();
        if(userid!=null){
            badge1 = new BadgeView(this, radio_account);//创建一个BadgeView对象，view为你需要显示提醒信息的控件
            badge1.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);//显示的位置.中间，还有其他位置属性
            badge1.setTextColor(Color.WHITE);  //文本颜色
            badge1.setBadgeBackgroundColor(Color.RED); //背景颜色
            badge1.setTextSize(12); //文本大小
            badge1.setBadgeMargin(0, 0); //水平和竖直方向的间距
            badge1.toggle();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(userid!=null){
            RequestParams params = UserCenterParams.getUpdateCode(userid);
            utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    String result = responseInfo.result;
                    JSONObject object = null;
                    try {
                        object = new JSONObject(result);
                        String strResponse = object.getString("argEncPara");
                        String strDe = DES3Util.decode(strResponse);
                        JSONObject obj2 = new JSONObject(strDe);
                        String len = obj2.getString("newMsgNum");
                        int tag = Integer.parseInt(len);
                        if (tag > 0) {
                            if (tag > 99) {
                                badge1.setText("99+");
                            } else {
                                badge1.setText(len); //显示类容
                            }
                        } else {
                            badge1.hide();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {

                }
            });
        }
    }

    @Override
    public void onClick(View v) {
       // FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
        switch (v.getId()){
            case R.id.radio_home:
                radiotype=radio_home;

                radio_home.setChecked(true);
                radio_financing.setChecked(false);
                radio_account.setChecked(false);
                radio_more.setChecked(false);

                switchContent(mContent, homeFragment);
                //setCurrentItemt(mContent,homeFragment);
                tv_banner.setText("Plus0乘10理财");

                //transaction2.commit();
                break;
            case R.id.radio_financing:
                radiotype=radio_financing;

                radio_home.setChecked(false);
                radio_financing.setChecked(true);
                radio_account.setChecked(false);
                radio_more.setChecked(false);

                switchContent(mContent, finacingFragment);
                //setCurrentItemt(mContent,finacingFragment);
                tv_banner.setText("投资列表");
                break;
            case R.id.radio_account:
              String  userid1 = SingleUserIdTool.newInstance().getUserid();
                if (userid1 == null)
                {
                    radiotype.setChecked(true);
                    radio_account.setChecked(false);
                    Intent intent00 = new Intent(this, PhoneActivity.class);
                    startActivityForResult(intent00,100);

                   // startActivity(intent00);
                } else {
                    radiotype=radio_account;

                    radio_home.setChecked(false);
                    radio_financing.setChecked(false);
                    radio_account.setChecked(true);
                    radio_more.setChecked(false);

                    switchContent(mContent, accountFragment);
                    //setCurrentItemt(mContent,accountFragment);
                    tv_banner.setText("账户中心");
                }
                break;
            case R.id.radio_more:
                String  userid2 = SingleUserIdTool.newInstance().getUserid();
                if (userid2 == null) {
                    radiotype.setChecked(true);
                    radio_more.setChecked(false);
                    Intent intent00 = new Intent(this, PhoneActivity.class);
                    startActivityForResult(intent00,200);
                    //startActivity(intent00);
                } else {
                    radiotype=radio_more;
                    radio_home.setChecked(false);
                    radio_financing.setChecked(false);
                    radio_account.setChecked(false);
                    radio_more.setChecked(true);

                    switchContent(mContent, moreFragment);
                    //setCurrentItemt(mContent,moreFragment);
                    TextView tv_banner=(TextView)this.findViewById(R.id.tv_banner);
                    tv_banner.setText("我的设置");
                    ImageView iv_answer=(ImageView)this.findViewById(R.id.iv_answer);
                    iv_answer.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                break;
        }
    }

    public void switchContent(Fragment from,Fragment to){
        FragmentTransaction transaction3=getSupportFragmentManager().beginTransaction();

         if(from!=to){
             mContent=to;
             if(from==accountFragment||from==moreFragment){
                 transaction3.remove(from);
                 if(!to.isAdded()){
                     transaction3.add(R.id.frag_container, to).commit();
                 }else{
                     transaction3.show(to).commit();
                 }
             }else {
                 if(!to.isAdded()){
                     transaction3.hide(from).add(R.id.frag_container, to).commit();
                 }else{
                     transaction3.hide(from).show(to).commit();
                 }

             }
         }
    }

//    public  void setCurrentItemt(Fragment from,Fragment to){
//        if(mContent!=to){
//            mContent=to;
//            FragmentTransaction transaction3=getSupportFragmentManager().beginTransaction();
//            if(!to.isAdded()){
//                transaction3.hide(from).add(R.id.frag_container,to).commit();
//            }else{
//                transaction3.hide(from).show(to).commit();
//            }
//        }
//    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

    @Override
    public void setActionBar(int i) {
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        iv_photo=(ImageView)findViewById(R.id.iv_photo);
       // Toast.makeText(this, "手机拍照", Toast.LENGTH_SHORT).show();
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                  //  Toast.makeText(this,"相册",Toast.LENGTH_SHORT).show();
                    cropPhoto(data.getData());//裁剪图片
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                 //   Toast.makeText(this,"手机拍照",Toast.LENGTH_SHORT).show();
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));//裁剪图片
                }
                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);//保存在SD卡中
                        Bitmap bitmap = CutBitmap.cutImage(head);
                        iv_photo.setImageBitmap(bitmap);//用ImageView显示出来
//                       iv_photo.setImageResource(R.drawable.u2);
                    }
                }
                break;
            default:
                break;
        }
        //super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "head.jpg";//图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
