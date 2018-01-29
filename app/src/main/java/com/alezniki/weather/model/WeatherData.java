package com.alezniki.weather.model;

import java.io.Serializable;

/**
 * Weather data entity
 * <p>
 * Created by nikola aleksic on 7/20/17.
 */
public class WeatherData implements Serializable {

    /**
     * Clear sky
     */
    public static final String WEATHER_DESCRIPTION_CLEAR_SKY = "Clear";

    /**
     * Clouds
     */
    public static final String WEATHER_DESCRIPTION_CLOUDS = "Clouds";

    /**
     * Rain
     */
    public static final String WEATHER_DESCRIPTION_RAIN = "Rain";

    /**
     * Thunderstorm
     */
    public static final String WEATHER_DESCRIPTION_THUNDERSTORM = "Thunderstorm";

    /**
     * Snow
     */
    public static final String WEATHER_DESCRIPTION_SNOW = "Snow";

    /**
     * Mist
     */
    public static final String WEATHER_DESCRIPTION_MIST = "Mist";

    /**
     * City name
     */
    private String cityName;

    /**
     * Country
     */
    private String country;

    /**
     * Day temperature
     */
    private int dayTemp;

    /**
     * Night temperature
     */
    private int nightTemp;

    /**
     * Evening temperature
     */
    private int eveningTemp;

    /**
     * Morning temperature
     */
    private int morningTemp;

    /**
     * Pressure
     */
    private int pressure;

    /**
     * Humidity
     */
    private int humidity;

    /**
     * Main weather
     */
    private String mainWeather;

    /**
     * Weather description
     */
    private String weatherDescription;

    /**
     * Clouds
     */
    private int clouds;

    /**
     * Wind speed
     */
    private int windSpeed;

    /**
     * Wind direction
     */
    private int windDirection;

    /**
     * String
     */
    private String date;

    /**
     * Constructor
     *
     * @param cityName           city name
     * @param country            country
     * @param dayTemp            day temperature
     * @param nightTemp          night temperature
     * @param eveningTemp        evening temperature
     * @param morningTemp        morning temperature
     * @param pressure           pressure
     * @param humidity           humidity
     * @param mainWeather        main weather
     * @param weatherDescription weather description
     * @param clouds             clouds
     * @param windSpeed          wind speed
     * @param windDirection      wind direction
     * @param date               date
     */
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
     *
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
