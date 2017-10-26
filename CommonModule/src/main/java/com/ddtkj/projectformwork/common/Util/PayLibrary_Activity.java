package com.ddtkj.projectformwork.common.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chenenyu.router.annotation.Route;
import com.ddt.pay_library.OnPaymentResultListener;
import com.ddt.pay_library.PayManageUtils;
import com.ddt.pay_library.TradeType_Code;
import com.ddt.pay_library.bean.Basic_Bean;
import com.ddt.pay_library.bean.Payment_Result;
import com.ddtkj.projectformwork.common.HttpRequest.ResultListener.Common_ResultDataListener;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean.Common_RequestBean;
import com.ddtkj.projectformwork.common.Public.Common_RouterUrl;
import com.ddtkj.projectformwork.common.R;
import com.heepay.plugin.activity.Constant;
import com.jhpay.sdk.ResponseListener;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.utlis.lib.L;
import com.utlis.lib.ToastUtils;
import com.wangyin.wepay.TradeResultInfo;
import com.wangyin.wepay.WePay;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * Created by Administrator on 2016/12/5.
 * 跳转此界面需传递Basic_Bean对象，所有参数不可为空
 * *********
 * 如需接受支付结果请使用startActivityForResult方法跳转至此界面
 *  并在onActivityResult(结果码为PayLibrary_Activity.PAYMENT_RESULT_CODE)方法中使用
 *  intent.getParcelableExtra("paymentResult")得到com.ddt.pay_library.bean实体类
 * @author lizhipei
 */
@Route(Common_RouterUrl.mainPayLibraryRouterUrl)
public class PayLibrary_Activity extends Activity implements OnPaymentResultListener, ResponseListener {

    //当前发起的支付类型
    private String currentTradeType = "";
    //当前发起的支付金额
    private String currentMoney = "";
    //订单号
    private String currentOrderNumber = "";
    //当前发的的支付来源（购物车支付、充值、购买会员）
    private String currentPayStatus = "";
    //购买成功后返回的可抽奖此时
    private String currentLotteryNumber = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_paylayout);
        Bundle bundle = getIntent().getExtras();
        if(!bundle.containsKey("basicBean")){
            this.finish();
            return ;
        }
        Basic_Bean basicBean = (Basic_Bean) bundle.getParcelable("basicBean");
        if(basicBean == null){
            this.finish();
            return ;
        }

        currentTradeType = basicBean.getTradeType();
        currentMoney = basicBean.getMoney();
        currentPayStatus = basicBean.getPayStatus();
        //再次校验支付类型以及金额是否为空
