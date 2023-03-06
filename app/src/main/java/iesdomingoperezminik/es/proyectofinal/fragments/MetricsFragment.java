package iesdomingoperezminik.es.proyectofinal.fragments;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import iesdomingoperezminik.es.proyectofinal.R;

public class MetricsFragment extends Fragment {

    private TextView view;
    private DisplayMetrics dm;

    public MetricsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dm = new DisplayMetrics();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_metrics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);

        this.view = (TextView) view.findViewById(R.id.metrics_view);
        this.view.setText(getMetrics());

    }

    private String getMetrics() {
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        return String.format("Tu dispositivo tiene una resolución de %d x %d píxeles", dm.widthPixels, dm.heightPixels);
    }

}
