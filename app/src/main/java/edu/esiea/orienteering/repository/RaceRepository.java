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

    public void insert(Race race) {
        AppDatabase.databaseWriteExecutor.execute(() -> dao.insert(race));
    }

    public LiveData<List<Race>> getAll() { return races; }

    public LiveData<Race> getRaceById(int id) { return dao.getRaceById(id); };

    public LiveData<String> getByColorCode(int idRace) {
        return dao.getByColorCode(idRace);
    }

    public void update(Race race) {
        AppDatabase.databaseWriteExecutor.execute(() -> dao.update(race));
    }

    public void delete(Race race) {
        AppDatabase.databaseWriteExecutor.execute(() -> dao.delete(race));
    }
}
