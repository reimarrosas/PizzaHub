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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import me.reimarrosas.pizzahub.databinding.FragmentEmailSignupBinding;
import me.reimarrosas.pizzahub.helper.ValidationHelper;

/**
 * Email Sign Up Fragment that shows how the user can Sign up on for the Application.
 */
public class EmailSignupFragment extends Fragment {

    private FragmentEmailSignupBinding binding;

    private FirebaseAuth auth;

    public EmailSignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EmailSignupFragment.
     */
    public static EmailSignupFragment newInstance() {
        EmailSignupFragment fragment = new EmailSignupFragment();
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
        // Inflate the layout for this fragment
        binding = FragmentEmailSignupBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseUser currentUser = auth.getCurrentUser();
        currentUser.reload();
        if (currentUser != null) {
            checkIfVerified(currentUser.isEmailVerified());
        }

        binding.textViewLoginLink.setOnClickListener(_view -> {
            NavDirections action = EmailSignupFragmentDirections.actionEmailSignupFragmentToEmailSigninFragment();
            Navigation.findNavController(_view).navigate(action);
        });
        binding.buttonSignup.setOnClickListener(this::signUpHandler);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        auth = null;
    }

    private void navigateToHome(View view) {
        NavDirections action = EmailSignupFragmentDirections
                .actionEmailSignupFragmentToHomeFragment();
        Navigation.findNavController(view).navigate(action);
    }

    private void navigateToVerification(View view) {
        NavDirections action = EmailSignupFragmentDirections
                .actionEmailSignupFragmentToUserVerificationFragment();
        Navigation.findNavController(view).navigate(action);
    }

    private void signUpHandler(View view) {
        String email = binding.editTextEmailAddressSignup.getText().toString();
        String password = binding.editTextPasswordSignup.getText().toString();
        String verifyPassword = binding.editTextVerifyPasswordSignup.getText().toString();

        String validationText = ValidationHelper.isCredentialsValid(
                email,
                password,
                verifyPassword,
                ValidationHelper.CredentialType.SIGN_UP
        );

        if ("".equals(validationText)) {
            signUpUser(email.toLowerCase().trim(), password, view);
        } else {
            Toast.makeText(getContext(), validationText, Toast.LENGTH_SHORT).show();
        }
    }

    private void signUpUser(String email, String password, View view) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser currentUser = auth.getCurrentUser();
                        currentUser.reload();
                        checkIfVerified(currentUser.isEmailVerified());
                    } else {
                        Toast.makeText(
                                EmailSignupFragment.this.getContext(),
                                "Sign Up Failed!",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }

    private void checkIfVerified(boolean isVerified) {
        if (isVerified) {
            navigateToHome(getView());
        } else {
            navigateToVerification(getView());
        }
    }

}