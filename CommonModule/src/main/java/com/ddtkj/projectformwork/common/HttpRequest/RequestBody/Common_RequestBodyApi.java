package com.ddtkj.projectformwork.common.HttpRequest.RequestBody;

import com.ddtkj.projectformwork.common.HttpRequest.Interface.Common_HttpRequestService;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.Api.BaseApi;

import java.util.Map;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * 请求体封装
 */

public class Common_RequestBodyApi extends BaseApi {
    private String url;//请求路径
    private String requestMethod;//请求方法（POST GET）
    private Map<String, Object> params;//请求参数

    public Common_RequestBodyApi(String url, String requestMethod) {
        this.url = url;
        this.requestMethod = requestMethod;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    /**
     * 发起请求并返回Observable对象
     *
     * @param retrofit
     * @return
     */
    @Override
    public Observable getObservable(Retrofit retrofit) {
        Common_HttpRequestService httpRequestService = retrofit.create(Common_HttpRequestService.class);
        Observable<String> observable = null;
        switch (getRequestMethod()) {
            case "POST":
                observable = httpRequestService.requestPost(url, params);
                break;
            case "GET":
                if (getParams() == null) {
                    observable = httpRequestService.requestGet(url);
                } else {
                    observable = httpRequestService.requestGet(url,params);
                }
                break;
        }
        return observable;
    }
}
