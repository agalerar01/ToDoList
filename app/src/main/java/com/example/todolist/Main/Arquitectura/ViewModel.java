package com.example.todolist.Main.Arquitectura;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.todolist.Main.Objetos.Tarea;

import java.util.Date;
import java.util.List;

public class ViewModel extends AndroidViewModel {

    TareaRepository TR = new TareaRepository(getApplication());
    public MutableLiveData<Tarea> tareaElegida = new MutableLiveData<>();

    public ViewModel(@NonNull Application application){
        super(application);
    }

    public void insertar(Tarea t1){
        TR.insertar(t1);
    }

    public LiveData<List<Tarea>> obtenerTareas() {
        return TR.obtenerTareas();
    }

    public LiveData<Tarea> buscarIgual(String titulo, String categoria, Date fechaLimite){
        return TR.buscarIgual(titulo,categoria,fechaLimite);
    }

    public LiveData<List<Tarea>> buscar(String linea){
        return TR.buscar(linea);
    }

    public void eliminar(Tarea tarea){
        TR.eliminar(tarea);
    }

    public void actualizar(Tarea tarea) {
        TR.actualizar(tarea);
    }
}
