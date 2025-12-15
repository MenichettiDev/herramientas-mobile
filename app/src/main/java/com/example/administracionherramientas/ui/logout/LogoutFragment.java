package com.example.administracionherramientas.ui.logout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.administracionherramientas.R;
import com.example.administracionherramientas.ui.login.LoginActivity;

public class LogoutFragment extends Fragment {

    private LogoutViewModel mViewModel;

    public static LogoutFragment newInstance() {
        return new LogoutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.logout_fragment, container, false);
        mViewModel = new ViewModelProvider(this).get(LogoutViewModel.class);

        mViewModel.getLogoutExitoso().observe(getViewLifecycleOwner(), exitoso -> {
            if (exitoso) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }
        });

        new AlertDialog.Builder(getContext())
                .setTitle("Cerrar Sesión")
                .setMessage("¿Estás seguro de que deseas cerrar la sesión?")
                .setPositiveButton("Sí", (dialog, which) -> mViewModel.logout(getContext()))
                .setNegativeButton("No", (dialog, which) -> {
                    if (getView() != null) {
                        Navigation.findNavController(root).popBackStack();
                    }
                })
                .show();

        return root;
    }
}
