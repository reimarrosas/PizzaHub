package me.reimarrosas.pizzahub.models;

public class Topping extends Extras {

    private String type;

    public Topping() {
    }

    public Topping(String name, String imageUrl, double price, String type) {
        super(name, imageUrl, price);
        this.type = type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
