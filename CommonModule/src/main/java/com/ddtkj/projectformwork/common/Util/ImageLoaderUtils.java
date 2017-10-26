package com.ddtkj.projectformwork.common.Util;

import android.app.Application;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ddtkj.projectformwork.common.Base.Common_Application;
import com.ddtkj.projectformwork.common.R;
import com.glide_library.GlideRoundTransform;


/**图片加载工具类
 * Created by Administrator on 2017/2/7.
 */

public class ImageLoaderUtils extends Application{
    //================================Glide加载其它资源图片不需要前缀，但是为了方便后期拓展和修改，请求其它资源加上前缀对象“”字符串==============
    public static final String RES_ASSETS = "";
    public static final String RES_SDCARD = "";
    public static final String RES_DRAWABLE = "";
    public static final String RES_HTTP = "";
    //===================================Netroid加载本地图片需要的前缀==============================
   /* public static final String RES_ASSETS = "assets://";
    public static final String RES_SDCARD = "sdcard://";
    public static final String RES_DRAWABLE = "drawable://";
    public static final String RES_HTTP = "http://";*/

    static ImageLoaderUtils mImageLoaderUtils;
    public static ImageLoaderUtils getInstance(Context context){
      if(mImageLoaderUtils==null){
          //第一次check，避免不必要的同步
          synchronized (ImageLoaderUtils.class){//同步
              if(mImageLoaderUtils==null){
                  //第二次check，保证线程安全
                  mImageLoaderUtils=new ImageLoaderUtils();
              }
          }
      }
        return mImageLoaderUtils;
    }

    public void displayImage(String url, ImageView imageView){
        //Netroid.displayImage(url,imageView);
        Glide.with(Common_Application.getApplication())
                .load(url)
                .placeholder(R.mipmap.common_icon_no_image)//默认加载图片
                .error(R.mipmap.common_icon_no_image)//错误图片
                .skipMemoryCache(true)//跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL)//硬盘缓存策略
                .animate(R.anim.common_item_alpha_in)//自定义加载动画
                .into(imageView);
    }
    public void displayNoAnimateImage(String url, ImageView imageView){
        //Netroid.displayImage(url,imageView);
        Glide.with(Common_Application.getApplication())
                .load(url)
                .placeholder(R.mipmap.common_icon_no_image)//默认加载图片
                .error(R.mipmap.common_icon_no_image)//错误图片
                .skipMemoryCache(true)//跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL)//硬盘缓存策略
                .dontAnimate()
                .into(imageView);
    }
    public void displayImage(String url, ImageView imageView,int radius){
        //Netroid.displayImage(url,imageView);
        Glide.with(Common_Application.getApplication())
                .load(url)
                .placeholder(R.mipmap.common_icon_no_image)//默认加载图片
                .error(R.mipmap.common_icon_no_image)//错误图片
                .skipMemoryCache(true)//跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL)//硬盘缓存策略
                .animate(R.anim.common_item_alpha_in)//自定义加载动画
                .transform(new GlideRoundTransform(Common_Application.getApplication(), radius))
                .into(imageView);
    }
    public void displayImage(int resourceId, ImageView imageView){
        //Netroid.displayImage(url,imageView);
        Glide.with(Common_Application.getApplication())
                .load(resourceId)
                .placeholder(R.mipmap.common_icon_no_image)//默认加载图片
                .error(R.mipmap.common_icon_no_image)//错误图片
                .skipMemoryCache(true)//跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL)//硬盘缓存策略
                .animate(R.anim.common_item_alpha_in)//自定义加载动画
                .into(imageView);
    }
    public  void displayImage(String url, ImageView imageView, int defaultImageResId, int errorImageResId) {
        //Netroid.displayImage(url,imageView,defaultImageResId,errorImageResId);
        Glide.with(Common_Application.getApplication())
                .load(url)
                .placeholder(defaultImageResId)
                .error(errorImageResId)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(imageView);
    }
    /**
     * 用于设置显示照片大小
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    public  void displayImageCustom(String url, ImageView imageView,int width, int height) {
        //Netroid.imagepickDisplayImage(url,imageView,width,height);
        Glide.with(Common_Application.getApplication())
                .load(url)
                .placeholder(R.mipmap.common_icon_no_image)
                .error(R.mipmap.common_icon_no_image)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .override(width,height)
                .into(imageView);
    }

    /**
     * 加载Gif图
     * @param url
     * @param imageView
     */
    public void displayGifImage(String url, ImageView imageView){
        Glide.with(Common_Application.getApplication())
                .load(url)
                .asGif()
                .placeholder(R.mipmap.common_icon_no_image)//默认加载图片
                .error(R.mipmap.common_icon_no_image)//错误图片
                .skipMemoryCache(true)//跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)//硬盘缓存策略
                .dontAnimate()
                .into(imageView);
    }
}
