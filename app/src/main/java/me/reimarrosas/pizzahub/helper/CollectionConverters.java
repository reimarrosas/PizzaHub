package me.reimarrosas.pizzahub.helper;

import androidx.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import me.reimarrosas.pizzahub.models.MenuItem;

public abstract class CollectionConverters {

    public static <T> List<T> fromArrayToList(T[] arr) {
        return Arrays.asList(arr);
    }

    public static <T> T[] fromListToArray(@NonNull List<T> l, Class<T> clazz) {
        T[] res = (T[]) Array.newInstance(clazz, l.size());
        return l.toArray(res);
    }

}
