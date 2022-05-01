package me.reimarrosas.pizzahub.fragments.admin;

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

import java.util.ArrayList;
import java.util.List;

import me.reimarrosas.pizzahub.contracts.Notifiable;
import me.reimarrosas.pizzahub.contracts.Service;
import me.reimarrosas.pizzahub.databinding.FragmentSavePremadeBinding;
import me.reimarrosas.pizzahub.helper.CollectionConverters;
import me.reimarrosas.pizzahub.models.MenuItem;
import me.reimarrosas.pizzahub.models.Premade;
import me.reimarrosas.pizzahub.models.Topping;
import me.reimarrosas.pizzahub.recycleradapters.adapterdata.CustomizePizzaData;
import me.reimarrosas.pizzahub.recycleradapters.customizeadapters.ToppingCustomizeAdapter;
import me.reimarrosas.pizzahub.services.PremadeService;
import me.reimarrosas.pizzahub.services.ToppingService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SavePremadeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavePremadeFragment extends Fragment implements Notifiable {

    private FragmentSavePremadeBinding binding;
    private List<Topping> selectedToppings;
    private Service<Premade> premadeService;
    private Service<Topping> toppingService;
    private ToppingCustomizeAdapter adapter;
    private Premade premade;

    public SavePremadeFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SavePremadeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SavePremadeFragment newInstance() {
        SavePremadeFragment fragment = new SavePremadeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toppingService = new ToppingService(this);
        premadeService = new PremadeService(this);
        premade = new Premade();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSavePremadeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupArgs();
        setupRecyclerView();
        toppingService.fetchAllData(new ArrayList<>());

        binding.buttonPremadeSave.setOnClickListener(_view -> {
            if (selectedToppings.size() != 0) {
                premade.setName(binding.editTextPremadeName.getText().toString());
                premade.setImageUrl(binding.editTextPremadeImageUrl.getText().toString());
                premade.setToppings(selectedToppings);
                premadeService.upsertData(premade);
            } else {
                Toast.makeText(
                        getContext(),
                        "Please choose at least 1 topping!",
                        Toast.LENGTH_SHORT
                ).show();
            }
            goToAdminPremade();
        });
        binding.buttonPremadeCancel.setOnClickListener(_view -> {
            goToAdminPremade();
        });
    }

    @Override
    public void notifyUpdatedData(@NonNull List<? extends MenuItem> items, MenuItem.MenuItemType type) {
        if (type == MenuItem.MenuItemType.TOPPING) {
            adapter.updateDataList(convertToppingToCustomData((List<Topping>) items));
        }
    }

    @Override
    public <T extends MenuItem> void notifyUpdatedData(@NonNull T item, MenuItem.MenuItemType type) {
        if (type == MenuItem.MenuItemType.TOPPING) {
            addOrRemoveFromList(selectedToppings, (Topping) item);
            Log.d("SavePremade", "notifyUpdatedData: " + selectedToppings);
        }
    }

    private void setupArgs() {
        premade = SavePremadeFragmentArgs.fromBundle(getArguments()).getPremade();
        selectedToppings = new ArrayList<>(premade.getToppings());
        setupPrompts(selectedToppings.size() == 0);
        Log.d("SavePremade", "setupArgs: " + selectedToppings);
    }

    private void setupRecyclerView() {
        int spanCount = 2;

        int onePerRow = spanCount / 1;
        int twoPerRow = spanCount / 2;

        adapter = new ToppingCustomizeAdapter(getContext(), this, selectedToppings);
        binding.recyclerViewPremadeToppings.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), spanCount);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = adapter.getItemViewType(position);

                return type == 0 ? onePerRow : twoPerRow;
            }
        });
        binding.recyclerViewPremadeToppings.setLayoutManager(layoutManager);
    }

    private void setupPrompts(boolean isCreate) {
        if (isCreate) {
            binding.textViewSavePremadeTitle.setText("Create Premade");
            binding.buttonPremadeSave.setText("Create");
        } else {
            binding.textViewSavePremadeTitle.setText("Update Premade");
            binding.buttonPremadeSave.setText("Update");
        }

        binding.editTextPremadeName.setText(premade.getName());
        binding.editTextPremadeImageUrl.setText(premade.getImageUrl());
    }

    private List<CustomizePizzaData> convertToppingToCustomData(List<Topping> toppings) {
        List<CustomizePizzaData> res = new ArrayList<>();
        String currentType = "";

        for (Topping t : toppings) {
            if (!currentType.equals(t.getType())) {
                currentType = t.getType();
                res.add(new CustomizePizzaData(CustomizePizzaData.DataType.HEADER, null, t.getType()));
            }
            res.add(new CustomizePizzaData(CustomizePizzaData.DataType.TOPPING, t, null));
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

    private void goToAdminPremade() {
        NavDirections action = SavePremadeFragmentDirections
                .actionSavePremadeFragmentToAdminPremadeFragment();
        Navigation.findNavController(getView()).navigate(action);
    }

}