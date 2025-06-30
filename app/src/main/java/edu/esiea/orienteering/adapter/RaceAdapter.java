package edu.esiea.orienteering.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import edu.esiea.orienteering.R;
import edu.esiea.orienteering.model.Marker;
import edu.esiea.orienteering.model.Race;
import edu.esiea.orienteering.utils.MapUtils;

public class RaceAdapter extends RecyclerView.Adapter<RaceAdapter.RaceViewHolder> {
    private List<Race> races;
    private List<Marker> markers;
    private final Consumer<Race> onEditClick;
    private final Consumer<Race> onDeleteClick;

    public RaceAdapter(List<Race> races, List<Marker> markers, Consumer<Race> onEditClick, Consumer<Race> onDeleteClick) {
        this.races = races;
        this.markers = markers;
        this.onEditClick = onEditClick;
        this.onDeleteClick = onDeleteClick;
    }

    public void updateRaces(List<Race> races, List<Marker> markers) {
        this.races = races;
        this.markers = markers;
    }

    @NonNull
    @Override
    public RaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.race_item_fragment, parent, false);
        return new RaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RaceViewHolder holder, int position) {
        Race race = races.get(position);
        holder.tvName.setText(race.getName());
        holder.tvNumMarkers.setText(String.valueOf(race.getNumMarkers()));
        List<Marker> currRaceMarkers= getCurrRaceMarkers(race);
        if (currRaceMarkers.size() >= 2) {
            int dist = getDistance(currRaceMarkers);
            holder.tvDistance.setText("Distance réelle : " + dist + " m");
        } else {
            holder.tvDistance.setText("Pas de marqueurs");
        }
        holder.btnEdit.setOnClickListener(v -> onEditClick.accept(race));
        holder.btnDelete.setOnClickListener(v -> onDeleteClick.accept(race));
        holder.itemView.setBackgroundColor(Color.parseColor(race.getColor()));
    }

    private List<Marker> getCurrRaceMarkers(Race race){
        List<Marker> currRaceMakers = new ArrayList<>();
        for (Marker m : markers) {
            if (m.getIdRace() == race.getId()) {
                currRaceMakers.add(m);
            }
        }
        return currRaceMakers;
    }

    private int getDistance(List<Marker> markers){
        Marker first = markers.get(0);
        Marker last = markers.get(markers.size() - 1);
        GeoPoint startPoint = new GeoPoint(first.getLatitude(), first.getLongitude());
        GeoPoint endPoint = new GeoPoint(last.getLatitude(), last.getLongitude());
        return (int) MapUtils.getDistanceInMeters(startPoint, endPoint);
    }

    @Override
    public int getItemCount() {
        return (races == null) ? 0 : races.size();
    }

    public static class RaceViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvNumMarkers, tvDistance;
        Button btnEdit, btnDelete;

        public RaceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvNumMarkers = itemView.findViewById(R.id.tv_numMarkers);
            tvDistance = itemView.findViewById(R.id.tv_distance);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
