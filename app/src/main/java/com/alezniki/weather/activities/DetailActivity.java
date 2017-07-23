package com.alezniki.weather.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alezniki.weather.R;
import com.alezniki.weather.model.WeatherData;

import static com.alezniki.weather.WeatherAdapter.KEY_ID;

public class DetailActivity extends AppCompatActivity {

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

    WeatherData wd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvCity = (TextView) findViewById(R.id.tv_detail_city);
        tvDate = (TextView) findViewById(R.id.tv_detail_date);
        tvMorningTemp = (TextView) findViewById(R.id.tv_detail_morning_temp);
        tvDayTemp = (TextView) findViewById(R.id.tv_detail_day_temp);
        tvEveningTemp = (TextView) findViewById(R.id.tv_detail_evening_temp);
        tvNightTemp = (TextView) findViewById(R.id.tv_detail_night_temp);
        tvPressure = (TextView) findViewById(R.id.tv_detail_pressure);
        tvHumidity = (TextView) findViewById(R.id.tv_detail_humidity);
        tvWeather = (TextView) findViewById(R.id.tv_detail_weather);
        tvClouds = (TextView) findViewById(R.id.tv_detail_clouds);
        tvWind = (TextView) findViewById(R.id.tv_detail_wind);


        //Receive the object that which has been sent through Intent from MainActivity
        WeatherData wd = (WeatherData) getIntent().getSerializableExtra(KEY_ID);
        loadWeatherData(wd);

    }

    private void loadWeatherData(WeatherData wd) {

        tvCity.setText(wd.getCityName() + ", " + wd.getCountry());
        tvDate.setText(wd.getDate());
        tvMorningTemp.setText("Morning temperature: " + wd.getMorningTemp() + " ˚C");
        tvDayTemp.setText("Day temperature: " + wd.getDayTemp()  + " ˚C");
        tvEveningTemp.setText("Evening temperature: " + wd.getEveningTemp()  + " ˚C");
        tvNightTemp.setText("Night temperature: " + wd.getNightTemp() + " ˚C");
        tvPressure.setText("Pressure: " + wd.getPressure() + " hPa");
        tvHumidity.setText("Humidity: " + wd.getHumidity() + " %");
        tvWeather.setText("Weather: " + wd.getMainWeather() + "(" + wd.getWeatherDescription() + ")");
        tvClouds.setText("Cloudiness: " + wd.getClouds() + " %");
        tvWind.setText("Wind: " + windDirectionDescription(wd.getWindDirection()) + ", " + wd.getWindSpeed() + " m/s");
    }

    private String windDirectionDescription(int degree) {
        if (degree > 337.5) return "North";
        if (degree > 292.5) return "Northwest";
        if (degree > 247.5) return "West";
        if (degree > 202.5) return "Southwest";
        if (degree > 157.5) return "South";
        if (degree > 122.5) return "SouthEast";
        if (degree > 67.5)  return "East";
        if (degree > 22.5)  return "Northeast";

        return "North";
    }
}
