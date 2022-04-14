package me.reimarrosas.pizzahub.models;

public class Topping extends Extras {

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

}
