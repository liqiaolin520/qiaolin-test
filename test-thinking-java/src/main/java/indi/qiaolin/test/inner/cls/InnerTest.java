package indi.qiaolin.test.inner.cls;

import org.junit.Test;

/**
 *  内部类测试
 *  @author  qiaolin
 *  @version  2018年5月3日
 *
 */

public class InnerTest {

    /**
     *  测试 .this 关键字 ，此语法可以获取当前内部类所处的外部类的当前对象
     */
    @Test
    public void test1(){
        /** 创建外部类对象 */
        DotThis dotThis = new DotThis();
        /** 获取内部类对象 */
        DotThis.Inner inner = dotThis.getInner();
        /** 获取内部类当前所处的外部类对象 */
        DotThis dotThis1 = inner.getDotThis();
        /** 验证 inner 的外部类对象是否就是创建他的哪个外部类对象 */
        System.out.println("是否为一个对象 ——> " + (dotThis == dotThis1));

        /** 测试创建多个内部类对象，看他们是否是一个！ */
        DotThis.Inner inner1 = dotThis.getInner();
        System.out.println("同一个外部类创建的内部类对象是否相等 ——> " + (inner == inner1));

        /** 测试创建的多个内部类对象他们的外部类对象是否相等！ */
        System.out.println("同一个外部类创建的内部类返回的外部类对象是否相等 ——> " + (dotThis1 == inner1.getDotThis()));
    }

    /**
     *  测试 .new 关键字 ，此语法用于外部类对象创建内部类对象
     */
    @Test
    public void test2(){
        DotThis dotThis = new DotThis();
        DotThis.Inner inner = dotThis.new Inner();
        System.out.println(dotThis);
        System.out.println(inner);
    }

}
