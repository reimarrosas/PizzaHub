package me.reimarrosas.pizzahub.services;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import me.reimarrosas.pizzahub.contracts.Notifiable;
import me.reimarrosas.pizzahub.contracts.Service;
import me.reimarrosas.pizzahub.models.MenuItem;
import me.reimarrosas.pizzahub.models.Premade;
import me.reimarrosas.pizzahub.models.Topping;

public class PremadeService implements Service<Premade> {

    private static final String TAG = "PremadeService";
    private static final MenuItem.MenuItemType ITEM_TYPE = MenuItem.MenuItemType.PREMADE;

    private static final CollectionReference DB =
            FirebaseFirestore.getInstance()
                    .collection("premades");

    private Notifiable n;

    public PremadeService(Notifiable n) {
        this.n = n;
    }

    @Override
    public void fetchAllData(List<Premade> data) {
        DB.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot ds : task.getResult()) {
                            Premade p = new Premade();

                            p.setId(ds.getId());
                            p.setName(ds.getString("name"));
                            p.setImageUrl(ds.getString("imageUrl"));

                            data.add(p);
                        }
                        
                        n.notifyUpdatedData(data, ITEM_TYPE);
                        Log.d(TAG, "Premade List Fetching Success!");
                    } else {
                        Log.w(TAG, "Error fetching premades: ", task.getException());
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
                        Premade p = snapshotToObject(ds, Premade.class);
                        n.notifyUpdatedData(p, ITEM_TYPE);
                        Log.d(TAG, "Topping Premade Success!");
                    } else {
                        Log.w(TAG, "Error fetching Premade: ", task.getException());
                    }
                });
    }

    @Override
    public void insertData(Premade data) {
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
