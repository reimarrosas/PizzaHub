package me.reimarrosas.pizzahub.models;

import java.util.List;

public class Pizza {

    private Size size;
    private List<Topping> toppings;
    private List<Side> sides;
    private List<Drink> drinks;

    public Pizza(Size size, List<Topping> toppings, List<Side> sides, List<Drink> drinks) {
        this.size = size;
        this.toppings = toppings;
        this.sides = sides;
        this.drinks = drinks;
    }

    public Pizza(Size size, Premade premade, List<Side> sides, List<Drink> drinks) {
        this.size = size;
        this.toppings = premade.getToppings();
        this.sides = sides;
        this.drinks = drinks;
    }

    public Pizza(Pizza p) {
        clearLists();
        size = new Size(p.size);
        toppings.addAll(p.toppings);
        sides.addAll(p.sides);
        drinks.addAll(p.drinks);
    }

    public Size getSize() {
        return size;
    }

    public List<Topping> getToppings() {
        return toppings;
    }

    public List<Side> getSides() {
        return sides;
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    public double calculatePrice() {
        return size.getPrice() +
                getTotalToppingPrice() +
                getTotalDrinkPrice() +
                getTotalSidePrice();
    }

    private double getTotalDrinkPrice() {
        double totalPrice = 0.0f;

        for (Drink d : drinks) {
            totalPrice += d.getPrice();
        }

        return totalPrice;
    }

    private double getTotalSidePrice() {
        double totalPrice = 0.0f;

        for (Side s : sides) {
            totalPrice += s.getPrice();
        }

        return totalPrice;
    }

    private double getTotalToppingPrice() {
        double totalPrice = 0.0f;

        for (Topping t : toppings) {
            totalPrice += t.getPrice();
        }

        return totalPrice;
    }

    private void clearLists() {
        size = null;
        toppings.clear();
        sides.clear();
        drinks.clear();
    }

}
