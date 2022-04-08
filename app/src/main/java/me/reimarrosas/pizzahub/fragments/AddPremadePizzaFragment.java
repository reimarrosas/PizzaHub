package me.reimarrosas.pizzahub.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.reimarrosas.pizzahub.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPremadePizzaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPremadePizzaFragment extends Fragment {

    public AddPremadePizzaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddPremadePizzaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPremadePizzaFragment newInstance() {
        AddPremadePizzaFragment fragment = new AddPremadePizzaFragment();
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
        return inflater.inflate(R.layout.fragment_add_premade_pizza, container, false);
    }
}