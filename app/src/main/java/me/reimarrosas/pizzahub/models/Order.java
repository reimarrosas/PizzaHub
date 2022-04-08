package me.reimarrosas.pizzahub.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private Long orderNo;
    private BigDecimal totalPrice;
    private List<PizzaParameter> pizzaParameterList;

    public Order() {
        orderNo = 0L;
        totalPrice = BigDecimal.ZERO;
        pizzaParameterList = new ArrayList<>();
    }

    public Order(Long orderNo, BigDecimal totalPrice, List<PizzaParameter> pizzaParameterList) {
        this.orderNo = orderNo;
        this.totalPrice = totalPrice;
        this.pizzaParameterList = pizzaParameterList;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean addToPizzaParameterList(PizzaParameter p) {
        return pizzaParameterList.add(p);
    }

    public boolean removeFromPizzaParameterList(PizzaParameter p) {
        return pizzaParameterList.remove(p);
    }

    public void clearPizzaParameterList() {
        pizzaParameterList.clear();
    }

}
