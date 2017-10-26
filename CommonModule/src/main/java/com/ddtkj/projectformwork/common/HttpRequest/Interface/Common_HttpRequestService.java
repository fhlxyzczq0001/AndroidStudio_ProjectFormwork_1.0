package com.ddtkj.projectformwork.common.HttpRequest.Interface;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 请求接口
 */

public interface Common_HttpRequestService {
    @GET()//get请求 @Url——请求路径 @QueryMap——请求参数
    Observable<String> requestGet(@Url String url, @QueryMap Map<String, Object> params);


    @GET()//get请求 @Url——请求路径 @QueryMap——请求参数
    Observable<String> requestGet(@Url String url);

    @FormUrlEncoded
    @POST()//post请求 @Url——请求路径 @QueryMap——请求参数
    Observable<String> requestPost(@Url String url, @FieldMap Map<String, Object> params);

    @Streaming/*大文件需要加入这个判断，防止下载过程中写入到内存中*/
    @GET
    Observable<ResponseBody> download(@Url String url);
    /**
     * 文件上传
     * @param url
     * @param Body
     * @return
     */
    @POST()
    Call<ResponseBody> upLoad(@Url() String url, @Body RequestBody Body);
}
