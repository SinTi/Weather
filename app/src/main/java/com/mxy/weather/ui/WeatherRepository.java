package com.mxy.weather.ui;

import android.support.annotation.NonNull;
import com.google.android.agera.BaseObservable;
import com.google.android.agera.Supplier;
import com.google.android.agera.Updatable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mxy.weather.api.Constants;
import com.mxy.weather.model.WeatherData;

import java.util.List;
import java.util.Map;

/**
 * Created by Mengxy on 16/4/26.
 */
public class WeatherRepository extends BaseObservable implements Supplier<WeatherData>, Updatable, WeatherFetcher
        .WeatherCallback{
    private WeatherData mWeatherData;

    private boolean lastRefreshError;

    private final WeatherFetcher mWeatherFetcher;

    public WeatherRepository(WeatherFetcher weatherFetcher) {
        super();
        this.mWeatherFetcher = weatherFetcher;
    }


    @NonNull
    @Override
    public WeatherData get() {
        return mWeatherData;
    }

    public boolean isFailure() {
        return lastRefreshError;
    }

    @Override
    public void update() {
        mWeatherFetcher.getWeatherResponse(this);
    }

    @Override
    public void setFailure() {
        lastRefreshError = true;
        dispatchUpdate();
    }

    @Override
    public void setWeatherResponse(String responseString) {
        Map<String, List<WeatherData>> map = new Gson().fromJson(responseString, new TypeToken<Map<String,
                List<WeatherData>>>(){}.getType());
        if (map != null) {
            lastRefreshError = false;
            this.mWeatherData = map.get(Constants.WEATHER_DATA_KEY).get(0);
        } else {
            lastRefreshError = true;
        }
        dispatchUpdate();
    }
}
