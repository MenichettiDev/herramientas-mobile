package com.example.administracionherramientas.ui.alertas;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.administracionherramientas.R;
import com.example.administracionherramientas.models.Alerta;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AlertaAdapter extends RecyclerView.Adapter<AlertaAdapter.AlertaViewHolder> {

    private List<Alerta> alertas;

    public AlertaAdapter(List<Alerta> alertas) {
        this.alertas = alertas;
    }

    @NonNull
    @Override
    public AlertaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alerta, parent, false);
        return new AlertaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlertaViewHolder holder, int position) {
        Alerta alerta = alertas.get(position);
        holder.bind(alerta);
    }

    @Override
    public int getItemCount() {
        return alertas != null ? alertas.size() : 0;
    }

    public void setAlertas(List<Alerta> newAlertas) {
        this.alertas = newAlertas;
        notifyDataSetChanged();
    }

    class AlertaViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNombre;
        private final TextView tvCodigo;
        private final TextView tvDiasVencimiento;

        public AlertaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tv_herramienta_nombre);
            tvCodigo = itemView.findViewById(R.id.tv_herramienta_codigo);
            tvDiasVencimiento = itemView.findViewById(R.id.tv_dias_vencimiento);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Alerta alerta = alertas.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putInt("alertaId", alerta.getIdAlerta());
                    Navigation.findNavController(v).navigate(R.id.action_nav_alertas_to_detalleAlerta, bundle);
                }
            });
        }

        void bind(Alerta alerta) {
            tvNombre.setText(alerta.getHerramientaNombre());
            tvCodigo.setText(alerta.getHerramientaCodigo());

            long diff = alerta.getFechaVencimiento().getTime() - System.currentTimeMillis();
            long dias = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            if (dias < 0) {
                tvDiasVencimiento.setText("Vencido");
            } else if (dias == 0) {
                tvDiasVencimiento.setText("Vence hoy");
            } else {
                tvDiasVencimiento.setText("Vence en " + dias + " días");
            }
        }
    }
}
