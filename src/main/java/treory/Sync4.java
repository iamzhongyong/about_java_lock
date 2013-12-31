package treory;

import com.sun.tools.classfile.StackMap_attribute.stack_map_frame;

/**
Java中的每一个对象都可以作为锁。
对于同步方法，锁是当前实例对象。
对于静态同步方法，锁是当前对象的Class对象。
对于同步方法块，锁是Synchonized括号里配置的对象。
 *
 */
public class Sync4 implements Runnable{

	private static int number = 0;

	private synchronized static void addCount(){
		System.out.println(Thread.currentThread().getName()+"--"+"进入被锁住的static方法");
		number++;
	}
	private static void noSyncMethod(){
		System.out.println(Thread.currentThread().getName()+"--"+"类中，没有被锁的静态方法");
	}
	public void run() {
		for(int i=0;i<2;i++){
			noSyncMethod();
			addCount();
		}
	}
	public static void main(String[] args) throws Exception{
		Sync4 s = new Sync4();
		for(int i=0;i<3;i++){
			new Thread(s).start();
		}
		Thread.sleep(3000);
		System.out.println(s.number);
	}
	
		
}
