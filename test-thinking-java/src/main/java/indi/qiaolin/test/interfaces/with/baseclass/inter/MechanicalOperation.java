package indi.qiaolin.test.interfaces.with.baseclass.inter;


/**
 *  机械操作类，用于调用机械的开关方法
 *  @author  qiaolin
 *  @version 2018年4月21日
 */

public class MechanicalOperation {

    public static void executeOpen(Mechanical mechanical){
        mechanical.open();
    }

    public static void executeClose(Mechanical mechanical){
        mechanical.close();
    }
}
