package me.reimarrosas.pizzahub.models;

public abstract class Extras extends MenuItem {

    private double price;

    public Extras() {
        super();
    }

    public Extras(String name, String imageUrl, double price) {
        super(name, imageUrl);
        this.price = price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

}
