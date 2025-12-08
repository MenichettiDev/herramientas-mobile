package com.example.administracionherramientas.ui.prestamo;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.administracionherramientas.R;
import com.example.administracionherramientas.models.Cliente;
import com.example.administracionherramientas.models.Herramienta;
import com.example.administracionherramientas.models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class PrestamoFragment extends Fragment {

    private PrestamoViewModel mViewModel;
    private AutoCompleteTextView autoCompleteUsuario;
    private AutoCompleteTextView autoCompleteHerramienta;
    private AutoCompleteTextView autoCompleteCliente;

    public static PrestamoFragment newInstance() {
        return new PrestamoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_prestamo, container, false);
        autoCompleteUsuario = root.findViewById(R.id.cb_usuario);
        autoCompleteHerramienta = root.findViewById(R.id.cb_herramientas);
        autoCompleteCliente = root.findViewById(R.id.cb_cliente);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PrestamoViewModel.class);

        mViewModel.getUsuarios().observe(getViewLifecycleOwner(), usuarios -> {
            if (usuarios != null) {
                ArrayAdapter<Usuario> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, usuarios);
                autoCompleteUsuario.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        mViewModel.getHerramientas().observe(getViewLifecycleOwner(), herramientas -> {
            if (herramientas != null) {
                ArrayAdapter<Herramienta> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, herramientas);
                autoCompleteHerramienta.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        mViewModel.getClientes().observe(getViewLifecycleOwner(), clientes -> {
            if (clientes != null) {
                ArrayAdapter<Cliente> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, clientes);
                autoCompleteCliente.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        mViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteUsuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 2) { // Para no buscar con cada letra
                    mViewModel.fetchUsuarios(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        autoCompleteHerramienta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mViewModel.fetchHerramientas(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        autoCompleteCliente.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 2) { // Para no buscar con cada letra
                    mViewModel.fetchClientes(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

}
