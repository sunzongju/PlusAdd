package com.wrmoney.administrator.plusadd;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wrmoney.administrator.plusadd.accountview.fragments.AccountFragment;
import com.wrmoney.administrator.plusadd.allinterface.FragmentCallback;
import com.wrmoney.administrator.plusadd.financingview.fragments.FinancingFragment;
import com.wrmoney.administrator.plusadd.homeview.fragments.HomeFrament;
import com.wrmoney.administrator.plusadd.loginview.activitys.PhoneActivity;
import com.wrmoney.administrator.plusadd.moreview.fragments.MoreFragment;
import com.wrmoney.administrator.plusadd.tools.CutBitmap;
import com.wrmoney.administrator.plusadd.tools.SingleUserIdTool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2015/11/2.
 */
public class CommnActivity extends BaseActivity implements View.OnClickListener,FragmentCallback {
    private String userid;
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
    private RadioButton radio_home;
    private RadioButton radio_financing;
    private RadioButton radio_account;
    private RadioButton radio_more;
    private RadioButton radiotype;
    private Fragment mContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commn);

//        drawables = getCompoundDrawables();
//        drawables[1].setBounds();
//        setCompoundDrawables(drawables[0],drawables[1],drawables[2].drawables[3]);

        userid = SingleUserIdTool.newInstance().getUserid();
        radio_home = ((RadioButton) findViewById(R.id.radio_home));
        //radio_home.setCompoundDrawables(10,10,10,10);
        radiotype=radio_home;
        radio_financing = ((RadioButton) findViewById(R.id.radio_financing));
        radio_account = ((RadioButton) findViewById(R.id.radio_account));
        radio_more = ((RadioButton) findViewById(R.id.radio_more));
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
            transaction2.add(R.id.frag_container,homeFragment);
            mContent=homeFragment;
            transaction2.commit();
        }else{
//            homeFragment=getFragmentManager().findFragmentById(R.id.)
//            getSupportFragmentManager().beginTransaction().show()

        }

    }

    @Override
    public void onClick(View v) {
       // FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
        switch (v.getId()){
            case R.id.radio_home:
                radiotype=radio_home;
//                if(!homeFragment.isAdded()){
//                    transaction2.add(R.id.frag_container, homeFragment);}
//                else {
//                    transaction2.show(homeFragment);
//                    transaction2.hide(finacingFragment);
//                    transaction2.hide(accountFragment);
//                    transaction2.hide(moreFragment);
//                }
                switchContent(mContent,homeFragment);
                //transaction2.commit();
                break;
            case R.id.radio_financing:
                radiotype=radio_financing;
                switchContent(mContent,finacingFragment);
//                if (!finacingFragment.isAdded()){
//                    transaction2.add(R.id.frag_container, finacingFragment);
//                }
//                else {
//                    transaction2.show(finacingFragment);
//                    transaction2.hide(homeFragment);
//                    transaction2.hide(accountFragment);
//                    transaction2.hide(moreFragment);
//                }
                //transaction2.commit();
                break;
            case R.id.radio_account:
                if (userid == null) {
                    radiotype.setChecked(true);
                    radio_account.setChecked(false);
                    Intent intent00 = new Intent(this, PhoneActivity.class);
                    startActivityForResult(intent00,100);
                   // startActivity(intent00);
                } else {
                    radiotype=radio_account;
                    switchContent(mContent,accountFragment);
//                    if (!accountFragment.isAdded()){
//                        transaction2.add(R.id.frag_container, accountFragment);
//                    }
//                    else {
//                        transaction2.show(accountFragment);
//                        transaction2.hide(finacingFragment);
//                        transaction2.hide(homeFragment);
//                        transaction2.hide(moreFragment);
//                    }
                  //  transaction2.commit();
                }
                break;
            case R.id.radio_more:
                if (userid == null) {
                    radiotype.setChecked(true);
                    radio_more.setChecked(false);
                    Intent intent00 = new Intent(this, PhoneActivity.class);
                    startActivityForResult(intent00,200);
                    //startActivity(intent00);
                } else {
                    radiotype=radio_more;
                    switchContent(mContent,moreFragment);
//                    if (!moreFragment.isAdded()){
//                        transaction2.add(R.id.frag_container, moreFragment);
//                    }
//                    else {
//                        transaction2.show(moreFragment);
//                        transaction2.hide(finacingFragment);
//                        transaction2.hide(homeFragment);
//                        transaction2.hide(accountFragment);
//                    }
                   // transaction2.commit();
                }
                break;
            default:
                break;
        }
    }

    public void switchContent(Fragment from,Fragment to){
         if(mContent!=to){
             mContent=to;
             FragmentTransaction transaction3=getSupportFragmentManager().beginTransaction();
             if(!to.isAdded()){
                 transaction3.hide(from).add(R.id.frag_container,to).commit();
             }else{
                 transaction3.hide(from).show(to).commit();
             }
         }
    }
//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
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
        Toast.makeText(this, "手机拍照", Toast.LENGTH_SHORT).show();
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this,"相册",Toast.LENGTH_SHORT).show();
                    cropPhoto(data.getData());//裁剪图片
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this,"手机拍照",Toast.LENGTH_SHORT).show();
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
