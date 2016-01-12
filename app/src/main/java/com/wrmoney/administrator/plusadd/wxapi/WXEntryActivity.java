package com.wrmoney.administrator.plusadd.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.ConstantsAPI;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.wrmoney.administrator.plusadd.BaseActivity;
import com.wrmoney.administrator.plusadd.R;
import com.wrmoney.administrator.plusadd.accountview.activitys.InvitationDetailActivity;
import com.wrmoney.administrator.plusadd.bean.ActivityListBean;
import com.wrmoney.administrator.plusadd.bean.InvitationBean;
import com.wrmoney.administrator.plusadd.encode.UserCenterParams;
import com.wrmoney.administrator.plusadd.tools.ActionBarSet;
import com.wrmoney.administrator.plusadd.tools.CheckNetTool;
import com.wrmoney.administrator.plusadd.tools.DES3Util;
import com.wrmoney.administrator.plusadd.tools.HttpXutilTool;
import com.wrmoney.administrator.plusadd.tools.SingleUserIdTool;
import com.wrmoney.administrator.plusadd.tools.UrlTool;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler,View.OnClickListener {
	
	private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;
	private Button gotoBtn, regBtn, launchBtn, checkBtn;
	private String app_id="wxbe11ed5198a06e5e";


	private TextView tv_invitation;
	private TextView tv_indetail;
	private String userid;
	private HttpUtils utils;
	private TextView tv_inviteCount;
	private TextView tv_backedCommsAmount;
	private TextView tv_unbackCommsAmount;
	private EditText et_invitationCode;
	private InvitationBean bean;
	private IWXAPI api;
	private String login_url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_invitation);
//		ActionBarSet.setHelpBar(this);
//		ActionBarSet.setActionBar(this);
		TextView tv_banner=(TextView)this.findViewById(R.id.tv_banner);
		tv_banner.setText("邀请好友");
		api = WXAPIFactory.createWXAPI(this, UrlTool.app_id, true);
		boolean b = api.registerApp(UrlTool.app_id);
		init();
    }
	public void init(){
		tv_invitation=(TextView)this.findViewById(R.id.tv_invitation);//邀请
		tv_invitation.setOnClickListener(this);
		tv_indetail=(TextView)this.findViewById(R.id.tv_indetail);//邀请详情
		tv_indetail.setOnClickListener(this);

		tv_inviteCount=(TextView)this.findViewById(R.id.tv_inviteCount);//邀请人数
		tv_backedCommsAmount=(TextView)this.findViewById(R.id.tv_backedCommsAmount);//已返金额
		tv_unbackCommsAmount=(TextView)this.findViewById(R.id.tv_unbackCommsAmount);//待返金额；
		et_invitationCode=(EditText)this.findViewById(R.id.et_invitationCode);//我的邀请码

		userid = SingleUserIdTool.newInstance().getUserid();
		utils = HttpXutilTool.getUtils();
		dataRequest();
	}

	/**
	 * 数据请求
	 */
	public void dataRequest(){
		Boolean b = CheckNetTool.checkNet(this);
		if(b){
			RequestParams params = UserCenterParams.getInviteCode(userid);
			utils.send(HttpRequest.HttpMethod.POST, UrlTool.resURL, params, new RequestCallBack<String>() {
				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					String result = responseInfo.result;
					JSONObject object = null;
					try {
						List<ActivityListBean> list2 = new ArrayList<ActivityListBean>();
						object = new JSONObject(result);
						String strResponse = object.getString("argEncPara");
						String strDe = DES3Util.decode(strResponse);
						Log.i("========邀请好友1", strDe);
//                    Toast.makeText(ActivityCenterActivity.this, strDe, Toast.LENGTH_SHORT).show();
						JSONObject object1 = new JSONObject(strDe);
						String str = object1.getString("intiteCodeRecord");
						login_url=object1.getString("login_url");
						JSONObject object2 = new JSONObject(str);
						if ("0000".equals(object1.getString("rescode"))) {
							bean = new InvitationBean();
							bean.setInviteCount(object2.getString("inviteCount"));
							bean.setBackedCommsAmount(object2.getString("backedCommsAmount"));
							bean.setUnbackCommsAmount(object2.getString("unbackCommsAmount"));
							bean.setInvitationCode(object2.getString("invitationCode"));
							bean.setBindInviteCode(object2.getString("bindInviteCode"));
							// Log.i("========邀请好友", "人数" + bean.getInviteCount() + "已返金额" + bean.getBackedCommsAmount());
							tv_inviteCount.setText(bean.getInviteCount());
							tv_backedCommsAmount.setText(bean.getBackedCommsAmount());
							tv_unbackCommsAmount.setText(bean.getUnbackCommsAmount());
							et_invitationCode.setText("您的邀请码："+bean.getInvitationCode());
						} else {
							// Toast.makeText(InvitationActivity.this,"请求失败",Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					//Toast.makeText(LoginActivity.this, strDe, Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onFailure(HttpException e, String s) {
					e.printStackTrace();
				}
			});
		}
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_invitation://邀请好友
				if (login_url != null) {
					showPopwindow();
//                    InviteFriendsDialog dialog = new InviteFriendsDialog(this, R.style.dialog);
//                    dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
//                    dialog.show();
//                    Intent intent0=new Intent(this,InvitationFriendActivity.class);
//                    startActivity(intent0);
					//this.setTheme(R.style.ActionSheetStyleiOS7);
					//showActionSheet();
				}
				break;
			case R.id.tv_indetail:
				if (bean != null) {
					Intent intent = new Intent(this, InvitationDetailActivity.class);
					intent.putExtra("CODE",bean.getInvitationCode());
					startActivity(intent);
				}
				break;
			default:
				break;
		}
	}

	/**
	 * 显示popupWindow
	 */
	private void showPopwindow() {
		// 利用layoutInflater获得View
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.share_pop_window, null);

		// 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

		final PopupWindow window = new PopupWindow(view,
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.WRAP_CONTENT);

		// 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
		window.setFocusable(true);


		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		window.setBackgroundDrawable(dw);


		// 设置popWindow的显示和消失动画
		window.setAnimationStyle(R.style.mypopwindow_anim_style);
		// 在底部显示
		window.showAtLocation(WXEntryActivity.this.findViewById(R.id.start),
				Gravity.BOTTOM, 0, 0);

		// 这里检验popWindow里的button是否可以点击
		Button first = (Button) view.findViewById(R.id.first);
		first.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				window.dismiss();
			}
		});
		LinearLayout ll_01=(LinearLayout)view.findViewById(R.id.ll_01);
		ll_01.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//                Intent intent=new Intent(InvitationActivity.this, WXEntryActivity.class);
