package lock;
/**
 * 非线程安全的情况模拟
 * @author bixiao.zy
 *
 */
public class LockTestNo implements Runnable{

	private int number = 0;

	public void run() {
		number++;
	}
	public static void main(String[] args) throws Exception{
		LockTestNo t = new LockTestNo();
		for(int i=0;i<100;i++){
			new Thread(t).start();
		}
		System.out.println(t.number);
	}
	
}
