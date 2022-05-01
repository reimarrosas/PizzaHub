package me.reimarrosas.pizzahub.contracts;

import java.util.List;

public interface Updatable<T> {

    List<T> getDataList();

    void notifyDataListChange();

    default void notifyAddData(int position) {
    }

    default void notifyUpdateData(int position) {
    }

    default void updateDataList(List<T> data) {
        getDataList().clear();
        getDataList().addAll(data);
        notifyDataListChange();
    }

    default void addData(int position, T data) {
        getDataList().add(position, data);
        notifyAddData(position);
    }

    default void updateData(T oldData, T data) {
        int pos = getDataList().indexOf(oldData);
        if (pos != -1) {
            getDataList().set(pos, data);
            notifyUpdateData(pos);
        }
    }

}
