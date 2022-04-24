package me.reimarrosas.pizzahub.services;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import me.reimarrosas.pizzahub.contracts.Notifiable;
import me.reimarrosas.pizzahub.models.Order;

public class OrderService {

    private static final CollectionReference DB =
            FirebaseFirestore.getInstance()
                    .collection("orders");

    private Notifiable n;

    public OrderService(Notifiable n) {
        this.n = n;
    }

    public void insertData(Order data) {
        DB.add(data)
                .addOnSuccessListener(ref -> {
                    data.setId(ref.getId());
                    n.notifyOperationSuccess(null);
                })
                .addOnFailureListener(ex -> n.notifyOperationSuccess(ex));
    }

}
