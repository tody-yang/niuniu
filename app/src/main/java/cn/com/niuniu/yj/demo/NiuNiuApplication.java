package cn.com.niuniu.yj.demo;

import android.app.Application;
import android.util.Log;

/**
 * <h1>Application入口</h1>
 * <p>整个程序的Application入口
 *
 * @author: niuniu
 * @date: 2017/10/10.
 */

public class NiuNiuApplication extends Application {

    /**
     * NiuNiuApplication对象
     */
    private static NiuNiuApplication mApplication;




    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("NiuNiuApplication","-------onCreate----");
    }

    /**
     * 获取NiuNiuApplication
     * @return NiuNiuApplication
     */
    public static NiuNiuApplication getApplication() {
        if (mApplication == null) {
            //保证异步处理安全操作
            synchronized (NiuNiuApplication.class) {
                if (mApplication == null) {
                    mApplication = new NiuNiuApplication();
                }
            }
        }
        return mApplication;
    }


}
