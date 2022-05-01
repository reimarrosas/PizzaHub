package me.reimarrosas.pizzahub.services;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import me.reimarrosas.pizzahub.contracts.Notifiable;
import me.reimarrosas.pizzahub.contracts.Service;
import me.reimarrosas.pizzahub.models.Drink;
import me.reimarrosas.pizzahub.models.MenuItem;

public class DrinkService implements Service<Drink> {

    private static final String TAG = "DrinkService";
    private static final MenuItem.MenuItemType ITEM_TYPE = MenuItem.MenuItemType.DRINK;

    private static final CollectionReference DB =
            FirebaseFirestore.getInstance()
                    .collection("drinks");

    private final Notifiable n;

    public DrinkService(Notifiable n) {
        this.n = n;
    }

    @Override
    public void fetchAllData(List<Drink> data) {
        DB.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot snapshotList = task.getResult();
                        data.addAll(snapshotToObject(snapshotList, Drink.class));
                        n.notifyUpdatedData(data, ITEM_TYPE);
                        Log.d(TAG, "Drink List Fetching Successful!");
                    } else {
                        Log.w(TAG, "Error Fetching Drink List: ", task.getException());
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
                        Drink d = snapshotToObject(ds, Drink.class);
                    } else {
                        Log.w(TAG, "Error Fetching Drink: ", task.getException());
                    }
                });
    }

    @Override
    public void upsertData(Drink data) {
        DB.add(data)
                .addOnSuccessListener(ref ->
                        Log.d(TAG, "Document written on ID: " + ref.getId()))
                .addOnFailureListener(ex -> Log.w(TAG, "Error inserting document: ", ex));
    }

    @Override
    public void deleteData(String dataId) {
        DB.document(dataId)
                .delete()
                .addOnSuccessListener(ref ->
                        Log.d(TAG, "Document " + dataId + " successfully deleted"))
                .addOnFailureListener(ex -> Log.w(TAG, "Error deleting document: ", ex));
    }

}
