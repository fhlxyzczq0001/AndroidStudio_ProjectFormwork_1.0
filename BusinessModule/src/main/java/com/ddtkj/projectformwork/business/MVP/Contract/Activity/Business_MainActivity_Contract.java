package com.ddtkj.projectformwork.business.MVP.Contract.Activity;


import com.ddtkj.projectformwork.common.Base.Common_BasePresenter;
import com.ddtkj.projectformwork.common.Base.Common_BaseView;

/**
 * 主Activity接口契约类
 * Created by ${杨重诚} on 2017/6/2.
 */

public interface Business_MainActivity_Contract {
    interface View extends Common_BaseView {

    }

    abstract class Presenter extends Common_BasePresenter<View> {
        /**
         * 初始化P层
         */
        public abstract void initP();
        @Override
        public void onStart() {

        }
    }
}
