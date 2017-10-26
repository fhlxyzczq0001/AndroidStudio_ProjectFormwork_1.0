package com.ddtkj.projectformwork.common.HttpRequest.HttpManager;

import com.ddtkj.projectformwork.common.HttpRequest.Common_HttpPath;
import com.ddtkj.projectformwork.common.HttpRequest.Interface.Common_HttpRequestService;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.DownInfo;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.downlaod.DownLoadListener.DownloadInterceptor;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.HttpTimeException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.exception.RetryWhenNetworkException;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.subscribers.ProgressDownSubscriber;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.wzgiceman.rxretrofitlibrary.retrofit_rx.utils.AppUtil.writeCache;

/**
 * http下载处理类
 * Created by WZG on 2016/7/16.
 */
public class Common_HttpDownManager {
    /*单利对象*/
    private volatile static Common_HttpDownManager INSTANCE;

    /**
     * 获取单例
     * @return
     */
    public static Common_HttpDownManager getInstance() {
        if (INSTANCE == null) {
            synchronized (Common_HttpDownManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Common_HttpDownManager();
                }
            }
        }
        return INSTANCE;
    }


    /**
     * 开始下载
     */
    public void startDown(final DownInfo info){
        /*添加回调处理类*/
        ProgressDownSubscriber subscriber=new ProgressDownSubscriber(info);
        DownloadInterceptor interceptor = new DownloadInterceptor(subscriber);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //手动创建一个OkHttpClient并设置超时时间
        builder.connectTimeout(info.getConnectonTime(), TimeUnit.SECONDS);
        builder.addInterceptor(interceptor);

            Retrofit retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(Common_HttpPath.HTTP_HOST_PROJECT)
                    .build();
        Common_HttpRequestService httpRequestService = retrofit.create(Common_HttpRequestService.class);
        /*得到rx对象-上一次下載的位置開始下載*/
        httpRequestService.download(info.getUrl())
                /*指定线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                   /*失败后的retry配置*/
                .retryWhen(new RetryWhenNetworkException())
                /*读取下载写入文件*/
                .map(new Func1<ResponseBody, DownInfo>() {
                    @Override
                    public DownInfo call(ResponseBody responseBody) {
                        try {
                            writeCache(responseBody,new File(info.getSavePath()),info);
                        } catch (IOException e) {
                            e.printStackTrace();
                            /*失败抛出异常*/
                            throw new HttpTimeException(e.getMessage());
                        }
                        return info;
                    }
                })
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread())
                /*数据回调*/
                .subscribe(subscriber);

    }

}
