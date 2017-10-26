package com.ddtkj.projectformwork.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.utlis.lib.L;

import org.greenrobot.eventbus.EventBus;

/**
 * 微信支付回调界面
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	api = WXAPIFactory.createWXAPI(this, "wxe1fc40621bc879b9");
        api.handleIntent(getIntent(), this);
    }

//	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(final BaseResp resp) {
		L.d(TAG, "onPayFinish, errCode = " + resp.errCode);
		new Handler().post(new Runnable() {
			@Override
			public void run() {
				//这里发送粘性事件
				EventBus.getDefault().post(resp);
			}
		});
		this.finish();
	}
}