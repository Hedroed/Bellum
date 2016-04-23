package debug;

import java.util.ArrayList;

public class Debug {
	
	private long[] times;
	private long current;
	private int i;
	
	public Debug() {
		times = new long[100];
		i = 0;
	}
	
	public void start() {
		current = System.nanoTime();
	}
	
	public void end() {
		addTime((System.nanoTime()-current)/1000000);
	}
	
	public void addTime(long time) {
		times[i] = time;
		i++;
		
		if(i >= 100) {
			printAvg();
			i = 0;
			times = new long[100];
		}
	}
	
	public void printAvg() {
		long sum = 0;
		
		for(long l : times) {
			sum += l;
		}
		
		System.out.println("Temps aproximatif :"+(sum/times.length));
		
	}
	
}