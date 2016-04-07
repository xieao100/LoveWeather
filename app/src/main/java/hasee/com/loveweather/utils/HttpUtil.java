package hasee.com.loveweather.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 连接服务器的工具类
 * Created by Hasee on 2016/4/7.
 */
public class HttpUtil {
    public static void sendHttpRequest(final String address, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine())!=null){
                        response.append(line);
                    }
                    if (listener!=null){
                        //回调onFinish（）方法
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                   if (listener!=null){
                       //回调onError方法
                       listener.onError(e);
                   }
                }finally {
                    //关闭掉连接
                    if (connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
