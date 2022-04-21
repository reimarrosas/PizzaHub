package me.reimarrosas.pizzahub.recycleradapters.adapterdata;

import androidx.annotation.Nullable;

import java.util.Objects;

import me.reimarrosas.pizzahub.contracts.Reproducible;
import me.reimarrosas.pizzahub.models.Topping;

public class ReproducibleTopping extends Reproducible<Topping> {

    public ReproducibleTopping(Topping data, boolean state) {
        super(data, state);
    }

    @Override
    public String toString() {
        return String.format("ReproducibleTopping={%s, %s}", getData(), getState());
    }
}
