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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todolist.Main.Adapter;
import com.example.todolist.Main.Objetos.Tarea;
import com.example.todolist.R;
import com.example.todolist.Main.Arquitectura.ViewModel;
import com.example.todolist.databinding.FragmentListaBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ListaFragment extends Fragment {

    FragmentListaBinding binding;
    ViewModel viewModel;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentListaBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(requireView());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        binding.recyclerLista.setLayoutManager(gridLayoutManager);

        Adapter ad = new Adapter();
        binding.recyclerLista.setAdapter(ad.recuperarAdapter(getLayoutInflater(),navController,R.id.action_listaFragment_to_detallesListaFragment, this.getView(),viewModel));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT
        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int posicion = viewHolder.getAdapterPosition();
                Tarea tarea = ad.obtenerTarea(posicion);

                if(tarea.getEmailCreador().equals(mAuth.getCurrentUser().getEmail().toString())) {
                    viewModel.eliminar(tarea);
                }
                ad.notifyDataSetChanged();
            }
        });

        itemTouchHelper.attachToRecyclerView(binding.recyclerLista);

        viewModel.obtenerTareas();

        viewModel.obtenerTareas().observe(getViewLifecycleOwner(), new Observer<List<Tarea>>() {
            @Override
            public void onChanged(List<Tarea> tareas) {
                ad.establecerListaTareas(tareas);
            }
        });

        binding.floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_listaFragment_to_nuevaTareaFragment);
            }
        });
    }
}