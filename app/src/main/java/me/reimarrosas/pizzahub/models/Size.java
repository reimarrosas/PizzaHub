package me.reimarrosas.pizzahub.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Size extends Extras implements Parcelable {

    private int size;

    public Size() {
    }

    public Size(String name, int size, double price, String imageUrl) {
        super(name, imageUrl, price);
        this.size = size;
    }

    public Size(Size s) {
        super(s.getName(), s.getImageUrl(), s.getPrice());
        this.size = s.size;
        setId(s.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Size)) return false;
        Size s = (Size) o;
        return getName().equals(s.getName()) &&
                getImageUrl().equals(s.getImageUrl()) &&
                getSize() == s.getSize();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getImageUrl(), getSize());
    }

    protected Size(Parcel in) {
        size = in.readInt();
    }

    public static final Creator<Size> CREATOR = new Creator<Size>() {
        @Override
        public Size createFromParcel(Parcel in) {
            return new Size(in);
        }

        @Override
        public Size[] newArray(int size) {
            return new Size[size];
        }
    };

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return String.format(
                "Size={%din, %s, %s, $%.2f}",
                getSize(), getName(), getImageUrl(), getPrice()
        );
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getName());
        parcel.writeInt(getSize());
        parcel.writeDouble(getPrice());
        parcel.writeString(getImageUrl());
    }
}
