package com.ddtkj.projectformwork.common.MVP.Model.Bean.EventBus;

/**Cookie同步成功
 * @author: Administrator 杨重诚
 * @date: 2016/11/14:15:33
 */

public class Common_SyncCookie_EventBus {
    boolean syncCookie =false;
    boolean isReceive=false;
    public Common_SyncCookie_EventBus(boolean syncCookie){
        this.syncCookie=syncCookie;
    }

    public boolean isSyncCookie() {
        return syncCookie;
    }

    public void setSyncCookie(boolean syncCookie) {
        this.syncCookie = syncCookie;
    }

    public boolean isReceive() {
        return isReceive;
    }

    public void setReceive(boolean receive) {
        isReceive = receive;
    }
}
