package edu.esiea.orienteering.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import org.osmdroid.views.MapView;

import edu.esiea.orienteering.R;

public class OSMFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_osm_fragment, container, false);
        MapView mapView = view.findViewById(R.id.map);
        mapView.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK);

        return view;
    }
}
