package indi.qiaolin.test.interfaces.with.baseclass.cls;

/**
 *  电视机类
 *  @author  qiaolin
 *  @version 2018年4月21日
 */
public class Television extends Mechanical{
       @Override
    public void open() {
        System.out.println("电视机启动了！");
    }

    @Override
    public void close() {
        System.out.println("电视机关闭了！");
    }
}
