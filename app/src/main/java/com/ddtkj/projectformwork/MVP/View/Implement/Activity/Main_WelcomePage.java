package com.ddtkj.projectformwork.MVP.View.Implement.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chenenyu.router.annotation.Route;
import com.customview.lib.CircleTextProgressbar;
import com.ddtkj.projectformwork.Base.Main_BaseActivity;
import com.ddtkj.projectformwork.MVP.Contract.Activity.Main_WelcomePage_Contract;
import com.ddtkj.projectformwork.MVP.Model.Bean.CommonBean.Main_WelcomePageBean;
import com.ddtkj.projectformwork.MVP.Presenter.Implement.Activity.Main_WelcomePage_Presenter;
import com.ddtkj.projectformwork.MVP.Presenter.Implement.Project.Main_ProjectUtil_Implement;
import com.ddtkj.projectformwork.MVP.Presenter.Interface.Project.Main_ProjectUtil_Interface;
import com.ddtkj.projectformwork.R;
import com.ddtkj.projectformwork.Service.Main_WelcomePageService;
import com.ddtkj.projectformwork.Util.Main_SharePer_GlobalInfo;
import com.ddtkj.projectformwork.Util.Main_SharePer_SdCard_Info;
import com.ddtkj.projectformwork.common.Public.Common_PublicMsg;
import com.ddtkj.projectformwork.common.Public.Common_RouterUrl;
import com.ddtkj.projectformwork.common.Public.Common_SD_FilePath;
import com.tendcloud.appcpa.TalkingDataAppCpa;
import com.utlis.lib.FileUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 启动页
 * @author: Administrator 杨重诚
 * @date: 2016/10/14:16:23
 */
@Route(Common_RouterUrl.mainWelcomePageRouterUrl)
public class Main_WelcomePage extends Main_BaseActivity<Main_WelcomePage_Contract.Presenter,Main_WelcomePage_Presenter> implements Main_WelcomePage_Contract.View{
    @BindView(R.id.lyParent)
    LinearLayout lyParent;
    //显示图片
    @BindView(R.id.imgWelcome)
    ImageView imgWelcome;
    //跳过进度条
    @BindView(R.id.cusCircleTextProgressbar)
    CircleTextProgressbar cusCircleTextProgressbar;

    Bitmap pageBitmap;// 启动页图片
    Main_ProjectUtil_Interface mMainProjectUtil_presenter_interface;
    boolean isOnStop=false;//标识是否执行了OnStop方法
    //---------------------------倒计时--------------------------------
    private int allTime=5*1000;//总时间

    //---------------------点击事件---------------------------------------
    @OnClick({R.id.cusCircleTextProgressbar,R.id.imgWelcome})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cusCircleTextProgressbar:
                //跳过按钮点击事件
                toNextActivity();
                break;
            case R.id.imgWelcome:
                //图片点击事件
                Main_WelcomePageBean welcomePageBean= Main_SharePer_SdCard_Info.sharePre_GetWelcomePageBean();
                if(welcomePageBean!=null&&!welcomePageBean.getLink().isEmpty()){
                    mMainProjectUtil_presenter_interface.urlIntentJudge(context,welcomePageBean.getLink(),"");
                    cusCircleTextProgressbar.stop();
                }
                break;
        }
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.main_act_welcome_page_layout);
    }
    @Override
    public void onResume() {
        isShowSystemBarTintManager=false;
        super.onResume();
        if(isOnStop){
            toNextActivity();
        }
    }
    @Override
    protected void init() {
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            finish();
            return;
        }
        //talkingdata自定义事件，每次打开app
        TalkingDataAppCpa.onCustEvent1();
        //初始化presenter
        mMainProjectUtil_presenter_interface =new Main_ProjectUtil_Implement();
        //请求是否下载启动页
        mPresenter.requestStartPageUpdate();
        //设置启动页
        setWelcomePageData();
        //5秒后跳转
        startCountDownTimer(allTime);
    }

    @Override
    protected void initMyView() {
    }

    @Override
    protected void setListeners() {
    }

    @Override
    protected void setTitleBar() {
        setActionbarGone();//隐藏actionbar
    }

    @Override
    protected void getData() {

    }

    @Override
    public void getBundleValues(Bundle mBundle) {
        super.getBundleValues(mBundle);
    }

    /**
     * 启动启动页service后台下载
     * @param pageBean
     */
    @Override
    public void startWelcomePageService(Main_WelcomePageBean pageBean) {
        // 启动service后台下载
        Intent intent = new Intent(context,
                Main_WelcomePageService.class);
        intent.putExtra("WelcomePageBean",pageBean);
        context.startService(intent);
    }

    /**
     * 设置启动页
     * @Title: setWelcomePageData
     * @Description: TODO
     * @return: void
     */
    private void setWelcomePageData() {
        List<String> filePaths = FileUtils.getFileName(new File(
                Common_SD_FilePath.welcomePagePath).listFiles());
        if (null != filePaths && filePaths.size() > 0) {
            // 随机抽取一张启动页显示
            int index = (int) (Math.random() * filePaths.size());
            pageBitmap = BitmapFactory.decodeFile(filePaths.get(index));// 读取本地图片
            imgWelcome.setImageBitmap(pageBitmap);
            //设置动画
            Animation animationTop = AnimationUtils.loadAnimation(this,
                    R.anim.common_tutorail_scalate_top);//渐变放大效果
            imgWelcome.startAnimation(animationTop);
        }else{
            imgWelcome.setImageResource(R.mipmap.common_bg_welcome_default);
        }
    }
    @Override
    /**
     * 待跳转的下个界面
     */
    public void toNextActivity(){
        cusCircleTextProgressbar.stop();//停止倒计时
        if (Main_SharePer_GlobalInfo.sharePre_GetFirstInstall()) {
            Main_SharePer_GlobalInfo.sharePre_PutFirstInstall(false);//设置不是第一次进入
            // 进入引导页
            getIntentTool().intent_RouterTo(context, Common_RouterUrl.mainGuideRouterUrl);
            finish();
        } else {
            // 进入首页
            getIntentTool().intent_RouterTo(context, Common_PublicMsg.ROUTER_MAINACTIVITY);
            finish();
        }
    }
    /**
     * 启动倒计时
     */
    public void startCountDownTimer(long downTime) {
        // 和系统普通进度条一样，0-100。
        cusCircleTextProgressbar.setProgressType(CircleTextProgressbar.ProgressType.COUNT);
        // 改变外部边框颜色。
        cusCircleTextProgressbar.setOutLineColor(Color.parseColor("#20dbdbdb"));
        // 设置倒计时时间毫秒
        cusCircleTextProgressbar.setTimeMillis(downTime);
        // 改变圆心颜色。
        cusCircleTextProgressbar.setInCircleColor(Color.parseColor("#20000000"));
        //设置进度条颜色
        cusCircleTextProgressbar.setProgressColor(context.getResources().getColor(R.color.app_color));
        //设置进度条边宽
        cusCircleTextProgressbar.setProgressLineWidth(1);
        //设进度监听
        cusCircleTextProgressbar.setCountdownProgressListener(1, new CircleTextProgressbar.OnCountdownProgressListener() {
            @Override
            public void onProgress(int what, int progress) {
                if(progress==100){
                    //倒计时结束
                    toNextActivity();
                }
            }
        });
        //启动倒计时
        cusCircleTextProgressbar.reStart();
    }
    @Override
    protected void onPause() {
        super.onPause();
        isOnStop=true;
    }

    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (pageBitmap != null) {
            pageBitmap.recycle();
        }
    }
}
