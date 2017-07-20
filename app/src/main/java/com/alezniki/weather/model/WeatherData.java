package com.alezniki.weather.model;

/**
 * Created by nikola on 7/20/17.
 */

public class WeatherData {
    // City object
    private String cityName;
    private String country;
    // List Array - main object
    private int temp;
    private int minTemp;
    private int maxTemp;
    private int pressure;
    private int humidity;
    // List Array - weather array
    private String mainWeather;
    private String weatherDescription;
    // List Array - clouds object
    private String clouds;
    // List Array - wind object
    private int windSpeed;
    private int windDirection;
    // Raw Date String
//    private String formattedDate;
    private String date;
    private String time;
//    private String rawDate;

    public WeatherData() {

        //this.formattedDate = rawDateFormatted(formattedDate);
    }

//    public String rawDateFormatted(String rawDate) {
//        // Convert raw date into formatted date
////        return rawDate;
//        return "TODAY";
//    }


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

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
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

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
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

//    public String getFormattedDate() {
//        return formattedDate;
//    }
//
//    public void setFormattedDate(String formattedDate) {
////        this.formattedDate = formattedDate;
//        this.formattedDate = rawDateFormatted(formattedDate);
//    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

//    public String getRawDate() {
//        return rawDate;
//    }
//
//    public void setRawDate(String rawDate) {
//        this.rawDate = rawDate;
//    }

}
