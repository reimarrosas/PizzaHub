package me.reimarrosas.pizzahub.contracts;

import android.util.Log;

import java.util.List;

public interface Updatable<T> {

    List<T> getDataList();

    void notifyDataListChange();

    default void updateDataList(List<T> data) {
        Log.d("Updatable", "updateDataList: " + data);
        getDataList().clear();
        getDataList().addAll(data);
        notifyDataListChange();
    }

}
