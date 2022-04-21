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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.contracts.Notifiable;
import me.reimarrosas.pizzahub.contracts.Service;
import me.reimarrosas.pizzahub.databinding.FragmentHomeBinding;
import me.reimarrosas.pizzahub.models.Drink;
import me.reimarrosas.pizzahub.models.MenuItem;
import me.reimarrosas.pizzahub.models.Premade;
import me.reimarrosas.pizzahub.models.Side;
import me.reimarrosas.pizzahub.models.Topping;
import me.reimarrosas.pizzahub.recycleradapters.homeadapters.DrinkHomeAdapter;
import me.reimarrosas.pizzahub.recycleradapters.homeadapters.SideHomeAdapter;
import me.reimarrosas.pizzahub.recycleradapters.homeadapters.PremadeHomeAdapter;
import me.reimarrosas.pizzahub.services.DrinkService;
import me.reimarrosas.pizzahub.services.PremadeService;
import me.reimarrosas.pizzahub.services.SideService;

/**
 * Home Fragment that shows the menu of the business (Pizzas, Sides, and Drinks).
 */
public class HomeFragment extends Fragment implements Notifiable {

    private static final String TAG = "HomeFragment";

    private FirebaseAuth auth;
    private FirebaseUser user;

    private FragmentHomeBinding binding;
    private boolean isFabExpanded = false;

    private Service<Premade> premadeService;
    private Service<Side> sideService;
    private Service<Drink> drinkService;

    private PremadeHomeAdapter premadeHomeAdapter;
    private SideHomeAdapter sideHomeAdapter;
    private DrinkHomeAdapter drinkHomeAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        premadeService = new PremadeService(this);
        sideService = new SideService(this);
        drinkService = new DrinkService(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setFragmentNavigation();
        hideFABs();

        premadeService.fetchAllData(new ArrayList<>());
        sideService.fetchAllData(new ArrayList<>());
        drinkService.fetchAllData(new ArrayList<>());

        binding.floatingActionButtonMain.setOnClickListener(_view -> {
            if (!isFabExpanded) {
                showFABs();
            } else {
                hideFABs();
            }

            isFabExpanded = !isFabExpanded;
        });

        setupRecyclerViews();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupRecyclerViews() {
        premadeHomeAdapter = new PremadeHomeAdapter(getContext());
        sideHomeAdapter = new SideHomeAdapter(getContext());
        drinkHomeAdapter = new DrinkHomeAdapter(getContext());

        GridLayoutManager premadeLayoutManager = new GridLayoutManager(getContext(), 2);
        GridLayoutManager sideLayoutManager = new GridLayoutManager(getContext(), 2);
        GridLayoutManager drinkLayoutManager = new GridLayoutManager(getContext(), 2);

        binding.recyclerViewHomePremade.setAdapter(premadeHomeAdapter);
        binding.recyclerViewHomePremade.setLayoutManager(premadeLayoutManager);
        binding.recyclerViewHomeSides.setAdapter(sideHomeAdapter);
        binding.recyclerViewHomeSides.setLayoutManager(sideLayoutManager);
        binding.recylerViewHomeDrinks.setAdapter(drinkHomeAdapter);
        binding.recylerViewHomeDrinks.setLayoutManager(drinkLayoutManager);
    }

    private void showFABs() {
        binding.floatingActionButtonMain.setImageResource(R.drawable.ic_cross_mark);

        binding.floatingActionButtonLogout.show();
        binding.floatingActionButtonHistory.show();
        binding.floatingActionButtonSave.show();
        binding.floatingActionButtonOrder.show();
    }

    private void hideFABs() {
        binding.floatingActionButtonMain.setImageResource(R.drawable.ic_plus);

        binding.floatingActionButtonLogout.hide();
        binding.floatingActionButtonHistory.hide();
        binding.floatingActionButtonSave.hide();
        binding.floatingActionButtonOrder.hide();
    }

    private void setFragmentNavigation() {
        binding.floatingActionButtonOrder.setOnClickListener(this::goToOrderCombo);
        binding.floatingActionButtonSave.setOnClickListener(this::goToOrderSave);
        binding.floatingActionButtonHistory.setOnClickListener(this::goToOrderHistory);
        binding.floatingActionButtonLogout.setOnClickListener(this::logout);
    }

    private void goToOrderCombo(View view) {
        NavDirections action = HomeFragmentDirections
                .actionHomeFragmentToOrderComboFragment(new Topping[]{});
        Navigation.findNavController(view).navigate(action);
    }

    private void goToOrderSave(View view) {
        NavDirections action = HomeFragmentDirections.actionHomeFragmentToOrderSavedFragment();
        Navigation.findNavController(view).navigate(action);
    }

    private void goToOrderHistory(View view) {
        NavDirections action = HomeFragmentDirections.actionHomeFragmentToOrderHistoryFragment();
        Navigation.findNavController(view).navigate(action);
    }

    private void logout(View view) {
        auth.signOut();
        NavDirections action = HomeFragmentDirections.actionHomeFragmentToSignInOptionsFragment();
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void notifyUpdatedData(@NonNull List<? extends MenuItem> items, MenuItem.MenuItemType type) {
        switch (type) {
            case PREMADE:
                premadeHomeAdapter.updateDataList((List<Premade>) items);
                break;
            case SIDE:
                sideHomeAdapter.updateDataList((List<Side>) items);
                break;
            case DRINK:
                drinkHomeAdapter.updateDataList((List<Drink>) items);
                break;
            default:
                throw new UnsupportedOperationException("Supposed to be unreachable");
        }
    }

    @Override
    public <T extends MenuItem> void notifyUpdatedData(@NonNull T item, MenuItem.MenuItemType type) {

    }

}