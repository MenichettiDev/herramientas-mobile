package com.example.administracionherramientas.ui.dashboard;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administracionherramientas.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private DashboardViewModel mViewModel;
    private FragmentDashboardBinding binding;

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        mViewModel.getDisponiblesCount().observe(getViewLifecycleOwner(), count -> {
            if (count != null) {
                binding.availableToolsCount.setText(String.valueOf(count));
            }
        });

        mViewModel.getPrestamoCount().observe(getViewLifecycleOwner(), count -> {
            if (count != null) {
                binding.loanedToolsCount.setText(String.valueOf(count));
            }
        });

        mViewModel.getReparacionCount().observe(getViewLifecycleOwner(), count -> {
            if (count != null) {
                binding.repairToolsCount.setText(String.valueOf(count));
            }
        });

        mViewModel.getTotalCount().observe(getViewLifecycleOwner(), count -> {
            if (count != null) {
                binding.totalToolsCount.setText(String.valueOf(count));
            }
        });

        mViewModel.cargarDatos();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
