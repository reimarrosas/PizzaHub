package me.reimarrosas.pizzahub.fragments.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.reimarrosas.pizzahub.helper.CollectionConverters;
import me.reimarrosas.pizzahub.contracts.Notifiable;
import me.reimarrosas.pizzahub.contracts.Service;
import me.reimarrosas.pizzahub.databinding.FragmentOrderComboBinding;
import me.reimarrosas.pizzahub.models.Drink;
import me.reimarrosas.pizzahub.models.MenuItem;
import me.reimarrosas.pizzahub.models.Order;
import me.reimarrosas.pizzahub.models.Premade;
import me.reimarrosas.pizzahub.models.Side;
import me.reimarrosas.pizzahub.models.Size;
import me.reimarrosas.pizzahub.models.Topping;
import me.reimarrosas.pizzahub.recycleradapters.comboadapters.DrinkComboAdapter;
import me.reimarrosas.pizzahub.recycleradapters.comboadapters.PremadeComboAdapter;
import me.reimarrosas.pizzahub.recycleradapters.comboadapters.SideComboAdapter;
import me.reimarrosas.pizzahub.recycleradapters.comboadapters.SizeComboAdapter;
import me.reimarrosas.pizzahub.recycleradapters.comboadapters.ToppingComboAdapter;
import me.reimarrosas.pizzahub.services.DrinkService;
import me.reimarrosas.pizzahub.services.PremadeService;
import me.reimarrosas.pizzahub.services.SideService;
import me.reimarrosas.pizzahub.services.SizeService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderComboFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderComboFragment extends Fragment implements Notifiable {

    private FragmentOrderComboBinding binding;

    private Order order;

    public Size size;
    private SizeComboAdapter sizeAdapter;
    private Service<Size> sizeService;

    private List<Topping> toppingList;
    private PremadeComboAdapter premadeAdapter;
    private Service<Premade> premadeService;

    private ToppingComboAdapter toppingListAdapter;

    private List<Side> selectedSideList;
    private SideComboAdapter sideAdapter;
    private Service<Side> sideService;

    private List<Drink> selectedDrinkList;
    private DrinkComboAdapter drinkAdapter;
    private Service<Drink> drinkService;

    public OrderComboFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment OrderComboFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderComboFragment newInstance() {
        OrderComboFragment fragment = new OrderComboFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sizeService = new SizeService(this);
        premadeService = new PremadeService(this);
        sideService = new SideService(this);
        drinkService = new DrinkService(this);

        selectedSideList = new ArrayList<>();
        selectedDrinkList = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderComboBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
        fetchData();
        setupArgs();

        binding.buttonComboCustomize.setOnClickListener(this::customizeHandler);
        binding.buttonComboContinue.setOnClickListener(this::continueHandler);
        binding.buttonComboSave.setOnClickListener(this::saveHandler);
        binding.buttonComboCancel.setOnClickListener(this::cancelHandler);
    }

    @Override
    public void notifyUpdatedData(@NonNull List<? extends MenuItem> items, MenuItem.MenuItemType type) {
        switch (type) {
            case SIZE:
                sizeAdapter.updateDataList((List<Size>) items);
                break;
            case PREMADE:
                premadeAdapter.updateDataList((List<Premade>) items);
                break;
            case TOPPING:
                toppingList = (List<Topping>) items;
                toppingListAdapter.updateDataList(toppingList);
                break;
            case SIDE:
                sideAdapter.updateDataList((List<Side>) items);
                break;
            case DRINK:
                drinkAdapter.updateDataList((List<Drink>) items);
                break;
        }
    }

    @Override
    public <T extends MenuItem> void notifyUpdatedData(@NonNull T item, MenuItem.MenuItemType type) {
        switch (type) {
            case SIZE:
                size = (Size) item;
                break;
            case SIDE:
                addOrRemoveFromList(selectedSideList, (Side) item);
                break;
            case DRINK:
                addOrRemoveFromList(selectedDrinkList, (Drink) item);
                break;
        }
    }

    private void setupRecyclerView() {
        sizeAdapter = new SizeComboAdapter(getContext(), this);
        binding.recyclerViewComboSize.setAdapter(sizeAdapter);
        binding.recyclerViewComboSize
                .setLayoutManager(new GridLayoutManager(getContext(), 2));

        premadeAdapter = new PremadeComboAdapter(getContext(), this);
        binding.recyclerViewComboPremade.setAdapter(premadeAdapter);
        binding.recyclerViewComboPremade
                .setLayoutManager(new GridLayoutManager(getContext(), 2));

        toppingListAdapter = new ToppingComboAdapter(getContext(), this);
        binding.recyclerViewComboToppings.setAdapter(toppingListAdapter);
        binding.recyclerViewComboToppings
                .setLayoutManager(new GridLayoutManager(getContext(), 2));

        sideAdapter = new SideComboAdapter(getContext(), this);
        binding.recyclerViewComboSides.setAdapter(sideAdapter);
        binding.recyclerViewComboSides
                .setLayoutManager(new GridLayoutManager(getContext(), 2));

        drinkAdapter = new DrinkComboAdapter(getContext(), this);
        binding.recyclerViewComboDrinks.setAdapter(drinkAdapter);
        binding.recyclerViewComboDrinks
                .setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    private void fetchData() {
        sizeService.fetchAllData(new ArrayList<>());
        premadeService.fetchAllData(new ArrayList<>());
        sideService.fetchAllData(new ArrayList<>());
        drinkService.fetchAllData(new ArrayList<>());
    }

    private void setupArgs() {
        Topping[] toppings = OrderComboFragmentArgs.fromBundle(getArguments()).getToppingList();
        if (toppings.length != 0) {
            toppingList = CollectionConverters.fromArrayToList(toppings);
            toppingListAdapter.updateDataList(toppingList);
        }
    }

    private void customizeHandler(View view) {
        List<Topping> toppings = toppingList == null ? new ArrayList<>() : toppingList;
        NavDirections action = OrderComboFragmentDirections
                .actionOrderComboFragmentToCustomizePizzaFragment(
                        CollectionConverters.fromListToArray(toppings, Topping.class));
        Navigation.findNavController(view).navigate(action);
    }

    private void continueHandler(View view) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (size != null && toppingList != null && toppingList.size() != 0) {
            order = new Order(
                    size,
                    toppingList,
                    selectedSideList,
                    selectedDrinkList,
                    new Date(System.currentTimeMillis()),
                    currentUser.getUid());

            NavDirections action = OrderComboFragmentDirections
                    .actionOrderComboFragmentToDeliveryLocationFragment();
            Navigation.findNavController(view).navigate(action);
        } else {
            Toast.makeText(getContext(), "Please complete your order!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveHandler(View view) {
    }

    private void cancelHandler(View view) {
        NavDirections action = OrderComboFragmentDirections
                .actionOrderComboFragmentToHomeFragment();
        Navigation.findNavController(view).navigate(action);
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