package indi.qiaolin.test.interfaces.with.baseclass.inter;


/**
 *  新增电风扇类
 *  @author  qiaolin
 *  @version 2018年4月21日
 */

public class ElectricFan implements Mechanical{
    @Override
    public void open() {
        System.out.println("interface 电风扇开了！");
    }

    @Override
    public void close() {
        System.out.println("interface 电风扇关了！");
    }
}
