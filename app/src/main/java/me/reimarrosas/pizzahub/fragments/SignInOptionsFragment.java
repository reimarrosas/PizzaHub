package me.reimarrosas.pizzahub.fragments;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.databinding.FragmentSigninOptionsBinding;

/**
 * Sign In Options Fragment that shows the options the user can use to sign in on the application.
 */
public class SignInOptionsFragment extends Fragment {

    private static final int AUTH_GOOGLE = 1337;
    private static final String TAG = "SignInOptionsFragment";

    private FragmentSigninOptionsBinding binding;

    private FirebaseAuth auth;

    private GoogleSignInClient googleSignInClient;

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

        auth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .build();
        googleSignInClient = GoogleSignIn.getClient(getContext(), gso);
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

        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            goToHome(currentUser);
        }

        binding.buttonGoogle.setOnClickListener(this::googleLoginHandler);
        binding.buttonEmail.setOnClickListener(this::goToSignIn);
        binding.textViewAdminLink.setOnClickListener(this::goToAdminLogin);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTH_GOOGLE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void goToSignIn(View view) {
        NavDirections action = SignInOptionsFragmentDirections
                .actionSignInOptionsFragmentToEmailSigninFragment();
        Navigation.findNavController(view).navigate(action);
    }

    private void goToHome(FirebaseUser user) {
        NavDirections action = SignInOptionsFragmentDirections
                .actionSignInOptionsFragmentToHomeFragment(user);
        Navigation.findNavController(getView()).navigate(action);
    }

    private void goToAdminLogin(View view) {
        NavDirections action = SignInOptionsFragmentDirections
                .actionSignInOptionsFragmentToAdminLoginFragment();
        Navigation.findNavController(view).navigate(action);
    }

    private void googleLoginHandler(View view) {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, AUTH_GOOGLE);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String idToken = account.getIdToken();
            if (idToken != null) {
                AuthCredential firebaseCredential = GoogleAuthProvider
                        .getCredential(idToken, null);
                firebaseGoogleAuth(firebaseCredential);
            }
        } catch (ApiException e) {
            Log.e(TAG, "Sign In Result: " + e.getStatusCode());
        }
    }

    private void firebaseGoogleAuth(AuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        goToHome(task.getResult().getUser());
                    } else {
                        Toast.makeText(getContext(), "Google Sign In Failed!",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }

}