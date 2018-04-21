package indi.qiaolin.test.interfaces.with.baseclass.inter;

/**
 * 冲床类
 */

public class Television implements Mechanical {
    @Override
    public void open() {
        System.out.println("interface 冲床开机！");
    }

    @Override
    public void close() {
        System.out.println("interface 冲床关机！");
    }
}
