package com.example.diego.smn;

/**
 * Created by diego on 29/05/16.
 */
public class ForecastData {

    String dia;

    String parteDelDia;

    String icono;

    String temperaturaTipo;

    String temperaturaValor;

    String descripcion;

    public ForecastData(String dia, String parteDelDia, String icono, String temperaturaTipo, String temperaturaValor, String descripcion) {
        this.dia = dia;
        this.parteDelDia = parteDelDia;
        this.icono = icono;
        this.temperaturaTipo = temperaturaTipo;
        this.temperaturaValor = temperaturaValor;
        this.descripcion = descripcion;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getParteDelDia() {
        return parteDelDia;
    }

    public void setParteDelDia(String parteDelDia) {
        this.parteDelDia = parteDelDia;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getTemperaturaTipo() {
        return temperaturaTipo;
    }

    public void setTemperaturaTipo(String temperaturaTipo) {
        this.temperaturaTipo = temperaturaTipo;
    }

    public String getTemperaturaValor() {
        return temperaturaValor;
    }

    public void setTemperaturaValor(String temperaturaValor) {
        this.temperaturaValor = temperaturaValor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
