package com.example.diego.smn;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by diego on 25/05/16.
 */
public class Utils {

    public static ArrayList<ForecastData> getForecastData(Context context) {

        String html;
        try {
            html = Utils.convert(new URL("http://www.smn.gov.ar/?mod=pron&id=1").openStream());
        } catch (IOException e) {
            return null;
        }

        ArrayList<ForecastData> data = new ArrayList<>();

        int index = html.indexOf("HOY ");
        int index2 = html.indexOf("</", index);
        String dia = html.substring(index, index2);

        index = html.indexOf("<b>", index2) + 3;
        index2 = html.indexOf("</", index);
        String dia2 = html.substring(index, index2);

        index = html.indexOf("<b>", index2) + 3;
        index2 = html.indexOf("</", index);
        String parteDelDia = html.substring(index, index2);

        index = html.indexOf("<b>", index2) + 3;
        index2 = html.indexOf("</", index);
        String parteDelDia2 = html.substring(index, index2);

        index = html.indexOf("<b>", index2) + 3;
        index2 = html.indexOf("</", index);
        String parteDelDia3 = html.substring(index, index2);

        index = html.indexOf("gifs/header/iconos/chicos/", index2);
        index2 = html.indexOf(".jpg", index) + 4;
        String icono1 = html.substring(index, index2);

        index = html.indexOf("gifs/header/iconos/chicos/", index2);
        index2 = html.indexOf(".jpg", index) + 4;
        String icono2 = html.substring(index, index2);

        index = html.indexOf("gifs/header/iconos/chicos/", index2);
        index2 = html.indexOf(".jpg", index) + 4;
        String icono3 = html.substring(index, index2);

        index = html.indexOf("<em>", index2) + 4;
        index2 = html.indexOf("</em>", index);
        String temperaturaTipo = html.substring(index, index2);

        index = html.indexOf("\">", index2) + 2;
        index2 = html.indexOf(" �C", index);
        String temperaturaValor = html.substring(index, index2);

        index = html.indexOf("<em>", index2) + 4;
        index2 = html.indexOf("</em>", index);
        String temperaturaTipo2 = html.substring(index, index2);

        index = html.indexOf("\">", index2) + 2;
        index2 = html.indexOf(" �C", index);
        String temperaturaValor2 = html.substring(index, index2);

        index = html.indexOf("M", index2);
        index2 = html.indexOf("</em>", index);
        String temperaturaTipo3 = html.substring(index, index2);

        index = html.indexOf("\">", index2) + 2;
        index2 = html.indexOf(" �C", index);
        String temperaturaValor3 = html.substring(index, index2);

        index = html.indexOf("\"font1\">", index2) + 8;
        index2 = html.indexOf("</td>", index);
        String descripcion = html.substring(index, index2);

        index = html.indexOf("\"font1\">", index2) + 8;
        index2 = html.indexOf("</td>", index);
        String descripcion2 = html.substring(index, index2);

        index = html.indexOf("\"font1\">", index2) + 8;
        index2 = html.indexOf("</td>", index);
        String descripcion3 = html.substring(index, index2);


        ForecastData uno = new ForecastData(dia,parteDelDia, icono1, temperaturaTipo, temperaturaValor, descripcion);
        ForecastData dos = new ForecastData(dia2,parteDelDia2, icono2, temperaturaTipo2, temperaturaValor2, descripcion2);
        ForecastData tres = new ForecastData(dia2,parteDelDia3, icono3, temperaturaTipo3, temperaturaValor3, descripcion3);

        data.add(uno);
        data.add(dos);
        data.add(tres);

        return data;
    }

    public static WeatherData getWeatherData(Context context) {

        String html;
        try {
            html = Utils.convert(new URL("http://www.smn.gov.ar/?mod=dpd&id=21&e=87585").openStream());
        } catch (IOException e) {
            return null;
        }

        int index = html.indexOf("gifs/header/iconos/");
        final String backgroundUrl = "http://www.smn.gov.ar/" + html.substring(index, html.indexOf(".jpg", index) + 4);

        index = html.indexOf(context.getString(R.string.estado_del_tiempo));
        final String estado = html.substring(html.indexOf(">", index) + 1, html.indexOf("</span>", index));

        index = html.indexOf(context.getString(R.string.visibilidad));
        final String visibilidad = html.substring(html.indexOf(">", index) + 1, html.indexOf("</span>", index));

        index = html.indexOf(context.getString(R.string.temperatura));
        final String temperatura = html.substring(html.indexOf(">", index) + 1, html.indexOf(" �C</span>", index));

        index = html.indexOf(context.getString(R.string.humedad));
        final String humedad = html.substring(html.indexOf(">", index) + 1, html.indexOf("</span>", index));

        index = html.indexOf(context.getString(R.string.viento));
        final String viento = html.substring(html.indexOf(">", index) + 1, html.indexOf("</span>", index));

        index = html.indexOf(context.getString(R.string.sensacion_termica));
        final String sensacion = html.substring(html.indexOf(">", index) + 1, html.indexOf("</span>", index));

        index = html.indexOf(context.getString(R.string.presion_nivel_localidad));
        final String presion = html.substring(html.indexOf(">", index) + 1, html.indexOf("</span>", index));

        return new WeatherData(backgroundUrl, estado, visibilidad, temperatura, humedad, viento, sensacion, presion);
    }

    public static String convert(InputStream inputStream) throws IOException {

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString();

    }

    public static String weather(float temp) {
        if (temp < 12) {
            return " y frío";
        } else if ((temp >= 12) && (temp < 17)) {
            return " y fresco";
        } else if ((temp >= 17) && (temp < 22)) {
            return " y agradable";
        } else if ((temp >= 22) && (temp < 26)) {
            return " y templado";
        } else if ((temp >= 26) && (temp < 31)) {
            return " y cálido";
        } else if (temp >= 31) {
            return " y caluroso";
        } else {
            return "";
        }
    }
}
