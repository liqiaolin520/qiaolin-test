package indi.qiaolin.test.interfaces.with.baseclass.inter;


/**
 *  测试方法参数是类的时候会出现的问题。
 *
 *  @author  qiaolin
 *  @version 2018年4月21日
 */

public class TestInter {
    public static void main(String[] args) {
        Mechanical television = new Television();
        Mechanical punch = new Punch();
        MechanicalOperation.executeOpen(television);
        MechanicalOperation.executeOpen(punch);
        MechanicalOperation.executeClose(television);
        MechanicalOperation.executeClose(punch);
        // 新增电风扇时只需要让电风扇实现机械接口即可
        MechanicalOperation.executeOpen(new ElectricFan());
        MechanicalOperation.executeClose(new ElectricFan());
    }
}
