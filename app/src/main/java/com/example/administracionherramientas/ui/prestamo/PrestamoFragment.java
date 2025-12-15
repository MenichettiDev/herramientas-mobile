package com.example.administracionherramientas.ui.prestamo;

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
import android.widget.Adapter;
import android.widget.AutoCompleteTextView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.administracionherramientas.R;
import com.example.administracionherramientas.databinding.FragmentPrestamoBinding;
import com.example.administracionherramientas.models.Cliente;
import com.example.administracionherramientas.models.Herramienta;
import com.example.administracionherramientas.models.Obra;
import com.example.administracionherramientas.models.Usuario;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PrestamoFragment extends Fragment {

    private PrestamoViewModel mViewModel;
    private FragmentPrestamoBinding binding;

    private AutoCompleteTextView autoCompleteUsuario;
    private AutoCompleteTextView autoCompleteHerramienta;
    private AutoCompleteTextView autoCompleteCliente;
    private AutoCompleteTextView autoCompleteObra;
    private int selectedClientId = -1;

    private int selectedHerramientaId = -1;
    private int selectedUsuarioId = -1;
    private int selectedObraId = -1;
    private long selectedFechaMillis = Calendar.getInstance().getTimeInMillis();

    public static PrestamoFragment newInstance() {
        return new PrestamoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPrestamoBinding.inflate(inflater, container, false);
        autoCompleteUsuario = binding.cbUsuario;
        autoCompleteHerramienta = binding.cbHerramientas;
        autoCompleteCliente = binding.cbCliente;
        autoCompleteObra = binding.cbObra;

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PrestamoViewModel.class);

        autoCompleteObra.setEnabled(false);

        mViewModel.fetchClientes("");
        mViewModel.fetchHerramientas("");
        mViewModel.fetchUsuarios("");


        // ============================
        // OBSERVERS
        // ============================


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
            }
        });

        mViewModel.getObras().observe(getViewLifecycleOwner(), obras -> {
            if (obras != null) {
                ArrayAdapter<Obra> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, obras);
                autoCompleteObra.setAdapter(adapter);
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

        autoCompleteUsuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // reset selected id when user types to avoid stale selection
                if (s.length() == 0) {
                    selectedUsuarioId = -1;
                }
                mViewModel.fetchUsuarios(s.toString());
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

        autoCompleteCliente.addTextChangedListener(new TextWatcher() {
             @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 1) {
                    mViewModel.fetchClientes(s.toString());
                } else if (s.length() == 0) {
                    mViewModel.fetchClientes("");
                    selectedClientId = -1;
                    autoCompleteObra.setText("");
                    autoCompleteObra.setEnabled(false);
                    mViewModel.clearObras();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        autoCompleteCliente.setOnItemClickListener((parent, view, position, id) -> {
            if (parent.getAdapter() == null || position < 0 || position >= parent.getAdapter().getCount()) return;
            Cliente selected = (Cliente) parent.getItemAtPosition(position);
            selectedClientId = selected.getIdCliente();
            autoCompleteObra.setEnabled(true);
//            autoCompleteObra.setText("");
            mViewModel.fetchObras(selectedClientId, "");
        });

        autoCompleteObra.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // reset selected obra id when typing
                if (s.length() == 0) {
                    selectedObraId = -1;
                }
                if (selectedClientId != -1) {
                    mViewModel.fetchObras(selectedClientId, s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        autoCompleteUsuario.setOnItemClickListener((parent, view, position, id) -> {
            if (parent.getAdapter() == null || position < 0 || position >= parent.getAdapter().getCount()) return;
            Usuario selected = (Usuario) parent.getItemAtPosition(position);
            selectedUsuarioId = selected.getId();
        });

        autoCompleteHerramienta.setOnItemClickListener((parent, view, position, id) -> {
            if (parent.getAdapter() == null || position < 0 || position >= parent.getAdapter().getCount()) return;
            Herramienta selected = (Herramienta) parent.getItemAtPosition(position);
            selectedHerramientaId = selected.getIdHerramienta();
        });

        autoCompleteObra.setOnItemClickListener((parent, view, position, id) -> {
            if (parent.getAdapter() == null || position < 0 || position >= parent.getAdapter().getCount()) return;
            Obra selected = (Obra) parent.getItemAtPosition(position);
            selectedObraId = selected.getIdObra();
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
        // BOTÓN GUARDAR PRÉSTAMO
        // ============================

        binding.btnCargarPrestamo.setOnClickListener(v -> {
            if (selectedHerramientaId <= 0) {
                Toast.makeText(getContext(), "Seleccione una herramienta válida", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedUsuarioId <= 0) {
                Toast.makeText(getContext(), "Seleccione un usuario responsable", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedObraId <= 0) {
                Toast.makeText(getContext(), "Seleccione una obra válida", Toast.LENGTH_SHORT).show();
                return;
            }

            mViewModel.guardarPrestamo(
                    selectedHerramientaId,
                    selectedUsuarioId,
                    selectedObraId,
                    selectedFechaMillis
            );
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.resetNavegacion();
        selectedClientId = -1;
        selectedHerramientaId = -1;
        selectedUsuarioId = -1;
        selectedObraId = -1;
        selectedFechaMillis = Calendar.getInstance().getTimeInMillis();


        if (autoCompleteUsuario != null) autoCompleteUsuario.setText("");
        if (autoCompleteHerramienta != null) autoCompleteHerramienta.setText("");
        if (autoCompleteCliente != null) autoCompleteCliente.setText("");
        if (autoCompleteObra != null) {
            autoCompleteObra.setText("");
            autoCompleteObra.setEnabled(false);
        }
    }

}
