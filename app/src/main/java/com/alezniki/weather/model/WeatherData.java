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

    // City and Country
    private String cityName;
    private String country;
    // Temperature
    private int dayTemp;
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

    public WeatherData(String cityName, String country, double dayTemp, double nightTemp, double eveningTemp, double morningTemp, double pressure, int humidity, String mainWeather, String weatherDescription, int clouds, double windSpeed, double windDirection, String date) {
        this.cityName = cityName;
        this.country = country;
        this.dayTemp = (int) dayTemp;
        this.nightTemp = (int) nightTemp;
        this.eveningTemp = (int) eveningTemp;
        this.morningTemp = (int) morningTemp;
        this.pressure = (int) pressure;
        this.humidity = humidity;
        this.mainWeather = mainWeather;
        this.weatherDescription = weatherDescription;
        this.clouds = clouds;
        this.windSpeed = (int) windSpeed;
        this.windDirection = (int) windDirection;
        this.date = date;
    }

    /**
     * Get City Name
     *
     * @return cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Get Country
     *
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Get Day Temp
     *
     * @return dayTemp
     */
    public int getDayTemp() {
        return dayTemp;
    }

    /**
     * Get Night Temp
     *
     * @return nightTemp
     */
    public int getNightTemp() {
        return nightTemp;
    }

    /**
     * Get Evening Temp
     *
     * @return eveningTemp
     */
    public int getEveningTemp() {
        return eveningTemp;
    }

    /**
     * Get Morning Temp
     *
     * @return morningTemp
     */
    public int getMorningTemp() {
        return morningTemp;
    }

    /**
     * Get Pressure
     *
     * @return pressure
     */
    public int getPressure() {
        return pressure;
    }

    /**
     * Get Humidity
     *
     * @return humidity
     */
    public int getHumidity() {
        return humidity;
    }

    /**
     * Get Main Weather
     *
     * @return mainWeather
     */
    public String getMainWeather() {
        return mainWeather;
    }

    /**
     * Get Weather Description
     * @return WeatherDescription
     */
    public String getWeatherDescription() {
        return weatherDescription;
    }

    /**
     * Get Clouds
     *
     * @return clouds
     */
    public int getClouds() {
        return clouds;
    }

    /**
     * Get Wind Speed
     *
     * @return windSpeed
     */
    public int getWindSpeed() {
        return windSpeed;
    }

    /**
     * Get Wind Direction
     *
     * @return windDirection
     */
    public int getWindDirection() {
        return windDirection;
    }

    /**
     * Get Date
     *
     * @return date
     */
    public String getDate() {
        return date;
    }
}
