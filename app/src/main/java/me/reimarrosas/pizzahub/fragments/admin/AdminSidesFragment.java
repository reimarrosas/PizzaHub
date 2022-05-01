package me.reimarrosas.pizzahub.fragments.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
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
import me.reimarrosas.pizzahub.databinding.FragmentAdminSidesBinding;
import me.reimarrosas.pizzahub.helper.SwipeHelper;
import me.reimarrosas.pizzahub.models.Drink;
import me.reimarrosas.pizzahub.models.MenuItem;
import me.reimarrosas.pizzahub.models.Side;
import me.reimarrosas.pizzahub.recycleradapters.adminadapters.AdminAdapter;
import me.reimarrosas.pizzahub.services.SideService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminSidesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminSidesFragment extends Fragment implements Notifiable {

    private FragmentAdminSidesBinding binding;
    private AdminAdapter<Side> adapter;
    private Service<Side> service;
    private Side side;

    public AdminSidesFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddSidesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminSidesFragment newInstance() {
        AdminSidesFragment fragment = new AdminSidesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        service = new SideService(this);
        adapter = new AdminAdapter<>(getContext(), service, this, MenuItem.MenuItemType.SIDE);
        side = new Side();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminSidesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        service.fetchAllData(new ArrayList<>());
        SwipeHelper swipeHelper = new SwipeHelper(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeHelper);
        binding.recyclerViewAdminSide.setAdapter(adapter);
        binding.recyclerViewAdminSide.setLayoutManager(new LinearLayoutManager(getContext()));
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewAdminSide);

        binding.buttonSideSave.setOnClickListener(_view -> {
            String buttonType = binding.buttonSideSave.getText().toString();
            boolean isCreate = buttonType.equals("Create");
            Side oldSide = new Side(side);

            side.setName(binding.editTextSideName.getText().toString());
            side.setImageUrl(binding.editTextSideImageUrl.getText().toString());
            side.setPrice(Double.parseDouble(binding.editTextSidePrice.getText().toString()));

            service.upsertData(side);

            if (isCreate) {
                Log.d("AdminDrinks", "onViewCreated: " + side + ", ID: " + side.getId());
                adapter.addData(side);
            } else {
                adapter.updateData(oldSide, side);
            }

            clearInputs();
        });

        binding.buttonSideReset.setOnClickListener(_view -> {
            clearInputs();
        });
        binding.textViewSidesGoBack.setOnClickListener(_view -> {
            NavDirections action = AdminSidesFragmentDirections
                    .actionAdminSidesFragmentToAdminHomeFragment();
            Navigation.findNavController(_view).navigate(action);
        });
    }

    @Override
    public void notifyUpdatedData(@NonNull List<? extends MenuItem> items, MenuItem.MenuItemType type) {
        if (type == MenuItem.MenuItemType.SIDE) {
            adapter.updateDataList((List<Side>) items);
        }
    }

    @Override
    public <T extends MenuItem> void notifyUpdatedData(@NonNull T item, MenuItem.MenuItemType type) {
        if (type == MenuItem.MenuItemType.SIDE) {
            side = new Side((Side) item);
            onItemClick(side);
            binding.buttonSideSave.setText("Update");
        }
    }

    private void onItemClick(Side s) {
        binding.editTextSideName.setText(s.getName());
        binding.editTextSideImageUrl.setText(s.getImageUrl());
        binding.editTextSidePrice.setText(Double.toString(s.getPrice()));
    }

    private void clearInputs() {
        binding.editTextSidePrice.setText("");
        binding.editTextSideImageUrl.setText("");
        binding.editTextSideName.setText("");
        binding.buttonSideSave.setText("Create");
        side = new Side();
    }

}