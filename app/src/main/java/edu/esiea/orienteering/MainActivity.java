package edu.esiea.orienteering;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.coly_main_fragment), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar app_toolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(app_toolbar);
        ImageButton btn_home = app_toolbar.findViewById(R.id.btn_home);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fg_container_view);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            boolean isOnOsmFragment = (destination.getId() == R.id.fg_osm);
            btn_home.setVisibility(isOnOsmFragment ? ImageButton.GONE : ImageButton.VISIBLE);
            btn_home.setClickable(!isOnOsmFragment);
        });

        btn_home.setOnClickListener(v -> {
            navController.navigate(R.id.go_to_OSMFragment);
        });
    }
}