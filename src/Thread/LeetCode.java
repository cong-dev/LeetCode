package Thread;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 奇偶数打印
 * @author 王思聪
 *
 */
class ZeroEvenOdd {
    private int n;
    Lock lock = new ReentrantLock();
    Condition c0 = lock.newCondition();
    Condition c1 = lock.newCondition();
    Condition c2 = lock.newCondition();
    
    private int flag = 0;
    private int num = 0;
    
    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero() {
    	for(;num<n;) {
    		lock.lock();
    		try {
    			if(flag != 0) {
    				c0.await();
    			}
    			
    			System.out.print(0);
    			num += 1;
    			
    			if(num%2==1) {
    				flag = 1;
    				c1.signal();
    				if(num != n) {
    					c0.await();
    				}
    				  
    			}else {
    				flag = 2;
    				c2.signal();

    				if(num != n) {
    					c0.await();
    				}
    			}
    			
    		} catch (Exception e) {
    			// TODO: handle exception
    		}finally {
    			lock.unlock();
    		}    
    	}    	  
    }

    // 打印偶数
    public void even() {
    	for(;num<=n;) {
    		lock.lock();
    		try {
    			if(flag != 2) {
    				c2.await();
    			}
    			System.out.print(num);
    			
    			flag = 0;
    			c0.signal();
    			if(num+1 >= n) {
    				break;
    			}else {
    				c2.await();
    			}
    		} catch (Exception e) {
    			// TODO: handle exception
    		}finally {
    			lock.unlock();
    		}    
    	}    	
    }

    // 打印奇数
    public void odd() {
    	for(;num<=n;) {
    		lock.lock();
    		try {
    			if(flag != 1) {
    				c1.await();
    			}
    			System.out.print(num);
    			flag = 0;
    			c0.signal();
    			if(num+1 >= n) {
    				break;
    				
    			}else {
    				c1.await();
    			} 			
    		} catch (Exception e) {
    			// TODO: handle exception
    		}finally {
    			lock.unlock();
    		}    
    	}    	
    }
}

public class LeetCode {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ZeroEvenOdd zev = new ZeroEvenOdd(8);
		new Thread(()->zev.zero(),"zero").start();
		new Thread(()->zev.even(),"even").start();
		new Thread(()->zev.odd(),"odd").start();
	}

}
