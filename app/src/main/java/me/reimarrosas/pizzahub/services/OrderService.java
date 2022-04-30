package me.reimarrosas.pizzahub.services;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import me.reimarrosas.pizzahub.contracts.Notifiable;
import me.reimarrosas.pizzahub.models.DeliveryAddress;
import me.reimarrosas.pizzahub.models.Drink;
import me.reimarrosas.pizzahub.models.Order;
import me.reimarrosas.pizzahub.models.Side;
import me.reimarrosas.pizzahub.models.Size;
import me.reimarrosas.pizzahub.models.Topping;

public class OrderService {

    private static final String TAG = "OrderService";

    private Notifiable n;

    public OrderService(Notifiable n) {
        this.n = n;
    }

    public void fetchData(String userId, String collection) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(collection)
                .whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        n.notifyUpdatedOrders(convertFirestoreResult(task.getResult()));
                        Log.d(TAG, "Fetching order list successful!");
                    } else {
                        Log.e(TAG, "Error fetching order list", task.getException());
                    }
                });
    }

    public void insertData(Order data, String collection) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(collection)
                .add(data)
                .addOnSuccessListener(ref -> {
                    data.setId(ref.getId());
                    n.notifyOperationSuccess(null);
                })
                .addOnFailureListener(ex -> n.notifyOperationSuccess(ex));
    }

    private List<Order> convertFirestoreResult(QuerySnapshot qs) {
        List<Order> orderList = new ArrayList<>();
        for (QueryDocumentSnapshot qds : qs) {
            Order o;
            String orderId = qds.getId();
            Size s = convertRawToSize((Map<String, Object>) qds.get("size"));
            List<Map<String, Object>> rawTopping = (List<Map<String, Object>>) qds.get("toppings");
            List<Topping> toppings = convertRawToTopping(rawTopping);
            List<Map<String, Object>> rawSides = (List<Map<String, Object>>) qds.get("sides");
            List<Side> sides = convertRawToSide(rawSides);
            List<Map<String, Object>> rawDrinks = (List<Map<String, Object>>) qds.get("drinks");
            List<Drink> drinks = convertRawToDrink(rawDrinks);
            DeliveryAddress address = qds.toObject(DeliveryAddress.class);
            Date date = qds.getDate("orderDate");
            double price = qds.getDouble("price");
            String status = qds.getString("status");
            String userId = qds.getString("userId");
            o = new Order(s, toppings, sides, drinks, price, date, userId, address, status);
            o.setId(orderId);
            orderList.add(o);
        }

        return orderList;
    }

    private Size convertRawToSize(Map<String, Object> raw) {
        Size s = new Size();

        for (Map.Entry<String, Object> e : raw.entrySet()) {
            switch (e.getKey()) {
                case "name":
                    s.setName(e.getValue().toString());
                    break;
                case "size":
                    long l = (long) e.getValue();
                    s.setSize((int) l);
                    break;
                case "price":
                    s.setPrice((Double) e.getValue());
                    break;
                case "imageUrl":
                    s.setImageUrl(e.getValue().toString());
                    break;
                default:
                    throw new UnsupportedOperationException("Key not supported!: " + e.getKey());
            }
        }

        return s;
    }

    private DeliveryAddress convertRawToDeliveryAddress(Map<String, Object> raw) {
        DeliveryAddress address = new DeliveryAddress();

        for (Map.Entry<String, Object> e : raw.entrySet()) {
            switch (e.getKey()) {
                case "address1":
                    address.setAddress1(e.getValue().toString());
                    break;
                case "address2":
                    address.setAddress2(e.getValue().toString());
                    break;
                case "city":
                    address.setCity(e.getValue().toString());
                    break;
                case "name":
                    address.setName(e.getValue().toString());
                    break;
                case "postalCode":
                    address.setPostalCode(e.getValue().toString());
                    break;
                case "province":
                    address.setProvince(e.getValue().toString());
                    break;
                default:
                    throw new UnsupportedOperationException("Key not supported!: " + e.getKey());
            }
        }

        return address;
    }

    private List<Topping> convertRawToTopping(List<Map<String, Object>> raw) {
        List<Topping> res = new ArrayList<>();

        for (Map<String, Object> m : raw) {
            Topping t = new Topping();
            for (Map.Entry<String, Object> e : m.entrySet()) {
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
            res.add(t);
        }

        return res;
    }

    private List<Side> convertRawToSide(List<Map<String, Object>> raw) {
        List<Side> res = new ArrayList<>();

        for (Map<String, Object> m : raw) {
            Side s = new Side();
            for (Map.Entry<String, Object> e : m.entrySet()) {
                switch (e.getKey()) {
                    case "name":
                        s.setName(e.getValue().toString());
                        break;
                    case "imageUrl":
                        s.setImageUrl(e.getValue().toString());
                        break;
                    case "price":
                        s.setPrice((Double) e.getValue());
                        break;
                    default:
                        throw new UnsupportedOperationException("Key not supported!: " + e.getKey());
                }
            }
            res.add(s);
        }

        return res;
    }

    private List<Drink> convertRawToDrink(List<Map<String, Object>> raw) {
        List<Drink> res = new ArrayList<>();

        for (Map<String, Object> m : raw) {
            Drink d = new Drink();
            for (Map.Entry<String, Object> e : m.entrySet()) {
                switch (e.getKey()) {
                    case "name":
                        d.setName(e.getValue().toString());
                        break;
                    case "imageUrl":
                        d.setImageUrl(e.getValue().toString());
                        break;
                    case "price":
                        d.setPrice((Double) e.getValue());
                        break;
                    default:
                        throw new UnsupportedOperationException("Key not supported!: " + e.getKey());
                }
            }
            res.add(d);
        }

        return res;
    }

}
