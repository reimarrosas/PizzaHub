package me.reimarrosas.pizzahub.fragments.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.contracts.Notifiable;
import me.reimarrosas.pizzahub.databinding.FragmentAdminHomeBinding;
import me.reimarrosas.pizzahub.helper.CollectionConverters;
import me.reimarrosas.pizzahub.models.Drink;
import me.reimarrosas.pizzahub.models.Order;
import me.reimarrosas.pizzahub.models.Pair;
import me.reimarrosas.pizzahub.models.Side;
import me.reimarrosas.pizzahub.models.Single;
import me.reimarrosas.pizzahub.models.Topping;
import me.reimarrosas.pizzahub.models.Triple;
import me.reimarrosas.pizzahub.recycleradapters.adapterdata.OrderData;
import me.reimarrosas.pizzahub.recycleradapters.orderadapters.OrderAdminAdapter;
import me.reimarrosas.pizzahub.services.OrderService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminHomeFragment extends Fragment implements Notifiable {

    private FragmentAdminHomeBinding binding;

    private OrderService service;

    private OrderAdminAdapter adapter;

    public AdminHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AdminHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminHomeFragment newInstance() {
        AdminHomeFragment fragment = new AdminHomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        service = new OrderService(this);
        adapter = new OrderAdminAdapter(getContext(), service);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        service.fetchData("orders");
        binding.recyclerViewAdminHome.setAdapter(adapter);
        binding.recyclerViewAdminHome.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    @Override
    public void notifyOperationSuccess(Throwable t) {
        if (t == null) {
            Toast.makeText(getContext(), "Order successfully accepted!", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(getContext(), "Error accepting order!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void notifyUpdatedOrders(List<Order> orders) {
        adapter.updateDataList(convertOrdersToAdapterData(orders));
    }

    private List<OrderData> convertOrdersToAdapterData(List<Order> orders) {
        List<OrderData> res = new ArrayList<>();

        for (Order o : orders) {
            res.add(new OrderData(
                    null,
                    new Pair<String, String>(o.getId(), ""),
                    null, OrderData.DataType.HEADER, false));
            res.add(new OrderData(
                    null, null,
                    new Triple("Sides", "Toppings", "Drinks"),
                    OrderData.DataType.CONTENT, true));
            List<Triple<String, String, String>> orderExtrasNames = CollectionConverters
                    .zipOrderNamesByLongest(
                            o.getSides(),
                            o.getToppings(),
                            o.getDrinks()
                    );
            for (Triple<String, String, String> t : orderExtrasNames) {
                res.add(new OrderData(null, null, t, OrderData.DataType.CONTENT, false));
            }
            OrderData footerData = new OrderData(
                    new Single<>("Accept"),
                    null, null,
                    OrderData.DataType.FOOTER, false);
            footerData.setOrder(o);
            res.add(footerData);
        }

        return res;
    }


}