package edu.esiea.orienteering.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// TODO Ajouter les annotations Room nécessaires ainsi que les clefs éventuelles.
@Entity
public class Race {
    // fields
    @PrimaryKey
    @NonNull
    private int id;
    @NonNull
    private String name;
    @NonNull
    private String color;
    @NonNull
    private int numMarkers;
    @NonNull
    private float minDistance;
    @NonNull
    private float maxDistance;


    // constructors
    public Race() {};
    public Race(@NonNull String name, @NonNull String color, @NonNull int numMarkers, @NonNull float minDistance, @NonNull float maxDistance) {
        setId(id);
        this.name = name;
        this.color = color;
        this.numMarkers = numMarkers;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
    }


    // get
    public int getId() { return id; }
    public String getName() { return name; }
    public String getColor() { return color; }
    public int getNumMarkers() { return numMarkers; }
    public float getMinDistance() { return minDistance; }
    public float getMaxDistance() { return maxDistance; }


    // setter
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setColor(String color) { this.color = color; }
    public void setNumMarkers(int numMarkers) { this.numMarkers = numMarkers; }
    public void setMinDistance(int minDistance) { this.minDistance = minDistance; }
    public void setMaxDistance(int maxDistance) { this.maxDistance = maxDistance; }
}
