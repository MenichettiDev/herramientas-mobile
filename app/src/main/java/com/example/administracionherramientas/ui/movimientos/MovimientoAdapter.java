package com.example.administracionherramientas.ui.movimientos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.administracionherramientas.R;
import com.example.administracionherramientas.models.Movimiento;

import java.util.List;

public class MovimientoAdapter extends RecyclerView.Adapter<MovimientoAdapter.ViewHolder> {
    private List<Movimiento> lista;
    private Context context;
    private LayoutInflater li;

    public MovimientoAdapter(List<Movimiento> lista, Context context, LayoutInflater li) {
        this.lista = lista;
        this.context = context;
        this.li = li;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = li.inflate(R.layout.item_movimiento, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movimiento movimiento = lista.get(position);
        holder.tvNombre.setText(movimiento.getNombreHerramienta());
        holder.tvTipoMovimiento.setText(movimiento.getTipoMovimiento());
        holder.tvCodigo.setText(movimiento.getCodigoHerramienta());

        if (movimiento.getTipoMovimiento().equals("Prestamo")) {
            holder.ivTipoMovimiento.setImageResource(R.drawable.derechamenu);
        } else if (movimiento.getTipoMovimiento().equals("Devolucion")) {
            holder.ivTipoMovimiento.setImageResource(R.drawable.izquierdamenu);
        } else if (movimiento.getTipoMovimiento().equals("Envio Reparación")) {
            holder.ivTipoMovimiento.setImageResource(R.drawable.repairmenu);
        }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView  tvNombre, tvTipoMovimiento, tvCodigo;
        ImageView ivTipoMovimiento;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tv_nombre);
            tvTipoMovimiento = itemView.findViewById(R.id.tv_responsable);
            ivTipoMovimiento = itemView.findViewById(R.id.iv_tipo_movimiento);
            tvCodigo = itemView.findViewById(R.id.tv_codigo);
        }
    }
}
