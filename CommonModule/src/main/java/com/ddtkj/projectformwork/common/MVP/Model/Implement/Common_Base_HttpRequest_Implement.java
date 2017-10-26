package com.ddtkj.projectformwork.common.MVP.Model.Implement;

import android.content.Context;

import com.ddtkj.projectformwork.common.Base.Common_Application;
import com.ddtkj.projectformwork.common.BuildConfig;
import com.ddtkj.projectformwork.common.HttpRequest.Common_CustomRequestResponseBeanDataManager;
import com.ddtkj.projectformwork.common.HttpRequest.Common_HttpPath;
import com.ddtkj.projectformwork.common.HttpRequest.Common_HttpRequestMethod;
import com.ddtkj.projectformwork.common.HttpRequest.HttpManager.Common_HttpDownManager;
import com.ddtkj.projectformwork.common.HttpRequest.Interface.Common_HttpRequestService;
import com.ddtkj.projectformwork.common.HttpRequest.RequestBody.Common_RequestBodyApi;
import com.ddtkj.projectformwork.common.HttpRequest.ResultListener.Common_ResultDataListener;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean.Common_RequestBean;
import com.ddtkj.projectformwork.common.MVP.Model.Interface.Common_Base_HttpRequest_Interface;
import com.ddtkj.projectformwork.common.R;
import com.utlis.lib.L;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.DownInfo;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.RetryWhenNetworkException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpDownOnNextListener;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${杨重诚} on 2017/10/12.
 */

public class Common_Base_HttpRequest_Implement implements Common_Base_HttpRequest_Interface {
    Common_CustomRequestResponseBeanDataManager mCommon_customRequestResponseBeanDataManager;
    public boolean isOpenPost=true;//强制开启post请求
    public Common_Base_HttpRequest_Implement(){
        if(mCommon_customRequestResponseBeanDataManager==null){
            mCommon_customRequestResponseBeanDataManager= Common_CustomRequestResponseBeanDataManager.getInstance();
        }
        if(BuildConfig.IsHttpPost){
            isOpenPost=true;
        }else {
            isOpenPost=false;
        }
    }
    /**
     * 请求网络数据接口
     * @param context 上下文
     * @param URL 路径
     * @param params 参数
     * @param resultListener 回调
     * @param isLoadingDialog 是否加载进度
     * @param requestMethod 请求类型
     */
    @Override
    public void requestData(final Context context, final String URL, final Map<String, Object> params, final Common_ResultDataListener resultListener, final boolean isLoadingDialog, final Common_HttpRequestMethod requestMethod) {
        if(BuildConfig.IsRequestHttpDns){
            //Rxjava请求模式
            RXjavaRequest(context, new HttpDnsListener() {
                @Override
                public void requestListener(boolean isSucc, final String newUrl) {
                    if (isSucc){
                        if(isOpenPost){
                            //强制开启Post请求
                            mCommon_customRequestResponseBeanDataManager.requestPost(context,getURL(newUrl,URL),
                                    params, resultListener,isLoadingDialog);
                        }else {
                            if(requestMethod==Common_HttpRequestMethod.POST){
                                mCommon_customRequestResponseBeanDataManager.requestPost(context,getURL(newUrl,URL),
                                        params, resultListener,isLoadingDialog);
                            }else {
                                mCommon_customRequestResponseBeanDataManager.requestGet(context,URL,params,
                                        resultListener,isLoadingDialog);
                            }
                        }
                    }
                }
            });
        }else {
            if(isOpenPost){
                //强制开启Post请求
                    mCommon_customRequestResponseBeanDataManager.requestPost(context,getURL(Common_HttpPath.HTTP_HOST_PROJECT,URL),
                        params, resultListener,isLoadingDialog);
            }else {
                if(requestMethod==Common_HttpRequestMethod.POST){
                    mCommon_customRequestResponseBeanDataManager.requestPost(context,getURL(Common_HttpPath.HTTP_HOST_PROJECT,URL),
                            params, resultListener,isLoadingDialog);
                }else {
                    mCommon_customRequestResponseBeanDataManager.requestGet(context,URL,params,
                            resultListener,isLoadingDialog);
                }
            }
        }
    }
    /**
     * 获取URL
     * @param newUrl
     * @param oldUrl
     */
    private String getURL(String newUrl,String oldUrl){
        return newUrl+oldUrl;
    }
    /**
     * 下载文件
     * @param saveFilePath 保存路径
     * @param uploadFilePath 下载路径
     * @param resultListener 回调
     */
    @Override
    public void FileDownloader(String saveFilePath, String uploadFilePath, final Common_ResultDataListener resultListener) {
        HttpDownOnNextListener httpDownOnNextListener=new HttpDownOnNextListener() {
            @Override
            public void onNext(Object o) {
                resultListener.onResult(true,"下载成功",null);
            }
            @Override
            public void onStart() {

            }
            @Override
            public void onComplete() {

            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                resultListener.onResult(false,"下载失败",null);
            }
            @Override
            public void updateProgress(long readLength, long countLength) {

            }
        };
        DownInfo downInfo=new DownInfo(uploadFilePath,saveFilePath,httpDownOnNextListener);
        new Common_HttpDownManager().startDown(downInfo);
    }

