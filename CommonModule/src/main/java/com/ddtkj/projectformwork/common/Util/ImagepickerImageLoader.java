package com.ddtkj.projectformwork.common.Util;

import android.app.Activity;
import android.widget.ImageView;

import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;

/**本地照片展示
 * @author: Administrator 杨重诚
 * @date: 2016/10/11:16:15
 */

public class ImagepickerImageLoader implements ImageLoader {
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        ImageLoaderUtils.getInstance(activity.getApplicationContext()).displayImageCustom(ImageLoaderUtils.RES_SDCARD+new File(path).toString(),imageView,width,height);
    }

    @Override
    public void clearMemoryCache() {

    }
}
