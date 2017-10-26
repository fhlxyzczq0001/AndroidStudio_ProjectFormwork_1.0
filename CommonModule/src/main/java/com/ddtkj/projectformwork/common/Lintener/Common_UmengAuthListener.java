package com.ddtkj.projectformwork.common.Lintener;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

import com.ddtkj.projectformwork.common.Base.Common_Application;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.EventBus.Common_UmengAuth_EventBus;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.EventBus.Common_UmengDeleteAuth_EventBus;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.SocializeUtils;
import com.utlis.lib.L;
import com.utlis.lib.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import static mvpdemo.com.unmeng_share_librarys.ShareCode.SHARE_CANCLE;
import static mvpdemo.com.unmeng_share_librarys.ShareCode.SHARE_FAIL;
import static mvpdemo.com.unmeng_share_librarys.ShareCode.SHARE_SUCCESS;

/**友盟授权监听
 * @author: Administrator 杨重诚
 * @date: 2016/11/15:15:05
 */

public class Common_UmengAuthListener {
    Common_Application mCommonApplication;
    Common_UmengAuth_EventBus mUmengAuth_eventBus;
    Common_UmengDeleteAuth_EventBus mUmengDeleteAuth_eventBus;
    private ProgressDialog dialog;
    public Common_UmengAuthListener(Context context){
        mCommonApplication=Common_Application.getInstance();
        dialog=new ProgressDialog(context);
    }
    /** auth callback interface**/
    public UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            //授权开始的回调
            SocializeUtils.safeShowDialog(dialog);
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            for (String key : data.keySet()) {
                 L.e("xxxxxx key = "+key+"    value= "+data.get(key));
            }
            //授权成功
            mUmengAuth_eventBus=new Common_UmengAuth_EventBus(platform,action,data,SHARE_SUCCESS);
            ToastUtils.RightImageToast(mCommonApplication.getApplication(),"授权成功",300);
            sendEventBus(mUmengAuth_eventBus);
            SocializeUtils.safeCloseDialog(dialog);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            //错误
            mUmengAuth_eventBus=new Common_UmengAuth_EventBus(platform,action,SHARE_FAIL);
            ToastUtils.ErrorImageToast(mCommonApplication.getApplication(),"授权错误");
            sendEventBus(mUmengAuth_eventBus);
            SocializeUtils.safeCloseDialog(dialog);
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            //取消
            mUmengAuth_eventBus=new Common_UmengAuth_EventBus(platform,action,SHARE_CANCLE);
            ToastUtils.WarnImageToast(mCommonApplication.getApplication(),"授权取消了");
            sendEventBus(mUmengAuth_eventBus);
            SocializeUtils.safeCloseDialog(dialog);
        }
    };
    /** delauth callback interface**/
    public UMAuthListener umdelAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            //授权开始的回调
            SocializeUtils.safeShowDialog(dialog);
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            mUmengDeleteAuth_eventBus=new Common_UmengDeleteAuth_EventBus(platform,action,data,SHARE_SUCCESS);
            ToastUtils.RightImageToast(mCommonApplication.getApplication(),"解绑授权成功");
            sendEventBus(mUmengDeleteAuth_eventBus);
            SocializeUtils.safeCloseDialog(dialog);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            mUmengDeleteAuth_eventBus=new Common_UmengDeleteAuth_EventBus(platform,action,SHARE_FAIL);
            ToastUtils.ErrorImageToast(mCommonApplication.getApplication(),"解绑授权错误");
            sendEventBus(mUmengDeleteAuth_eventBus);
            SocializeUtils.safeCloseDialog(dialog);
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            mUmengDeleteAuth_eventBus=new Common_UmengDeleteAuth_EventBus(platform,action,SHARE_CANCLE);
            ToastUtils.WarnImageToast(mCommonApplication.getApplication(),"解绑授权取消了");
            sendEventBus(mUmengDeleteAuth_eventBus);
            SocializeUtils.safeCloseDialog(dialog);
        }
    };

    private void sendEventBus(final Object o){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //这里发送粘性事件
                EventBus.getDefault().post(o);
            }
        }, 300);
    }

}
