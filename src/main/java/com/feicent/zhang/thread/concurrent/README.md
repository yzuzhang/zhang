#Java并发编程：CountDownLatch、CyclicBarrier和 Semaphore
	http://www.importnew.com/21889.html

##线程安全
	如果你的代码在多线程下执行和在单线程下执行永远都能获得一样的结果，那么你的代码就是线程安全的。
	
##CyclicBarrier和CountDownLatch的区别
	两个看上去有点像的类，都在java.util.concurrent下，都可以用来表示代码运行到某个点上，二者的区别在于：
	1. CyclicBarrier的某个线程运行到某个点上之后，该线程即停止运行，直到所有的线程都到达了这个点，所有线程才重新运行；CountDownLatch则不是，某线程运行到某个点上之后，只是给某个数值-1而已，该线程继续运行
	2. CyclicBarrier只能唤起一个任务，CountDownLatch可以唤起多个任务
	3. CyclicBarrier可重用，CountDownLatch不可重用，计数值为0该CountDownLatch就不可再用了