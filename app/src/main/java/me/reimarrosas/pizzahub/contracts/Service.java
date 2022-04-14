package me.reimarrosas.pizzahub.contracts;

import android.util.Log;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import me.reimarrosas.pizzahub.models.MenuItem;

public interface Service<T extends MenuItem> {

    void fetchAllData(List<T> data);

    void fetchSingleData(String dataId);

    void insertData(T data);

    void deleteData(String dataId);

    default List<T> snapshotToObject(QuerySnapshot snapshotList, Class<T> clazz) {
        List<T> res = new ArrayList<>();

        for (QueryDocumentSnapshot ds : snapshotList) {
            T t = ds.toObject(clazz);
            t.setId(ds.getId());
            res.add(t);
        }

        return res;
    }

    default T snapshotToObject(QueryDocumentSnapshot ds, Class<T> clazz) {
        T t = ds.toObject(clazz);
        t.setId(ds.getId());

        return t;
    }

}
