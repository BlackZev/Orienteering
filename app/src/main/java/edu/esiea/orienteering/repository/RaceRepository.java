package edu.esiea.orienteering.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.esiea.orienteering.dao.RaceDao;
import edu.esiea.orienteering.model.Race;
import edu.esiea.orienteering.room.AppDatabase;

public class RaceRepository {
    private RaceDao dao;
    private LiveData<List<Race>> races;

    public RaceRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        dao = db.raceDao();
        races = dao.getAll();
    }

    public LiveData<List<Race>> getAll() { return races; }

    public void insert(Race race) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            dao.insert(race);
        });
    }
}
