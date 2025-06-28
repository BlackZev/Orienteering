package edu.esiea.orienteering.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.esiea.orienteering.model.Race;

@Dao
public interface RaceDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Race race);

    @Query("DELETE FROM race")
    void deleteAll();

    @Query("SELECT * FROM race")
    LiveData<List<Race>> getAll();
}
