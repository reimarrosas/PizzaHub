package me.reimarrosas.pizzahub.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Side extends Extras implements Parcelable {

    public Side() {
    }

    public Side(String name, String imageUrl, double price) {
        super(name, imageUrl, price);
    }

    public Side(Side s) {
        super(s.getName(), s.getImageUrl(), s.getPrice());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Side)) return false;
        Side s = (Side) obj;
        return getName().equals(s.getName()) &&
                getImageUrl().equals(s.getImageUrl()) &&
                getPrice() == s.getPrice();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    protected Side(Parcel in) {
    }

    public static final Creator<Side> CREATOR = new Creator<Side>() {
        @Override
        public Side createFromParcel(Parcel in) {
            return new Side(in);
        }

        @Override
        public Side[] newArray(int size) {
            return new Side[size];
        }
    };

    @Override
    public String toString() {
        return String.format(
                "Side={%s, %s, $%.2f}",
                getName(), getImageUrl(), getPrice()
        );
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getName());
        parcel.writeString(getImageUrl());
        parcel.writeDouble(getPrice());
    }
}