//        if(currentTradeType == null || currentMoney == null
//                || TextUtils.isEmpty(currentTradeType) || TextUtils.isEmpty(currentMoney)){
        if(currentTradeType == null || TextUtils.isEmpty(currentTradeType)){
            ToastUtils.RightImageToast(this, "支付类型不可为空");
            this.finish();
            return;
        }
        if(currentTradeType.contains("WX") && (currentTradeType.contains("APP") || currentTradeType.contains("APK"))){
            IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
            boolean sIsWXAppInstalledAndSupported = msgApi.isWXAppInstalled()
                    && msgApi.isWXAppSupportAPI();
            if(!sIsWXAppInstalledAndSupported){
                ToastUtils.WarnImageToast(this, "手机没有安装微信，请先安装微信");
                this.finish();
                return;
            }
        }
        initView();
         PayManageUtils.getInstance().sendPay(this, currentTradeType,
                 currentMoney, basicBean.getOrderInfo(), currentPayStatus, this);
    }

    RelativeLayout mainLayout;
    TextView messageText;
    Button button1, button2;
    ImageView activityPayLayout_cancle;
    private void initView(){
        mainLayout = (RelativeLayout) findViewById(R.id.activityPayLayout_main);
        messageText = (TextView) findViewById(R.id.activityPayLayout_message);
        activityPayLayout_cancle = (ImageView) findViewById(R.id.activityPayLayout_cancle);
        button1 = (Button) findViewById(R.id.activityPayLayout_button1);
        button2 = (Button) findViewById(R.id.activityPayLayout_button2);
        messageText.setText("支付中...");
        button1.setText("支付成功");
        button2.setText("遇到问题");
        activityPayLayout_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestTradeNumber(false);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestTradeNumber(true);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestTradeNumber(false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 注册EventBus
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注销EventBus
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null){
            Bundle bundle = data.getExtras();
            if(resultCode == Constant.RESULTCODE){//汇付宝支付
                String respCode = bundle.getString("respCode");
                String respMessage = bundle.getString("respMessage");
                if(!TextUtils.isEmpty(respCode)){
                    if("01".equals(respCode)){
                        onPaymentResultListener(true, ""+currentOrderNumber, ""+currentLotteryNumber);
                    }else if("00".equals(respCode)){
                        onPaymentResultListener(false, "处理中...", ""+currentLotteryNumber);
                    }else{
                        onPaymentResultListener(false, "支付失败", ""+currentLotteryNumber);
                    }
                }else{
                    onPaymentResultListener(false, ""+currentOrderNumber,  ""+currentLotteryNumber);
                }
            }else{
                if(currentTradeType.equals(TradeType_Code.JD_APP)){
                    //京东支付回调
                    TradeResultInfo tradeResultInfo = (TradeResultInfo) bundle
                            .getSerializable(WePay.PAY_RESULT);
                    if (tradeResultInfo != null) {
                        // 交易状态 UNDO：0,SUCCESS:1,FAIL：-1
                        if (tradeResultInfo.resultStatus == 0) {
                            onPaymentResultListener(false, ""+currentOrderNumber, ""+currentLotteryNumber);
                        } else if (tradeResultInfo.resultStatus == 1) {
                            onPaymentResultListener(true, ""+currentOrderNumber, ""+currentLotteryNumber);
                            // 充值成功后，将回调中返回token存入本地，下次调用京东支付时，将该值传入调起支付方法中
                            String jdPayToken = tradeResultInfo.token;
                            Common_SharePer_GlobalInfo.sharePre_PutJD_Token(jdPayToken);
                        } else if (tradeResultInfo.resultStatus == -1) {
                            onPaymentResultListener(false, ""+currentOrderNumber, ""+currentLotteryNumber);
                        }
                    }
                }else if(currentTradeType.equals(TradeType_Code.WFT_WX_APP) || currentTradeType.equals(TradeType_Code.WFT_WX_WAP)){
                    String respCode = bundle.getString("resultCode");//威富通支付结果回调判断
                    if (!TextUtils.isEmpty(respCode) && respCode.equalsIgnoreCase("success")) {
                        onPaymentResultListener(true, ""+currentOrderNumber, ""+currentLotteryNumber);
                    } else {
                        onPaymentResultListener(false, ""+currentOrderNumber, ""+currentLotteryNumber);
                    }
                }
            }
        }else if(requestCode == 10011 || requestCode == 10012 || requestCode == 10013){
            //第三方网页支付或支付宝网页支付或招行支付
            if(resultCode == RESULT_OK){
                onPaymentResultListener(true, ""+currentOrderNumber, ""+currentLotteryNumber);
            }else{
                onPaymentResultListener(false, ""+currentOrderNumber, ""+currentLotteryNumber);
            }
        }else{
            onPaymentResultListener(false, ""+currentOrderNumber, ""+currentLotteryNumber);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 再次确认支付结果，请求后台reconfirmPayment
     * 支付完成，并退出此界面exitCurrent
     * @param bean 支付结果
     *              需设置是否成功（state）和结果信息（msg）
     */
    private void exitCurrent(final Payment_Result bean){
        if(!bean.isState() && !TextUtils.isEmpty(bean.getMsg())){
            ToastUtils.WarnImageToast(this, bean.getMsg());
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //这里发送粘性事件
                EventBus.getDefault().post(bean);
            }
        }, 300);
        this.finish();
    }

    /**
     * 根据订单号查询是否充值成功
     */
    private void requestTradeNumber(boolean isSuccess){
        if (isSuccess){
            L.e("currentOrderNumber----"+currentOrderNumber+"  currentMoney:"+currentMoney);
            UserRechargeUtils.getInstance().requestTrade(this, currentOrderNumber, currentMoney, new Common_ResultDataListener() {
                @Override
                public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                    if(isSucc){
                        int type = request_bean.getType();
                        // type为1，说明充值成功；否则充值失败
                        if(type == 1){
                            Payment_Result paymentBean = new Payment_Result(currentTradeType, currentOrderNumber, currentMoney, true, "", currentLotteryNumber);
                            exitCurrent(paymentBean);
                        } else {
                            Payment_Result paymentBean = new Payment_Result(currentTradeType, currentOrderNumber, currentMoney, false, "支付排队中...请您耐心等待或联系客服", currentLotteryNumber);
                            exitCurrent(paymentBean);
                        }
                    } else {
                        Payment_Result paymentBean = new Payment_Result(currentTradeType, currentOrderNumber, currentMoney, false, "支付排队中...请您耐心等待或联系客服", currentLotteryNumber);
                        exitCurrent(paymentBean);
                    }
                }
            });
        } else {
            Payment_Result paymentBean = new Payment_Result(currentTradeType, currentOrderNumber, currentMoney, false, "", currentLotteryNumber);
            exitCurrent(paymentBean);
        }

    }
    /**
     * 记录订单号查询次数
     */
    int checkTradeNumberCount = 0;
    @Override
    public void onPaymentResultListener(final boolean isResult, final String resultMsg, final String LotteryNumber) {
        if(currentPayStatus != null && !currentPayStatus.equals("") && currentPayStatus.endsWith("member")){
            L.i("充值或购买会员", "currentPayStatus="+currentPayStatus+"  获得抽奖次数="+LotteryNumber);
            Payment_Result paymentBean = new Payment_Result(currentTradeType, resultMsg, currentMoney, isResult, resultMsg, LotteryNumber);
            if(isResult){
                ToastUtils.RightImageToast(this, "支付成功");
            }
            exitCurrent(paymentBean);
        }else{
            L.i("购物车普通购买", "isResult="+isResult+"  resultMsg="+resultMsg+" currentOrderNumber="+currentOrderNumber);
            /*if(isResult && !TextUtils.isEmpty(currentMoney)){
                UserRechargeUtils.getInstance().requestTrade(this, resultMsg, currentMoney, new ResultListener() {
                    @Override
                    public void onResult(boolean isSucc, String msg, String request) {
                        if(isSucc){
                            Request_Bean request_Bean= JSONObject.parseObject(request, Request_Bean.class);
                            int type = request_Bean.getType();
                            // type为1，说明充值成功；否则充值失败
                            if(type == 1){
                                Payment_Result paymentBean = new Payment_Result(currentTradeType, resultMsg, currentMoney, true, "充值成功", LotteryNumber);
                                exitCurrent(paymentBean);
                            }else{
                                //如果此接口请求次数少于2，则再次请求
                                if(checkTradeNumberCount <= 2){
                                    onPaymentResultListener(isResult, resultMsg, LotteryNumber);
                                }else{//查询失败
                                    Payment_Result paymentBean = new Payment_Result(currentTradeType, resultMsg, currentMoney, true, "查询失败", LotteryNumber);
                                    exitCurrent(paymentBean);
                                }
                            }
                        }else{
                            //如果此接口请求次数少于2，则再次请求
                            if(checkTradeNumberCount <= 2){
                                onPaymentResultListener(isResult, resultMsg, LotteryNumber);
                            }else{//查询失败
                                Payment_Result paymentBean = new Payment_Result(currentTradeType, resultMsg, currentMoney, false, "查询失败", LotteryNumber);
                                exitCurrent(paymentBean);
                            }
                        }
                        //次数加1
                        checkTradeNumberCount++;
                    }
                });
            }else{
                Payment_Result paymentBean = new Payment_Result(currentTradeType, resultMsg, currentMoney, false, "未支付", LotteryNumber);
                exitCurrent(paymentBean);
            }*/
            mainLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPayOrderNumberListener(String orderNumber, String LotteryNumber) {
        currentOrderNumber = orderNumber;
        currentLotteryNumber = LotteryNumber;
    }


    @Override
    public void responseData(int i, String s) {
        //聚合富的支付回调
        switch (i) {
            case ResponseListener.RESULT_PAYCODE_OK:// 支付成功
                onPaymentResultListener(true, ""+currentOrderNumber, ""+currentLotteryNumber);
                break;
            case ResponseListener.RESULT_PAYCODE_ERROR:// 支付失败
                onPaymentResultListener(false, ""+currentOrderNumber, ""+currentLotteryNumber);
                break;
        }
    }

    /**
     * 接受微信回调发送的支付结果
     * @param baseResp
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void wxPayResult(BaseResp baseResp) {
        if(baseResp.errCode == 0 && !currentOrderNumber.equals("")){
            onPaymentResultListener(true, ""+currentOrderNumber, ""+currentLotteryNumber);
        }else{
            onPaymentResultListener(false, "未支付", ""+currentLotteryNumber);
        }
    }

}
