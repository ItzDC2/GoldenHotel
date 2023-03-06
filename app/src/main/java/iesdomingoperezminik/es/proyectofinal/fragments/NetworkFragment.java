package iesdomingoperezminik.es.proyectofinal.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import iesdomingoperezminik.es.proyectofinal.R;

public class NetworkFragment extends Fragment {

    private LocationManager locationManager;
    private TextView locationView;
    private String locationS;

    public NetworkFragment() {}

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
            } else {
                Location loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if(loc != null) {
                    double latitud = loc.getLatitude();
                    double longitud = loc.getLongitude();
                    double altitud = loc.getAltitude();
                    double rumbo = loc.getBearing();
                    locationS = String.format("Latitud: %f\nLongitud: %f\nAltitud: %f\nRumbo: %f", latitud, longitud, altitud, rumbo);
                } else {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {

                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            double latitud = location.getLatitude();
                            double longitud = location.getLongitude();
                            double altitud = location.getAltitude();
                            double rumbo = location.getBearing();
                            locationS = String.format("Latitud: %f\nLongitud: %f\nAltitud: %f\nRumbo: %f", latitud, longitud, altitud, rumbo);
                            locationManager.removeUpdates(this);
                        }
                    });
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_network, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);

        this.locationView = (TextView) view.findViewById(R.id.location_view);
        this.locationView.setText(locationS);

    }

}
