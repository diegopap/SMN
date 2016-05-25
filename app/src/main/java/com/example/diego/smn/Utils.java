package com.example.diego.smn;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by diego on 25/05/16.
 */
public class Utils {

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
