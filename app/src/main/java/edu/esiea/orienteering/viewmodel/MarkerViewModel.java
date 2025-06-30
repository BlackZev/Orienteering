package edu.esiea.orienteering.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.esiea.orienteering.model.Marker;
import edu.esiea.orienteering.repository.MarkerRepository;

public class MarkerViewModel extends AndroidViewModel {
    private MarkerRepository repo;
    private LiveData<List<Marker>> markers;

    public MarkerViewModel (Application application) {
        super(application);
        repo = new MarkerRepository(application);
        markers = repo.getAll();
    }

    public LiveData<List<Marker>> getAll() { return markers; }
    public void insert(Marker marker) { repo.insert(marker); }
}
