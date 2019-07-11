package com.qs.base.contract;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

public class UserInfoEntity extends BaseObservable implements Parcelable {

    private String id;
    private String sid;
    private String name;
    private String avatar;
    private int sex;
    private int age;
    private String describel;


    protected UserInfoEntity(Parcel in) {
        id = in.readString();
        sid = in.readString();
        name = in.readString();
        avatar = in.readString();
        sex = in.readInt();
        age = in.readInt();
        describel = in.readString();
    }

    public static final Creator<UserInfoEntity> CREATOR = new Creator<UserInfoEntity>() {
        @Override
        public UserInfoEntity createFromParcel(Parcel in) {
            return new UserInfoEntity(in);
        }

        @Override
        public UserInfoEntity[] newArray(int size) {
            return new UserInfoEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(sid);
        dest.writeString(name);
        dest.writeString(avatar);
        dest.writeInt(sex);
        dest.writeInt(age);
        dest.writeString(describel);
    }

    public UserInfoEntity(String id, String sid, String name, String avatar, int sex, int age, String describel) {
        this.id = id;
        this.sid = sid;
        this.name = name;
        this.avatar = avatar;
        this.sex = sex;
        this.age = age;
        this.describel = describel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescribel() {
        return describel;
    }

    public void setDescribel(String describel) {
        this.describel = describel;
    }
}
