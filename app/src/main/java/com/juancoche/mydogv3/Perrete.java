package com.juancoche.mydogv3;

public class Perrete {

//    private int imagen;
    private String nombre;
    private String raza;
    private String fnac;
    private String sexo;
    private String lastVac;
    private String medicacion;
    private String nChip;



    public Perrete() {
    }

    public Perrete(/*int imagen,*/ String nombre, String raza) {
//        this.imagen = imagen;
        this.nombre = nombre;
        this.raza = raza;
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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getLastVac() {
        return lastVac;
    }

    public void setLastVac(String lastVac) {
        this.lastVac = lastVac;
    }

    public String getMedicacion() {
        return medicacion;
    }

    public void setMedicacion(String medicacion) {
        this.medicacion = medicacion;
    }

    public String getnChip() {
        return nChip;
    }

    public void setnChip(String nChip) {
        this.nChip = nChip;
    }
}
