package com.example.todolist.Objetos;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Tarea {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String Titulo;
    public String Categoria;
    public String Descripcion;
    public Date FechaLimite;
    public byte[] Foto;
    public boolean Hecha;


    public Tarea() {
    }

    public Tarea(String titulo, String categoria, String descripcion,Date fechaLimite,byte[] foto) {
        Titulo = titulo;
        Categoria = categoria;
        Descripcion = descripcion;
        FechaLimite = fechaLimite;
        Foto = foto;
        Hecha = false;
    }

    public String getTitulo() {
        return Titulo;
    }

    public String getCategoria() {
        return Categoria;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public Date getFechaLimite() {
        return FechaLimite;
    }

    public byte[] getFoto() {
        return Foto;
    }

    public boolean isHecha() {
        return Hecha;
    }

    public void setHecha(boolean fav) {
        Hecha = fav;
    }
}
