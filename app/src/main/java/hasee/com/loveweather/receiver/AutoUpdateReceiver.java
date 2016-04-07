package hasee.com.loveweather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import hasee.com.loveweather.service.AutoUpdateService;

/**
 * Created by Hasee on 2016/4/7.
 */
public class AutoUpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, AutoUpdateService.class);
        context.startService(i);
    }
}
