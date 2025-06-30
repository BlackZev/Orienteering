package edu.esiea.orienteering.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Pair;
import android.widget.ImageButton;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.List;

import edu.esiea.orienteering.R;
import edu.esiea.orienteering.adapter.RaceAdapter;
import edu.esiea.orienteering.model.Race;
import edu.esiea.orienteering.viewmodel.MarkerViewModel;
import edu.esiea.orienteering.viewmodel.RaceViewModel;

public class ListFragment extends Fragment {
    private RaceAdapter raceAdapter;
    private RaceViewModel raceViewModel;
    private MarkerViewModel markerViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_raceList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        raceAdapter = new RaceAdapter(new ArrayList<>(), new ArrayList<>(), this::onEditRace, this::onDeleteRace);
        recyclerView.setAdapter(raceAdapter);

        raceViewModel = new ViewModelProvider(this).get(RaceViewModel.class);
        markerViewModel = new ViewModelProvider(this).get(MarkerViewModel.class);

        raceViewModel.getAll().observe(getViewLifecycleOwner(), races -> {
            markerViewModel.getAll().observe(getViewLifecycleOwner(), markers -> {
                raceAdapter.updateRaces(races, markers);
                raceAdapter.notifyDataSetChanged();
            });
        });
        return view;
    }

    private void onEditRace(Race race) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("race", race);
        NavHostFragment.findNavController(this).navigate(R.id.go_to_EditFragment, bundle);
    }

    private void onDeleteRace(Race race) {
        new AlertDialog.Builder(getContext())
                .setTitle("Supprimer cette course ?")
                .setMessage("Es-tu sûr de vouloir supprimer \"" + race.getName() + "\" ?")
                .setPositiveButton("Oui", (dialog, which) -> raceViewModel.delete(race))
                .setNegativeButton("Non", null)
                .show();
    }
}
