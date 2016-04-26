package com.mxy.weather.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.google.android.agera.Observable;
import com.google.android.agera.Updatable;
import com.mxy.weather.R;
import com.mxy.weather.base.BaseAppCompatActivity;
import com.mxy.weather.model.WeatherData;

public class WeatherActivity extends BaseAppCompatActivity implements Updatable {

    @Bind(R.id.toolbar)
    Toolbar mToolBar;
    @Bind(R.id.text)
    TextView textView;
    @Bind(R.id.fab)
    FloatingActionButton mFloatingButton;

    private WeatherRepository mWeatherRepository;

    private Observable mObservable = new Observable() {
        @Override
        public void addUpdatable(@NonNull Updatable updatable) {
            updatable.update();
        }

        @Override
        public void removeUpdatable(@NonNull Updatable updatable) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);
        setSupportActionBar(mToolBar);

        mFloatingButton.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show());
        mWeatherRepository = new WeatherRepository(new WeatherFetcher());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mObservable.addUpdatable(mWeatherRepository);
        mWeatherRepository.addUpdatable(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mObservable.removeUpdatable(mWeatherRepository);
        mWeatherRepository.removeUpdatable(this);
    }

    @Override
    public void update() {
        if (mWeatherRepository.isFailure()) {
            showTips("加载失败");
        } else {
            WeatherData weatherData = mWeatherRepository.get();
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
