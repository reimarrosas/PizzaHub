package me.reimarrosas.pizzahub.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class Premade extends MenuItem implements Parcelable {

    private List<Topping> toppings;

    public Premade() {
        super("", "");
        toppings = new ArrayList<>();
    }

    public Premade(String name, String imageUrl, List<Topping> toppings) {
        super(name, imageUrl);
        this.toppings = toppings;
    }

    public Premade(Premade p) {
        super(p.getName(), p.getImageUrl());
        this.toppings = p.toppings;
        setId(p.getId());
    }

    protected Premade(Parcel in) {
        toppings = in.createTypedArrayList(Topping.CREATOR);
    }

    public static final Creator<Premade> CREATOR = new Creator<Premade>() {
        @Override
        public Premade createFromParcel(Parcel in) {
            return new Premade(in);
        }

        @Override
        public Premade[] newArray(int size) {
            return new Premade[size];
        }
    };

    public void setToppings(List<Topping> toppings) {
        this.toppings = toppings;
    }

    public List<Topping> getToppings() {
        return toppings;
    }

    @Override
    public String toString() {
        return String.format(
                "Premade={%s, %s, %s}",
                getName(), getImageUrl(), getToppings().toString()
        );
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Premade)) return false;
        Premade premade = (Premade) o;
        return getImageUrl().equals(premade.getImageUrl()) &&
                getName().equals(premade.getName()) &&
                getToppings().equals(premade.getToppings());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getImageUrl(), getName(), getToppings());
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(toppings);
        parcel.writeString(getName());
        parcel.writeString(getImageUrl());
    }
}
