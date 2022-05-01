package me.reimarrosas.pizzahub.fragments.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.databinding.FragmentAdminSidesBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminSidesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminSidesFragment extends Fragment {

    private FragmentAdminSidesBinding binding;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminSidesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}