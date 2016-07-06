package com.h3c.idcloud.infrastructure.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class IdGenerator implements Runnable{
	
	private static Log log = LogFactory.getLog(IdGenerator.class);
	
	public static long baseId;

	public static long maxId=90000000000000000l;
	
	public static IdGenerator createGenerator() {
		return new IdGenerator();
	}
	private IdGenerator() {
	}
	
	public synchronized long generate() {
		if(baseId<System.currentTimeMillis()*1000){
			baseId=System.currentTimeMillis()*1000;
		}
		baseId++;
		return maxId-baseId;
	}
	@Override
	public void run() {
		for(int i=0;i<10;i++){
		   String name=Thread.currentThread().getName();
		   System.out.println(System.currentTimeMillis()+"-"+baseId+"--"+name+"--"+ generate());
	    }
	}

	public static void main(String arg[]){
		IdGenerator idg= IdGenerator.createGenerator();
		IdGenerator idg2= IdGenerator.createGenerator();

		System.out.println("-1-"+Long.MAX_VALUE);
		System.out.println("-2-"+System.currentTimeMillis());
		System.out.println("-3-"+maxId);
		System.out.println("-4-"+idg.generate());

		Thread t1 = new Thread(idg,"t1");
		Thread t1_1 = new Thread(idg,"t1_1");
		Thread t2 = new Thread(idg2,"t2");
		t2.start();
		t1.start();
		try {
			Thread.currentThread().sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		IdGenerator idg3= IdGenerator.createGenerator();
		Thread t3 = new Thread(idg3,"t3");

		t1_1.start();
		t3.start();
	}
}
