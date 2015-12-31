package com.wrmoney.administrator.plusadd.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.ConstantsAPI;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.wrmoney.administrator.plusadd.R;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;
	private Button gotoBtn, regBtn, launchBtn, checkBtn;
    private IWXAPI api;
	private String app_id="wxbe11ed5198a06e5e";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weixin_share);
		// 微信注册初始化
		api = WXAPIFactory.createWXAPI(this, "wx387db71dad0fb42c", true);
		boolean b = api.registerApp("wx387db71dad0fb42c");
		//Toast.makeText(this,b+"",Toast.LENGTH_SHORT).show();
		share2weixin(1);
		//分享消息
		regBtn = (Button) findViewById(R.id.btn_share);
		regBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 将该app注册到微信
				share2weixin(1);
			}
		});
    }
	private void share2weixin(int flag) {
		// Bitmap bmp = BitmapFactory.decodeResource(getResources(),
		// R.drawable.weixin_share);
		if (!api.isWXAppInstalled()) {
			Toast.makeText(WXEntryActivity.this, "您还未安装微信客户端",
					Toast.LENGTH_SHORT).show();
			return;
		}
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = "http://baidu.com";
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = "title";
		msg.description = getResources().getString(
				R.string.reg);
		Bitmap thumb = BitmapFactory.decodeResource(getResources(),
				R.drawable.abc_ab_share_pack_mtrl_alpha);
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
		Log.i("=========",result);
		Toast.makeText(this, result, Toast.LENGTH_LONG).show();
	}
}