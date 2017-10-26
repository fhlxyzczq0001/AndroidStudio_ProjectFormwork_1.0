package com.ddtkj.projectformwork.MVP.Model.Bean.CommonBean;

import android.os.Parcel;
import android.os.Parcelable;

/**启动页bean
 * @ClassName: com.ygworld.MVP.Model.Bean
 * @author: Administrator 杨重诚
 * @date: 2016/10/14:15:35
 */

public class Main_WelcomePageBean implements Parcelable {
    private String url;
    private String link;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.link);
    }

    public Main_WelcomePageBean() {
    }

    protected Main_WelcomePageBean(Parcel in) {
        this.url = in.readString();
        this.link = in.readString();
    }

    public static final Parcelable.Creator<Main_WelcomePageBean> CREATOR = new Parcelable.Creator<Main_WelcomePageBean>() {
        @Override
        public Main_WelcomePageBean createFromParcel(Parcel source) {
            return new Main_WelcomePageBean(source);
        }

        @Override
        public Main_WelcomePageBean[] newArray(int size) {
            return new Main_WelcomePageBean[size];
        }
    };
}
