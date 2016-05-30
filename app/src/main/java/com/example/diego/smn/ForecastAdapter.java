package com.example.diego.smn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by diego on 29/05/16.
 */
public class ForecastAdapter extends BaseAdapter {

    Context context;
    ArrayList<ForecastData> data;

    public ForecastAdapter(Context context, ArrayList<ForecastData> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public ForecastData getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.forecast_item_main, parent, false);

        }

        ForecastData item = getItem(position);

        TextView dia = (TextView) convertView.findViewById(R.id.dia);
        dia.setText(item.getDia());

        TextView parteDelDia = (TextView) convertView.findViewById(R.id.parteDelDia);
        parteDelDia.setText(item.getParteDelDia());

        TextView temperatura = (TextView) convertView.findViewById(R.id.temperatura);
        temperatura.setText(item.getTemperaturaTipo() + ": " + item.temperaturaValor + " °C");

        TextView descripcion = (TextView) convertView.findViewById(R.id.descripcion);
        descripcion.setText(item.getDescripcion());

        String iconoUrl = "http://www.smn.gov.ar/" + item.getIcono();
        ImageView icono = (ImageView) convertView.findViewById(R.id.icono);

        Glide.with(context).load(iconoUrl).into(icono);

        return convertView;
    }
}
