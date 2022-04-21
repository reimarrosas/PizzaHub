package me.reimarrosas.pizzahub.contracts;

public abstract class Reproducible<T> {

    private T data;
    private boolean state;

    public Reproducible(T data, boolean state) {
        this.data = data;
        this.state = state;
    }

    public T getData() {
        return data;
    };

    public boolean getState() {
        return state;
    };

}
