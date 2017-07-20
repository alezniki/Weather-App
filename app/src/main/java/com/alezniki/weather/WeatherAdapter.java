package com.alezniki.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alezniki.weather.model.WeatherData;

import java.util.List;

/**
 * Created by nikola on 7/20/17.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

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
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get element from your data set at this position
        final WeatherData pos = list.get(position);

        // Get first letter capitalized from Weather Description
        String weatherDescription = pos.getWeatherDescription().substring(0, 1).toUpperCase()
                + pos.getWeatherDescription().substring(1);

        holder.tvDate.setText(pos.getDate());
        holder.tvDescription.setText(weatherDescription);
        holder.tvTemp.setText("Current: " + String.valueOf(pos.getTemp()) + "˚C");
        holder.tvHumidity.setText("Humidity: " + String.valueOf(pos.getHumidity()) + "%");
        holder.tvWindSpeed.setText("Wind speed: " + String.valueOf(pos.getWindSpeed()) + "m/s");
        // holder.ivImage.setImageDrawable(R.drawable.ic_action_sun);

    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        // return 0;
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate;
        TextView tvDescription;
        TextView tvTemp;
        TextView tvHumidity;
        TextView tvWindSpeed;
        ImageView ivImage;

        public ViewHolder(View itemView) {
            super(itemView);

            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
            tvTemp = (TextView) itemView.findViewById(R.id.tv_temp);
            tvHumidity = (TextView) itemView.findViewById(R.id.tv_humidity);
            tvWindSpeed = (TextView) itemView.findViewById(R.id.tv_wind_speed);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }

    public void addDataToAdapter(List<WeatherData> dataList) {
        this.list.addAll(dataList);
        this.notifyItemRangeInserted(0, list.size() - 1);
    }

    public void clearDataFromAdapter() {
        int listSize = this.list.size();
        if (listSize > 0) {
            for (int i = 0; i < listSize ; i++) {
                this.list.remove(0);
            }
            this.notifyItemRangeInserted(0, listSize);
        }
    }


}
