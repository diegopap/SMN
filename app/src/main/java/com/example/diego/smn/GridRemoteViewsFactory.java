package com.example.diego.smn;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by diego on 05/07/16.
 */
public class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = "GridRemoteViewsFactory";

    private ArrayList<ForecastData> forecastData;
    private Context mContext;
    private int mAppWidgetId;

    public GridRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        forecastData = Utils.getForecastData(mContext);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return forecastData.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        ForecastData data = forecastData.get(position);

        // Construct a remote views item based on the app widget item XML file,
        // and set the text based on the position.
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.forecast_item_main);

        views.setImageViewBitmap(R.id.icono, getImageBitmap("http://www.smn.gov.ar/" + data.getIcono()));
        views.setTextViewText(R.id.dia, data.getDia());
        views.setTextViewText(R.id.parteDelDia, data.getParteDelDia());
        views.setTextViewText(R.id.temperatura, data.getTemperaturaTipo() + ": " + data.getTemperaturaValor() + " °C");
        views.setTextViewText(R.id.descripcion, data.getDescripcion());

        // Return the remote views object.
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e(TAG, "Error getting bitmap", e);
        }
        return bm;
    }

}
