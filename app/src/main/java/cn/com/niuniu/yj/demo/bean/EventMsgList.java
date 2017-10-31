package cn.com.niuniu.yj.demo.bean;

/**
 * <h1>所有EventBus所用到的消息类型的汇总类</h1>
 * <p>这么写其实是因为EventBus耦合度太低，如果过多，
 * 容易造成难以把控，项目复杂加剧。
 *
 * @author: niuniu
 * @date: 2017/10/31.
 */

public class EventMsgList {

    /**
     * GreenDao操作相关的Event
     */
    public static class GreenDaoEvent{
        public String logMsg;

        public GreenDaoEvent(String logMsg) {
            this.logMsg = logMsg;
        }

        public GreenDaoEvent() {

        }
    }

}
