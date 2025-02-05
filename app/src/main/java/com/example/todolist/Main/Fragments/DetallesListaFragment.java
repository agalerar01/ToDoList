package com.example.todolist.Main.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todolist.Main.Arquitectura.ImageUtils;
import com.example.todolist.Main.Objetos.Tarea;
import com.example.todolist.Main.Arquitectura.ViewModel;
import com.example.todolist.R;
import com.example.todolist.databinding.FragmentDetallesListaBinding;

import java.text.SimpleDateFormat;

public class DetallesListaFragment extends Fragment {

    FragmentDetallesListaBinding binding;
    ViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDetallesListaBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.tareaElegida.observe(getViewLifecycleOwner(), new Observer<Tarea>() {
            @Override
            public void onChanged(Tarea tarea) {
                binding.titulo.setText(tarea.getTitulo());
                binding.categoria.setText(tarea.getCategoria());
                binding.descripcion.setText(tarea.getDescripcion());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                binding.fechaLimite.setText(dateFormat.format(tarea.getFechaLimite()));
                binding.creador.setText("Creada por: "+tarea.getEmailCreador());
                if(tarea.getFoto() != null){
                    binding.imageView.setImageBitmap(ImageUtils.blobToBitmap(tarea.getFoto()));
                }
                if(tarea.isHecha()){
                    binding.completada.setText("Si");
                    binding.completada.setTextColor(getResources().getColor(R.color.green));
                }else{
                    binding.completada.setText("No");
                    binding.completada.setTextColor(getResources().getColor(R.color.red));
                }
            }
        });
    }
}