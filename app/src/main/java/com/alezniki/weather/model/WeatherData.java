package com.alezniki.weather.model;

import java.io.Serializable;

/**
 * Created by nikola on 7/20/17.
 */

public class WeatherData implements Serializable {


    public static final String WEATHER_DESCRIPTION_CLEAR_SKY = "Clear";
    public static final String WEATHER_DESCRIPTION_CLOUDS = "Clouds";
    public static final String WEATHER_DESCRIPTION_RAIN = "Rain";
    public static final String WEATHER_DESCRIPTION_THUNDERSTORM = "Thunderstorm";
    public static final String WEATHER_DESCRIPTION_SNOW = "Snow";
    public static final String WEATHER_DESCRIPTION_MIST = "Mist";

    // Weather data ID
    private int dataID;
    // City and Country
    private String cityName;
    private String country;
    // Temperature
    private int dayTemp;
    private int minTemp;
    private int maxTemp;
    private int nightTemp;
    private int eveningTemp;
    private int morningTemp;
    private int pressure;
    private int humidity;
    // Weather
    private String mainWeather;
    private String weatherDescription;
    // Clouds and Wind
    private int clouds;
    private int windSpeed;
    private int windDirection;
    // Date String
    private String date;

    public WeatherData() {}

    public int getDataID() {
        return dataID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getDayTemp() {
        return dayTemp;
    }

    public void setDayTemp(int dayTemp) {
        this.dayTemp = dayTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public int getNightTemp() {
        return nightTemp;
    }

    public void setNightTemp(int nightTemp) {
        this.nightTemp = nightTemp;
    }

    public int getEveningTemp() {
        return eveningTemp;
    }

    public void setEveningTemp(int eveningTemp) {
        this.eveningTemp = eveningTemp;
    }

    public int getMorningTemp() {
        return morningTemp;
    }

    public void setMorningTemp(int morningTemp) {
        this.morningTemp = morningTemp;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getMainWeather() {
        return mainWeather;
    }

    public void setMainWeather(String mainWeather) {
        this.mainWeather = mainWeather;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(int windDirection) {
        this.windDirection = windDirection;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
