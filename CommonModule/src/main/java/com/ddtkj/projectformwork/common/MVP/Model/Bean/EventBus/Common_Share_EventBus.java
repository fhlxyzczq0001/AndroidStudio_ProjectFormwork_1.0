package com.ddtkj.projectformwork.common.MVP.Model.Bean.EventBus;
import mvpdemo.com.unmeng_share_librarys.ShareCode;

/**分享的EventBus对象
 * @author: Administrator 杨重诚
 * @date: 2016/11/3:9:38
 */

public class Common_Share_EventBus {
    private ShareCode sareCode;//分享标识
    private boolean isReceive=false;
    public Common_Share_EventBus(ShareCode sareCode){

        this.sareCode=sareCode;
    }

    public ShareCode getSareCode() {
        return sareCode;
    }

    public void setSareCode(ShareCode sareCode) {
        this.sareCode = sareCode;
    }

    public boolean isReceive() {
        return isReceive;
    }

    public void setReceive(boolean receive) {
        isReceive = receive;
    }
}
