package com.example.todolist.Main;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.Objetos.Tarea;
import com.example.todolist.Arquitectura.ViewModel;
import com.example.todolist.R;
import com.example.todolist.databinding.ViewholderBinding;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Adapter {
    public ListaAdapter la;
    ViewModel viewModel;
    SharedPreferencesHelper helper;

    public ListaAdapter recuperarAdapter(LayoutInflater lt, NavController navController, int id, View view, ViewModel viewModel){
        this.la = new ListaAdapter(lt, navController, id, view);
        this.viewModel = viewModel;
        helper = new SharedPreferencesHelper(lt.getContext());

        return la;
    }

    public void establecerListaTareas(List<Tarea> lTareas){
        la.establecerLista(lTareas);
        notifyDataSetChanged();
    }

    public Tarea obtenerTarea(int posicion){
        return la.obtenerTarea(posicion);
    }

    public void notifyDataSetChanged(){
        la.notifyDataSetChanged();
    }

    class ListaViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderBinding binding;

        public ListaViewHolder(ViewholderBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class ListaAdapter extends RecyclerView.Adapter<ListaViewHolder>{
        NavController navController;
        private final LayoutInflater lt;
        private final int id;
        private final View view;
        List<Tarea> lTareas;

        public ListaAdapter(LayoutInflater lt, NavController navController, int id, View view){
            this.lt = lt;
            this.navController = navController;
            this.id = id;
            this.view = view;
        }

        @NonNull
        @Override
        public ListaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ListaViewHolder(ViewholderBinding.inflate(lt, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ListaViewHolder holder, int position) {
            Tarea tarea = lTareas.get(position);

            holder.binding.viewNombre.setText(tarea.getTitulo());
            holder.binding.viewCategoria.setText(String.valueOf(tarea.getFechaLimite()));
            holder.binding.viewCategoria.setClickable(false);

            holder.binding.imageButton2.setBackgroundColor(Color.parseColor("#FFFFFFFF"));

            establecerTick(tarea, holder);

            if (helper.devolverActivaAlerta()) {
                cambiarColorTarea(holder, tarea);
            }

            holder.itemView.setOnClickListener(v -> navegarPantallaDetalle(tarea));

            holder.binding.imageButton2.setOnClickListener(v -> marcarHecha(tarea, holder));
        }

        @Override
        public int getItemCount() {
            return lTareas != null ? lTareas.size() : 0;
        }

        public void navegarPantallaDetalle(Tarea tarea){
            if(tarea != null){
                viewModel.tareaElegida.postValue(tarea);
            }
            navController.navigate(id);
        }

        public void cambiarColorTarea(ListaViewHolder holder, Tarea tarea){

            Date fechaLimite = tarea.getFechaLimite();

            Calendar fechaActualCalendar = Calendar.getInstance();
            fechaActualCalendar.set(Calendar.HOUR_OF_DAY, 0);
            fechaActualCalendar.set(Calendar.MINUTE, 0);
            fechaActualCalendar.set(Calendar.SECOND, 0);
            fechaActualCalendar.set(Calendar.MILLISECOND, 0);
            Date fechaActual = fechaActualCalendar.getTime();

            long diferenciaEnMilisegundos = fechaLimite.getTime() - fechaActual.getTime();
            long diasFaltantes = diferenciaEnMilisegundos / (1000 * 60 * 60 * 24);

            if(diasFaltantes <= helper.devolverMedia()){
                holder.binding.imageButton2.setBackgroundColor(Color.parseColor(helper.devolverColorMedia()));
                holder.binding.constraintViewHolder.setBackgroundColor(Color.parseColor(helper.devolverColorMedia()));

                if(diasFaltantes <= helper.devolverUrgente()){
                    holder.binding.imageButton2.setBackgroundColor(Color.parseColor(helper.devolverColorUrgente()));
                    holder.binding.constraintViewHolder.setBackgroundColor(Color.parseColor(helper.devolverColorUrgente()));
                }
            }
        }

        public void marcarHecha(Tarea tarea, ListaViewHolder holder){
            if(tarea.isHecha() == false){
                tarea.setHecha(true);
            }else{
                tarea.setHecha(false);
            }

            establecerTick(tarea, holder);

            viewModel.actualizar(tarea);
        }

        public void establecerTick(Tarea tarea, ListaViewHolder holder){
            if(tarea.isHecha()){
                holder.binding.imageButton2.setImageResource(R.drawable.hecha);
            }else{
                holder.binding.imageButton2.setImageResource(R.drawable.no_hecha);
            }
        }

        public void establecerLista(List<Tarea> lTareas){
            this.lTareas = lTareas;
        }

        public Tarea obtenerTarea(int posicion){
            return lTareas.get(posicion);
        }
    }
}
