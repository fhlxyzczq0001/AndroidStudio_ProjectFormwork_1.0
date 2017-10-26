package com.ddtkj.projectformwork.common.Util;

import android.content.Context;
import android.os.Bundle;

import com.ddt.pay_library.bean.Basic_Bean;
import com.ddtkj.projectformwork.common.HttpRequest.Common_HttpPath;
import com.ddtkj.projectformwork.common.HttpRequest.Common_HttpRequestMethod;
import com.ddtkj.projectformwork.common.HttpRequest.ResultListener.Common_ResultDataListener;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean.Common_RequestBean;
import com.ddtkj.projectformwork.common.MVP.Model.Implement.Common_Base_HttpRequest_Implement;
import com.ddtkj.projectformwork.common.MVP.Model.Interface.Common_Base_HttpRequest_Interface;
import com.ddtkj.projectformwork.common.Public.Common_RouterUrl;
import com.utlis.lib.L;
import com.utlis.lib.ToastUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/2.
 * 用户充值工具类
 * @author lizhipei
 */
public class UserRechargeUtils {
    Common_Base_HttpRequest_Interface mBase_model_implement;//请求网络数据的model实现类
    private volatile static UserRechargeUtils singleton;

    private UserRechargeUtils() {
        mBase_model_implement = new Common_Base_HttpRequest_Implement();
    }

    /**
     * 获取工具类的实例对象
     * @return
     */
    public static UserRechargeUtils getInstance(){
        if(singleton==null){
            synchronized(UserRechargeUtils.class){
                if(singleton==null){
                    singleton=new UserRechargeUtils();
                }
            }
        }
        return singleton;
    }

