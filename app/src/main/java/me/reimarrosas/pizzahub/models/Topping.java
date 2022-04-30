package me.reimarrosas.pizzahub.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Topping extends Extras implements Parcelable {

    private String type;

    public Topping() {
    }

    public Topping(String name, String imageUrl, double price, String type) {
        super(name, imageUrl, price);
        this.type = type;
    }

    public Topping(Topping t) {
        super(t.getName(), t.getImageUrl(), t.getPrice());
        this.type = t.type;
    }

    protected Topping(Parcel in) {
        type = in.readString();
    }

    public static final Creator<Topping> CREATOR = new Creator<Topping>() {
        @Override
        public Topping createFromParcel(Parcel in) {
            return new Topping(in);
        }

        @Override
        public Topping[] newArray(int size) {
            return new Topping[size];
        }
    };

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format(
                "Topping={%s, %s, %s, $%.2f}",
                getType(), getName(), getImageUrl(), getPrice()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Topping)) return false;
        Topping topping = (Topping) o;
        return getImageUrl().equals(topping.getImageUrl()) &&
                getName().equals(topping.getName()) &&
                getType().equals(topping.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getImageUrl(), getName(), getType());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getType());
        parcel.writeString(getName());
        parcel.writeString(getImageUrl());
        parcel.writeDouble(getPrice());
    }

}
