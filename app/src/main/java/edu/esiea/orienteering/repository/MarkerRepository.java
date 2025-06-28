package edu.esiea.orienteering.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.esiea.orienteering.dao.MarkerDao;
import edu.esiea.orienteering.model.Marker;
import edu.esiea.orienteering.room.AppDatabase;

public class MarkerRepository {
    private MarkerDao dao;
    private LiveData<List<Marker>> markers;

    public MarkerRepository(Application application){
        AppDatabase db = AppDatabase.getInstance(application);
        dao = db.markerDao();
        markers = dao.getAll();
    }

    public LiveData<List<Marker>> getAll() { return markers; }

    public void insert(Marker marker) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            dao.insert(marker);
        });
    }
}
