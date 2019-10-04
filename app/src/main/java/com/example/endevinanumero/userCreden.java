package com.example.endevinanumero;

public class userCreden {
    private String email;
    private int puntos;

    public userCreden(String email, int puntos) {
        this.email = email;
        this.puntos = puntos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}
