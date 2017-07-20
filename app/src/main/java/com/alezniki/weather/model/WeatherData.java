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
    private String description;
    // List Array - clouds object
    private String cloudiness;
    // List Array - wind object
    private String speed;
    private String direction;
    // Raw Date String
    private String formattedDate;
    private String date;
    private String time;
    private String rawDate;

    public WeatherData() {

        //this.formattedDate = rawDateFormatted(formattedDate);
    }

    public String rawDateFormatted(String rawDate) {
        // Convert raw date into formatted date
//        return rawDate;
        return "TODAY";
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(String cloudiness) {
        this.cloudiness = cloudiness;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
//        this.formattedDate = formattedDate;
        this.formattedDate = rawDateFormatted(formattedDate);
    }

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

    public String getRawDate() {
        return rawDate;
    }

    public void setRawDate(String rawDate) {
        this.rawDate = rawDate;
    }
}
