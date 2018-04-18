package com.example.lsaal.cf.Clases_Datos;

/**
 * Created by mauri on 12/04/2018.
 */

public class hectareas {
    int id_hectarea;
    String lugar;
    double tamaño;
    String ubicacion;
    String fecha_ciembra;
    int id_producto;
    String nameproduct;
    public hectareas(){
    }

    public int getId_hectarea() {
        return id_hectarea;
    }

    public void setId_hectarea(int id_hectarea) {
        this.id_hectarea = id_hectarea;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public double getTamaño() {
        return tamaño;
    }

    public void setTamaño(double tamaño) {
        this.tamaño = tamaño;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFecha_ciembra() {
        return fecha_ciembra;
    }

    public void setFecha_ciembra(String fecha_ciembra) {
        this.fecha_ciembra = fecha_ciembra;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNameproduct() {
        return nameproduct;
    }

    public void setNameproduct(String nameproduct) {
        this.nameproduct = nameproduct;
    }
}
