package com.ddtkj.projectformwork.MVP.Contract.Activity;

import com.ddtkj.projectformwork.MVP.Model.Bean.CommonBean.Main_WelcomePageBean;
import com.ddtkj.projectformwork.common.Base.Common_BasePresenter;
import com.ddtkj.projectformwork.common.Base.Common_BaseView;

/**启动页接口契约类
 * Created by ${杨重诚} on 2017/6/2.
 */

public interface Main_WelcomePage_Contract {
    interface View extends Common_BaseView {
        /**
         * 启动启动页service后台下载
         * @param pageBean
         */
        public void startWelcomePageService(Main_WelcomePageBean pageBean);

        /**
         * 待跳转的下个界面
         */
        public void toNextActivity();
    }

    abstract class Presenter extends Common_BasePresenter<View> {
        @Override
        public void onStart() {

        }
        /**
         * 请求启动页更新
         */
        public abstract void requestStartPageUpdate();
    }
}
