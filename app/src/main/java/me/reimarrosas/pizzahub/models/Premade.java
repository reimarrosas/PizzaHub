package me.reimarrosas.pizzahub.models;

import java.util.List;

public class Premade extends MenuItem {

    private List<Topping> toppings;

    public Premade() {
    }

    public Premade(String name, String imageUrl, List<Topping> toppings) {
        super(name, imageUrl);
        this.toppings = toppings;
    }

    public Premade(Premade p) {
        super(p.getName(), p.getImageUrl());
        this.toppings = p.toppings;
    }

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

}
