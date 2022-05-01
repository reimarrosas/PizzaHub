package me.reimarrosas.pizzahub.fragments.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.reimarrosas.pizzahub.R;
import me.reimarrosas.pizzahub.databinding.FragmentSavePremadeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SavePremadeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavePremadeFragment extends Fragment {

    private FragmentSavePremadeBinding binding;

    public SavePremadeFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SavePremadeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SavePremadeFragment newInstance() {
        SavePremadeFragment fragment = new SavePremadeFragment();
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
        binding = FragmentSavePremadeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

}