package com.juancoche.mydogv3;

public class Perrete {

    private int imagen;
    private String nombre;
    private String raza;

    public Perrete() {
    }

    public Perrete(int imagen, String nombre, String raza) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.raza = raza;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }
}
