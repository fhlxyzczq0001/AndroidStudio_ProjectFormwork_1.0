package com.ddtkj.projectformwork.userinfo.MVP.View.Interface.PopupWindow;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;
/**PopupWindow弹出窗接口
 * @ClassName: com.ygworld.MVP.View.Interface
 * @author: Administrator 杨重诚
 * @date: 2016/10/11:16:55
 */

public interface UserInfo_PopupWindow_View_Interface {
    /**
     * 通用webview弹窗
     * @param context
     * @param url
     */
    public void commonWebviewPopup(int parentLayout, final Context context, String url);
    /**
     * 设置PopupWindow
     *
     * @param context
     * @param popupwindowView
     */
    public PopupWindow setPopupWindow(Context context, View popupwindowView, PopupWindow popupWindow);
}
