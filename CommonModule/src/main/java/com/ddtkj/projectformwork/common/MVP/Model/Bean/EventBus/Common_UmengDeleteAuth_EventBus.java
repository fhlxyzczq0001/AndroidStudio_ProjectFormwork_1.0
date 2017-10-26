package com.ddtkj.projectformwork.common.MVP.Model.Bean.EventBus;

import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import mvpdemo.com.unmeng_share_librarys.ShareCode;

/**友盟解绑授权
 * @author: Administrator 杨重诚
 * @date: 2016/11/15:15:45
 */

public class Common_UmengDeleteAuth_EventBus {
    private SHARE_MEDIA platform;
    private int action;
    private Map<String, String> data;
    private ShareCode mShareCode;
    boolean isReceive=false;
    public Common_UmengDeleteAuth_EventBus(SHARE_MEDIA platform, int action, ShareCode mShareCode){

        this.platform=platform;
        this.action=action;
        this.mShareCode=mShareCode;
    }

    public Common_UmengDeleteAuth_EventBus(SHARE_MEDIA platform, int action, Map<String, String> data, ShareCode mShareCode){

        this.platform=platform;
        this.action=action;
        this.data=data;
        this.mShareCode=mShareCode;
    }
    public SHARE_MEDIA getPlatform() {
        return platform;
    }

    public void setPlatform(SHARE_MEDIA platform) {
        this.platform = platform;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public ShareCode getShareCode() {
        return mShareCode;
    }

    public void setShareCode(ShareCode shareCode) {
        mShareCode = shareCode;
    }

    public boolean isReceive() {
        return isReceive;
    }

    public void setReceive(boolean receive) {
        isReceive = receive;
    }
}
