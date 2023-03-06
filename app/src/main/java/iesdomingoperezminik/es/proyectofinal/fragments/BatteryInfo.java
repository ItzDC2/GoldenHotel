package iesdomingoperezminik.es.proyectofinal.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import iesdomingoperezminik.es.proyectofinal.R;

public class BatteryInfo extends Fragment {

    private TextView batteryView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_battery_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);

        batteryView = (TextView) view.findViewById(R.id.battery_view);
        BatteryReceiver batteryReceiver = new BatteryReceiver();
        getActivity().registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));



    }

    private class BatteryReceiver extends BroadcastReceiver {

        private int batteryPercent;

        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
            batteryPercent = (level * 100) / scale;

            batteryView.setText(String.format("El porcentaje de la batería es %d%%", batteryPercent));
        }

        public int getBatteryPercent() {
            return batteryPercent;
        }
    }

}