//                startActivity(intent);
				share2weixin(0);
				window.dismiss();
			}
		});
		LinearLayout ll_02=(LinearLayout)view.findViewById(R.id.ll_02);
		ll_02.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				share2weixin(1);
				window.dismiss();
//                Intent intent=new Intent(InvitationActivity.this, WXEntryActivity.class);
//                startActivity(intent);
			}
		});
		//popWindow消失监听方法
		window.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
//                System.out.println("popWindow消失");
			}
		});
	}

	private void share2weixin(int flag) {
		Log.i("=======yaoqinghaoyou","2222222222222");
		// Bitmap bmp = BitmapFactory.decodeResource(getResources(),
		// R.drawable.weixin_share);
		if (!api.isWXAppInstalled()) {
			Toast.makeText(WXEntryActivity.this, "您还未安装微信客户端",
					Toast.LENGTH_SHORT).show();
			return;
		}
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = login_url;
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = "Plus0 乘10理财";
		msg.description = getResources().getString(
				R.string.reg);
		Bitmap thumb = BitmapFactory.decodeResource(getResources(),
				R.drawable.headmage_pic);
		msg.setThumbImage(thumb);
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = String.valueOf(System.currentTimeMillis());
		req.message = msg;
		req.scene = flag;
		api.sendReq(req);
		api.handleIntent(getIntent(), this);

	}
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		Log.i("=======yaoqinghaoyou", "233333333333333");
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq baseReq) {
		switch (baseReq.getType()) {
			case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
				//goToGetMsg();
				break;
			case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
				//goToShowMsg((ShowMessageFromWX.Req) baseReq);
				break;
			default:
				break;
		}
	}
	@Override
	public void onResp(BaseResp baseResp) {
		Log.i("=======yaoqinghaoyou","11111111111111");
		String result = "";
		switch (baseResp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				result ="发送成功";
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				result = "发送取消";
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				result ="发送拒绝";
				break;
			default:
				result = "发送返回";
				break;
		}
		Toast.makeText(this, result, Toast.LENGTH_LONG).show();
	}
}