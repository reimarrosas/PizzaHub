package me.reimarrosas.pizzahub.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.reimarrosas.pizzahub.databinding.FragmentSigninOptionsBinding;

/**
 * Sign In Options Fragment that shows the options the user can use to sign in on the application.
 */
public class SignInOptionsFragment extends Fragment {

    private FragmentSigninOptionsBinding binding;

    public SignInOptionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SignInOptionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignInOptionsFragment newInstance() {
        SignInOptionsFragment fragment = new SignInOptionsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // SET THE ARGUMENTS AS MEMBERS
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSigninOptionsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonEmail.setOnClickListener(_view -> {
            NavDirections action = SignInOptionsFragmentDirections.actionSignInOptionsFragmentToEmailSigninFragment();
            Navigation.findNavController(_view).navigate(action);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}