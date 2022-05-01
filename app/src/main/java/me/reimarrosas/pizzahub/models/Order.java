package me.reimarrosas.pizzahub.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order implements Parcelable {

    private String id;
    private Size size;
    private List<Topping> toppings;
    private List<Side> sides;
    private List<Drink> drinks;
    private double price;
    private Date orderDate;
    private String userId;
    private DeliveryAddress address;
    private String status;

    public Order(Size size,
                 List<Topping> toppings,
                 List<Side> sides,
                 List<Drink> drinks,
                 double price,
                 Date orderDate,
                 String userId,
                 DeliveryAddress address,
                 String status) {
        this.size = size;
        this.toppings = toppings;
        this.sides = sides;
        this.drinks = drinks;
        this.price = price;
        this.orderDate = orderDate;
        this.userId = userId;
        this.address = address;
        this.status = status;
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
        this.status = "pending";
    }

    public Order(Order p) {
        clearLists();
        size = new Size(p.size);
        toppings.addAll(p.toppings);
        sides.addAll(p.sides);
        drinks.addAll(p.drinks);
        this.orderDate = p.orderDate;
        this.userId = p.userId;
        this.status = "pending";
        setId(p.getId());
    }

    protected Order(Parcel in) {
        id = in.readString();
        size = in.readParcelable(Size.class.getClassLoader());
        toppings = in.createTypedArrayList(Topping.CREATOR);
        sides = in.createTypedArrayList(Side.CREATOR);
        drinks = in.createTypedArrayList(Drink.CREATOR);
        price = in.readDouble();
        userId = in.readString();
        address = in.readParcelable(DeliveryAddress.class.getClassLoader());
        status = in.readString();
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

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public BigDecimal calculateNetPrice() {
        return calculateTaxablePrice()
                .add(calculateQuebecTax())
                .add(calculateFederalTax())
                .add(BigDecimal.valueOf(2.99));
    }

    public BigDecimal calculateFederalTax() {
        return calculateTaxablePrice()
                .multiply(BigDecimal.valueOf(0.05));
    }

    public BigDecimal calculateQuebecTax() {
        return calculateTaxablePrice()
                .multiply(BigDecimal.valueOf(0.09975));
    }

    public BigDecimal calculateTaxablePrice() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        parcel.writeString(id);
        parcel.writeDouble(getPrice());
        parcel.writeString(getUserId());
        parcel.writeString(status);
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
