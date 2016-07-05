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

        boolean extended = true;

        int index = html.indexOf("HOY ");
        int index2 = 0;
        int index3 = 0;
        int index4 = 0;
        int index5 = 0;
        int index6 = 0;
        int index7 = 0;

        if (index == -1) {
            extended = false;

            index = html.indexOf("lunes");
            index2 = html.indexOf("martes");
            index3 = html.indexOf("mi�rcoles");
            index4 = html.indexOf("jueves");
            index5 = html.indexOf("viernes");
            index6 = html.indexOf("s�bado");
            index7 = html.indexOf("domingo");

            index = firstIndex(index, index2, index3, index4, index5, index6, index7);
        }

        index2 = html.indexOf("</", index);
        String dia = html.substring(index, index2);

        String dia2 = null;
        if (extended) {
            index = html.indexOf("<b>", index2) + 3;
            index2 = html.indexOf("</", index);
            dia2 = html.substring(index, index2);
        }

        index = html.indexOf("<b>", index2) + 3;
        index2 = html.indexOf("</", index);
        String parteDelDia = html.substring(index, index2);

        index = html.indexOf("<b>", index2) + 3;
        index2 = html.indexOf("</", index);
        String parteDelDia2 = html.substring(index, index2);

        String parteDelDia3 = null;
        if (extended) {
            index = html.indexOf("<b>", index2) + 3;
            index2 = html.indexOf("</", index);
            parteDelDia3 = html.substring(index, index2);
        }

        index = html.indexOf("gifs/header/iconos/chicos/", index2);
        index2 = html.indexOf(".jpg", index) + 4;
        String icono1 = html.substring(index, index2);

        index = html.indexOf("gifs/header/iconos/chicos/", index2);
        index2 = html.indexOf(".jpg", index) + 4;
        String icono2 = html.substring(index, index2);

        String icono3 = null;
        String descripcion = null;
        String descripcion2 = null;
        if (extended) {
            index = html.indexOf("gifs/header/iconos/chicos/", index2);
            index2 = html.indexOf(".jpg", index) + 4;
            icono3 = html.substring(index, index2);
        } else {
            index = html.indexOf("center;\">", index2) + 9;
            index2 = html.indexOf("</div>", index);
            descripcion = html.substring(index, index2);

            index = html.indexOf("center;\">", index2) + 9;
            index2 = html.indexOf("</div>", index);
            descripcion2 = html.substring(index, index2);
        }

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

        String temperaturaTipo3 = null;
        String temperaturaValor3 = null;
        if (extended) {
            index = html.indexOf("M", index2);
            index2 = html.indexOf("</em>", index);
            temperaturaTipo3 = html.substring(index, index2);

            index = html.indexOf("\">", index2) + 2;
            index2 = html.indexOf(" �C", index);
            temperaturaValor3 = html.substring(index, index2);

            index = html.indexOf("\"font1\">", index2) + 8;
            index2 = html.indexOf("</td>", index);
            descripcion = html.substring(index, index2);

            index = html.indexOf("\"font1\">", index2) + 8;
            index2 = html.indexOf("</td>", index);
            descripcion2 = html.substring(index, index2);
        }

        String descripcion3 = null;
        if (extended) {
            index = html.indexOf("\"font1\">", index2) + 8;
            index2 = html.indexOf("</td>", index);
            descripcion3 = html.substring(index, index2);
        }

        ForecastData uno = new ForecastData(dia, parteDelDia, icono1, temperaturaTipo, temperaturaValor, descripcion);
        ForecastData dos = null;
        ForecastData tres = null;
        if (extended) {
            dos = new ForecastData(dia2, parteDelDia2, icono2, temperaturaTipo2, temperaturaValor2, descripcion2);
            tres = new ForecastData(dia2, parteDelDia3, icono3, temperaturaTipo3, temperaturaValor3, descripcion3);
        } else {
            dos = new ForecastData(dia, parteDelDia2, icono2, temperaturaTipo2, temperaturaValor2, descripcion2);
        }

        data.add(uno);
        data.add(dos);
        if (extended) {
            data.add(tres);
        }
        return data;
    }

    private static int firstIndex(int index, int index2, int index3, int index4, int index5, int index6, int index7) {

        int smallest = Integer.MAX_VALUE;

        if (smallest > index && index != -1) smallest = index;
        if (smallest > index2 && index2 != -1) smallest = index2;
        if (smallest > index3 && index3 != -1) smallest = index3;
        if (smallest > index4 && index4 != -1) smallest = index4;
        if (smallest > index5 && index5 != -1) smallest = index5;
        if (smallest > index6 && index6 != -1) smallest = index6;
        if (smallest > index7 && index7 != -1) smallest = index7;

        return smallest;
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
