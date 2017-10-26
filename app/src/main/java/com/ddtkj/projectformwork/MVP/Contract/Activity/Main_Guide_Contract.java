package com.ddtkj.projectformwork.MVP.Contract.Activity;


import com.ddtkj.projectformwork.common.Base.Common_BasePresenter;
import com.ddtkj.projectformwork.common.Base.Common_BaseView;

/**引导页接口契约类
 * Created by ${杨重诚} on 2017/6/2.
 */

public interface Main_Guide_Contract {
    interface View extends Common_BaseView {

    }

    abstract class Presenter extends Common_BasePresenter<View> {
        @Override
        public void onStart() {

        }
        /**
         * 获取默认引导页
         * @Title: setDefaultGuidPage
         * @Description: TODO
         * @return: void
         */
        public abstract int[]  getDefaultGuidPage();
    }
}
