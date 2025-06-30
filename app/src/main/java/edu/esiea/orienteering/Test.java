//package edu.esiea.orienteering;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//
//import androidx.activity.EdgeToEdge;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import org.osmdroid.api.IMapController;
//import org.osmdroid.config.Configuration;
//import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
//import org.osmdroid.util.GeoPoint;
//import org.osmdroid.views.MapView;
//
//import java.util.ArrayList;
//
//public class MainActivity extends AppCompatActivity {
//    private MapView map = null;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.main_fragment);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.coly_main_fragment), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        Context ctx = getApplicationContext();
//        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
//        map = findViewById(R.id.map);
//        map.setTileSource(TileSourceFactory.MAPNIK);
//        map.setMultiTouchControls(true);
//
//        IMapController mapController = map.getController();
//        mapController.setZoom(20);
//        GeoPoint startPoint = new GeoPoint(43.7194789, -1.0521627);
//        mapController.setCenter(startPoint);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        map.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        map.onPause();
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        ArrayList<String> permissionsToRequest = new ArrayList<>();
//        for (int i = 0; i < grantResults.length; i++) {
//            permissionsToRequest.add(permissions[i]);
//        }
//        if (!permissionsToRequest.isEmpty()) {
//            int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
//            ActivityCompat.requestPermissions(
//                    this,
//                    permissionsToRequest.toArray(new String[0]),
//                    REQUEST_PERMISSIONS_REQUEST_CODE);
//        }
//    }
//}