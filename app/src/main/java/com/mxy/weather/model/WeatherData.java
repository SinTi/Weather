package com.mxy.weather.model;


import java.util.List;

/**
 * Created by Mengxy on 16/1/21.
 */
public class WeatherData {
    public Aqi aqi;
    public Basic basic;
    public List<DailyForecast> daily_forecast;
    public List<HourlyForecast> hourly_forecast;
    public Now now;
    public String status;
    public Suggestion suggestion;
}
