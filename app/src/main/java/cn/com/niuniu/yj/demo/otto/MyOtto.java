package cn.com.niuniu.yj.demo.otto;

import com.squareup.otto.Bus;

/**
 * <h1>自定义OTTO类</h1>
 * <p>详细功能说明
 *
 * @author: niuniu
 * @date: 2017/10/31.
 */

public class MyOtto extends Bus {

    private volatile static MyOtto bus;

    private MyOtto (){
    }

    public static MyOtto getInstance() {
        if (bus == null) {
            synchronized (MyOtto.class){
                if(bus == null){
                    bus = new MyOtto();
                }
            }
        }
        return bus;
    }

}
