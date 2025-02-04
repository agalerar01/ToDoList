package com.example.todolist.Arquitectura;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.todolist.Objetos.Tarea;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TareaRepository {

    private TareaDao TareaDao;
    private Executor executor;

    public TareaRepository(Application application) {

        TareaDao = TareaDatabase.getInstance(application).TareaDao();

        executor = Executors.newSingleThreadExecutor();
    }

    public void insertar(Tarea tarea) {
        executor.execute(() -> {
            TareaDao.insertar(tarea);
        });
    }

    public void actualizar(Tarea tarea) {
        executor.execute(() -> {
            TareaDao.actualizar(tarea);
        });
    }

    public LiveData<List<Tarea>> obtenerTareas() {
        return TareaDao.obtenerTodos();
    }

    public LiveData<Tarea> buscarIgual(String titulo, String categoria, Date fechaLimite){
        return TareaDao.buscarIgual(titulo,categoria,fechaLimite);
    }

    public LiveData<List<Tarea>> buscar(String linea){
        return TareaDao.buscar(linea);
    }

    public LiveData<List<Tarea>> ordenarPorFecha(){
        return TareaDao.ordenarPorFecha();
    }

    public void eliminar(Tarea tarea){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                TareaDao.eliminar(tarea);
            }
        });
    }
}
