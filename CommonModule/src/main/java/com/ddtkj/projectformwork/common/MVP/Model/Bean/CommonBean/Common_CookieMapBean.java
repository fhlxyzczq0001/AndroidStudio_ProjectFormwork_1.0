package com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean;

import java.util.HashMap;
import java.util.HashSet;

/**cookie缓存
 * Created by Administrator on 2017/3/30.
 */

public class Common_CookieMapBean {
    private HashMap<String ,HashSet<String>> cookieMap;

    public HashMap<String, HashSet<String>> getCookieMap() {
        return cookieMap;
    }

    public void setCookieMap(HashMap<String, HashSet<String>> cookieMap) {
        this.cookieMap = cookieMap;
    }
}
