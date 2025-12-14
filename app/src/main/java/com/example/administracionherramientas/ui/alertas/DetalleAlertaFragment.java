package com.example.administracionherramientas.ui.alertas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.administracionherramientas.R;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DetalleAlertaFragment extends Fragment {

    private AlertaViewModel mViewModel;
    private TextView tvNombreHerramienta, tvCodigoHerramienta, tvTipoAlerta, tvFechaVencimiento, tvResponsable, tvTipoMovimiento;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.detalle_alerta, container, false);
        tvNombreHerramienta = root.findViewById(R.id.tv_detalle_nombre_herramienta);
        tvCodigoHerramienta = root.findViewById(R.id.tv_detalle_codigo_herramienta);
        tvTipoAlerta = root.findViewById(R.id.tv_detalle_tipo_alerta);
        tvFechaVencimiento = root.findViewById(R.id.tv_detalle_fecha_vencimiento);
        tvResponsable = root.findViewById(R.id.tv_detalle_responsable);
        tvTipoMovimiento = root.findViewById(R.id.tv_detalle_tipo_movimiento);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AlertaViewModel.class);

        if (getArguments() != null) {
            int alertaId = getArguments().getInt("alertaId");
            mViewModel.fetchAlertaDetalle(alertaId);
        }

        mViewModel.getAlertaSeleccionada().observe(getViewLifecycleOwner(), alerta -> {
            if (alerta != null) {
                tvNombreHerramienta.setText(alerta.getHerramientaNombre());
                tvCodigoHerramienta.setText(alerta.getHerramientaCodigo());
                tvTipoAlerta.setText(alerta.getNombreTipoAlerta());
                if (alerta.getFechaVencimiento() != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    tvFechaVencimiento.setText(sdf.format(alerta.getFechaVencimiento()));
                }
                tvResponsable.setText(alerta.getResponsableNombre());
                tvTipoMovimiento.setText(alerta.getTipoMovimiento());
            }
        });
    }
}
