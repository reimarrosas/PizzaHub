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

import me.reimarrosas.pizzahub.databinding.FragmentEmailSigninBinding;
import me.reimarrosas.pizzahub.helper.ValidationHelper;

/**
 * Email Sign In Fragment that shows how the user can Sign in on for the Application.
 */
public class EmailSignInFragment extends Fragment {

    FirebaseAuth auth;

    private FragmentEmailSigninBinding binding;

    public EmailSignInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EmailSigninFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmailSignInFragment newInstance() {
        EmailSignInFragment fragment = new EmailSignInFragment();
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
        binding = FragmentEmailSigninBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            NavDirections action = EmailSignInFragmentDirections
                    .actionEmailSigninFragmentToHomeFragment();
            Navigation.findNavController(view).navigate(action);
        }

        binding.textViewSignUpLink.setOnClickListener(this::goToSignUp);
        binding.textViewGoBackLink.setOnClickListener(this::goToSignInOptions);
        binding.buttonLogin.setOnClickListener(this::loginHandler);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        auth = null;
    }

    private void loginHandler(View view) {
        String email = binding.editTextEmailAddress.getText().toString();
        String password = binding.editTextTextPassword.getText().toString();

        String validationText = ValidationHelper.isCredentialsValid(
                email,
                password,
                null,
                ValidationHelper.CredentialType.SIGN_IN
        );

        if ("".equals(validationText)) {
            signInUser(email.toLowerCase().trim(), password, view);
        } else {
            Toast.makeText(getActivity(), validationText, Toast.LENGTH_SHORT).show();
        }
    }

    private void signInUser(String email, String password, View view) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        goToHome(view);
                    } else {
                        Toast.makeText(getContext(), "Login Failed!", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    private void goToSignUp(View view) {
        NavDirections action = EmailSignInFragmentDirections.actionEmailSigninFragmentToEmailSignupFragment();
        Navigation.findNavController(view).navigate(action);
    }

    private void goToHome(View view) {
        NavDirections action = EmailSignInFragmentDirections
                .actionEmailSigninFragmentToHomeFragment();
        Navigation.findNavController(view).navigate(action);
    }

    private void goToSignInOptions(View view) {
        NavDirections action = EmailSignInFragmentDirections
                .actionEmailSignInFragmentToSignInOptionsFragment();
        Navigation.findNavController(view).navigate(action);
    }

}