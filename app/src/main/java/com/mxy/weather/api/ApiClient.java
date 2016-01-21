package com.mxy.weather.api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

/**
 * Created by Mengxy on 16/1/21.
 */
public class ApiClient {
    private static ApiClient sharedInstance = null;
    private AsyncHttpClient client = null;
    private static final String KEY = "4074598ae7ed4cbeb6c2674c54bf0e8a";
    //public static final String BASE_URL = "http://weatherapi.market.xiaomi.com/wtr-v2/weather";
    public static final String BASE_URL = "https://api.heweather.com/x3";

    public static ApiClient shareClient() {
        if (ApiClient.sharedInstance == null) {
            ApiClient.sharedInstance = new ApiClient();
        }
        return ApiClient.sharedInstance;
    }

    public ApiClient() {
        this.client = new AsyncHttpClient();
    }

    public RequestHandle get(String url, RequestParams params, AsyncHttpResponseHandler handler) {
        url = getAbsoluteUrl(url);
        params.put("key", KEY);
        return client.get(url, params, handler);
    }

    private String getAbsoluteUrl(String url) {
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return url;
        }
        return BASE_URL + url;
    }

    public void destory() {
        this.cancelAllRequest();
    }

    private void cancelAllRequest() {
        client.cancelAllRequests(true);
    }
}
