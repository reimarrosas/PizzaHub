package me.reimarrosas.pizzahub.fragments.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.contracts.Notifiable;
import me.reimarrosas.pizzahub.contracts.Service;
import me.reimarrosas.pizzahub.databinding.FragmentAdminPremadeBinding;
import me.reimarrosas.pizzahub.helper.CollectionConverters;
import me.reimarrosas.pizzahub.helper.SwipeHelper;
import me.reimarrosas.pizzahub.models.MenuItem;
import me.reimarrosas.pizzahub.models.Premade;
import me.reimarrosas.pizzahub.models.Topping;
import me.reimarrosas.pizzahub.recycleradapters.adminadapters.AdminPremadeAdapter;
import me.reimarrosas.pizzahub.recycleradapters.homeadapters.PremadeHomeAdapter;
import me.reimarrosas.pizzahub.services.PremadeService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminPremadeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminPremadeFragment extends Fragment implements Notifiable {

    private FragmentAdminPremadeBinding binding;
    private Service<Premade> service;
    private AdminPremadeAdapter adapter;

    public AdminPremadeFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddPremadePizzaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminPremadeFragment newInstance() {
        AdminPremadeFragment fragment = new AdminPremadeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        service = new PremadeService(this);
        adapter = new AdminPremadeAdapter(getContext(), service, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminPremadeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        service.fetchAllData(new ArrayList<>());
        setupRecyclerViews();

        binding.buttonCreatePremade.setOnClickListener(_view -> {
            NavDirections action = AdminPremadeFragmentDirections
                    .actionAdminPremadeFragmentToSavePremadeFragment(new Premade());
            Navigation.findNavController(_view).navigate(action);
        });
        binding.textViewPremadeGoBack.setOnClickListener(_view -> {
            NavDirections action = AdminPremadeFragmentDirections
                    .actionAdminPremadeFragmentToAdminHomeFragment();
            Navigation.findNavController(_view).navigate(action);
        });
    }

    @Override
    public void notifyUpdatedData(@NonNull List<? extends MenuItem> items, MenuItem.MenuItemType type) {
        if (type == MenuItem.MenuItemType.PREMADE) {
            adapter.updateDataList((List<Premade>) items);
        }
    }

    private void setupRecyclerViews() {
        SwipeHelper swipeHelper = new SwipeHelper(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeHelper);
        binding.recyclerViewAdminPremade.setAdapter(adapter);
        binding.recyclerViewAdminPremade.setLayoutManager(new LinearLayoutManager(getContext()));
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewAdminPremade);
    }

}