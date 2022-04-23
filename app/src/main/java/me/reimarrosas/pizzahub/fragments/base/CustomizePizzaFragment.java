package me.reimarrosas.pizzahub.fragments.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.contracts.Notifiable;
import me.reimarrosas.pizzahub.contracts.Service;
import me.reimarrosas.pizzahub.databinding.FragmentCustomizePizzaBinding;
import me.reimarrosas.pizzahub.models.MenuItem;
import me.reimarrosas.pizzahub.models.Order;
import me.reimarrosas.pizzahub.models.Topping;
import me.reimarrosas.pizzahub.recycleradapters.adapterdata.CustomizePizzaData;
import me.reimarrosas.pizzahub.recycleradapters.customizeadapters.ToppingCustomizeAdapter;
import me.reimarrosas.pizzahub.recycleradapters.adapterdata.CustomizePizzaData.DataType;
import me.reimarrosas.pizzahub.services.ToppingService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomizePizzaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomizePizzaFragment extends Fragment implements Notifiable {

    private FragmentCustomizePizzaBinding binding;

    private Order order;
    private List<Topping> defaultToppingList;

    private ToppingCustomizeAdapter toppingAdapter;
    private Service<Topping> toppingService;

    public CustomizePizzaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CustomizePizzaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomizePizzaFragment newInstance() {
        CustomizePizzaFragment fragment = new CustomizePizzaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toppingService = new ToppingService(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCustomizePizzaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupArgs();
        setupRecyclerViews();
        fetchData();

        binding.buttonCustomizePizzaCancel.setOnClickListener(this::buttonHandler);
        binding.buttonCustomizePizzaFinish.setOnClickListener(this::buttonHandler);
    }

    @Override
    public void notifyUpdatedData(@NonNull List<? extends MenuItem> items, MenuItem.MenuItemType type) {
        switch (type) {
            case TOPPING:
                toppingAdapter.updateDataList(convertToppingToCustomData((List<Topping>) items));
                break;
        }
    }

    @Override
    public <T extends MenuItem> void notifyUpdatedData(@NonNull T item, MenuItem.MenuItemType type) {
        switch (type) {
            case TOPPING:
                addOrRemoveFromList(order.getToppings(), (Topping) item);
                break;
        }
    }

    private void setupRecyclerViews() {
        int spanCount = 2;

        int onePerRow = spanCount / 1;
        int twoPerRow = spanCount / 2;

        toppingAdapter = new ToppingCustomizeAdapter(getContext(), this, order.getToppings());
        binding.recyclerViewCustomize.setAdapter(toppingAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), spanCount);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = toppingAdapter.getItemViewType(position);

                return type == 0 ? onePerRow : twoPerRow;
            }
        });
        binding.recyclerViewCustomize
                .setLayoutManager(layoutManager);
    }

    private void setupArgs() {
        order = CustomizePizzaFragmentArgs
                .fromBundle(getArguments())
                .getOrder();
        defaultToppingList = new ArrayList<>(order.getToppings());
    }

    private void fetchData() {
        toppingService.fetchAllData(new ArrayList<>());
    }

    private void buttonHandler(View view) {
        if (view.getId() == R.id.buttonCustomizePizzaCancel) {
            order.getToppings().clear();
            order.getToppings().addAll(defaultToppingList);
        }

        NavDirections action = CustomizePizzaFragmentDirections
                .actionCustomizePizzaFragmentToOrderComboFragment(order);
        Navigation.findNavController(view).navigate(action);
    }

    private List<CustomizePizzaData> convertToppingToCustomData(List<Topping> toppings) {
        List<CustomizePizzaData> res = new ArrayList<>();
        String currentType = "";

        for (Topping t : toppings) {
            if (!currentType.equals(t.getType())) {
                currentType = t.getType();
                res.add(new CustomizePizzaData(DataType.HEADER, null, t.getType()));
            }
            res.add(new CustomizePizzaData(DataType.TOPPING, t, null));
        }

        return res;
    }

    private <T> void addOrRemoveFromList(List<T> list, T item) {
        int ind = list.indexOf(item);
        if (ind != -1) {
            list.remove(ind);
        } else {
            list.add(item);
        }
    }

}