package com.example.todolist.Main;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {

    private SharedPreferences sharedPreferences;

    private static final String PREFS_NAME = "prefs";
    private static final String KEY_NO_MOSTRAR = "no_mostrar";
    private static final String KEY_CUENTA = "cuenta_tarea";
    private static final String KEY_DIAS_URGENTE = "cuenta_urgente";
    private static final String KEY_DIAS_MEDIA = "cuenta_media";
    private static final String KEY_COLOR_URGENTE = "color_urgente";
    private static final String KEY_COLOR_MEDIA = "color_media";
    private static final String KEY_ACTIVA_ALERTA = "alerta";


    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void guardarNoMostrar(Boolean noMostrar){
        sharedPreferences.edit().putBoolean(KEY_NO_MOSTRAR, noMostrar).apply();
    }

    public boolean devolverNoMostrar(){
        return sharedPreferences.getBoolean(KEY_NO_MOSTRAR, false);
    }

    public void guardarActivaAlerta(Boolean noMostrar){
        sharedPreferences.edit().putBoolean(KEY_ACTIVA_ALERTA, noMostrar).apply();
    }

    public boolean devolverActivaAlerta(){
        return sharedPreferences.getBoolean(KEY_ACTIVA_ALERTA, false);
    }

    public void actualizarCuenta(){
        int cuenta = sharedPreferences.getInt(KEY_CUENTA, 0);

        cuenta++;

        sharedPreferences.edit().putInt(KEY_CUENTA, cuenta).apply();
    }

    public int devolverCuenta(){
        return sharedPreferences.getInt(KEY_CUENTA, 0);
    }

    public void reiniciarCuenta(){
        sharedPreferences.edit().putInt(KEY_CUENTA, 0).apply();
    }

    public void guardarUrgente(int dia){
        sharedPreferences.edit().putInt(KEY_DIAS_URGENTE, dia).apply();
    }

    public int devolverUrgente(){
        return sharedPreferences.getInt(KEY_DIAS_URGENTE, 2);
    }

    public void guardarMedia(int dia){
        sharedPreferences.edit().putInt(KEY_DIAS_MEDIA, dia).apply();
    }

    public int devolverMedia(){
        return sharedPreferences.getInt(KEY_DIAS_MEDIA, 5);
    }

    public void guardarColorUrgente(String color){
        sharedPreferences.edit().putString(KEY_COLOR_URGENTE, color).apply();
    }

    public String devolverColorUrgente(){
        return sharedPreferences.getString(KEY_COLOR_URGENTE, "#f7bebe");
    }

    public void guardarColorMedia(String color){
        sharedPreferences.edit().putString(KEY_COLOR_MEDIA, color).apply();
    }

    public String devolverColorMedia(){
        return sharedPreferences.getString(KEY_COLOR_MEDIA, "#f7f7ba");
    }
}
