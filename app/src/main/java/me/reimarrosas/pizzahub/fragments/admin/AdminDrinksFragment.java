package me.reimarrosas.pizzahub.fragments.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.databinding.FragmentAdminDrinksBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminDrinksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminDrinksFragment extends Fragment {

    private FragmentAdminDrinksBinding binding;

    public AdminDrinksFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AdminDrinksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminDrinksFragment newInstance() {
        AdminDrinksFragment fragment = new AdminDrinksFragment();
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
        binding = FragmentAdminDrinksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

}