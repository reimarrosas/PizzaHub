package me.reimarrosas.pizzahub.services;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import me.reimarrosas.pizzahub.contracts.Notifiable;
import me.reimarrosas.pizzahub.contracts.Service;
import me.reimarrosas.pizzahub.models.MenuItem;
import me.reimarrosas.pizzahub.models.Size;

public class SizeService implements Service<Size> {

    private static final String TAG = "SizeService";
    private static final MenuItem.MenuItemType ITEM_TYPE = MenuItem.MenuItemType.SIZE;

    private static final CollectionReference DB =
            FirebaseFirestore.getInstance()
                    .collection("sizes");

    private final Notifiable n;

    public SizeService(Notifiable n) {
        this.n = n;
    }

    @Override
    public void fetchAllData(List<Size> data) {
        DB.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        data.addAll(snapshotToObject(task.getResult(), Size.class));
                        n.notifyUpdatedData(data, ITEM_TYPE);
                        Log.d(TAG, "Size List Fetching Successful!");
                    } else {
                        Log.w(TAG, "Error Fetching Size List: ", task.getException());
                    }
                });
    }

    @Override
    public void fetchSingleData(String dataId) {

    }

    @Override
    public void upsertData(Size data) {

    }

    @Override
    public void deleteData(String dataId) {

    }

}
