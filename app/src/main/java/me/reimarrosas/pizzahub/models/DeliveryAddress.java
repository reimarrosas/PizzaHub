package me.reimarrosas.pizzahub.models;

import android.os.Parcel;
import android.os.Parcelable;

public class DeliveryAddress implements Parcelable {

    private String name;
    private String address1;
    private String address2;
    private String city;
    private String province;
    private String postalCode;

    public DeliveryAddress() {
    }

    protected DeliveryAddress(Parcel in) {
        name = in.readString();
        address1 = in.readString();
        address2 = in.readString();
        city = in.readString();
        province = in.readString();
        postalCode = in.readString();
    }

    public static final Creator<DeliveryAddress> CREATOR = new Creator<DeliveryAddress>() {
        @Override
        public DeliveryAddress createFromParcel(Parcel in) {
            return new DeliveryAddress(in);
        }

        @Override
        public DeliveryAddress[] newArray(int size) {
            return new DeliveryAddress[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "DeliveryAddress{" +
                "name='" + name + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(address1);
        parcel.writeString(address2);
        parcel.writeString(city);
        parcel.writeString(province);
        parcel.writeString(postalCode);
    }
}
