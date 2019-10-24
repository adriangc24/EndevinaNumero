package com.example.endevinanumero;

public class User {

    public String email;
    public String puntos;


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


    public User(String email, String puntos) {
        this.email = email;
        this.puntos = puntos;
    }
    public User (){

    }

    @Override
    public String toString() {
        return "Usuario "+email+" con "+puntos;
    }
}