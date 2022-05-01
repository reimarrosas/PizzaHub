package me.reimarrosas.pizzahub.services;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

import me.reimarrosas.pizzahub.contracts.Notifiable;
import me.reimarrosas.pizzahub.contracts.Service;
import me.reimarrosas.pizzahub.models.MenuItem;
import me.reimarrosas.pizzahub.models.Side;
import me.reimarrosas.pizzahub.models.Topping;

public class SideService implements Service<Side> {

    private static final String TAG = "SideService";
    private static final MenuItem.MenuItemType ITEM_TYPE = MenuItem.MenuItemType.SIDE;

    private static final CollectionReference DB =
            FirebaseFirestore.getInstance()
                    .collection("sides");

    private final Notifiable n;

    public SideService(Notifiable n) {
        this.n = n;
    }

    @Override
    public void fetchAllData(List<Side> data) {
        DB.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot ds : task.getResult()) {
                            Side s = ds.toObject(Side.class);
                            s.setId(ds.getId());
                            data.add(s);
                        }

                        n.notifyUpdatedData(data, ITEM_TYPE);
                        Log.d(TAG, "Side List Fetching Success!");
                    } else {
                        Log.w(TAG, "Error Fetching Sides: ", task.getException());
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
                        Side s = snapshotToObject(ds, Side.class);
                        n.notifyUpdatedData(s, ITEM_TYPE);
                        Log.d(TAG, "Side Fetching Success!");
                    } else {
                        Log.w(TAG, "Error Fetching Side: ", task.getException());
                    }
                });
    }

    @Override
    public void upsertData(Side data) {
        DB.add(data)
                .addOnSuccessListener(ref ->
                        Log.d(TAG, "Document written with ID: " + ref.getId()))
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