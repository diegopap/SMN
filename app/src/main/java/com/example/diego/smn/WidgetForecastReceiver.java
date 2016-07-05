package com.example.diego.smn;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import java.util.ArrayList;

/**
 * Created by diego on 17/05/16.
 */
public class WidgetForecastReceiver extends AppWidgetProvider {

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

    private class AsyncTaskRunner extends AsyncTask<Context, Void, ArrayList<ForecastData>> {

        @Override
        protected ArrayList<ForecastData> doInBackground(Context... context) {
            return Utils.getForecastData(context[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<ForecastData> forecastData) {

            if (forecastData == null) return;

            for(int i = 0; i < appWidgetIds.length; i++) {

                int currentWidgetId = appWidgetIds[i];

                final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_forecast);

                Intent intent = new Intent(context, GridWidgetService.class);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, currentWidgetId);
                intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

                views.setRemoteAdapter(currentWidgetId, R.id.pronostico, intent);

                appWidgetManager.updateAppWidget(currentWidgetId, views);
            }

        }
    }

}
