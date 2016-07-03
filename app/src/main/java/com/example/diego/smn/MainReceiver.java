package com.example.diego.smn;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.AppWidgetTarget;

/**
 * Created by diego on 17/05/16.
 */
public class MainReceiver extends AppWidgetProvider {

    Context context;
    AppWidgetManager appWidgetManager;
    int[] appWidgetIds;

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        this.context = context;
        this.appWidgetManager = appWidgetManager;
        this.appWidgetIds = appWidgetIds;
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute(context, null, null);
    }

    private class AsyncTaskRunner extends AsyncTask<Context, Void, WeatherData> {

        @Override
        protected WeatherData doInBackground(Context... context) {
            return Utils.getWeatherData(context[0]);
        }

        @Override
        protected void onPostExecute(WeatherData weatherData) {

            if (weatherData == null) return;

            for(int i = 0; i < appWidgetIds.length; i++) {

                int currentWidgetId = appWidgetIds[i];

                final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.activity_widget_current);

                AppWidgetTarget appWidgetTarget = new AppWidgetTarget(context, views, R.id.background, appWidgetIds);

                Glide.with(context.getApplicationContext())
                        .load(weatherData.getBackgroundUrl())
                        .asBitmap()
                        .into(appWidgetTarget);

                views.setTextViewText(R.id.estado, context.getString(R.string.estado_del_tiempo) + " " + weatherData.getEstado() + Utils.weather(Float.valueOf(weatherData.getTemperatura())));
                views.setTextViewText(R.id.visibilidad, context.getString(R.string.visibilidad) + " " + weatherData.getVisibilidad());
                views.setTextViewText(R.id.temperatura, context.getString(R.string.temperatura)  + " " + weatherData.getTemperatura() + " °C");
                views.setTextViewText(R.id.humedad, context.getString(R.string.humedad) + weatherData.getHumedad());
                views.setTextViewText(R.id.viento, context.getString(R.string.viento) + " " + weatherData.getViento());

                if (weatherData.getSensacion().contains("No se calcula")) {
                    views.setTextViewText(R.id.sensacion, context.getString(R.string.sensacion_termica) + " " + "No se calcula");
                } else {
                    views.setTextViewText(R.id.sensacion, context.getString(R.string.sensacion_termica) + " " + weatherData.getSensacion());
                }

                views.setTextViewText(R.id.presion, context.getString(R.string.presion_nivel_localidad) + " " + weatherData.getPresion());

                appWidgetManager.updateAppWidget(currentWidgetId, views);
            }

        }
    }

}
