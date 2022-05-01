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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.databinding.FragmentUserVerificationBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserVerificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserVerificationFragment extends Fragment {

    private FragmentUserVerificationBinding binding;

    public UserVerificationFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserVerificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserVerificationFragment newInstance() {
        UserVerificationFragment fragment = new UserVerificationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUser.reload();
        if (currentUser == null) {
            NavDirections action = UserVerificationFragmentDirections
                    .actionUserVerificationFragmentToSignInOptionsFragment();
            Navigation.findNavController(getView()).navigate(action);
        } else if (currentUser.isEmailVerified()) {
            NavDirections action = UserVerificationFragmentDirections
                    .actionUserVerificationFragmentToHomeFragment2();
            Navigation.findNavController(getView()).navigate(action);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserVerificationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sendEmailVerification();
        binding.textViewHomePageLink.setOnClickListener(_view -> {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            currentUser.reload();
            if (currentUser.isEmailVerified()) {
                NavDirections action = UserVerificationFragmentDirections
                        .actionUserVerificationFragmentToSignInOptionsFragment();
                Navigation.findNavController(_view).navigate(action);
            } else {
                Toast.makeText(getContext(), "User not yet verified!", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void sendEmailVerification() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUser.sendEmailVerification()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(
                                getContext(),
                                "Email verification link sent!",
                                Toast.LENGTH_SHORT
                        ).show();
                    } else {
                        Toast.makeText(getContext(), "Error on sending email verification link",
                                Toast.LENGTH_SHORT).show();
                        Log.e("UserVerification", "sendEmailVerification: ", task.getException());
                    }
                });
    }

}