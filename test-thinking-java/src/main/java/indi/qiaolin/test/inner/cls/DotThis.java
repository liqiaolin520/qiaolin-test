package indi.qiaolin.test.inner.cls;

/**
 *  测试 .this
 */

public class DotThis {

    public void print(){
        System.out.println("my is DotThis !");
    }
    public class Inner{
        /**
         *  获取外部类当前对象.
         * @return
         */
        public DotThis getDotThis(){
            return DotThis.this;
        }
    }

    /**
     *  获取内部类实例
     * @return
     */
    public Inner getInner(){
        return new Inner();
    }

}
