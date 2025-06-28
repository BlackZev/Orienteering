package edu.esiea.orienteering.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.esiea.orienteering.R;
import edu.esiea.orienteering.model.Race;
import edu.esiea.orienteering.viewmodel.RaceViewModel;

import java.util.ArrayList;

public class ListFragment extends Fragment {
    private RecyclerView recyclerView;
    private RaceAdapter raceAdapter;
    private RaceViewModel raceViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_liste, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        raceAdapter = new RaceAdapter(new ArrayList<>(), this::onEditRace, this::onDeleteRace);
        recyclerView.setAdapter(raceAdapter);

        raceViewModel = new ViewModelProvider(this).get(RaceViewModel.class);
        raceViewModel.getRaces().observe(getViewLifecycleOwner(), races -> raceAdapter.updateRaces(races));

        return view;
    }

    private void onEditRace(Race race) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("race", race);
        NavHostFragment.findNavController(ListFragment.this).navigate(R.id.action_to_fragmentEdition, bundle);
    }

    private void onDeleteRace(Race race) {
        new AlertDialog.Builder(getContext())
                .setTitle("Supprimer la race")
                .setMessage("Êtes-vous sûr de vouloir supprimer cette race ?")
                .setPositiveButton("Oui", (dialog, which) -> raceViewModel.deleteRace(race))
                .setNegativeButton("Non", null)
                .show();
    }
}
