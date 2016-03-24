package com.mxy.weather.base;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;
import com.mxy.weather.R;
import com.mxy.weather.api.ApiClient;

/**
 * Created by Mengxy on 16/1/21.
 */
public class BaseAppCompatActivity extends AppCompatActivity {
    private ApiClient mClient = null;

    public ApiClient getClient() {
        if (this.mClient != null) {
            return this.mClient;
        }
        synchronized (this) {
            if (this.mClient == null) {
                this.mClient = new ApiClient();
            }
            return this.mClient;
        }
    }

    protected void showTips(String tips) {
        if (TextUtils.isEmpty(tips)) {
            return;
        }

        Toast.makeText(this, tips, Toast.LENGTH_SHORT).show();
    }

    public static void zoomInActivity(Activity activity) {
        activity.overridePendingTransition(R.anim.zoom_in, 0);
    }

    public static void zoomOutActivity(Activity activity) {
        activity.overridePendingTransition(0, R.anim.zoom_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mClient != null) {
            this.mClient.destroy();
        }
    }
}
