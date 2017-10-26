package com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean;

import java.util.HashSet;
import java.util.Set;

/**用户登录成功的手机号缓存
 * @author: Administrator 杨重诚
 * @date: 2016/11/29:11:03
 */

public class Common_UserLogingPhoneCacheBean {
    Set<String> phoneCache=new HashSet<>();

    public Set<String> getPhoneCache() {
        return phoneCache;
    }

    public void setPhoneCache(Set<String> phoneCache) {
        this.phoneCache = phoneCache;
    }
}
