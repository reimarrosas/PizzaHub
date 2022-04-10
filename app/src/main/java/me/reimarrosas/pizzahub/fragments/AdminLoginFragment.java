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

import org.mindrot.jbcrypt.BCrypt;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.databinding.FragmentAdminLoginBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminLoginFragment extends Fragment {

    private String adminId;
    private String adminPw;

    private FragmentAdminLoginBinding binding;

    public AdminLoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AdminLoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminLoginFragment newInstance(String param1, String param2) {
        AdminLoginFragment fragment = new AdminLoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adminId = getString(R.string.admin_identifier);
        adminPw = getString(R.string.admin_pw);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonAdminLogin.setOnClickListener(_view -> {
            String adminId = binding.editTextAdminId.getText().toString();
            String adminPw = binding.editTextAdminPassword.getText().toString();
            if (adminId.equals(this.adminId) && BCrypt.checkpw(adminPw, this.adminPw)) {
                goToAdminHome();
            } else {
                Toast.makeText(getContext(), "Admin Login Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goToAdminHome() {
        NavDirections action = AdminLoginFragmentDirections
                .actionAdminLoginFragmentToAdminHomeFragment();
        Navigation.findNavController(getView()).navigate(action);
    }

}