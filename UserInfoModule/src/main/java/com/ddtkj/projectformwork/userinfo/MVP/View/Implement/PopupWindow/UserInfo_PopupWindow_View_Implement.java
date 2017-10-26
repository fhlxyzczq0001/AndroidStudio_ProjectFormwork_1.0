package com.ddtkj.projectformwork.userinfo.MVP.View.Implement.PopupWindow;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

import com.ddtkj.projectformwork.common.MVP.View.Implement.PopupWindow.Common_PopupWindow_View_Implement;
import com.ddtkj.projectformwork.common.MVP.View.Interface.PopupWindow.Common_PopupWindow_View_Interface;
import com.ddtkj.projectformwork.userinfo.MVP.View.Interface.PopupWindow.UserInfo_PopupWindow_View_Interface;


/**
 * PopupWindow实现类
 *
 * @author: Administrator 杨重诚
 * @date: 2016/10/11:16:56
 */

public class UserInfo_PopupWindow_View_Implement implements UserInfo_PopupWindow_View_Interface {
    Common_PopupWindow_View_Interface mCommonPopupWindowViewInterface;
    PopupWindow commonWebviewPopupWindow = null;//通用webview弹窗

    public UserInfo_PopupWindow_View_Implement() {
        mCommonPopupWindowViewInterface=new Common_PopupWindow_View_Implement();
    }

    /**
     * 通用webview弹窗
     *
     * @param context
     * @param url
     */
    @Override
    public void commonWebviewPopup(int parentLayout, final Context context, String url) {
        mCommonPopupWindowViewInterface.commonWebviewPopup(parentLayout,context,url);
    }

    /**
     * 设置PopupWindow
     *
     * @param context
     * @param popupwindowView
     */
    @Override
    public PopupWindow setPopupWindow(Context context, View popupwindowView, PopupWindow popupWindow) {
        return mCommonPopupWindowViewInterface.setPopupWindow(context,popupwindowView,popupWindow);
    }
}
