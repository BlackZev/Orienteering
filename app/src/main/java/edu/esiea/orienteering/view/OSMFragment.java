package edu.esiea.orienteering.view;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import androidx.appcompat.widget.Toolbar;

import edu.esiea.orienteering.R;
import edu.esiea.orienteering.dao.MarkerDao;
import edu.esiea.orienteering.model.Race;
import edu.esiea.orienteering.utils.MapUtils;
import edu.esiea.orienteering.viewmodel.MarkerViewModel;
import edu.esiea.orienteering.viewmodel.RaceViewModel;

public class OSMFragment extends Fragment {
    MapView mapView = null;
    private ActivityResultLauncher<String[]> permissionLauncher;
    private Race currRace;
    private boolean isPlacingMarkers = false;
    private int currMarkerIndex = 1;
    private GeoPoint endPoint = null;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Context ctx = requireContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        view = inflater.inflate(R.layout.osm_fragment, container, false);
        mapView = view.findViewById(R.id.app_mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);

        IMapController mapController = mapView.getController();
        mapController.setZoom(20.0);
        GeoPoint startPoint = new GeoPoint(43.7194789, -1.0521627);
        mapController.setCenter(startPoint);

        if (getArguments() != null) {
            currRace = (Race) getArguments().getSerializable("race");
            isPlacingMarkers = getArguments().getBoolean("poseMode", false);
        }

        mapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isPlacingMarkers && event.getAction() == MotionEvent.ACTION_UP) {
                    IGeoPoint geo = mapView.getProjection().fromPixels((int) event.getX(), (int) event.getY());
                    GeoPoint tappedPoint = new GeoPoint(geo.getLatitude(), geo.getLongitude());
                    handlePlaceMarker(tappedPoint);
                }
                return false;
            }
        });

        instanceBtn(view);
        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                result -> {
                    boolean allGranted = true;
                    for (Boolean granted : result.values()) {
                        if (!granted) {
                            allGranted = false;
                            break;
                        }
                    }
                    if (!allGranted) {
                        Toast.makeText(requireContext(), "Certaines permissions ont été refusées", Toast.LENGTH_SHORT).show();
                    }
                });
        checkPermissions();

        if (!isPlacingMarkers) {
            RaceViewModel raceViewModel = new ViewModelProvider(this).get(RaceViewModel.class);
            MarkerViewModel markerViewModel = new ViewModelProvider(this).get(MarkerViewModel.class);

            markerViewModel.getAll().observe(getViewLifecycleOwner(), markers -> {
                mapView.getOverlays().clear();
                for (edu.esiea.orienteering.model.Marker m : markers) {
                    int raceId = m.getIdRace();
                    raceViewModel.getByColorCode(raceId).observe(getViewLifecycleOwner(), colorCode -> {
                        Marker marker = new Marker(mapView);
                        marker.setPosition(new GeoPoint(m.getLatitude(), m.getLongitude()));
                        marker.setTitle(m.getName());
                        marker.setIcon(getColoredDrawable(colorCode));
                        mapView.getOverlays().add(marker);
                    });
                }
                mapView.invalidate();
            });
        }

        return view;
    }

    private void instanceBtn(View view){
        Button btnNewRace = view.findViewById(R.id.btn_newRace);
        btnNewRace.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.go_to_EditFragment);
        });

        Button btnListRace = view.findViewById(R.id.btn_listRaces);
        btnListRace.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.go_to_ListFragment);
        });
    }

    private void checkPermissions() {
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        List<String> toRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                toRequest.add(permission);
            }
        }

        if (!toRequest.isEmpty()) {
            permissionLauncher.launch(toRequest.toArray(new String[0]));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    private Drawable getColoredDrawable(String colorHex) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_marker);
        if (drawable != null) {
            try {
                if (colorHex == null) colorHex = "#000000";
                drawable.setTint(Color.parseColor(colorHex));
            } catch (IllegalArgumentException e) {
                drawable.setTint(Color.BLACK);
            }
        }
        return drawable;
    }


    private void handlePlaceMarker(GeoPoint tappedPoint) {
        if (endPoint != null) {
            float distance = MapUtils.getDistanceInMeters(endPoint, tappedPoint);
            if (distance < currRace.getMinDistance() || distance > currRace.getMaxDistance()) {
                Toast.makeText(getContext(), "Point hors zone. Choisis un emplacement entre " +
                        currRace.getMinDistance() + "m et " + currRace.getMaxDistance() + "m", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Marker marker = new Marker(mapView);
        marker.setPosition(tappedPoint);
        marker.setTitle("Balise " + currMarkerIndex);
        marker.setIcon(getColoredDrawable(currRace.getColor()));
        mapView.getOverlays().add(marker);
        mapView.invalidate();

        edu.esiea.orienteering.model.Marker m = new edu.esiea.orienteering.model.Marker(
                currRace.getId(),
                "Balise " + currMarkerIndex,
                tappedPoint.getLatitude(),
                tappedPoint.getLongitude()
        );
        new ViewModelProvider(this).get(edu.esiea.orienteering.viewmodel.MarkerViewModel.class).insert(m);

        endPoint = tappedPoint;

        if (currMarkerIndex == currRace.getNumMarkers()) {
            isPlacingMarkers = false;
            Toast.makeText(getContext(), "Toutes les balises ont été posées !", Toast.LENGTH_SHORT).show();
            MapUtils.eraseMinMaxDistanceCircles(mapView);
        } else {
            currMarkerIndex++;
            MapUtils.drawMinMaxDistanceCirclesAroundPoint(mapView, tappedPoint,
                    currRace.getMinDistance(), currRace.getMaxDistance());
        }
    }
}
