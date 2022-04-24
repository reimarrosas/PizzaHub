package me.reimarrosas.pizzahub.fragments.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.databinding.FragmentDeliveryLocationBinding;
import me.reimarrosas.pizzahub.helper.ValidationHelper;
import me.reimarrosas.pizzahub.models.DeliveryAddress;
import me.reimarrosas.pizzahub.models.Order;
import me.reimarrosas.pizzahub.models.Topping;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeliveryLocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeliveryLocationFragment extends Fragment {

    private FragmentDeliveryLocationBinding binding;


    private Order order;
    private DeliveryAddress address;

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

        address = new DeliveryAddress();
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

        setupArgs();

        binding.buttonContinueDelivery.setOnClickListener(this::goToPayment);
        binding.buttonCancelDelivery.setOnClickListener(this::goToOrderCombo);
    }

    private void setupArgs() {
        order = DeliveryLocationFragmentArgs.fromBundle(getArguments()).getOrder();
        order.setAddress(address);
    }

    private void goToOrderCombo(View view) {
        NavDirections action = DeliveryLocationFragmentDirections
                .actionDeliveryLocationFragmentToOrderComboFragment(order);
        Navigation.findNavController(view).navigate(action);
    }

    private void goToPayment(View view) {
        address.setName(binding.editTextName.getText().toString());
        address.setAddress1(binding.editTextAddress1.getText().toString());
        address.setAddress2(binding.editTextAddress2.getText().toString());
        address.setCity(binding.editTextCity.getText().toString());
        address.setProvince(binding.editTextProvince.getText().toString());
        address.setPostalCode(binding.editTextPostalCode.getText().toString());
        
        if (ValidationHelper.isAddressValid(address)) {
            Log.d("DeliveryLocationFrag", "goToPayment: " + order);
            // Go to Payment
            NavDirections action = DeliveryLocationFragmentDirections
                    .actionDeliveryLocationFragmentToOrderSummaryFragment(order);
            Navigation.findNavController(view).navigate(action);
        } else {
            Toast.makeText(getContext(), "Address Invalid!", Toast.LENGTH_SHORT).show();
        }
    }

}