package com.example.administracionherramientas.ui.alertas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.administracionherramientas.R;

import java.util.ArrayList;

public class AlertaFragment extends Fragment {

    private AlertaViewModel mViewModel;
    private RecyclerView recyclerView;
    private AlertaAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_alerta, container, false);
        recyclerView = root.findViewById(R.id.rv_alertas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AlertaAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AlertaViewModel.class);
        mViewModel.getAlertas().observe(getViewLifecycleOwner(), alertas -> {
            adapter.setAlertas(alertas);
        });
        mViewModel.fetchAlertas();
    }

}
