package lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 非线程安全的情况模拟
 * @author bixiao.zy
 *
 */
public class LockTestLock implements Runnable{

	private int number = 0;
	
	private ReentrantLock lock = new ReentrantLock();

	public void run() {
		lock.lock();
		try{
			number++;
		}finally{
			lock.unlock();
		}
	}
	public static void main(String[] args) throws Exception{
		LockTestLock t = new LockTestLock();
		for(int i=0;i<100;i++){
			new Thread(t).start();
		}
		Thread.sleep(2000);
		System.out.println(t.number);
	}
	
}
