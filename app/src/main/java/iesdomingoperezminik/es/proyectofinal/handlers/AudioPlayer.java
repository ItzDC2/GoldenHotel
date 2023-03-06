package iesdomingoperezminik.es.proyectofinal.handlers;

import android.media.MediaPlayer;

import java.io.IOException;

public class AudioPlayer {

    private MediaPlayer mediaPlayer;
    private String nombreArchivo;

    public AudioPlayer(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public void empezarReproduccion() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(nombreArchivo);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public void pararReproduccion() {
        mediaPlayer.release();
        mediaPlayer = null;
    }


}
