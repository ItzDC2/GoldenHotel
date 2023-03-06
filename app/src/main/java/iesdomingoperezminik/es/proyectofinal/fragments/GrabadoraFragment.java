package iesdomingoperezminik.es.proyectofinal.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import iesdomingoperezminik.es.proyectofinal.R;
import iesdomingoperezminik.es.proyectofinal.handlers.AudioPlayer;
import iesdomingoperezminik.es.proyectofinal.handlers.AudioRecorder;

public class GrabadoraFragment extends Fragment {

    private AudioRecorder audioRecorder;
    private AudioPlayer audioPlayer;

    private ImageButton empezarGrabacionButton;
    private Button pararGrabacionButton;

    private boolean grabando = false;

    private String nombreArchivo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        audioRecorder = new AudioRecorder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/audio.3gp");
        audioPlayer = new AudioPlayer(Environment.getExternalStorageDirectory().getAbsolutePath() + "/audio.3gp");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grabadora, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);

        empezarGrabacionButton = (ImageButton) view.findViewById(R.id.empezarGrabacionButton);

        empezarGrabacionButton.setOnClickListener(i -> {
            if(grabando) {
                audioRecorder.stopRecording();
                empezarGrabacionButton.setImageResource(R.drawable.ic_mic);
                grabando = false;
            } else {
                audioRecorder.startRecording();
                empezarGrabacionButton.setImageResource(R.drawable.ic_muted_mic);
            }
        });


    }

}