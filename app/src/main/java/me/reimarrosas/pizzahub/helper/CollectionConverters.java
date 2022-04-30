package me.reimarrosas.pizzahub.helper;

import androidx.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.reimarrosas.pizzahub.models.Drink;
import me.reimarrosas.pizzahub.models.MenuItem;
import me.reimarrosas.pizzahub.models.Side;
import me.reimarrosas.pizzahub.models.Topping;
import me.reimarrosas.pizzahub.models.Triple;

public abstract class CollectionConverters {

    public static <T> List<T> fromArrayToList(T[] arr) {
        return new ArrayList<>(Arrays.asList(arr));
    }

    public static <T> T[] fromListToArray(@NonNull List<T> l, Class<T> clazz) {
        T[] res = (T[]) Array.newInstance(clazz, l.size());
        return l.toArray(res);
    }

    public static List<Triple<String, String, String>> zipOrderNamesByLongest(
            List<Side> sides,
            List<Topping> toppings,
            List<Drink> drinks) {
        int sideSize = sides.size();
        int toppingSize = toppings.size();
        int drinkSize = drinks.size();
        int maxSize = Math.max(sideSize, Math.max(toppingSize, drinkSize));
        List<Triple<String, String, String>> orderExtrasTriples = new ArrayList<>();

        for (int i = 0; i < maxSize; ++i) {
            String sideName = sideSize > i ? sides.get(i).getName() : "";
            String toppingName = toppingSize > i ? toppings.get(i).getName() : "";
            String drinkName = drinkSize > i ? drinks.get(i).getName() : "";

            orderExtrasTriples.add(new Triple<>(sideName, toppingName, drinkName));
        }

        return orderExtrasTriples;
    }

}
