package edu.esiea.orienteering.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.esiea.orienteering.model.Marker;
import edu.esiea.orienteering.model.Race;
import edu.esiea.orienteering.repository.RaceRepository;

public class RaceViewModel extends AndroidViewModel {
    private RaceRepository repo;
    private LiveData<List<Race>> races;

    public RaceViewModel(Application application) {
        super(application);
        repo = new RaceRepository(application);
        races = repo.getAll();
    }

    public LiveData<List<Race>> getAll() {
        return races;
    }

    public LiveData<Race> getRaceById(int id) {
        return repo.getRaceById(id);
    }

    public LiveData<String> getByColorCode(int idRace) {
        return repo.getByColorCode(idRace);
    }

    public void insert(Race race) {
        repo.insert(race);
    }

    public void update(Race race) {
        repo.update(race);
    }

    public void delete(Race race) {
        repo.delete(race);
    }
}
