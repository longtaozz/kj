package com.qs.setting.entity;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

public class SettingEntity extends BaseObservable implements Parcelable{

    private String id;
    //类型 1 正常, -1 间隔线
    private int type;
    private String title;
    private String describel;
    private String unread;
    //路由路径
    private String routerPath;


    public SettingEntity(String id, int type, String title, String describel, String unread, String routerPath) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.describel = describel;
        this.unread = unread;
        this.routerPath = routerPath;
    }

    protected SettingEntity(Parcel in) {
        id = in.readString();
        type = in.readInt();
        title = in.readString();
        describel = in.readString();
        unread = in.readString();
        routerPath = in.readString();
    }

    public static final Creator<SettingEntity> CREATOR = new Creator<SettingEntity>() {
        @Override
        public SettingEntity createFromParcel(Parcel in) {
            return new SettingEntity(in);
        }

        @Override
        public SettingEntity[] newArray(int size) {
            return new SettingEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(type);
        dest.writeString(title);
        dest.writeString(describel);
        dest.writeString(unread);
        dest.writeString(routerPath);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribel() {
        return describel;
    }

    public void setDescribel(String describel) {
        this.describel = describel;
    }

    public String getUnread() {
        return unread;
    }

    public void setUnread(String unread) {
        this.unread = unread;
    }

    public String getRouterPath() {
        return routerPath;
    }

    public void setRouterPath(String routerPath) {
        this.routerPath = routerPath;
    }
}
