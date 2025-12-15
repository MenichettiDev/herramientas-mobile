package com.example.administracionherramientas.ui.herramienta;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.administracionherramientas.R;
import com.example.administracionherramientas.databinding.FragmentHerramientaBinding;
import com.example.administracionherramientas.models.EstadoDisponibilidad;
import com.example.administracionherramientas.models.Herramienta;

import java.util.ArrayList;
import java.util.List;

public class HerramientaFragment extends Fragment implements HerramientaAdapter.OnItemClickListener {

    private FragmentHerramientaBinding binding;
    private HerramientaViewModel viewModel;
    private HerramientaAdapter adapter;
    private final List<Herramienta> herramientaList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHerramientaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HerramientaViewModel.class);

        setupRecyclerView();
        setupObservers();
        setupListeners();

        viewModel.onSuccessMessageShown();
        viewModel.cargarEstadosDisponibilidad();
        viewModel.buscarHerramientas(null, null, null, null);
    }

    private void setupRecyclerView() {
        adapter = new HerramientaAdapter(herramientaList, getContext(), this);
        binding.rvHerramientas.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvHerramientas.setAdapter(adapter);
    }

    private void setupObservers() {
        viewModel.getEstadosDisponibilidad().observe(getViewLifecycleOwner(), estados -> {
            if (estados != null) {
                ArrayAdapter<EstadoDisponibilidad> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, estados);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spinnerDisponibilidad.setAdapter(adapter);
            }
        });

        viewModel.getHerramientas().observe(getViewLifecycleOwner(), list -> {
            if (list != null) {
                herramientaList.clear();
                herramientaList.addAll(list);
                adapter.notifyDataSetChanged();
            }
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading ->
                binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE));

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getSuccessMessage().observe(getViewLifecycleOwner(), successMessage -> {
            if (successMessage != null) {
                Toast.makeText(getContext(), successMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupListeners() {
        binding.btnProcesar.setOnClickListener(v -> {
            String codigo = binding.etCodigo.getText().toString();
            String nombre = binding.etNombre.getText().toString();
            String marca = binding.etMarca.getText().toString();
            EstadoDisponibilidad estado = (EstadoDisponibilidad) binding.spinnerDisponibilidad.getSelectedItem();
            Integer idEstado = (estado != null) ? estado.getIdEstadoDisponibilidad() : null;
            viewModel.buscarHerramientas(codigo, nombre, marca, idEstado);
        });

        binding.rvHerramientas.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == herramientaList.size() - 1) {
                    String codigo = binding.etCodigo.getText().toString();
                    String nombre = binding.etNombre.getText().toString();
                    String marca = binding.etMarca.getText().toString();
                    EstadoDisponibilidad estado = (EstadoDisponibilidad) binding.spinnerDisponibilidad.getSelectedItem();
                    Integer idEstado = (estado != null) ? estado.getIdEstadoDisponibilidad() : null;
                    viewModel.cargarMasHerramientas(codigo, nombre, marca, idEstado);
                }
            }
        });
    }

    @Override
    public void onDeleteClick(Herramienta herramienta) {
        new AlertDialog.Builder(getContext())
                .setTitle("Eliminar Herramienta")
                .setMessage("¿Estás seguro de que quieres eliminar esta herramienta?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    viewModel.eliminarHerramienta(herramienta.getIdHerramienta());
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
