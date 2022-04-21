package me.reimarrosas.pizzahub.models;

public class Drink extends Extras {

    public Drink() {
        super();
    }

    public Drink(String name, String imageUrl, double price) {
        super(name, imageUrl, price);
    }

    @Override
    public String toString() {
        return String.format(
                "Drink={%s, %s, $%.2f}",
                getName(), getImageUrl(), getPrice()
        );
    }

}
