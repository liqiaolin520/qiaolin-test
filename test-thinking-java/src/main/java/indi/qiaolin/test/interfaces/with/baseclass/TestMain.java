package indi.qiaolin.test.interfaces.with.baseclass;

import indi.qiaolin.test.interfaces.with.baseclass.cls.TestCls;
import indi.qiaolin.test.interfaces.with.baseclass.inter.TestInter;
import org.junit.Test;

/**
 *  测试在方法的参数中是应该用父类还是接口做参数。
 *    看这两种方法中哪一种办法更好
 *  @author qiaolin
 *  @version 2018年4月21日
 */

public class TestMain {

    /**
     * 测试方法基于类类型参数
     * 经过测试，使用类类型作为参数的耦合度比较大。
     * 因为如果新加入一个带有机器开关功能的类，例如电风扇。
     * 电风扇就必须继承机器类，否则的话就类型不匹配；继承机械类的话又不能继承其他类了。
     * 使用接口的话就不会有这个问题，一个类实现多个接口，不管新增多少类，只需要实现机械接口。
     */
    @Test
    public void testCls(){
        TestCls.main(new String[10]);
    }

    /**
     *  测试方法基于接口类型参数
     *  基于接口类型参数耦合度比较小。一个类只需要实现指定接口即可。
     *  一个类又可以实现多个接口，相当于多继承一样的效果。
     */
    @Test
    public void testInter(){
        TestInter.main(new String[10]);
    }

}
