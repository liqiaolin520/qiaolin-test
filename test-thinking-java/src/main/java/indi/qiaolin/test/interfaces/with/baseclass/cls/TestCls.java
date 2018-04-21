package indi.qiaolin.test.interfaces.with.baseclass.cls;

/**
 *  测试方法参数是类的时候会出现的问题。
 *
 *  @author  qiaolin
 *  @version 2018年4月21日
 */

public class TestCls {
    public static void main(String[] args) {
        Mechanical television = new Television();
        Mechanical punch = new Punch();
        MechanicalOperation.executeOpen(television);
        MechanicalOperation.executeOpen(punch);
        MechanicalOperation.executeClose(television);
        MechanicalOperation.executeClose(punch);
        // 新增电风扇时只需要让电风扇继承机械类，这样一个类过于依赖一个类
        MechanicalOperation.executeOpen(new ElectricFan());
        MechanicalOperation.executeClose(new ElectricFan());
    }
}
