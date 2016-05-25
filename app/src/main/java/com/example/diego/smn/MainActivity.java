package com.example.diego.smn;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute(getApplicationContext(), null, null);

    }

    private class AsyncTaskRunner extends AsyncTask<Context, Void, WeatherData> {

        @Override
        protected WeatherData doInBackground(Context... context) {
            return Utils.getWeatherData(context[0]);
        }

        @Override
        protected void onPostExecute(WeatherData weatherData) {
            final ImageView background = (ImageView) findViewById(R.id.background);
            Glide.with(MainActivity.this)
                    .load(weatherData.getBackgroundUrl())
                    .into(background);
            ((TextView) findViewById(R.id.estado)).setText(getString(R.string.estado_del_tiempo) + " " + weatherData.getEstado() + Utils.weather(Float.valueOf(weatherData.getTemperatura())));
            ((TextView) findViewById(R.id.visibilidad)).setText(getString(R.string.visibilidad) + " " + weatherData.getVisibilidad());
            ((TextView) findViewById(R.id.temperatura)).setText(getString(R.string.temperatura)  + " " + weatherData.getTemperatura() + " °C");
            ((TextView) findViewById(R.id.humedad)).setText(getString(R.string.humedad) + weatherData.getHumedad());
            ((TextView) findViewById(R.id.viento)).setText(getString(R.string.viento) + " " + weatherData.getViento());

            if (weatherData.getSensacion().contains("No se calcula")) {
                ((TextView) findViewById(R.id.sensacion)).setText(getString(R.string.sensacion_termica) + " " + "No se calcula");
            } else {
                ((TextView) findViewById(R.id.sensacion)).setText(getString(R.string.sensacion_termica) + " " + weatherData.getSensacion());
            }

            ((TextView) findViewById(R.id.presion)).setText(getString(R.string.presion_nivel_localidad) + " " + weatherData.getPresion());
        }
    }
}
