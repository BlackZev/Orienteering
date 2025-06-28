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
import edu.esiea.orienteering.model.Course;
import edu.esiea.orienteering.viewmodel.CourseViewModel;

import java.util.ArrayList;

public class FragmentListe extends Fragment {
    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;
    private CourseViewModel courseViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_liste, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        courseAdapter = new CourseAdapter(new ArrayList<>(), this::onEditCourse, this::onDeleteCourse);
        recyclerView.setAdapter(courseAdapter);

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseViewModel.getCourses().observe(getViewLifecycleOwner(), courses -> courseAdapter.updateCourses(courses));

        return view;
    }

    private void onEditCourse(Course course) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("course", course);
        NavHostFragment.findNavController(FragmentListe.this).navigate(R.id.action_to_fragmentEdition, bundle);
    }

    private void onDeleteCourse(Course course) {
        new AlertDialog.Builder(getContext())
                .setTitle("Supprimer la course")
                .setMessage("Êtes-vous sûr de vouloir supprimer cette course ?")
                .setPositiveButton("Oui", (dialog, which) -> courseViewModel.deleteCourse(course))
                .setNegativeButton("Non", null)
                .show();
    }
}
