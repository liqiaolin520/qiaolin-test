package indi.qiaolin.test.object.clean;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试finalize方法 
 *  finalize是Object超类的一个方法，用于对象清理(垃圾回收器执行)时调用。
 * @author qiaolin
 * @date 2018年4月6日
 **/

@Slf4j
public class FinalizeTest {
    private boolean checkOut;
    
	public FinalizeTest(boolean checkOut) {
		this.checkOut = checkOut;
	}

	public void checkIn() {
		this.checkOut = false;
	}
	
	/**
	 *  对象销毁前调用
	 */
	@Override
	protected void finalize() throws Throwable {
		if(checkOut) {
			log.debug("每一个对象的都应该被签入(checkOut == false)");
		}else {
			log.debug("已正常签入！");
		}
		super.finalize();
	}
	
	/**
	 *  需求：测试调用GC的时候是否会调用 finalize
	 *  测试结果： 
	 *    1、当创建几个对象调用GC时，并不会去调用finalize(因此测试出gc不是调用了就立马执行)，
	 *    `所以我加了for循环
	 *    2、当对象数量到一定数量时(分配大量的内存空间)我们不调用Gc，他也会自己执行。
	 * @param args
	 */
	public static void main(String[] args) {
		for (int i = 0; i < 100000000; i++) {
		
			// 已被检入
			FinalizeTest finalizeTest = new FinalizeTest(true);
			finalizeTest.checkIn();
			
			// 未检入
			new FinalizeTest(true);
			
			// 调用GC
			// System.gc();		
		}
	}
}
