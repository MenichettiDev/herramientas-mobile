package com.example.administracionherramientas.ui.reparacion;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.administracionherramientas.R;
import com.example.administracionherramientas.databinding.FragmentPrestamoBinding;
import com.example.administracionherramientas.databinding.FragmentReparacionBinding;
import com.example.administracionherramientas.models.Cliente;
import com.example.administracionherramientas.models.Herramienta;
import com.example.administracionherramientas.models.Obra;
import com.example.administracionherramientas.models.Proveedor;
import com.example.administracionherramientas.models.Usuario;
import com.example.administracionherramientas.ui.prestamo.PrestamoViewModel;

import java.util.Calendar;

public class ReparacionFragment extends Fragment {

    private ReparacionViewModel mViewModel;
    private FragmentReparacionBinding binding;

    private AutoCompleteTextView autoCompleteProveedor;
    private AutoCompleteTextView autoCompleteHerramienta;

    private int selectedHerramientaId = -1;
    private int selectedProveedorId = -1;
    private long selectedFechaMillis = Calendar.getInstance().getTimeInMillis();


    public static ReparacionFragment newInstance() {
        return new ReparacionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentReparacionBinding.inflate(inflater, container, false);
        autoCompleteProveedor = binding.cbProveedor;
        autoCompleteHerramienta = binding.cbHerramientas;

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReparacionViewModel.class);

        mViewModel.fetchHerramientas("");
        mViewModel.fetchProveedores("");


        // ============================
        // OBSERVERS
        // ============================


        mViewModel.getProveedores().observe(getViewLifecycleOwner(), proveedores -> {
            if (proveedores != null) {
                ArrayAdapter<Proveedor> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, proveedores);
                autoCompleteProveedor.setAdapter(adapter);
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


        mViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });

        mViewModel.getNavegarDashboard().observe(getViewLifecycleOwner(), navegarDashboard -> {
            if (navegarDashboard != null && navegarDashboard) {

                Navigation.findNavController(getView())
                        .navigate(R.id.nav_dashboard);
            }

        });


        // ============================
        // LISTENERS
        // ============================

        autoCompleteProveedor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // reset selected id when user types to avoid stale selection
                if (s.length() == 0) {
                    selectedProveedorId = -1;
                }
                if (s.length() > 1) {
                    mViewModel.fetchProveedores(s.toString());
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
                // SI el campo está completando un item del dropDown → IGNORAR
                if (autoCompleteHerramienta.isPerformingCompletion()) return;
                // reset selected id when user types to avoid stale selection
                if (s.length() == 0) {
                    selectedHerramientaId = -1;
                }
                mViewModel.fetchHerramientas(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


        autoCompleteProveedor.setOnItemClickListener((parent, view, position, id) -> {
            if (parent.getAdapter() == null || position < 0 || position >= parent.getAdapter().getCount()) return;
            Proveedor selected = (Proveedor) parent.getItemAtPosition(position);
            selectedProveedorId = selected.getIdProveedor();
        });

        autoCompleteHerramienta.setOnItemClickListener((parent, view, position, id) -> {
            if (parent.getAdapter() == null || position < 0 || position >= parent.getAdapter().getCount()) return;
            Herramienta selected = (Herramienta) parent.getItemAtPosition(position);
            selectedHerramientaId = selected.getIdHerramienta();
        });


        // ============================
        // FECHA (CalendarView)
        // ============================

        selectedFechaMillis = binding.calendarView.getDate();

        binding.calendarView.setOnDateChangeListener((view, year, month, day) -> {
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, day, 0, 0, 0);
            cal.set(Calendar.MILLISECOND, 0);
            selectedFechaMillis = cal.getTimeInMillis();
        });

        // ============================
        // BOTÓN GUARDAR REPARACION
        // ============================

        binding.btnCargarReparacion.setOnClickListener(v -> {
            if (selectedHerramientaId <= 0) {
                Toast.makeText(getContext(), "Seleccione una herramienta válida", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedProveedorId <= 0) {
                Toast.makeText(getContext(), "Seleccione un proveedor designado", Toast.LENGTH_SHORT).show();
                return;
            }

            mViewModel.guardarReparacion(
                    selectedHerramientaId,
                    selectedProveedorId,
                    selectedFechaMillis
            );
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        mViewModel.resetNavegacion();
        selectedHerramientaId = -1;
        selectedProveedorId = -1;
        selectedFechaMillis = Calendar.getInstance().getTimeInMillis();


        if (autoCompleteProveedor != null) autoCompleteProveedor.setText("");
        if (autoCompleteHerramienta != null) autoCompleteHerramienta.setText("");
    }

}