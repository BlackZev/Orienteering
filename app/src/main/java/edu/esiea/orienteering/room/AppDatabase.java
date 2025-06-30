package edu.esiea.orienteering.room;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.esiea.orienteering.dao.MarkerDao;
import edu.esiea.orienteering.dao.RaceDao;
import edu.esiea.orienteering.model.Marker;
import edu.esiea.orienteering.model.Race;

// TODO ajouter les anotations nécessaires.
@Database(entities = {Marker.class, Race.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MarkerDao markerDao();
    public abstract RaceDao raceDao();


    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "orienteering-database")
                            .fallbackToDestructiveMigration(true)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

