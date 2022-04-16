package me.reimarrosas.pizzahub.fragments.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.databinding.FragmentDeliveryLocationBinding;
import me.reimarrosas.pizzahub.models.Topping;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeliveryLocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeliveryLocationFragment extends Fragment {

    private FragmentDeliveryLocationBinding binding;

    public DeliveryLocationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DeliveryLocationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeliveryLocationFragment newInstance() {
        DeliveryLocationFragment fragment = new DeliveryLocationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDeliveryLocationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonCancelDelivery.setOnClickListener(this::goToOrderCombo);
    }

    private void goToOrderCombo(View view) {
        NavDirections action = DeliveryLocationFragmentDirections
                .actionDeliveryLocationFragmentToOrderComboFragment(new Topping[]{});
        Navigation.findNavController(view).navigate(action);
    }

}