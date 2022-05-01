package me.reimarrosas.pizzahub.fragments.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.contracts.Notifiable;
import me.reimarrosas.pizzahub.contracts.Service;
import me.reimarrosas.pizzahub.databinding.FragmentAdminDrinksBinding;
import me.reimarrosas.pizzahub.helper.SwipeHelper;
import me.reimarrosas.pizzahub.models.Drink;
import me.reimarrosas.pizzahub.models.MenuItem;
import me.reimarrosas.pizzahub.recycleradapters.adminadapters.AdminAdapter;
import me.reimarrosas.pizzahub.services.DrinkService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminDrinksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminDrinksFragment extends Fragment implements Notifiable {

    private FragmentAdminDrinksBinding binding;
    private AdminAdapter<Drink> adapter;
    private Service<Drink> service;
    private Drink drink;

    public AdminDrinksFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AdminDrinksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminDrinksFragment newInstance() {
        AdminDrinksFragment fragment = new AdminDrinksFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        service = new DrinkService(this);
        adapter = new AdminAdapter<>(getContext(), service, this, MenuItem.MenuItemType.DRINK);
        drink = new Drink();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminDrinksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        service.fetchAllData(new ArrayList<>());
        SwipeHelper swipeHelper = new SwipeHelper(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeHelper);
        binding.recyclerViewAdminDrink.setAdapter(adapter);
        binding.recyclerViewAdminDrink.setLayoutManager(new LinearLayoutManager(getContext()));
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewAdminDrink);

        binding.buttonDrinkSave.setOnClickListener(_view -> {
            String buttonType = binding.buttonDrinkSave.getText().toString();
            boolean isCreate = buttonType.equals("Create");
            Drink oldDrink = new Drink(drink);

            drink.setName(binding.editTextDrinkName.getText().toString());
            drink.setImageUrl(binding.editTextDrinkImageUrl.getText().toString());
            drink.setPrice(Double.parseDouble(binding.editTextDrinkPrice.getText().toString()));

            service.upsertData(drink);

            if (isCreate) {
                Log.d("AdminDrinks", "onViewCreated: " + drink + ", ID: " + drink.getId());
                adapter.addData(drink);
            } else {
                adapter.updateData(oldDrink, drink);
            }

            clearInputs();
        });

        binding.buttonDrinkReset.setOnClickListener(_view -> {
            clearInputs();
        });
    }

    @Override
    public void notifyUpdatedData(@NonNull List<? extends MenuItem> items, MenuItem.MenuItemType type) {
        if (type == MenuItem.MenuItemType.DRINK) {
            adapter.updateDataList((List<Drink>) items);
        }
    }

    @Override
    public <T extends MenuItem> void notifyUpdatedData(@NonNull T item, MenuItem.MenuItemType type) {
        if (type == MenuItem.MenuItemType.DRINK) {
            drink = new Drink((Drink) item);
            onItemClick(drink);
            binding.buttonDrinkSave.setText("Update");
        }
    }

    private void onItemClick(Drink d) {
        binding.editTextDrinkName.setText(d.getName());
        binding.editTextDrinkImageUrl.setText(d.getImageUrl());
        binding.editTextDrinkPrice.setText(Double.toString(d.getPrice()));
    }

    private void clearInputs() {
        binding.editTextDrinkPrice.setText("");
        binding.editTextDrinkImageUrl.setText("");
        binding.editTextDrinkName.setText("");
        binding.buttonDrinkSave.setText("Create");
        drink = new Drink();
    }

}