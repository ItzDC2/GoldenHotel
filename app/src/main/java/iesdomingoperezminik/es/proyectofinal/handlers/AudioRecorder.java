package iesdomingoperezminik.es.proyectofinal.handlers;

import android.media.MediaRecorder;

import java.io.IOException;

public class AudioRecorder {

    private MediaRecorder mediaRecorder;
    private String nombreArchivo;

    public AudioRecorder(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public void startRecording() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(nombreArchivo);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public void stopRecording() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

}
