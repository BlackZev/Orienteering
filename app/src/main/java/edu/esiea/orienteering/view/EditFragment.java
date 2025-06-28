package edu.esiea.orienteering.view;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import edu.esiea.orienteering.R;
import edu.esiea.orienteering.model.Race;
import edu.esiea.orienteering.viewmodel.RaceViewModel;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.google.android.material.slider.Slider;

public class EditFragment extends Fragment {
    private EditText raceName;
    private ColorPicker colorPicker;
    private Slider numMarkersSlider;
    private Slider minDistanceSlider;
    private Slider maxDistanceSlider;
    private RaceViewModel raceViewModel;
    private Race currentRace;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edition, container, false);
        raceName = view.findViewById(R.id.raceName);
        colorPicker = view.findViewById(R.id.colorPicker);
        numMarkersSlider = view.findViewById(R.id.numMarkersSlider);
        minDistanceSlider = view.findViewById(R.id.minDistanceSlider);
        maxDistanceSlider = view.findViewById(R.id.maxDistanceSlider);

        raceViewModel = new ViewModelProvider(this).get(RaceViewModel.class);

        if (getArguments() != null && getArguments().getSerializable("race") != null) {
            currentRace = (Race) getArguments().getSerializable("race");
            loadRaceDetails();
        }

        Button validateButton = view.findViewById(R.id.validateButton);
        validateButton.setOnClickListener(v -> saveRace());

        return view;
    }

    private void loadRaceDetails() {
        raceName.setText(currentRace.name);
        colorPicker.setColor(Color.parseColor(currentRace.color));
        numMarkersSlider.setValue(currentRace.numMarkers);
        minDistanceSlider.setValue(currentRace.minDistance);
        maxDistanceSlider.setValue(currentRace.maxDistance);
    }

    private void saveRace() {
        if (currentRace == null) {
            currentRace = new Race();
        }
        currentRace.name = raceName.getText().toString();
        currentRace.color = String.format("#%06X", (0xFFFFFF & colorPicker.getColor()));
        currentRace.numMarkers = (int) numMarkersSlider.getValue();
        currentRace.minDistance = minDistanceSlider.getValue();
        currentRace.maxDistance = maxDistanceSlider.getValue();

        if (currentRace.id == 0) {
            raceViewModel.insertRace(currentRace);
        } else {
            raceViewModel.updateRace(currentRace);
        }

        NavHostFragment.findNavController(EditFragment.this).navigate(R.id.action_to_fragmentCarte);
    }
}
