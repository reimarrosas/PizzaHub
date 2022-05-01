package me.reimarrosas.pizzahub.services;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
                        for (QueryDocumentSnapshot qds : task.getResult()) {
                            Premade p = new Premade();
                            p.setId(qds.getId());
                            p.setName(qds.getString("name"));
                            p.setImageUrl(qds.getString("imageUrl"));

                            List<Topping> toppingList = new ArrayList<>();
                            List<Map<String, Object>> toppingMapList =
                                    (List<Map<String, Object>>) qds.get("toppings");
                            for (Map<String, Object> m : toppingMapList) {
                                toppingList.add(objectMapToTopping(m));
                            }

                            p.setToppings(toppingList);
                            data.add(p);
                        }

                        n.notifyUpdatedData(data, ITEM_TYPE);
                        Log.d(TAG, "Premade list fetching success!");
                    } else {
                        Log.w(TAG, "Error fetching premade list", task.getException());
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
    public void upsertData(Premade data) {
        String id = DB.document().getId();
        if (data.getId() == null) {
            data.setId(id);
        }
        DB.document(data.getId())
                .set(data)
                .addOnSuccessListener(_void ->
                        Log.d(TAG, "Document written with ID: " + data.getId()))
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

    private Topping objectMapToTopping(Map<String, Object> m) {
        Topping t = new Topping();

        for (Entry<String, Object> e : m.entrySet()) {

            switch (e.getKey()) {
                case "type":
                    t.setType(e.getValue().toString());
                    break;
                case "name":
                    t.setName(e.getValue().toString());
                    break;
                case "imageUrl":
                    t.setImageUrl(e.getValue().toString());
                    break;
                case "price":
                    t.setPrice((Double) e.getValue());
                    break;
                default:
                    throw new UnsupportedOperationException("Key not supported!: " + e.getKey());
            }
        }

        return t;
    }

}
