package iesdomingoperezminik.es.proyectofinal.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import iesdomingoperezminik.es.proyectofinal.R;

public class LightSensor extends Fragment implements SensorEventListener {

    private SensorManager manager;
    private Sensor lightSensor;
    private String situacionLuminica = "normal";
    private TextView textView;

    @Override
    public void onResume() {
        super.onResume();
        manager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        lightSensor = manager.getDefaultSensor(Sensor.TYPE_LIGHT);

        manager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        manager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float luz = event.values[0];
        if(luz < 100)
            situacionLuminica = "Oscuro";
        else if(luz < 2000)
            situacionLuminica = "Normal";
        else
            situacionLuminica = "Brillante";
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No es necesario implementar este método
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // No es necesario implementar este método
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_light_sensor, container, false);
        textView = rootView.findViewById(R.id.light_view);
        textView.setText(String.format("La situación lumínica es: %s", situacionLuminica));
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        manager.unregisterListener(this);
    }

}