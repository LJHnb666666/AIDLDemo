package com.example.aidldemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by didiwei on 2022/5/10
 * desc: Book实体类
 */
public class Book implements Parcelable {
    String name;//书名
    int cost;//价钱

    public Book(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }

    //注意：以下均为Parcelable接口需要实现的方法
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(cost);
    }
    //这个方法我用编译器始终搞不出来，建议大家直接复制粘贴这里
    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    private Book(Parcel in){
        this.name = in.readString();
        this.cost = in.readInt();
    }


}
