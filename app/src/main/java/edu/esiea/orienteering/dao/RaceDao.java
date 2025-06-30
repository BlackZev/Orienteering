package edu.esiea.orienteering.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.esiea.orienteering.model.Race;

@Dao
public interface RaceDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Race race);

    @Query("SELECT * FROM race")
    LiveData<List<Race>> getAll();

    @Query("SELECT * FROM race WHERE id = :id")
    LiveData<Race> getRaceById(int id);

    @Query("SELECT color FROM race WHERE id = :idRace")
    LiveData<String> getByColorCode(int idRace);

    @Update
    void update(Race race);

    @Delete
    void delete(Race race);

    @Query("DELETE FROM race")
    void deleteAll();
}
