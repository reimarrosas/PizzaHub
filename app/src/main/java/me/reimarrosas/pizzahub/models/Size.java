package me.reimarrosas.pizzahub.models;

public class Size extends Extras {

    private int size;

    public Size() {
    }

    public Size(String name, int size, double price, String imageUrl) {
        super(name, imageUrl, price);
        this.size = size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

}
