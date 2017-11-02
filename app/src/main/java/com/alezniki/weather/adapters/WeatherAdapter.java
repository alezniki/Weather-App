package com.alezniki.weather.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alezniki.weather.R;
import com.alezniki.weather.activities.DetailActivity;
import com.alezniki.weather.model.WeatherData;

import java.util.List;

/**
 * Weather Adapter
 *
 * Created by nikola on 7/20/17.
 */
public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    public static final String KEY_ID = "key_id";

    private final List<WeatherData> list; // List of weather data
    private final Context context;

    public WeatherAdapter(List<WeatherData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.weather_card, parent, false);

        return new ViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    // This is where you supply data that you want to display to the user
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        // Get element from your data set at this position
        final WeatherData weatherData = list.get(position);

        // Add click listener to the view
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Unique ID to pass between activities;
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(KEY_ID, weatherData);
                context.startActivity(intent);
            }
        });

        holder.tvDate.setText(weatherData.getDate());
        holder.tvWeather.setText(weatherData.getMainWeather());
        holder.tvTemp.setText(String.valueOf(weatherData.getDayTemp() + " ˚C"));
        holder.tvHumidity.setText("Humidity: " + String.valueOf(weatherData.getHumidity()) + " %");
        holder.tvWindSpeed.setText("Wind speed: " + String.valueOf(weatherData.getWindSpeed()) + " m/s");


        switch (weatherData.getMainWeather()) {
            case WeatherData.WEATHER_DESCRIPTION_CLEAR_SKY:
                holder.ivImage.setImageResource(R.drawable.ic_clear);
                return;
            case WeatherData.WEATHER_DESCRIPTION_CLOUDS:
                holder.ivImage.setImageResource(R.drawable.ic_clouds);
                return;
            case WeatherData.WEATHER_DESCRIPTION_RAIN: {
                holder.ivImage.setImageResource(R.drawable.ic_rain);
                return;
            }
            case WeatherData.WEATHER_DESCRIPTION_THUNDERSTORM:
                holder.ivImage.setImageResource(R.drawable.ic_thunder);
                return;
            case WeatherData.WEATHER_DESCRIPTION_SNOW:
                holder.ivImage.setImageResource(R.drawable.ic_snow);
                return;
            case WeatherData.WEATHER_DESCRIPTION_MIST:
                holder.ivImage.setImageResource(R.drawable.ic_mist);
        }
    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        // return 0;
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tvDate;
        final TextView tvWeather;
        final TextView tvTemp;
        final TextView tvHumidity;
        final TextView tvWindSpeed;
        final ImageView ivImage;

        // Handle user events in a RecyclerView
        final View view;

        ViewHolder(View itemView) {
            super(itemView);

            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvWeather = (TextView) itemView.findViewById(R.id.tv_weather);
            tvTemp = (TextView) itemView.findViewById(R.id.tv_temp);
            tvHumidity = (TextView) itemView.findViewById(R.id.tv_humidity);
            tvWindSpeed = (TextView) itemView.findViewById(R.id.tv_wind_speed);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_image);

            // Now the view reference will be available to the rest of the adapter
            view = itemView;
        }
    }


    /**
     * Add Data To Adapter
     *
     * @param dataList weather data list
     */
    public void addDataToAdapter(List<WeatherData> dataList) {
        this.list.addAll(dataList);
        this.notifyItemRangeInserted(0, list.size() - 1);
    }

    /**
     * Clear Data From Adapter
     */
    public void clearDataFromAdapter() {
        int listSize = this.list.size();
        if (listSize > 0) {
            for (int i = 0; i < listSize; i++) {
                this.list.remove(0);
            }

            this.notifyItemRangeRemoved(0, listSize);
        }
    }
}
