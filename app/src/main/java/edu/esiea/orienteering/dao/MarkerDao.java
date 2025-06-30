package edu.esiea.orienteering.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.esiea.orienteering.model.Marker;

@Dao
public interface MarkerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Marker marker);

    @Query("SELECT * FROM marker WHERE idRace = :idRace")
    LiveData<List<Marker>> getMarkersRace(int idRace);

    @Query("DELETE FROM marker")
    void deleteAll();

    @Query("SELECT * FROM marker")
    LiveData<List<Marker>> getAll();
}
