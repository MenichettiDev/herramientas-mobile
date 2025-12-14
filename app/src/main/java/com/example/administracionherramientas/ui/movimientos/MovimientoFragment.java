package com.example.administracionherramientas.ui.movimientos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.administracionherramientas.R;
import com.example.administracionherramientas.databinding.FragmentMovimientoBinding;
import com.example.administracionherramientas.models.Movimiento;

import java.util.ArrayList;

public class MovimientoFragment extends Fragment {

    private MovimientoViewModel mViewModel;
    private FragmentMovimientoBinding binding;
    private MovimientoAdapter adapter;

    public static MovimientoFragment newInstance() {
        return new MovimientoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMovimientoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mViewModel = new ViewModelProvider(this).get(MovimientoViewModel.class);

        binding.rvMovimientos.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewModel.getMovimientos().observe(getViewLifecycleOwner(), pagedResponse -> {
            if (pagedResponse != null) {
                adapter = new MovimientoAdapter(pagedResponse.getData(), getContext(), getLayoutInflater());
                binding.rvMovimientos.setAdapter(adapter);
            }
        });

        mViewModel.obtenerMovimientos("", null);

        setupFilters();

        return root;
    }

    private void setupFilters() {
        EditText etNombre = binding.tilNombre.getEditText();
        Spinner spinner = binding.spinnerMovimiento;

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.tipos_movimiento, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterMovements();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        etNombre.addTextChangedListener(textWatcher);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterMovements();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void filterMovements() {
        String nombre = binding.tilNombre.getEditText().getText().toString();
        int selectedPosition = binding.spinnerMovimiento.getSelectedItemPosition();

        Integer idTipoMovimiento = null;
        if (selectedPosition > 0) {
            // Assuming the order in the array is: Todos, Préstamo, Devolución, Reparación
            idTipoMovimiento = selectedPosition;
        }

        mViewModel.obtenerMovimientos(nombre, idTipoMovimiento);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}