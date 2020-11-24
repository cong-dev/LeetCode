package Thread;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * 交替打印字符串
 * @author 王思聪
 *
 */
class FizzBuzz {
	private int n;
	private int i = 1;
//	Lock lock = new ReentrantLock();
//	Condition fizz = lock.newCondition();
//	Condition buzz = lock.newCondition();
//	Condition fizzbuzz = lock.newCondition();
//	Condition number = lock.newCondition();
	public FizzBuzz(int n) {
		this.n = n;
	}
	
	//如果这个数字可以被 3 整除，输出 "fizz"
	public synchronized void fizz() { 
		while(i<=n) {
			if(i%3==0 && i%5!=0) {
				System.out.println("fizz");
				i++;
			}else {
				this.notifyAll();
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(i%3==0 && i%5!=0) {
					System.out.println("fizz");
					i++;
				}else {
					continue;
				}				
			}	
		}
	}
	
	//如果这个数字可以被 5 整除，输出 "buzz"
	public synchronized void buzz() {
		while(i<=n) {
			if(i%3!=0 && i%5==0) {
				System.out.println("buzz");
				i++;
			}else {
				this.notifyAll();
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(i%3!=0 && i%5==0) {
					System.out.println("buzz");
					i++;
				}else {
					continue;
				}				
			}	
		}
	}
	
	//如果这个数字可以同时被 3 和 5 整除，输出 "fizzbuzz"
	public synchronized void fizzbuzz() {
		while(i<=n) {
			if(i%3==0 && i%5==0) {
				System.out.println("fizzbuzz");
				i++;
			}else {
				this.notifyAll();
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(i%3==0 && i%5==0) {
					System.out.println("fizzbuzz");
					i++;
				}else {
					continue;
				}				
			}	
		}
	}

	public synchronized void number() {	
		while(i<=n) {
			if(i%3!=0 && i%5!=0) {
				System.out.println(i);
				i++;
			}else {
				this.notifyAll();
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(i%3!=0 && i%5!=0) {
					System.out.println(i);
					i++;
				}else {
					continue;
				}				
			}	
		}
	}
}


public class LeetCode2 {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		FizzBuzz fb = new FizzBuzz(15);
		new Thread(()->fb.fizz(),"fizz").start();
		new Thread(()->fb.buzz(),"buzz").start();
		new Thread(()->fb.fizzbuzz(),"fizzbuzz").start();
		new Thread(()->fb.number(),"number").start();

	}

}