    /**
     * 下载文件的回调方法
     */
    public interface ProgressResultListener {

        /**
         * 下载进度
         *
         * @param fileSize
         * @param downloadedSize
         */
        public void onProgressChange(long fileSize, long downloadedSize);
    }

    /**
     * 下载文件
     * @param saveFilePath 保存路径
     * @param uploadFilePath 下载路径
     * @param resultListener 回调
     */
    @Override
    public void FileDownloader(String saveFilePath, String uploadFilePath, final Common_ResultDataListener resultListener, final ProgressResultListener onProgressChange) {
        HttpDownOnNextListener httpDownOnNextListener=new HttpDownOnNextListener() {
            @Override
            public void onNext(Object o) {
                resultListener.onResult(true,"下载成功",null);
            }
            @Override
            public void onStart() {

            }
            @Override
            public void onComplete() {

            }
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                resultListener.onResult(false,"下载失败",null);
            }
            @Override
            public void updateProgress(long readLength, long countLength) {
                onProgressChange.onProgressChange(countLength,readLength);
            }
        };
        DownInfo downInfo=new DownInfo(uploadFilePath,saveFilePath,httpDownOnNextListener);
        new Common_HttpDownManager().startDown(downInfo);
    }

    /**
     * 获取HttpDns的回调方法
     */
    public interface HttpDnsListener {
        public void requestListener(boolean isSucc, String newUrl);

    }
    /**
     * 获取HttpDns——observable模式
     * @param context
     * @param originalUrl
     * @param subscriber
     */
    public  void getHttpDnsIp(Context context,String originalUrl, Subscriber<? super String> subscriber) {
        String newUrl=originalUrl;
        try {
            URL url = new URL(originalUrl);
            // 同步接口获取IP
            String ip = Common_Application.getInstance().getHttpDnsService().getIpByHostAsync(url.getHost());
            if (ip != null&&!ip.isEmpty()) {
                // 通过HTTPDNS获取IP成功，进行URL替换和HOST头设置
                //L.e("通过HTTPDNS获取IP成功，进行URL替换和HOST头设置", "Get IP: " + ip + " for host: " + url.getHost() + " from HTTPDNS successfully!");
                newUrl = originalUrl.replaceFirst(url.getHost(), ip);
                //L.e("newUrl:", newUrl);
                //L.e("****************", "****************");

                //=======================获取成功，通知订阅者===================
                subscriber.onNext(newUrl);
                subscriber.onCompleted();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //===============================获取异常，发送自定义异常事件，调起重试机制========================
        subscriber.onError(new Exception(context.getResources().getString(R.string.HTTPDNS_Exception)));
        //注：测试时放开下面，注释掉上面，防止HTTPDNS解析失败调起5次重试机制
        subscriber.onNext(newUrl);
    }

    /**
     * 刷新Token的回调方法
     */
    public interface RefreshTokenFinishListener {
        /**
         * 刷新token结束
         * @param request
         */
        public void refreshTokenFinishListener(boolean isSucc, String request);
    }

    /**
     * RXjava请求模式
     * @param context
     * @param httpDnsListener
     */
    private void RXjavaRequest(final Context context,final HttpDnsListener httpDnsListener){
        //创建被观察者，获取HttpDns
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                getHttpDnsIp(context, Common_HttpPath.HTTP_HOST,subscriber);
            }
        }).retryWhen(new RetryWhenNetworkException())//重试机制
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        //结束订阅
                    }
                    @Override
                    public void onError(Throwable e) {
                        //如果获取IP失败，则用服务器域名访问
                        try {
                            httpDnsListener.requestListener(true,Common_HttpPath.HTTP_HOST+Common_HttpPath.PROJECT);
                        }catch (Exception exception){
                            exception.printStackTrace();
                        }
                    }
                    @Override
                    public void onNext(String newUrl) {
                        //如果获取IP成功，拼接IP地址继续执行
                        httpDnsListener.requestListener(true,newUrl+Common_HttpPath.PROJECT);
                    }
                });
    }
    @Override
    public  void uploadFile(String url, RequestBody requestBody, final Common_ResultDataListener resultListener, boolean isLoadingDialog){
        //如果和rxjava1.x , call就换成 Observable
        Common_RequestBodyApi requestBodyApi = new Common_RequestBodyApi(url,"POST");
        Retrofit retrofit= Common_Application.getInstance().getRetrofit(requestBodyApi);
        Common_HttpRequestService httpRequestService = retrofit.create(Common_HttpRequestService.class);
        Call<ResponseBody> call = httpRequestService.upLoad(url,requestBody);
        // 执行
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                L.e("Upload", "success");
                ResponseBody responseBody=response.body();
                Common_RequestBean common_requestBean=new Common_RequestBean();
                try {
                    common_requestBean.setData(responseBody.string());
                    resultListener.onResult(true,"上传成功",common_requestBean);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                L.e("Upload error:", t.getMessage());
                resultListener.onResult(true,"上传失败",null);
            }
        });
    }
}
