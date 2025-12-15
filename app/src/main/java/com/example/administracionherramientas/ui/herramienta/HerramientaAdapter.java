package com.example.administracionherramientas.ui.herramienta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.administracionherramientas.databinding.ItemHerramientaBinding;
import com.example.administracionherramientas.models.Herramienta;

import java.util.List;

public class HerramientaAdapter extends RecyclerView.Adapter<HerramientaAdapter.HerramientaViewHolder> {

    private final List<Herramienta> herramientaList;
    private final Context context;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDeleteClick(Herramienta herramienta);
    }

    public HerramientaAdapter(List<Herramienta> herramientaList, Context context, OnItemClickListener listener) {
        this.herramientaList = herramientaList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HerramientaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHerramientaBinding binding = ItemHerramientaBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HerramientaViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HerramientaViewHolder holder, int position) {
        Herramienta herramienta = herramientaList.get(position);
        holder.binding.tvCodigo.setText(herramienta.getCodigo());
        holder.binding.tvNombre.setText(herramienta.getNombreHerramienta());
        holder.binding.tvMarca.setText(herramienta.getMarca());
        holder.binding.ivEliminar.setOnClickListener(v -> listener.onDeleteClick(herramienta));
    }

    @Override
    public int getItemCount() {
        return herramientaList.size();
    }

    public static class HerramientaViewHolder extends RecyclerView.ViewHolder {
        ItemHerramientaBinding binding;

        public HerramientaViewHolder(ItemHerramientaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
