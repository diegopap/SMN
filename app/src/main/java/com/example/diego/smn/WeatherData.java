package com.example.diego.smn;

/**
 * Created by diego on 25/05/16.
 */
public class WeatherData {

    String backgroundUrl;
    String estado;
    String visibilidad;
    String temperatura;
    String humedad;
    String viento;
    String sensacion;
    String presion;

    public WeatherData(String backgroundUrl, String estado, String visibilidad, String temperatura, String humedad, String viento, String sensacion, String presion) {
        this.backgroundUrl = backgroundUrl;
        this.estado = estado;
        this.visibilidad = visibilidad;
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.viento = viento;
        this.sensacion = sensacion;
        this.presion = presion;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getVisibilidad() {
        return visibilidad;
    }

    public void setVisibilidad(String visibilidad) {
        this.visibilidad = visibilidad;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getHumedad() {
        return humedad;
    }

    public void setHumedad(String humedad) {
        this.humedad = humedad;
    }

    public String getViento() {
        return viento;
    }

    public void setViento(String viento) {
        this.viento = viento;
    }

    public String getSensacion() {
        return sensacion;
    }

    public void setSensacion(String sensacion) {
        this.sensacion = sensacion;
    }

    public String getPresion() {
        return presion;
    }

    public void setPresion(String presion) {
        this.presion = presion;
    }
}
