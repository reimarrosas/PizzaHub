package me.reimarrosas.pizzahub.fragments.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.contracts.Notifiable;
import me.reimarrosas.pizzahub.contracts.Service;
import me.reimarrosas.pizzahub.databinding.FragmentAdminToppingsBinding;
import me.reimarrosas.pizzahub.helper.SwipeHelper;
import me.reimarrosas.pizzahub.models.MenuItem;
import me.reimarrosas.pizzahub.models.Topping;
import me.reimarrosas.pizzahub.recycleradapters.adminadapters.AdminAdapter;
import me.reimarrosas.pizzahub.services.ToppingService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminToppingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminToppingsFragment extends Fragment implements Notifiable {

    private FragmentAdminToppingsBinding binding;
    private AdminAdapter<Topping> adapter;
    private Service<Topping> service;
    private List<String> toppingTypes;
    private Topping topping;

    public AdminToppingsFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AdminToppingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminToppingsFragment newInstance() {
        AdminToppingsFragment fragment = new AdminToppingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        service = new ToppingService(this);
        adapter = new AdminAdapter<>(getContext(), service, this, MenuItem.MenuItemType.TOPPING);
        topping = new Topping();
        toppingTypes = Arrays.asList(getResources().getStringArray(R.array.topping_types));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminToppingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.topping_types,
                R.layout.spinner_text);
        adapter.setDropDownViewResource(R.layout.spinner_row);
        binding.spinnerType.setAdapter(adapter);

        service.fetchAllData(new ArrayList<>());
        SwipeHelper swipeHelper = new SwipeHelper(this.adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeHelper);
        binding.recyclerViewAdminToppings.setAdapter(this.adapter);
        binding.recyclerViewAdminToppings.setLayoutManager(new LinearLayoutManager(getContext()));
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewAdminToppings);

        binding.buttonToppingSave.setOnClickListener(_view -> {
            String buttonType = binding.buttonToppingSave.getText().toString();
            boolean isCreate = buttonType.equals("Create");
            Topping oldTopping = new Topping(topping);

            topping.setType(binding.spinnerType.getSelectedItem().toString());
            topping.setImageUrl(binding.editTextImageUrl.getText().toString());
            topping.setName(binding.editTextItemName.getText().toString());
            topping.setPrice(Double.parseDouble(binding.editTextPrice.getText().toString()));

            service.upsertData(topping);

            if (isCreate) {
                this.adapter.addData(topping);
            } else {
                this.adapter.updateData(oldTopping, topping);
            }

            clearInputs();
        });
        binding.buttonToppingReset.setOnClickListener(_view -> {
            clearInputs();
        });
        binding.textViewToppingsGoBack.setOnClickListener(_view -> {
            NavDirections action = AdminToppingsFragmentDirections
                    .actionAdminToppingsFragmentToAdminHomeFragment();
            Navigation.findNavController(_view).navigate(action);
        });
    }

    @Override
    public void notifyUpdatedData(@NonNull List<? extends MenuItem> items, MenuItem.MenuItemType type) {
        if (type == MenuItem.MenuItemType.TOPPING) {
            adapter.updateDataList((List<Topping>) items);
        }
    }

    @Override
    public <T extends MenuItem> void notifyUpdatedData(@NonNull T item, MenuItem.MenuItemType type) {
        if (type == MenuItem.MenuItemType.TOPPING) {
            topping = new Topping((Topping) item);
            onItemClick(topping);
            binding.buttonToppingSave.setText("Update");
        }
    }

    private void onItemClick(Topping t) {
        binding.editTextItemName.setText(t.getName());
        binding.editTextImageUrl.setText(t.getImageUrl());
        binding.editTextPrice.setText(Double.toString(t.getPrice()));
        int selection = toppingTypes.indexOf(t.getType());
        if (selection != -1) {
            binding.spinnerType.setSelection(toppingTypes.indexOf(t.getType()));
        }
    }

    private void clearInputs() {
        binding.editTextPrice.setText("");
        binding.editTextImageUrl.setText("");
        binding.editTextItemName.setText("");
        binding.spinnerType.setSelection(0);
        binding.buttonToppingSave.setText("Create");
        topping = new Topping();
    }

}