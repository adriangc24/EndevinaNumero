package com.example.endevinanumero;

import android.graphics.Bitmap;

public class User {

    public String email;
    public String puntos;
    public Bitmap foto;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPuntos() {
        return puntos;
    }

    public void setPuntos(String puntos) {
        this.puntos = puntos;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public User(String email, String puntos) {
        this.email = email;
        this.puntos = puntos;
    }

    public User(String email, String puntos, Bitmap foto) {
        this.email = email;
        this.puntos = puntos;
        this.foto = foto;
    }

    public User (){

    }

    @Override
    public String toString() {
        return "Usuario "+email+" con "+puntos;
    }
}