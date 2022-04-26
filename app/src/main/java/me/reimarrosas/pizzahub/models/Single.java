package me.reimarrosas.pizzahub.models;

public class Single<T> {

    private T data;

    public Single(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Single{" +
                "data=" + data +
                '}';
    }

}
