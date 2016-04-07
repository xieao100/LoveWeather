package hasee.com.loveweather.utils;

/**
 * 网络连接监听的接口
 * Created by Hasee on 2016/4/7.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
