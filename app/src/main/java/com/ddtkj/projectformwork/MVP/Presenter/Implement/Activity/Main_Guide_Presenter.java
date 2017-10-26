package com.ddtkj.projectformwork.MVP.Presenter.Implement.Activity;

import com.ddtkj.projectformwork.MVP.Contract.Activity.Main_Guide_Contract;
import com.ddtkj.projectformwork.R;

/**
 * Created by ${杨重诚} on 2017/6/2.
 */

public class Main_Guide_Presenter extends Main_Guide_Contract.Presenter {
    /**
     * 获取默认引导页
     * @Title: setDefaultGuidPage
     * @Description: TODO
     * @return: void
     */
    @Override
    public int[]  getDefaultGuidPage(){
        return new int[]{R.mipmap.common_bg_guide1,R.mipmap.common_bg_guide2,R.mipmap.common_bg_guide3};
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
