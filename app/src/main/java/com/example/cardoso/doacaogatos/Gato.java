package com.example.cardoso.doacaogatos;

import android.graphics.Bitmap;

/**
 * Created by Guilherme Cardoso on 04/12/2017.
 */

public class Gato {

    public String id, descricao;
    public Bitmap fotoGato;
    public double latitude;
    public double longitude;

    public Gato() {

    }

    public Gato(String id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Gato(String id, String descricao, Bitmap fotoGato) {
        this.id = id;
        this.descricao = descricao;
        this.fotoGato = fotoGato;
    }

    public Gato(String id, String descricao, double latitude, double longitude) {
        this.id = id;
        this.descricao = descricao;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Gato(String id, String descricao, Bitmap fotoGato, double latitude, double longitude) {
        this.id = id;
        this.descricao = descricao;
        this.fotoGato = fotoGato;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Bitmap getFotoGato() {
        return fotoGato;
    }

    public void setFotoGato(Bitmap fotoGato) {
        this.fotoGato = fotoGato;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}
