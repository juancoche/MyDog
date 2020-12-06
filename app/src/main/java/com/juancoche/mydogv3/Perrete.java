package com.juancoche.mydogv3;

import java.util.Map;

public class Perrete {

//    private int imagen;
    private String nombre;
    private String raza;
    private String fnac;
    private String genero;
    private boolean esterilizado;
    private String peso;
    private String nChip;
    private String urlImg;



    public Perrete() {
    }

    //Constructor para mapeo del HashMap que devuelve BBDD
    public Perrete(Map<String, Object> data) {
        this.nombre = (String) data.get("nombre");
        this.raza = (String) data.get("raza");
        this.fnac = (String) data.get("fnac");
        this.genero = (String) data.get("genero");
        this.esterilizado = (boolean) data.get("esterilizado");
        this.peso = (String) data.get("peso");
        this.nChip = (String) data.get("nChip");
        this.urlImg = (String) data.get("urlImg");
    }

/*    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }*/

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

    public String getFnac() {
        return fnac;
    }

    public void setFnac(String fnac) {
        this.fnac = fnac;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public boolean isEsterilizado() {
        return esterilizado;
    }

    public void setEsterilizado(boolean esterilizado) {
        this.esterilizado = esterilizado;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getnChip() {
        return nChip;
    }

    public void setnChip(String nChip) {
        this.nChip = nChip;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}
