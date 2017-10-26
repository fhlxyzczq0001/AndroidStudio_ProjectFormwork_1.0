package com.ddtkj.projectformwork.common.HttpRequest.Interceptor;

import com.ddtkj.projectformwork.common.Base.Common_Application;
import com.ddtkj.projectformwork.common.MVP.Model.Bean.CommonBean.Common_CookieMapBean;
import com.ddtkj.projectformwork.common.Util.Common_SharePer_UserInfo;
import com.utlis.lib.L;
import com.utlis.lib.ToolsUtils;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求封装拦截器——（重定向/封装请求头/缓存）
 */
public class Common_RequestEncapsulationInterceptor implements Interceptor{

    @Override
    public Response intercept(Chain chain) throws IOException
    {
        //获取请求对象
        Request request = chain.request();
        //如果是get请求，不进行封装，直接返回
        if(request.method().contains("GET")){
            return chain.proceed(request);
        }
        //=============================封装请求头===============================
        Request.Builder newHeaderBuilder = request.newBuilder().url(request.url());
        String userAgent = "(Android " + android.os.Build.VERSION.RELEASE + ";" + "JSD/" + "1.0" + ")";
        newHeaderBuilder.addHeader("User-Agent", String.format(userAgent, "", "", "", "", ""));
        //给请求设置Host
        String httpdnsMapKey=request.url().toString().substring(0, ToolsUtils.getCharacterPosition(request.url().toString(),"/",4));
        if(null!= Common_Application.httpdnsHosMap.get(httpdnsMapKey)&&!Common_Application.httpdnsHosMap.get(httpdnsMapKey).isEmpty()){
            newHeaderBuilder.header("Host", Common_Application.httpdnsHosMap.get(httpdnsMapKey));
        }else {
            newHeaderBuilder.header("Host", request.url().host());
        }
        //=================获取cookie缓存对象==============================
        HashSet<String> cookie_session=getCookie(request.url().host());
        if(null!=cookie_session&&!cookie_session.isEmpty()){
            for(String cookie:cookie_session){
                L.e("==Cookie===",cookie);
                newHeaderBuilder.addHeader("Cookie", cookie);
            }
        }
        //============================================================================

        //继续请求服务器并返回response
        Response response = chain.proceed(newHeaderBuilder.build());
        //获取返回状态码
        int responseCode=response.code();
        //=================================获取登录成功的cookie拦截,保存cookie===========================
        saveCookie(response.headers(),request.url().host());
        //=================================重定向拦截===========================
        while (responseCode== 301||responseCode == 302){
            //获取重定向中返回请求头
            Headers LocationHeaders=response.headers();
            String location=LocationHeaders.get("Location");
            URL url=new URL(location);
            //封装新的请求对象
            Request.Builder builder = request.newBuilder().url(location);
            //=================获取cookie缓存对象==============================
            HashSet<String> locationCookieSession=getCookie(url.getHost());
            if(null!=locationCookieSession&&!locationCookieSession.isEmpty()){
                for(String cookie:locationCookieSession){
                    builder.addHeader("Cookie", cookie);
                }
            }
            builder.get();
            //添加请求头（addHeader是不覆盖请求参数，header是会覆盖之前的请求参数）
           /* builder.addHeader("Cookie" ,"val1");
            builder.addHeader("Cookie" ,"val2");
            builder.addHeader("valse","123");
            builder.header("valse","222");
            //如果是post请求，封装请求体参数
            RequestBody requestBodyPost = new FormBody.Builder()
                    .add("page", "1")
                    .add("code", "news")
                    .add("pageSize", "20")
                    .add("parentid", "0")
                    .add("type", "1")
                    .build();
            //请求方法
            builder.post(requestBodyPost);*/
            //新的请求对象
            Request newRequest=builder.build();
            response=chain.proceed(newRequest);
            //获取返回状态码
             responseCode=response.code();
            //===================缓存cookie=================================
            saveCookie(response.headers(),url.getHost());
        }
        return response;
    }
    private void saveCookie(Headers head,String keyHost){
        HashSet<String> set_cookie =getCookie(keyHost);
        if(set_cookie==null){
            set_cookie=new HashSet<>();
        }
        for(int i=0; i<head.size(); i++){
            if( head.name(i).equals("Set-Cookie")){
                //截取Set-Cookie第一个=前的信息
                String setCookieSub="";
                if(head.value(i).contains("=")){
                    setCookieSub=head.value(i).substring(0,head.value(i).indexOf("="));
                }
                //如果包含这些去循环缓存的set_cookie
                if(head.value(i).contains("sid=")||head.value(i).contains("JSESSIONID=")||head.value(i).contains("CASTGC=")){
                    for(String cookie:set_cookie){
                        //如果cookie包含setCookieSub,移除缓存对象
                        if(cookie.contains(setCookieSub)){
                            set_cookie.remove(cookie);
                            break;
                        }
                    }
                }
                //添加新的cookie对象信息
                set_cookie.add(head.value(i));
            }
        }
        //如果set_cookie不为空，怎存在本地
        if(null!=set_cookie&&!set_cookie.isEmpty()){
            Common_CookieMapBean setCookie_MapBean= Common_SharePer_UserInfo.sharePre_GetCookieCache();
            if(setCookie_MapBean==null){
                setCookie_MapBean=new Common_CookieMapBean();
                setCookie_MapBean.setCookieMap(new HashMap<String,HashSet<String>>());
            }
            setCookie_MapBean.getCookieMap().put(keyHost,set_cookie);
            Common_SharePer_UserInfo.sharePre_PutCookieCache(setCookie_MapBean);
        }
    }

    private HashSet<String> getCookie(String keyHost){
        //获取cookie缓存对象
        Common_CookieMapBean cookieMapBean= Common_SharePer_UserInfo.sharePre_GetCookieCache();
        if(null != cookieMapBean && !"".equals(cookieMapBean)){
            HashSet<String> hosts_cookie= cookieMapBean.getCookieMap().get(keyHost);
            if(null!=hosts_cookie&&!hosts_cookie.isEmpty()){
                return hosts_cookie;
            }
        }
        return null;
    }
}
