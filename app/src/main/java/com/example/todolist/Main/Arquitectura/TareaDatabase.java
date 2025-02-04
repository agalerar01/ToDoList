package com.example.todolist.Main.Arquitectura;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.todolist.Main.Objetos.Tarea;

@Database(entities = {Tarea.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class TareaDatabase extends RoomDatabase {

    public abstract TareaDao TareaDao();

    private static TareaDatabase instance;

    public static TareaDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (TareaDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    TareaDatabase.class,
                                    "tarea.db"
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
