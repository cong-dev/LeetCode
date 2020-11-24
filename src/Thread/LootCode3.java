package Thread;


/**
 * 交替打印Foobar
 */
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class FooBar {
	private int n;
	private Lock lock = new ReentrantLock();
	private Condition conditionFoo = lock.newCondition();
	private Condition conditionbar = lock.newCondition();

	private int flag = 1;

	FooBar(int n) {
		this.n = n;
	}

	public void foo() {	
		for(int i=0;i<n;i++) {
			lock.lock();
			if(flag != 1) {
				try {
					conditionFoo.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					lock.unlock();
				}
			}else {
				System.out.print("foo");
				flag = 2;
					conditionbar.signal();			
				try {
					if(i != n-1) {
						conditionFoo.await();
					}		
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					lock.unlock();
				}
			}
		}
	}

	public void bar() {
		for(int i=0;i<n;i++) {
			lock.lock();
			if(flag != 2) {
				try {
					conditionbar.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					lock.unlock();
				}
			}else {
				System.out.print("bar");
				flag = 1;
				conditionFoo.signal();
				try {
					if(i != n-1) {
						conditionbar.await();
					}				
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					lock.unlock();
				}
			}
		}
	}
}

public class LootCode3 {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		FooBar fooBar = new FooBar(1);

		new Thread(() -> {
			fooBar.foo();
		}, "foo").start();

		new Thread(() -> {
			fooBar.bar();
		}, "bar").start();
	}

}

