package indi.qiaolin.test.interfaces.with.baseclass.inter;


/**
 * 压力机类
 *
 * @author qiaolin
 * @version 2018年4月21日
 */

public class Punch implements Mechanical {
    @Override
    public void open() {
        System.out.println("interface 冲床启动了！");
    }

    @Override
    public void close() {
        System.out.println("interface 冲床关闭了！");
    }
}
