package me.reimarrosas.pizzahub.services;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import me.reimarrosas.pizzahub.contracts.Service;
import me.reimarrosas.pizzahub.models.MenuItem;
import me.reimarrosas.pizzahub.models.Size;

public class SizeService implements Service<Size> {

    private static final String TAG = "SizeService";
    private static final MenuItem.MenuItemType ITEM_TYPE = MenuItem.MenuItemType.SIZE;

    private static final CollectionReference DB =
            FirebaseFirestore.getInstance()
                    .collection("sizes");

    @Override
    public void fetchAllData(List<Size> data) {

    }

    @Override
    public void fetchSingleData(String dataId) {

    }

    @Override
    public void insertData(Size data) {

    }

    @Override
    public void deleteData(String dataId) {

    }

}
