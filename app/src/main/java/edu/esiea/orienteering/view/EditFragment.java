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
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import edu.esiea.orienteering.R;
import edu.esiea.orienteering.model.Race;
import edu.esiea.orienteering.viewmodel.RaceViewModel;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.google.android.material.slider.Slider;

public class EditFragment extends Fragment {
    private EditText et_name;
    private ColorPicker colpi_color;
    private Slider sli_numMarkers;
    private Slider sli_minDistance;
    private Slider sli_maxDistance;
    private RaceViewModel raceViewModel;
    private Race currRace;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.edit_fragment, container, false);
        et_name = view.findViewById(R.id.et_name);
        colpi_color = view.findViewById(R.id.colpi_color);
        sli_numMarkers = view.findViewById(R.id.sli_numMarkers);
        sli_minDistance = view.findViewById(R.id.sli_minDistance);
        sli_maxDistance = view.findViewById(R.id.sli_maxDistance);
        Button btn_add = view.findViewById(R.id.btn_add);

        raceViewModel = new ViewModelProvider(this).get(RaceViewModel.class);

        if (getArguments() != null && getArguments().getSerializable("race") != null) {
            currRace = (Race) getArguments().getSerializable("race");
            loadRaceDetails();
        }

        btn_add.setOnClickListener(v -> saveRace());

        return view;
    }

    private void loadRaceDetails() {
        et_name.setText(currRace.getName());
        colpi_color.setColor(Color.parseColor(currRace.getColor()));
        sli_numMarkers.setValue(currRace.getNumMarkers());
        sli_minDistance.setValue(currRace.getMinDistance());
        sli_maxDistance.setValue(currRace.getMaxDistance());
    }

    private void saveRace() {
        if (currRace == null) {
            currRace = new Race();
        }
        currRace.setName(et_name.getText().toString() + "e balise");
        currRace.setColor(String.format("#%06X", (0xFFFFFF & colpi_color.getColor())));
        currRace.setNumMarkers((int) sli_numMarkers.getValue());
        currRace.setMinDistance((int) sli_minDistance.getValue());
        currRace.setMaxDistance((int) sli_maxDistance.getValue());

        if (currRace.getId() == 0) {
            raceViewModel.insert(currRace);
        } else {
            raceViewModel.update(currRace);
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable("race", currRace);
        bundle.putBoolean("poseMode", true);
        Navigation.findNavController(view).navigate(R.id.go_to_OSMFragment, bundle);
    }
}
