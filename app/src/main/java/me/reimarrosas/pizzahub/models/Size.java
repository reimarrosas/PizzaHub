package me.reimarrosas.pizzahub.models;

public class Size extends Extras {

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
    }

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

}
