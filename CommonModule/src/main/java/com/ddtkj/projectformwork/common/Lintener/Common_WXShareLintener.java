package com.ddtkj.projectformwork.common.Lintener;

import android.os.Handler;

import com.ddtkj.projectformwork.common.Base.Common_Application;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.EventBus.Common_Share_EventBus;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.utlis.lib.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import mvpdemo.com.unmeng_share_librarys.ShareCode;
import mvpdemo.com.unmeng_share_librarys.ShareLintener;

/**
 * Created by Administrator on 2017/7/10.
 */

public class Common_WXShareLintener implements ShareLintener {
    @Override
    public void onResult(SHARE_MEDIA platform, final ShareCode shareCode) {
        switch(shareCode){
            case SHARE_FAVORITE:
                //收藏成功啦
                ToastUtils.RightImageToast(Common_Application.getApplication().getApplicationContext(),"收藏成功啦");
                break;
            case SHARE_SUCCESS:
                //分享成功啦
                ToastUtils.RightImageToast(Common_Application.getApplication().getApplicationContext(),"分享成功啦");
                break;
            case SHARE_FAIL:
                //分享失败啦
                ToastUtils.ErrorImageToast(Common_Application.getApplication().getApplicationContext(),"分享失败啦");
                break;
            case SHARE_CANCLE:
                //分享取消了
                ToastUtils.WarnImageToast(Common_Application.getApplication().getApplicationContext(),"分享取消了");
                break;
        }
        if(platform==SHARE_MEDIA.WEIXIN_CIRCLE||platform==SHARE_MEDIA.WEIXIN){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //这里发送粘性事件
                    EventBus.getDefault().post(new Common_Share_EventBus(shareCode));
                }
            }, 300);
        }
    }
}
