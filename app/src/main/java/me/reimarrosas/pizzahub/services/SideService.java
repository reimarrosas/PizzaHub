package me.reimarrosas.pizzahub.services;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import me.reimarrosas.pizzahub.contracts.Callable;
import me.reimarrosas.pizzahub.models.Side;

public class SideService {

    private static final String TAG = "SideService";

    private CollectionReference db;

    public SideService() {
        db = FirebaseFirestore.getInstance().collection("sides");
    }

    public void fetchAllSides(Callable c) {
        db.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        List<Side> sides = new ArrayList<>();
                        for (QueryDocumentSnapshot ds : task.getResult()) {
                            sides.add(ds.toObject(Side.class));
                        }
                        c.update(sides, "Sides");
                    } else {
                        Log.w(TAG, "fetchAllSides: Fetch Failed: ", task.getException());
                    }
                });
    }

}
