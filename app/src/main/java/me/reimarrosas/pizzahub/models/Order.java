package me.reimarrosas.pizzahub.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

public class Order implements Parcelable {

    private Size size;
    private List<Topping> toppings;
    private List<Side> sides;
    private List<Drink> drinks;
    private double price;
    private Date orderDate;
    private String userId;

    public Order(Size size,
                 List<Topping> toppings,
                 List<Side> sides,
                 List<Drink> drinks,
                 Date orderDate,
                 String userId) {
        this.size = size;
        this.toppings = toppings;
        this.sides = sides;
        this.drinks = drinks;
        price = calculatePrice();
        this.orderDate = orderDate;
        this.userId = userId;
    }

    public Order(Order p) {
        clearLists();
        size = new Size(p.size);
        toppings.addAll(p.toppings);
        sides.addAll(p.sides);
        drinks.addAll(p.drinks);
        price = calculatePrice();
        this.orderDate = p.orderDate;
        this.userId = p.userId;
    }

    protected Order(Parcel in) {
        size = in.readParcelable(Size.class.getClassLoader());
        toppings = in.createTypedArrayList(Topping.CREATOR);
        sides = in.createTypedArrayList(Side.CREATOR);
        drinks = in.createTypedArrayList(Drink.CREATOR);
        price = in.readDouble();
        userId = in.readString();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

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

    public double getPrice() {
        return price;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public String getUserId() {
        return userId;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(getPrice());
        parcel.writeString(getUserId());
    }

    @Override
    public String toString() {
        return "Order{" +
                "size=" + size +
                ", toppings=" + toppings +
                ", sides=" + sides +
                ", drinks=" + drinks +
                ", price=" + price +
                ", orderDate=" + orderDate +
                ", userId='" + userId + '\'' +
                '}';
    }

}
