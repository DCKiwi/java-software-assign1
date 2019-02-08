package test.nz.ac.massey.cs.sdc.assign1.s16163090;

import java.util.LinkedList;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import nz.ac.massey.cs.sdc.assign1.s16163090.MemAppender;

// Stress tests for appender and layout
// @author David Cockerill
public class StressTest {
		
	private MemAppender memAppender;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BasicConfigurator.configure();
	}
	
	@Before
	public void setUp() throws Exception {
		memAppender = MemAppender.getInstance();
	}
	
// 	Increases maxSize variable then creates large number of log events.
	@Test
	public void stressTestArrayList() {
				
		Logger logger = Logger.getLogger("test");
		logger.addAppender(memAppender);
		
		memAppender.setMaxSize(2000000);
		
		for(int i = 0; i < 1000000000; i++) {
			logger.debug("stress test array list " + i);
		}
		
	}
	
// 	Sets memAppender to LinkedList and increases maxSize variable then 
//	creates large number of log events.  
	@Test
	public void stressTestLinkedList() {
		
		memAppender.setList(new LinkedList<LoggingEvent>());
		
		Logger logger = Logger.getLogger("test");
		logger.addAppender(memAppender);
		
		memAppender.setMaxSize(2000000);
	
		for(int i = 0; i < 1000000000; i++) {
			logger.debug("stress test linked list " + i);
		}
		
	}
	
// 	Creates large number of log events to console. Does not use appender.
	@Test
	public void stressTestConsoleAppender() {
	
		Logger logger = Logger.getLogger("stressTestConsoleAppender");
		
		for(int j = 0; j < 1000000000 ; j++) {
			logger.warn("stress test console " + j);
		}
		
	}
	
// 	Creates large number of log events to file. Does not use appender.
	@Test
	public void stressTestFileAppender() throws Exception {
		
		Logger logger = Logger.getLogger("stressTestFileAppender");
		
		logger.addAppender(new org.apache.log4j.FileAppender(new org.apache.log4j.TTCCLayout(), "logs.txt"));
			
		for(int j = 0; j < 1000000000; j++) {
			logger.warn("stress test file " + j);
		}	
	}
}
