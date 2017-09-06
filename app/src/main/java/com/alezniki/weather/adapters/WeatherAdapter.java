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
 * Created by nikola on 7/20/17.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    public static final String KEY_ID = "key_id";

    private List<WeatherData> list; // List of weather data
    private Context context;

    public WeatherAdapter(List<WeatherData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //return null;

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.weather_card, parent, false);

        return new ViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    // This is where you supply data that you want to display to the user
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        // Get element from your data set at this position
        final WeatherData pos = list.get(position);

        // Add click listener to the view
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Unique ID to pass between activities;
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(KEY_ID, pos);
                context.startActivity(intent);
            }
        });

        holder.tvDate.setText(pos.getDate());
        holder.tvWeather.setText(pos.getMainWeather());
        holder.tvTemp.setText(String.valueOf(pos.getDayTemp() + " ˚C"));
        holder.tvHumidity.setText("Humidity: " + String.valueOf(pos.getHumidity()) + " %");
        holder.tvWindSpeed.setText("Wind speed: " + String.valueOf(pos.getWindSpeed()) + " m/s");


        switch (pos.getMainWeather()){
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
                return;
        }
    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        // return 0;
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate;
        TextView tvWeather;
        TextView tvTemp;
        TextView tvHumidity;
        TextView tvWindSpeed;
        ImageView ivImage;

        // Handle user events in a RecyclerView
        public final View view;


        public ViewHolder(View itemView) {
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


    public void addDataToAdapter(List<WeatherData> dataList) {
        this.list.addAll(dataList);
        this.notifyItemRangeInserted(0, list.size() - 1);
    }

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
