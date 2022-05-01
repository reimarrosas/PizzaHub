package me.reimarrosas.pizzahub.fragments.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.databinding.FragmentAdminPremadeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminPremadeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminPremadeFragment extends Fragment {

    private FragmentAdminPremadeBinding binding;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminPremadeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}