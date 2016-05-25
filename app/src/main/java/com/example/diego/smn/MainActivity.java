package com.example.diego.smn;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {

                String html;
                try {
                    html = convert(new URL("http://www.smn.gov.ar/?mod=dpd&id=21&e=87585").openStream());
                } catch (IOException e) {
                    return;
                }

                int index = html.indexOf("gifs/header/iconos/");
                final String backgroundUrl = "http://www.smn.gov.ar/" + html.substring(index, html.indexOf(".jpg", index) + 4);

                index = html.indexOf(getString(R.string.estado_del_tiempo));
                final String estado = html.substring(html.indexOf(">", index) + 1, html.indexOf("</span>", index));

                index = html.indexOf(getString(R.string.visibilidad));
                final String visibilidad = html.substring(html.indexOf(">", index) + 1, html.indexOf("</span>", index));

                index = html.indexOf(getString(R.string.temperatura));
                final String temperatura = html.substring(html.indexOf(">", index) + 1, html.indexOf(" �C</span>", index));

                index = html.indexOf(getString(R.string.humedad));
                final String humedad = html.substring(html.indexOf(">", index) + 1, html.indexOf("</span>", index));

                index = html.indexOf(getString(R.string.viento));
                final String viento = html.substring(html.indexOf(">", index) + 1, html.indexOf("</span>", index));

                index = html.indexOf(getString(R.string.sensacion_termica));
                final String sensacion = html.substring(html.indexOf(">", index) + 1, html.indexOf("</span>", index));

                index = html.indexOf(getString(R.string.presion_nivel_localidad));
                final String presion = html.substring(html.indexOf(">", index) + 1, html.indexOf("</span>", index));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final View background = findViewById(R.id.content);
                        Glide.with(MainActivity.this)
                                .load(backgroundUrl)
                                .asBitmap().into(new SimpleTarget<Bitmap>(background.getWidth(), background.getHeight()) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                Drawable drawable = new BitmapDrawable(resource);
                                background.setBackgroundDrawable(drawable);
                            }
                        });
                        ((TextView) findViewById(R.id.estado)).setText(getString(R.string.estado_del_tiempo) + " " + estado + weather(Float.valueOf(temperatura)));
                        ((TextView) findViewById(R.id.visibilidad)).setText(getString(R.string.visibilidad) + " " + visibilidad);
                        ((TextView) findViewById(R.id.temperatura)).setText(getString(R.string.temperatura)  + " " + temperatura + " °C");
                        ((TextView) findViewById(R.id.humedad)).setText(getString(R.string.humedad) + humedad);
                        ((TextView) findViewById(R.id.viento)).setText(getString(R.string.viento) + " " + viento);

                        if (sensacion.contains("No se calcula")) {
                            ((TextView) findViewById(R.id.sensacion)).setText(getString(R.string.sensacion_termica) + " " + "No se calcula");
                        } else {
                            ((TextView) findViewById(R.id.sensacion)).setText(getString(R.string.sensacion_termica) + " " + sensacion);
                        }

                        ((TextView) findViewById(R.id.presion)).setText(getString(R.string.presion_nivel_localidad) + " " + presion);

                    }
                });


            }
        }).start();
    }

    private String convert(InputStream inputStream) throws IOException {

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString();

    }

    private String weather(float temp) {
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