    /**
     * 请求后台生成支付订单
     * @param mContext
     * @param cardRecharge  是否为卡充值，true or false
     * @param tradeType     充值类型，卡充值为true时无效
     * @param money         充值金额，卡充值为true时无效
     * @param cardNo        卡充值的账号
     * @param cardPwd       卡充值的密码
     * @param payStatus      支付状态  "" 表示购物车支付， recharge 表示充值
     */
    private void requestPayInfo(final Context mContext, boolean cardRecharge, final String tradeType,
                                final String money, String cardNo, String cardPwd, final String payStatus){
        L.e(tradeType+"-"+money);
        requestPayInfo(mContext, false, tradeType, money, "", "", payStatus, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                //支付订单信息请求成功后的回调，以及后续处理
                if(isSucc){//订单请求成功
                    sendPayInfo(mContext, tradeType, money, request_bean.getData().toString(), payStatus);
                }
            }
        });
    }

    /**
     * 调起支付界面
     * @param mContext
     * @param tradeType     支付类型（不可为空）
     * @param money         支付金额
     * @param orderJson     支付接口返回的支付信息
     * @param payStatus     支付类型  （购物车支付、充值、会员支付）
     */
    public void sendPayInfo(final Context mContext, String tradeType, String money, String orderJson, String  payStatus){
        Bundle bundle = new Bundle();
        Basic_Bean bean = new Basic_Bean();
        bean.setTradeType(tradeType);
        bean.setMoney(money);
        bean.setOrderInfo(orderJson);
        bean.setPayStatus(payStatus);
        bundle.putParcelable("basicBean", bean);
        new IntentUtil().intent_RouterTo(mContext, Common_RouterUrl.mainPayLibraryRouterUrl, bundle);
    }

    /**
     * 请求后台生成支付订单，自定义回调
     * @param mContext
     * @param cardRecharge      是否为卡充值，true or false
     * @param tradeType         充值类型，卡充值为true时无效
     * @param money             充值金额，卡充值为true时无效
     * @param cardNo            卡充值的账号
     * @param cardPwd           卡充值的密码
     * @param payStatus     支付类型  （购物车支付、充值、会员支付）
     * @param resultListener    回调监听
     */
    private void requestPayInfo(Context mContext, boolean cardRecharge, String tradeType,
                               String money, String cardNo, String cardPwd, final String payStatus, Common_ResultDataListener resultListener){
        Map<String, Object> params = new HashMap<>();
        params.put("operation", "recharge");//充值接口
        params.put("recharge_trade_type", tradeType);//充值类型
        if(cardRecharge){//云购卡充值
            params.put("yg_recharge_card_no", ""+cardNo);//卡充值账号
            params.put("yg_recharge_card_pwd", ""+cardPwd);//卡充值密码
        }else{//第三方充值
            params.put("recharge_money", ""+money);//充值金额
            params.put("recharge_type", "4");//充值类型 表示充值购买

            String jdPayToken = Common_SharePer_GlobalInfo.sharePre_GetJD_Token().trim();
            if(tradeType.equals("JD_APP") && jdPayToken != null && !jdPayToken.equals("")){
                params.put("jdToken", ""+jdPayToken);
            }
        }
        L.e("recharge_trade_type:"+tradeType+",recharge_money:"+money);
        mBase_model_implement.requestData(mContext, Common_HttpPath.URL_API_USER_INFO, params, resultListener, true, Common_HttpRequestMethod.POST);
    }

    /**
     * 请求后台生成会员购买支付订单
     * @param mContext
     * @param tradeType
     * @param money
     * @param payStatus
     */
    private void requestPayMemberInfo(final Context mContext, final String tradeType,
                                      final String money, final String payStatus){
        requestPayInfoMember(mContext, false, tradeType, money, new Common_ResultDataListener() {
            @Override
            public void onResult(boolean isSucc, String msg, Common_RequestBean request_bean) {
                //支付订单信息请求成功后的回调，以及后续处理
                if(isSucc){//订单请求成功
                    sendPayInfo(mContext, tradeType, money, request_bean.getData().toString(), payStatus);
                }
            }
        });
    }

    /**
     * 查询当前支付订单是否支付成功
     * @param tradeNumber 订单号
     * @param money       订单金额
     */
    public void requestTrade(Context mContext, String tradeNumber, String money, Common_ResultDataListener resultListener){
        Map<String, Object> params = new HashMap<>();
        params.put("tradeNumber", tradeNumber);//充值订单号
        params.put("money", money);//充值金额
        mBase_model_implement.requestData(mContext, Common_HttpPath.CHECK_RECHARGE_TYPE, params, resultListener, false,Common_HttpRequestMethod.POST);
    }

    /**
     * 仅限购物车使用
     * @param mContext
     * @param tradeType
     * @param money
     */
    @Deprecated
    public void requestPay(final Context mContext, String tradeType,
                           final String money){
        requestPay(mContext, tradeType, money, "");
    }

    /**
     * 请求支付订单信息，并发起支付
     * @param mContext
     * @param tradeType 充值方式
     * @param money     充值金额
     * @param payStatus   充值类型   member(会员支付)   recharge(充值)   购物车设置为空
     */
    public void requestPay(final Context mContext, String tradeType,
                           final String money, final String payStatus){
        if(tradeType == null || tradeType.equals("")){
            ToastUtils.WarnImageToast(mContext, "充值类型不可为空");
            return;
        }
//        tradeType = "ZFB_SCAN_M_LF_APK_CJX";//
        /*WX_SCAN_M_JHF, WX_SCAN_M_JHF_CJX, WX_SCAN_M_JHF_QTF
                *   , ZFB_SCAN_M_LF_APK_QTF,*/
        L.e("#################tradeType",tradeType);
        if(money == null || money.equals("")){
            ToastUtils.WarnImageToast(mContext, "充值金额不可为空");
            return;
        }
        if(payStatus.endsWith("member")){
            requestPayMemberInfo(mContext, tradeType, money, payStatus);
        }else{
            requestPayInfo(mContext, false, tradeType, money, "", "", payStatus);
        }
    }


    /**
     * 请求后台生成支付订单，自定义回调
     * @param mContext
     * @param cardRecharge      是否为卡充值，true or false
     * @param tradeType         充值类型，卡充值为true时无效
     * @param money             充值金额，卡充值为true时无效
     * @param resultListener    回调监听
     */
    private void requestPayInfoMember(Context mContext, boolean cardRecharge, String tradeType,
                                String money, Common_ResultDataListener resultListener){
        Map<String, Object> params = new HashMap<>();
        params.put("recharge_trade_type", tradeType);//充值类型
        params.put("recharge_money", ""+money);//充值金额
        String jdPayToken = Common_SharePer_GlobalInfo.sharePre_GetJD_Token().trim();
        if(tradeType.equals("JD_APP") && jdPayToken != null && !jdPayToken.equals("")){
            params.put("jdToken", ""+jdPayToken);
        }
        mBase_model_implement.requestData(mContext, Common_HttpPath.URL_MEMBER_LEVEL_PAY, params, resultListener, true, Common_HttpRequestMethod.POST);
    }
}
