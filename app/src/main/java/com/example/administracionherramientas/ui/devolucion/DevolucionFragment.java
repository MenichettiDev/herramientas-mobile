package com.example.administracionherramientas.ui.devolucion;

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
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administracionherramientas.R;
import com.example.administracionherramientas.databinding.FragmentDevolucionBinding;
import com.example.administracionherramientas.databinding.FragmentPrestamoBinding;
import com.example.administracionherramientas.models.Cliente;
import com.example.administracionherramientas.models.EstadoFisicoHerramienta;
import com.example.administracionherramientas.models.Herramienta;
import com.example.administracionherramientas.models.Obra;
import com.example.administracionherramientas.models.Proveedor;
import com.example.administracionherramientas.models.Usuario;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class DevolucionFragment extends Fragment {

    private DevolucionViewModel mViewModel;
    private FragmentDevolucionBinding binding;

    private AutoCompleteTextView autoCompleteUsuario;
    private AutoCompleteTextView autoCompleteProveedor;
    private AutoCompleteTextView autoCompleteHerramienta;
    private AutoCompleteTextView autoCompleteEstadoFisicoHerramienta;
    private int selectedHerramientaId = -1;
    private int selectedUsuarioId = -1;
    private int selectedProveedorId = -1;
    private int selectedEstadoFisicoHerramientaId = -1;

    public static DevolucionFragment newInstance() {
        return new DevolucionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDevolucionBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(DevolucionViewModel.class);
        autoCompleteUsuario = binding.cbUsuario;
        autoCompleteProveedor = binding.cbProveedor;
        autoCompleteHerramienta = binding.cbHerramientas;
        autoCompleteEstadoFisicoHerramienta = binding.cbEstadoFisico;

        RadioGroup radioGroup = binding.radioGroupDevolucion;
        TextInputLayout textInputUsuario = binding.textInputLayoutUsuario;
        TextInputLayout textInputProveedor = binding.textInputLayoutProveedor;
        Button btnCargar = binding.btnCargarDevolucion;

        // Observa los cambios en el ViewModel
        mViewModel.getIsUsuarioVisible().observe(getViewLifecycleOwner(), isVisible ->
                textInputUsuario.setVisibility(isVisible ? View.VISIBLE : View.GONE)
        );

        mViewModel.getIsProveedorVisible().observe(getViewLifecycleOwner(), isVisible ->
                textInputProveedor.setVisibility(isVisible ? View.VISIBLE : View.GONE)
        );

        mViewModel.getIsHerramientaVisible().observe(getViewLifecycleOwner(), isVisible ->
                binding.textInputLayoutHerramientas.setVisibility(isVisible ? View.VISIBLE : View.GONE)
        );

        mViewModel.getIsEstadoFisicoVisible().observe(getViewLifecycleOwner(), isVisible ->
                binding.textInputLayoutEstadoFisico.setVisibility(isVisible ? View.VISIBLE : View.GONE)
        );

        mViewModel.getIsButtonVisible().observe(getViewLifecycleOwner(), isVisible ->
                btnCargar.setVisibility(isVisible ? View.VISIBLE : View.GONE)
        );

        // Maneja los eventos del RadioGroup
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbDevolucionPrestamo) {
                mViewModel.onDevolucionPrestamoSelected();
            } else if (checkedId == R.id.rbDevolucionReparacion) {
                mViewModel.onDevolucionReparacionSelected();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DevolucionViewModel.class);
//        mViewModel.fetchHerramientas("");
        mViewModel.fetchProveedores("");
        mViewModel.fetchUsuarios("");

        mViewModel.getUsuarios().observe(getViewLifecycleOwner(), usuarios -> {
            if (usuarios != null) {
                ArrayAdapter<Usuario> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, usuarios);
                autoCompleteUsuario.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

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

        mViewModel.getEstadoFisicoHerramienta().observe(getViewLifecycleOwner(), estadoFisicoHerramientas -> {
            if (estadoFisicoHerramientas != null) {
                ArrayAdapter<EstadoFisicoHerramienta> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, estadoFisicoHerramientas);
                autoCompleteEstadoFisicoHerramienta.setAdapter(adapter);
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

        autoCompleteUsuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // reset selected id when user types to avoid stale selection
                if (s.length() == 0) {
                    selectedUsuarioId = -1;
                }
                if (s.length() > 1) {
                    mViewModel.fetchUsuarios(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

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

        autoCompleteEstadoFisicoHerramienta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 1) {
                    mViewModel.fetchEstadoFisicoHerramientas();
                } else if (s.length() == 0) {
                    selectedEstadoFisicoHerramientaId = -1;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

//        autoCompleteHerramienta.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // SI el campo está completando un item del dropDown → IGNORAR
//                if (autoCompleteHerramienta.isPerformingCompletion()) return;
//                // reset selected id when user types to avoid stale selection
//                if (s.length() == 0) {
//                    selectedHerramientaId = -1;
//                }
//                mViewModel.fetchHerramientas(s.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {}
//        });


        autoCompleteUsuario.setOnItemClickListener((parent, view, position, id) -> {
            if (parent.getAdapter() == null || position < 0 || position >= parent.getAdapter().getCount()) return;
            Usuario selected = (Usuario) parent.getItemAtPosition(position);
            selectedUsuarioId = selected.getId();
            mViewModel.onUsuarioSelected(selectedUsuarioId);
        });

        autoCompleteProveedor.setOnItemClickListener((parent, view, position, id) -> {
            if (parent.getAdapter() == null || position < 0 || position >= parent.getAdapter().getCount()) return;
            Proveedor selected = (Proveedor) parent.getItemAtPosition(position);
            selectedProveedorId = selected.getIdProveedor();
            mViewModel.onProveedorSelected(selectedProveedorId);
        });

        autoCompleteHerramienta.setOnItemClickListener((parent, view, position, id) -> {
            if (parent.getAdapter() == null || position < 0 || position >= parent.getAdapter().getCount()) return;
            Herramienta selected = (Herramienta) parent.getItemAtPosition(position);
            selectedHerramientaId = selected.getIdHerramienta();
        });

        autoCompleteEstadoFisicoHerramienta.setOnItemClickListener((parent, view, position, id) -> {
            if (parent.getAdapter() == null || position < 0 || position >= parent.getAdapter().getCount()) return;
            EstadoFisicoHerramienta selected = (EstadoFisicoHerramienta) parent.getItemAtPosition(position);
            selectedEstadoFisicoHerramientaId = selected.getIdEstadoFisico();
        });


        // ============================
        // BOTÓN GUARDAR PRÉSTAMO
        // ============================

        binding.btnCargarDevolucion.setOnClickListener(v -> {
            if (selectedHerramientaId <= 0) {
                Toast.makeText(getContext(), "Seleccione una herramienta válida", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedUsuarioId <= 0 && selectedProveedorId <= 0) {
                Toast.makeText(getContext(), "Seleccione un responsable de devolucion", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedEstadoFisicoHerramientaId <= 0) {
                Toast.makeText(getContext(), "Seleccione un estado de la herramienta", Toast.LENGTH_SHORT).show();
                return;
            }

            mViewModel.guardarDevolucion(
                    selectedHerramientaId,
                    selectedUsuarioId,
                    selectedProveedorId,
                    selectedEstadoFisicoHerramientaId
            );
        });
    }

}