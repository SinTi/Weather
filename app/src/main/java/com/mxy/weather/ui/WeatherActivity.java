package com.mxy.weather.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mxy.weather.R;
import com.mxy.weather.base.BaseAppCompatActivity;
import com.mxy.weather.model.WeatherData;
import com.mxy.weather.model.WeatherResponse;
import cz.msebera.android.httpclient.Header;

import java.util.List;
import java.util.Map;

public class WeatherActivity extends BaseAppCompatActivity {

    public static final String CITY_ID_BEIJING = "CN101010100";

    @InjectView(R.id.toolbar)
    Toolbar mToolBar;
    @InjectView(R.id.text)
    TextView textView;
    @InjectView(R.id.fab)
    FloatingActionButton mFloatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.inject(this);
        setSupportActionBar(mToolBar);

        mFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        initData();
    }

    private void initData() {
        RequestParams params = new RequestParams();
        params.put("cityid", CITY_ID_BEIJING);
        getClient().get("/weather", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                showTips("获取失败");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                onReceiveWeatherResponse(responseString);
            }
        });
    }

    private void onReceiveWeatherResponse(String responseString) {
        Map<String, List<WeatherData>> map = new Gson().fromJson(responseString, new TypeToken<Map<String,
                List<WeatherData>>>(){}.getType());
        if (map != null) {
            WeatherData weatherData = map.get("HeWeather data service 3.0").get(0);
            if (weatherData != null) {
                String text = weatherData.basic.city + ", 更新时间: " + weatherData.basic.update.loc + ", 最高气温: " + weatherData
                        .daily_forecast.get(0)
                        .tmp
                        .max +
                        ", 最低气温: "
                        + weatherData.daily_forecast.get(0).tmp.min + ", pm2.5: " + weatherData.aqi.city.pm25 + ", "
                        + weatherData.aqi.city.qlty;
                textView.setText(text);
                textView.invalidate();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}