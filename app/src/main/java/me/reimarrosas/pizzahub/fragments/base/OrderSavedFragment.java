package me.reimarrosas.pizzahub.fragments.base;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.reimarrosas.pizzahub.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderSavedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderSavedFragment extends Fragment {

    public OrderSavedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment OrderSavedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderSavedFragment newInstance() {
        OrderSavedFragment fragment = new OrderSavedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_saved, container, false);
    }
}