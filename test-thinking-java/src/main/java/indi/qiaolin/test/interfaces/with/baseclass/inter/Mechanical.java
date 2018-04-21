package indi.qiaolin.test.interfaces.with.baseclass.inter;

/**
 * 机械接口，声明机械的开机、关机方法，用于减少耦合度。
 * @author qiaolin
 * @version 2018年4月21日
 */

public interface Mechanical {

    /**
     *  机械开机
     */
    void open();

    /**
     * 机械关机
     */
    void close();
}
