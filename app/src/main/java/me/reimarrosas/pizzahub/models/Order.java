package me.reimarrosas.pizzahub.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
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
    private DeliveryAddress address;

    public Order(Size size,
                 List<Topping> toppings,
                 List<Side> sides,
                 List<Drink> drinks,
                 double price,
                 Date orderDate,
                 String userId,
                 DeliveryAddress address) {
        this.size = size;
        this.toppings = toppings;
        this.sides = sides;
        this.drinks = drinks;
        this.price = price;
        this.orderDate = orderDate;
        this.userId = userId;
        this.address = address;
    }

    public Order(Size size,
                 List<Topping> toppings,
                 List<Side> sides,
                 List<Drink> drinks,
                 String userId) {
        this.size = size;
        this.toppings = toppings;
        this.sides = sides;
        this.drinks = drinks;
        this.userId = userId;
    }

    public Order(Order p) {
        clearLists();
        size = new Size(p.size);
        toppings.addAll(p.toppings);
        sides.addAll(p.sides);
        drinks.addAll(p.drinks);
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

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getUserId() {
        return userId;
    }

    private BigDecimal calculateNetPrice() {
        return calculateTaxablePrice()
                .add(calculateQuebecTax())
                .add(calculateFederalTax())
                .add(BigDecimal.valueOf(2.99));
    }

    private BigDecimal calculateFederalTax() {
        return calculateTaxablePrice()
                .multiply(BigDecimal.valueOf(0.05));
    }

    private BigDecimal calculateQuebecTax() {
        return calculateTaxablePrice()
                .multiply(BigDecimal.valueOf(0.09975));
    }

    private BigDecimal calculateTaxablePrice() {
        return BigDecimal.valueOf(size.getPrice())
                .add(getTotalToppingPrice())
                .add(getTotalSidePrice())
                .add(getTotalDrinkPrice());
    }

    private BigDecimal getTotalDrinkPrice() {
        BigDecimal total = BigDecimal.ZERO;

        for (Drink d : drinks) {
            total = total.add(BigDecimal.valueOf(d.getPrice()));
        }

        return total;
    }

    private BigDecimal getTotalSidePrice() {
        BigDecimal total = BigDecimal.ZERO;

        for (Side s : sides) {
            total = total.add(BigDecimal.valueOf(s.getPrice()));
        }

        return total;
    }

    private BigDecimal getTotalToppingPrice() {
        BigDecimal total = BigDecimal.ZERO;

        for (Topping t : toppings) {
            total = total.add(BigDecimal.valueOf(t.getPrice()));
        }

        return total;
    }

    public DeliveryAddress getAddress() {
        return address;
    }

    public void setAddress(DeliveryAddress address) {
        this.address = address;
    }

    public void recalculatePrice() {
        this.price = calculateNetPrice().doubleValue();
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
                ", address=" + address +
                '}';
    }
}
