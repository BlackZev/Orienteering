package edu.esiea.orienteering.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.esiea.orienteering.model.Race;
import edu.esiea.orienteering.repository.RaceRepository;

public class RaceViewModel extends AndroidViewModel {
    private RaceRepository repo;
    private LiveData<List<Race>> races;

    public RaceViewModel (Application application) {
        super(application);
        repo = new RaceRepository(application);
        races = repo.getAll();
    }

    LiveData<List<Race>> getAll() { return races; }
    public void insert(Race race) { repo.insert(race); }
}
