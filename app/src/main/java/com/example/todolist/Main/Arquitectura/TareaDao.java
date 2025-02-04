package com.example.todolist.Main.Arquitectura;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.room.Query;

import com.example.todolist.Main.Objetos.Tarea;

import java.util.Date;
import java.util.List;

@Dao
public interface TareaDao {

    @Insert
    void insertar(Tarea tarea);

    @Update
    void actualizar(Tarea tarea);

    @Delete
    void eliminar(Tarea tarea);

    @Query("SELECT * FROM Tarea ORDER BY Titulo ASC")
    LiveData<List<Tarea>> obtenerTodos();

    @Query("SELECT * FROM Tarea WHERE Titulo LIKE :linea")
    LiveData<List<Tarea>> buscar(String linea);

    @Query("SELECT * FROM Tarea ORDER BY FechaLimite")
    LiveData<List<Tarea>> ordenarPorFecha();

    @Query("SELECT * FROM Tarea WHERE Titulo = :titulo AND Categoria = :categoria AND FechaLimite = :fechaLimite LIMIT 1")
    LiveData<Tarea> buscarIgual(String titulo, String categoria, Date fechaLimite);
}
