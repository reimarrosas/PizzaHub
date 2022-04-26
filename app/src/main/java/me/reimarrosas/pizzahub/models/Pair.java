package me.reimarrosas.pizzahub.models;

public class Pair<T, U> {

    private T data1;
    private U data2;

    public Pair(T data1, U data2) {
        this.data1 = data1;
        this.data2 = data2;
    }

    public T getData1() {
        return data1;
    }

    public U getData2() {
        return data2;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "data1=" + data1 +
                ", data2=" + data2 +
                '}';
    }

}
