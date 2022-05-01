package me.reimarrosas.pizzahub.services;

import android.util.Log;

import com.google.api.LogDescriptor;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

import me.reimarrosas.pizzahub.contracts.Notifiable;
import me.reimarrosas.pizzahub.contracts.Service;
import me.reimarrosas.pizzahub.models.MenuItem;
import me.reimarrosas.pizzahub.models.Topping;

public class ToppingService implements Service<Topping> {

    private static final String TAG = "ToppingService";
    private static final MenuItem.MenuItemType ITEM_TYPE = MenuItem.MenuItemType.TOPPING;

    private static final CollectionReference DB =
            FirebaseFirestore.getInstance()
                    .collection("toppings");

    private final Notifiable n;

    public ToppingService(Notifiable n) {
        this.n = n;
    }

    @Override
    public void fetchAllData(List<Topping> data) {
        DB.orderBy("type")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        data.addAll(snapshotToObject(task.getResult(), Topping.class));
                        n.notifyUpdatedData(data, ITEM_TYPE);
                        Log.d(TAG, "Topping List Fetching Success!");
                    } else {
                        Log.w(TAG, "Error fetching toppings: ", task.getException());
                    }
                });
    }

    @Override
    public void fetchSingleData(String dataId) {
        DB.document(dataId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QueryDocumentSnapshot ds = (QueryDocumentSnapshot) task.getResult();
                        Topping t = snapshotToObject(ds, Topping.class);
                        n.notifyUpdatedData(t, ITEM_TYPE);
                        Log.d(TAG, "Topping Fetching Success!");
                    } else {
                        Log.w(TAG, "Error fetching topping: ", task.getException());
                    }
                });
    }

    @Override
    public void upsertData(Topping data) {
        String id = DB.document().getId();
        if (data.getId() == null) {
            data.setId(id);
        }
        DB.document(data.getId())
                .set(data)
                .addOnSuccessListener(_void ->
                        Log.d(TAG, "Document upserted with ID: " + id))
                .addOnFailureListener(ex -> Log.w(TAG, "Error inserting document: ", ex));
    }

    @Override
    public void deleteData(String dataId) {
        DB.document(dataId)
                .delete()
                .addOnSuccessListener(_void ->
                        Log.d(TAG, "Document " + dataId + " successfully deleted"))
                .addOnFailureListener(ex -> Log.w(TAG, "Error deleting document: ", ex));
    }

}
