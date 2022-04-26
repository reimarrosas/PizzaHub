package me.reimarrosas.pizzahub.models;

public class Triple<T, U, V> {

    private T data1;
    private U data2;
    private V data3;

    public Triple(T data1, U data2, V data3) {
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;
    }

    public T getData1() {
        return data1;
    }

    public U getData2() {
        return data2;
    }

    public V getData3() {
        return data3;
    }

    @Override
    public String toString() {
        return "Triple{" +
                "data1=" + data1 +
                ", data2=" + data2 +
                ", data3=" + data3 +
                '}';
    }

}
