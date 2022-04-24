package me.reimarrosas.pizzahub.models;

public class PaymentIntention {

    private String paymentIntent;
    private String ephemeralKey;
    private String customer;
    private String publishableKey;

    public PaymentIntention() {}

    public String getPaymentIntent() {
        return paymentIntent;
    }

    public void setPaymentIntent(String paymentIntent) {
        this.paymentIntent = paymentIntent;
    }

    public String getEphemeralKey() {
        return ephemeralKey;
    }

    public void setEphemeralKey(String ephemeralKey) {
        this.ephemeralKey = ephemeralKey;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPublishableKey() {
        return publishableKey;
    }

    public void setPublishableKey(String publishableKey) {
        this.publishableKey = publishableKey;
    }

    @Override
    public String toString() {
        return "PaymentIntention{" +
                "paymentIntent='" + paymentIntent + '\'' +
                ", ephemeralKey='" + ephemeralKey + '\'' +
                ", customer='" + customer + '\'' +
                ", publishableKey='" + publishableKey + '\'' +
                '}';
    }

}
