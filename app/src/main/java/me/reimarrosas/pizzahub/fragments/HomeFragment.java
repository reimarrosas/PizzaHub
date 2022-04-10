package me.reimarrosas.pizzahub.fragments;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.databinding.FragmentHomeBinding;

/**
 * Home Fragment that shows the menu of the business (Pizzas, Sides, and Drinks).
 */
public class HomeFragment extends Fragment {

    private FirebaseAuth auth;
    private FirebaseUser user;

    private FragmentHomeBinding binding;
    private boolean isFabExpanded = false;

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

        user = HomeFragmentArgs.fromBundle(getArguments()).getUser();

        setFragmentNavigation();
        hideFABs();

        binding.floatingActionButtonMain.setOnClickListener(_view -> {
            if (!isFabExpanded) {
                showFABs();
            } else {
                hideFABs();
            }

            isFabExpanded = !isFabExpanded;
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showFABs() {
        binding.floatingActionButtonMain.setImageResource(R.drawable.ic_cross_mark);

        binding.floatingActionButtonLogout.show();
        binding.floatingActionButtonHistory.show();
        binding.floatingActionButtonSave.show();
        binding.floatingActionButtonHome.show();

        binding.textViewLogout.setVisibility(View.VISIBLE);
        binding.textViewHistory.setVisibility(View.VISIBLE);
        binding.textViewSave.setVisibility(View.VISIBLE);
        binding.textViewHome.setVisibility(View.VISIBLE);
    }

    private void hideFABs() {
        binding.floatingActionButtonMain.setImageResource(R.drawable.ic_plus);

        binding.floatingActionButtonLogout.hide();
        binding.floatingActionButtonHistory.hide();
        binding.floatingActionButtonSave.hide();
        binding.floatingActionButtonHome.hide();

        binding.textViewLogout.setVisibility(View.GONE);
        binding.textViewHistory.setVisibility(View.GONE);
        binding.textViewSave.setVisibility(View.GONE);
        binding.textViewHome.setVisibility(View.GONE);

    }

    private void setFragmentNavigation() {
        binding.floatingActionButtonHome.setOnClickListener(this::goToHome);
        binding.floatingActionButtonSave.setOnClickListener(this::goToOrderSave);
        binding.floatingActionButtonHistory.setOnClickListener(this::goToOrderHistory);
        binding.floatingActionButtonLogout.setOnClickListener(this::logout);
    }

    private void goToHome(View view) {
        NavDirections action = HomeFragmentDirections.actionHomeFragmentSelf(user);
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

}