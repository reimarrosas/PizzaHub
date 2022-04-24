package me.reimarrosas.pizzahub.fragments.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.math.BigDecimal;
import java.math.RoundingMode;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.databinding.FragmentOrderSuccessBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderSuccessFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderSuccessFragment extends Fragment {

    private FragmentOrderSuccessBinding binding;
    private String orderId;
    private String totalPrice;

    public OrderSuccessFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment OrderSuccessFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderSuccessFragment newInstance() {
        OrderSuccessFragment fragment = new OrderSuccessFragment();
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
        binding = FragmentOrderSuccessBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupArgs();

        binding.textViewOrderSuccessOrderNumber.setText(orderId);
        binding.textViewOrderSuccessTotalPrice.setText(totalPrice);
    }

    private void setupArgs() {
        OrderSuccessFragmentArgs args = OrderSuccessFragmentArgs.fromBundle(getArguments());

        orderId = "Order ID: " + args.getOrderId();

        totalPrice = "Total Price: $" +
                roundToNearestHundredths(new BigDecimal(args.getTotalPrice()));
    }

    private double roundToNearestHundredths(BigDecimal b) {
        BigDecimal oneHundred = BigDecimal.valueOf(100);

        return b.multiply(oneHundred)
                .setScale(0, RoundingMode.CEILING)
                .divide(oneHundred)
                .doubleValue();
    }

}