package edu.esiea.orienteering.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

// TODO Ajouter les annotations Room nécessaires ainsi que les clefs éventuelles.
@Entity
public class Race implements Serializable {
    // fields
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String name = "";
    @NonNull
    private String color = "";
    private int numMarkers;
    private int minDistance;
    private int maxDistance;


    // constructors
    @Ignore
    public Race() {};
    public Race(@NonNull String name, @NonNull String color, int numMarkers, int minDistance, int maxDistance) {
        setId(id);
        this.name = name;
        this.color = color;
        this.numMarkers = numMarkers;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
    }


    // get
    public int getId() { return id; }
    @NonNull
    public String getName() { return name; }
    @NonNull
    public String getColor() { return color.matches("#[0-9a-fA-F]{6}") ? color : "#FFFFFF"; }
    public int getNumMarkers() { return numMarkers; }
    public int getMinDistance() { return minDistance; }
    public int getMaxDistance() { return maxDistance; }


    // setter
    public void setId(int id) { this.id = id; }
    public void setName(@NonNull String name) { this.name = name; }
    public void setColor(@NonNull String color) { this.color = color; }
    public void setNumMarkers(int numMarkers) { this.numMarkers = numMarkers; }
    public void setMinDistance(int minDistance) { this.minDistance = minDistance; }
    public void setMaxDistance(int maxDistance) { this.maxDistance = maxDistance; }
}
