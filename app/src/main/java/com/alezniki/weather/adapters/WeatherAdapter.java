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
 * <p>
 * Created by nikola aleksic on 7/20/17.
 */
public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    /**
     * Key id
     */
    public static final String KEY_ID = "key_id";

    /**
     * Weather data list
     */
    private final List<WeatherData> weathers;

    /**
     * Context
     */
    private final Context context;

    /**
     * Constructor
     *
     * @param context  context
     * @param weathers weathers list
     */
    public WeatherAdapter(Context context, List<WeatherData> weathers) {
        this.context = context;
        this.weathers = weathers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Create new views (invoked by the layout manager)
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.weather_card, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //Replace the contents of a view (invoked by the layout manager)
        //This is where you supply data that you want to display to the user

        //Get element from your data set at this position
        final WeatherData weatherData = weathers.get(position);

        //Add click listener to the view
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Unique ID to pass between activities;
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

            case WeatherData.WEATHER_DESCRIPTION_CLEAR_SKY: {
                holder.ivImage.setImageResource(R.drawable.ic_clear);
                break;
            }

            case WeatherData.WEATHER_DESCRIPTION_CLOUDS: {
                holder.ivImage.setImageResource(R.drawable.ic_clouds);
                break;
            }

            case WeatherData.WEATHER_DESCRIPTION_RAIN: {
                holder.ivImage.setImageResource(R.drawable.ic_rain);
                break;
            }

            case WeatherData.WEATHER_DESCRIPTION_THUNDERSTORM: {
                holder.ivImage.setImageResource(R.drawable.ic_thunder);
                break;
            }

            case WeatherData.WEATHER_DESCRIPTION_SNOW: {
                holder.ivImage.setImageResource(R.drawable.ic_snow);
                break;
            }

            case WeatherData.WEATHER_DESCRIPTION_MIST: {
                holder.ivImage.setImageResource(R.drawable.ic_mist);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        //Return the size of your data set (invoked by the layout manager)
        return weathers.size();
    }

    /**
     * View holder
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        //Date
        final TextView tvDate;

        //Weather
        final TextView tvWeather;

        //Temperature
        final TextView tvTemp;

        //Humidity
        final TextView tvHumidity;

        //Wind speed
        final TextView tvWindSpeed;

        //Image icon
        final ImageView ivImage;

        //View to handle user events
        final View view;

        /**
         * Constructor
         *
         * @param itemView item view
         */
        ViewHolder(View itemView) {
            super(itemView);

            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvWeather = (TextView) itemView.findViewById(R.id.tv_weather);
            tvTemp = (TextView) itemView.findViewById(R.id.tv_temp);
            tvHumidity = (TextView) itemView.findViewById(R.id.tv_humidity);
            tvWindSpeed = (TextView) itemView.findViewById(R.id.tv_wind_speed);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_image);

            //Now the view reference will be available to the rest of the adapter
            view = itemView;
        }
    }

    /**
     * Add Data To Adapter
     *
     * @param dataList weather data list
     */
    public void addDataToAdapter(List<WeatherData> dataList) {
        this.weathers.addAll(dataList);
        this.notifyItemRangeInserted(0, weathers.size() - 1);
    }

    /**
     * Clear Data From Adapter
     */
    public void clearDataFromAdapter() {
        int listSize = this.weathers.size();
        if (listSize > 0) {
            for (int i = 0; i < listSize; i++) {
                this.weathers.remove(0);
            }

            this.notifyItemRangeRemoved(0, listSize);
        }
    }
}
