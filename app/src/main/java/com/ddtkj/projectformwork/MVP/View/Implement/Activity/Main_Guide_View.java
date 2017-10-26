package com.ddtkj.projectformwork.MVP.View.Implement.Activity;

import android.view.View;
import android.widget.LinearLayout;

import com.chenenyu.router.annotation.Route;
import com.ddtkj.projectformwork.Base.Main_BaseActivity;
import com.ddtkj.projectformwork.MVP.Contract.Activity.Main_Guide_Contract;
import com.ddtkj.projectformwork.MVP.Presenter.Implement.Activity.Main_Guide_Presenter;
import com.ddtkj.projectformwork.R;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.EventBus.Common_LoginSuccess_EventBus;
import com.ddtkj.projectformwork.common.Public.Common_PublicMsg;
import com.ddtkj.projectformwork.common.Public.Common_RouterUrl;
import com.jazzy.viewpager.GuideViewPagers;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**引导页
 * @author: Administrator 杨重诚
 * @date: 2016/10/21:11:26
 */
@Route(Common_RouterUrl.mainGuideRouterUrl)
public class Main_Guide_View extends Main_BaseActivity<Main_Guide_Contract.Presenter,Main_Guide_Presenter> implements Main_Guide_Contract.View {
    @BindView(R.id.lyParent)
    LinearLayout lyParent;
    //引导页
    @BindView(R.id.cusGuideViewPagers)
    GuideViewPagers cusGuideViewPagers;

    @Override
    protected void initMyView() {

    }
    @Override
    protected void setContentView() {
        setContentView(R.layout.main_act_guide_layout);
    }

    @Override
    protected void init() {
        //初始化引导页
        cusGuideViewPagers.setGuideData(mPresenter.getDefaultGuidPage(),new int[]{R.mipmap.common_icon_menu_dot2,R.mipmap.common_icon_menu_dot1},R.mipmap.common_bg_guide1);
        //设置按钮背景
        cusGuideViewPagers.setLogingBtSty(R.drawable.common_shap_powder_radio_bg);
        cusGuideViewPagers.setTiYangBtSty(R.drawable.common_shap_powder_radio_bg);
    }

    @Override
    protected void setListeners() {
        cusGuideViewPagers.getLogingBt().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 进入登录
                getIntentTool().intent_RouterTo(context, Common_RouterUrl.userinfo_UserLogingRouterUrl);
            }
        });
        cusGuideViewPagers.getTiyanBt().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 进入首页
                getIntentTool().intent_RouterTo(context,  Common_PublicMsg.ROUTER_MAINACTIVITY);
                finish();
            }
        });
    }

    @Override
    protected void setTitleBar() {
        setActionbarGone();//隐藏actionbar
    }
    @Override
    protected void getData() {

    }
    @Override
    public void onResume() {
        isShowSystemBarTintManager=false;
        super.onResume();
        // 注册EventBus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销EventBus
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 登录成功执行的操作
     * @param eventBus
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND,sticky = true)
    public void logSuccess(Common_LoginSuccess_EventBus eventBus) {
        if(eventBus.isLoginSuccess()){
            finish();
        }
    }
}
