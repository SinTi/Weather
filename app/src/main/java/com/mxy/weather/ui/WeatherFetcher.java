package com.mxy.weather.ui;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mxy.weather.api.ApiClient;
import com.mxy.weather.api.Constants;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Mengxy on 16/4/26.
 */
public class WeatherFetcher {

    public void getWeatherResponse(WeatherCallback callback) {
        RequestParams params = new RequestParams();
        params.put("cityid", Constants.CITY_ID_BEIJING);
        ApiClient.shareClient().get("/weather", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                callback.setFailure();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                callback.setWeatherResponse(responseString);
            }
        });
    }

    public interface WeatherCallback {
        void setFailure();

        void setWeatherResponse(String responseString);
    }
}
