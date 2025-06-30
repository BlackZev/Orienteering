package edu.esiea.orienteering.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

// TODO Ajouter les annotations Room nécessaires ainsi que les clefs éventuelles.
@Entity
public class Marker {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private Integer idRace;
    @NonNull private String name = "";
    // TODO définir le champs servant à mémoriser la position de la balise
    private double latitude;
    private double longitude;


    // constructors
    @Ignore
    public Marker() {};
    public Marker(Integer idRace, @NonNull String name, double latitude, double longitude) {
        this.idRace = idRace;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    // get
    public Integer getId() { return id; }
    public Integer getIdRace() { return idRace; }
    @NonNull
    public String getName() { return name; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }


    // setter
    public void setId(Integer id) { this.id = id; }
    public void setIdRace(Integer idRace) { this.idRace = idRace; }
    public void setName(@NonNull String name) { this.name = name; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}
