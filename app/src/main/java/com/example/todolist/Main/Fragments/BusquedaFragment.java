package com.example.todolist.Main.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todolist.Main.Arquitectura.ViewModel;
import com.example.todolist.Main.Adapter;
import com.example.todolist.Main.Objetos.Tarea;
import com.example.todolist.R;
import com.example.todolist.databinding.FragmentBusquedaBinding;

import java.util.List;

public class BusquedaFragment extends Fragment {

    FragmentBusquedaBinding binding;
    ViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentBusquedaBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(requireView());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        binding.recyclerBusqueda.setLayoutManager(gridLayoutManager);

        Adapter ad = new Adapter();
        binding.recyclerBusqueda.setAdapter(ad.recuperarAdapter(getLayoutInflater(),navController,R.id.action_busquedaFragment_to_detallesListaFragment, this.getView(),viewModel));

        binding.floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_busquedaFragment_to_nuevaTareaFragment);
            }
        });

        binding.busqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String linea = charSequence.toString();

                if (!linea.equalsIgnoreCase("")) {
                    viewModel.buscar(("%"+linea+"%")).observe(getViewLifecycleOwner(), new Observer<List<Tarea>>() {
                        @Override
                        public void onChanged(List<Tarea> tareas) {
                            ad.establecerListaTareas(tareas);
                        }
                    });
                }else{
                    ad.establecerListaTareas(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}