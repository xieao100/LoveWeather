package hasee.com.loveweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import hasee.com.loveweather.utils.HttpCallbackListener;
import hasee.com.loveweather.utils.HttpUtil;
import hasee.com.loveweather.utils.Utility;

/**
 * Created by Hasee on 2016/4/7.
 */
public class AutoUpdateService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //开启子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateWeather();
            }
        }).start();
        //获取系统的事件管理器
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //设定时间
        int anHour = 8 * 60 * 60 * 1000;//8小时的毫秒数
        //获取系统的当前事件加上设定的时间
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        //设定意图，开启服务
        Intent i = new Intent(this,AutoUpdateService.class);
        startService(i);
        //PeddingIntent 把意图交给其它程序处理
        PendingIntent pi = PendingIntent.getBroadcast(this,0,i,0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 更新天气
     */
    private void updateWeather() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherCode = sharedPreferences.getString("weather_code", "");
        String address = "";
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.d("TAG", response);
                Utility.handleWeatherResponse(AutoUpdateService.this, response);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }
}
