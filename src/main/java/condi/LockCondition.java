package condi;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockCondition {
	// 实例化一个锁对象
	final Lock lock = new ReentrantLock();
	// 实例化两个condition
	final Condition notFull = lock.newCondition(); 
	final Condition notEmpty = lock.newCondition();

	final Object[] items = new Object[3];// 初始化一个长度为100的队列
	int putptr, takeptr, count;

	public void put(Object x) throws InterruptedException {
		lock.lock();// 获取锁
		try {
			while (count == items.length){
				notFull.await();// 当计数器count等于队列的长度时，不能在插入，因此等待
			}
			items[putptr] = x; // 将对象放入putptr索引处
			if (++putptr == items.length){
				putptr = 0;// 当索引长度等于队列长度时，将putptr置为0
			}			
			// 原因是，不能越界插入
			++count;// 没放入一个对象就将计数器加1
			notEmpty.signal();// 一旦插入就唤醒取数据线程
		} finally {
			lock.unlock();// 最后释放锁
		}
	}

	public Object take() throws InterruptedException {
		lock.lock();// 获取锁
		try {
			while (count == 0){
				// 如果计数器等于0那么等待
				notEmpty.await();
			}	
			Object x = items[takeptr]; // 取得takeptr索引处对象
			if (++takeptr == items.length){
				takeptr = 0;// 当takeptr达到队列长度时，从零开始取
			}
			--count;// 每取一个讲计数器减1
			notFull.signal();// 枚取走一个就唤醒存线程
			return x;
		} finally {
			lock.unlock();// 释放锁
		}
	}
	public static void main(String[] args) throws Exception {
		LockCondition c = new LockCondition();
		c.put("iamzhongyong");
		c.put("bixiao");
		c.put("zhongyong");
		System.out.println(c.take());
		System.out.println(c.take());
		System.out.println(c.take());
		c.take();

	}
}
