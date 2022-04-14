package me.reimarrosas.pizzahub.models;

import androidx.annotation.NonNull;

public class Side extends Extras {

    public Side() {
    }

    public Side(String name, String imageUrl, double price) {
        super(name, imageUrl, price);
    }

    public Side(Side s) {
        super(s.getName(), s.getImageUrl(), s.getPrice());
    }

    @Override
    public String toString() {
        return String.format(
                "Side={%s, %s, $%.2f}",
                getName(), getImageUrl(), getPrice()
        );
    }

}
