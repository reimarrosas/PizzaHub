package me.reimarrosas.pizzahub.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import java.util.Objects;

public class Drink extends Extras implements Parcelable {

    public Drink() {
        super();
    }

    public Drink(String name, String imageUrl, double price) {
        super(name, imageUrl, price);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Drink)) return false;
        Drink d = (Drink) obj;

        return getName().equals(d.getName()) &&
                getImageUrl().equals(d.getImageUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getImageUrl());
    }

    protected Drink(Parcel in) {
    }

    public static final Creator<Drink> CREATOR = new Creator<Drink>() {
        @Override
        public Drink createFromParcel(Parcel in) {
            return new Drink(in);
        }

        @Override
        public Drink[] newArray(int size) {
            return new Drink[size];
        }
    };

    @Override
    public String toString() {
        return String.format(
                "Drink={%s, %s, $%.2f}",
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
