package com.alezniki.weather.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.alezniki.weather.R;
import com.alezniki.weather.model.WeatherData;

import java.util.List;

/**
 * Detail Weather Adapter
 * <p>
 * Created by nikola on 7/26/17.
 */
@SuppressWarnings("ALL")
public class DetailWeatherAdapter extends ArrayAdapter<WeatherData> {

    /**
     * Constructor
     *
     * @param context context
     * @param list    weather data list
     */
    public DetailWeatherAdapter(Context context, List<WeatherData> list) {
        super(context, 0, list);
    }

    // View lookup cache: To improve performance for faster item loading
    private static class ViewHolder {

        private TextView tvCity;
        private TextView tvDate;
        private TextView tvMorningTemp;
        private TextView tvDayTemp;
        private TextView tvEveningTemp;
        private TextView tvNightTemp;
        private TextView tvPressure;
        private TextView tvHumidity;
        private TextView tvWeather;
        private TextView tvClouds;
        private TextView tvWind;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // return super.getView(position, convertView, parent);

        // Get the WeatherData item fot this position
        final WeatherData weatherData = getItem(position);

        // Check if an existing convertView is being reused, otherwise inflate the viewHolder
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.weather_item, parent, false);

            // Lookup viewHolder for data population
            viewHolder.tvCity = (TextView) convertView.findViewById(R.id.tv_detail_city);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_detail_date);
            viewHolder.tvMorningTemp = (TextView) convertView.findViewById(R.id.tv_detail_morning_temp);
            viewHolder.tvDayTemp = (TextView) convertView.findViewById(R.id.tv_detail_day_temp);
            viewHolder.tvEveningTemp = (TextView) convertView.findViewById(R.id.tv_detail_evening_temp);
            viewHolder.tvNightTemp = (TextView) convertView.findViewById(R.id.tv_detail_night_temp);
            viewHolder.tvPressure = (TextView) convertView.findViewById(R.id.tv_detail_pressure);
            viewHolder.tvHumidity = (TextView) convertView.findViewById(R.id.tv_detail_humidity);
            viewHolder.tvWeather = (TextView) convertView.findViewById(R.id.tv_detail_weather);
            viewHolder.tvClouds = (TextView) convertView.findViewById(R.id.tv_detail_clouds);
            viewHolder.tvWind = (TextView) convertView.findViewById(R.id.tv_detail_wind);

            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object into the template convertView
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data from the data object via the viewHolder object into the template convertView
        assert weatherData != null;
        viewHolder.tvCity.setText(weatherData.getCityName() + ", " + weatherData.getCountry());
        viewHolder.tvDate.setText(weatherData.getDate());
        viewHolder.tvMorningTemp.setText("Morning temperature: " + weatherData.getMorningTemp() + " ˚C");
        viewHolder.tvDayTemp.setText("Day temperature: " + weatherData.getDayTemp() + " ˚C");
        viewHolder.tvEveningTemp.setText("Evening temperature: " + weatherData.getEveningTemp() + " ˚C");
        viewHolder.tvNightTemp.setText("Night temperature: " + weatherData.getNightTemp() + " ˚C");
        viewHolder.tvPressure.setText("Pressure: " + weatherData.getPressure() + " hPa");
        viewHolder.tvHumidity.setText("Humidity: " + weatherData.getHumidity() + " %");
        viewHolder.tvWeather.setText("Weather: " + weatherData.getMainWeather() + "(" + weatherData.getWeatherDescription() + ")");
        viewHolder.tvClouds.setText("Cloudiness: " + weatherData.getClouds() + " %");
        viewHolder.tvWind.setText("Wind: " + windDirectionDescription(weatherData.getWindDirection()) + ", " + weatherData.getWindSpeed() + " m/s");

        // Return the completed convertView to render on screen
        return convertView;
    }

    /**
     * Wind Direction Description
     * Return wind description based on giver wind direction
     *
     * @param degree weather degree
     * @return wind direction
     */
    @SuppressWarnings("JavaDoc")
    private String windDirectionDescription(int degree) {
        if (degree > 337.5) return "North";
        if (degree > 292.5) return "Northwest";
        if (degree > 247.5) return "West";
        if (degree > 202.5) return "Southwest";
        if (degree > 157.5) return "South";
        if (degree > 122.5) return "SouthEast";
        if (degree > 67.5) return "East";
        if (degree > 22.5) return "Northeast";

        return "North";
    }
}