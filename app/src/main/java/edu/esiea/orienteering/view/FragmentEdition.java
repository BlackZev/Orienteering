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
import edu.esiea.orienteering.model.Course;
import edu.esiea.orienteering.viewmodel.CourseViewModel;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.google.android.material.slider.Slider;

public class FragmentEdition extends Fragment {
    private EditText courseName;
    private ColorPicker colorPicker;
    private Slider numBalisesSlider;
    private Slider minDistanceSlider;
    private Slider maxDistanceSlider;
    private CourseViewModel courseViewModel;
    private Course currentCourse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edition, container, false);
        courseName = view.findViewById(R.id.courseName);
        colorPicker = view.findViewById(R.id.colorPicker);
        numBalisesSlider = view.findViewById(R.id.numBalisesSlider);
        minDistanceSlider = view.findViewById(R.id.minDistanceSlider);
        maxDistanceSlider = view.findViewById(R.id.maxDistanceSlider);

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        if (getArguments() != null && getArguments().getSerializable("course") != null) {
            currentCourse = (Course) getArguments().getSerializable("course");
            loadCourseDetails();
        }

        Button validateButton = view.findViewById(R.id.validateButton);
        validateButton.setOnClickListener(v -> saveCourse());

        return view;
    }

    private void loadCourseDetails() {
        courseName.setText(currentCourse.name);
        colorPicker.setColor(Color.parseColor(currentCourse.color));
        numBalisesSlider.setValue(currentCourse.numBalises);
        minDistanceSlider.setValue(currentCourse.minDistance);
        maxDistanceSlider.setValue(currentCourse.maxDistance);
    }

    private void saveCourse() {
        if (currentCourse == null) {
            currentCourse = new Course();
        }
        currentCourse.name = courseName.getText().toString();
        currentCourse.color = String.format("#%06X", (0xFFFFFF & colorPicker.getColor()));
        currentCourse.numBalises = (int) numBalisesSlider.getValue();
        currentCourse.minDistance = minDistanceSlider.getValue();
        currentCourse.maxDistance = maxDistanceSlider.getValue();

        if (currentCourse.id == 0) {
            courseViewModel.insertCourse(currentCourse);
        } else {
            courseViewModel.updateCourse(currentCourse);
        }

        NavHostFragment.findNavController(FragmentEdition.this).navigate(R.id.action_to_fragmentCarte);
    }
}
