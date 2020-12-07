package com.juancoche.mydogv3.Model;

import java.util.Map;

public class Perrete {

    private String nombre;
    private String raza;
    private String fnac;
    private Long genero;
    private boolean esterilizado;
    private String peso;
    private String nChip;
    private String urlImg;
    private String medidas;
    private String vacuna;
    private String desparasitacion;
    private String medicacion;




    public Perrete() {
    }

    //Constructor para mapeo del HashMap que devuelve BBDD
    public Perrete(Map<String, Object> data) {
        this.nombre = (String) data.get("nombre");
        this.raza = (String) data.get("raza");
        this.fnac = (String) data.get("fnac");
        this.genero = (Long) data.get("genero");
        this.esterilizado = (boolean) data.get("esterilizado");
        this.peso = (String) data.get("peso");
        this.nChip = (String) data.get("nChip");
        this.urlImg = (String) data.get("urlImg");
        this.medidas = (String) data.get("medidas");
        this.medicacion = (String) data.get("medicacion");
        this.desparasitacion = (String) data.get("desparasitacion");
        this.vacuna = (String) data.get("vacuna");
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

    public Long getGenero() {
        return genero;
    }

    public void setGenero(Long genero) {
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

    public String getMedidas() {
        return medidas;
    }

    public void setMedidas(String medidas) {
        this.medidas = medidas;
    }

    public String getVacuna() {
        return vacuna;
    }

    public void setVacuna(String vacuna) {
        this.vacuna = vacuna;
    }

    public String getDesparasitacion() {
        return desparasitacion;
    }

    public void setDesparasitacion(String desparasitacion) {
        this.desparasitacion = desparasitacion;
    }

    public String getMedicacion() {
        return medicacion;
    }

    public void setMedicacion(String medicacion) {
        this.medicacion = medicacion;
    }
}
