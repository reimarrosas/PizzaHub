package me.reimarrosas.pizzahub.fragments.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import me.reimarrosas.pizzahub.contracts.Notifiable;
import me.reimarrosas.pizzahub.databinding.FragmentOrderHistoryBinding;
import me.reimarrosas.pizzahub.models.Drink;
import me.reimarrosas.pizzahub.models.Order;
import me.reimarrosas.pizzahub.models.Pair;
import me.reimarrosas.pizzahub.models.Side;
import me.reimarrosas.pizzahub.models.Single;
import me.reimarrosas.pizzahub.models.Topping;
import me.reimarrosas.pizzahub.models.Triple;
import me.reimarrosas.pizzahub.recycleradapters.adapterdata.OrderData;
import me.reimarrosas.pizzahub.recycleradapters.adapterdata.OrderData.DataType;
import me.reimarrosas.pizzahub.recycleradapters.orderadapters.OrderHistoryAdapter;
import me.reimarrosas.pizzahub.services.OrderService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderHistoryFragment extends Fragment implements Notifiable {

    private FragmentOrderHistoryBinding binding;

    private OrderHistoryAdapter adapter;

    private OrderService service;

    public OrderHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment OrderHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderHistoryFragment newInstance() {
        OrderHistoryFragment fragment = new OrderHistoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        service = new OrderService(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        service.fetchData(FirebaseAuth.getInstance().getUid(), "orders");
        setupRecyclerView();
        binding.textViewHistoryGoBack.setOnClickListener(_view -> {
            NavDirections action = OrderHistoryFragmentDirections
                    .actionOrderHistoryFragmentToHomeFragment();
            Navigation.findNavController(_view).navigate(action);
        });
    }

    @Override
    public void notifyUpdatedOrders(List<Order> orders) {
        adapter.updateDataList(convertOrdersToAdapterData(orders));
    }

    private void setupRecyclerView() {
        adapter = new OrderHistoryAdapter(getContext());
        binding.recyclerViewOrderHistory.setAdapter(adapter);
        binding.recyclerViewOrderHistory.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private List<OrderData> convertOrdersToAdapterData(List<Order> orders) {
        List<OrderData> res = new ArrayList<>();

        for (Order o : orders) {
            res.add(new OrderData(
                    null,
                    new Pair<String, String>(o.getId(), o.getStatus()),
                    null, DataType.HEADER, false));
            res.add(new OrderData(
                    null, null,
                    new Triple("Sides", "Toppings", "Drinks"),
                    DataType.CONTENT, true));
            List<Triple<String, String, String>> orderExtrasNames = zipOrderNamesByLongest(
                    o.getSides(),
                    o.getToppings(),
                    o.getDrinks()
            );
            for (Triple<String, String, String> t : orderExtrasNames) {
                res.add(new OrderData(null, null, t, DataType.CONTENT, false));
            }
            OrderData footerData = new OrderData(
                    new Single<>("Reorder"),
                    null, null,
                    DataType.FOOTER, false);
            footerData.setOrder(o);
            res.add(footerData);
        }

        return res;
    }

    private List<Triple<String, String, String>> zipOrderNamesByLongest(
            List<Side> sides,
            List<Topping> toppings,
            List<Drink> drinks) {
        int sideSize = sides.size();
        int toppingSize = toppings.size();
        int drinkSize = drinks.size();
        int maxSize = Math.max(sideSize, Math.max(toppingSize, drinkSize));
        List<Triple<String, String, String>> orderExtrasTriples = new ArrayList<>();

        for (int i = 0; i < maxSize; ++i) {
            String sideName = sideSize > i ? sides.get(i).getName() : "";
            String toppingName = toppingSize > i ? toppings.get(i).getName() : "";
            String drinkName = drinkSize > i ? drinks.get(i).getName() : "";

            orderExtrasTriples.add(new Triple<>(sideName, toppingName, drinkName));
        }

        return orderExtrasTriples;
    }

}