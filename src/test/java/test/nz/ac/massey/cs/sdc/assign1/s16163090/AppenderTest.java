package test.nz.ac.massey.cs.sdc.assign1.s16163090;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import nz.ac.massey.cs.sdc.assign1.s16163090.MVELLayout;
import nz.ac.massey.cs.sdc.assign1.s16163090.MemAppender;

// Tests custom appender and layout classes 
//@author David Cockerill
public class AppenderTest {
	
	private MemAppender memAppender;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BasicConfigurator.configure();
	}

	@Before
	public void setUp() throws Exception {
		memAppender = MemAppender.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		memAppender.clear();
	}
	
// 	Adds three different level logs to event, then checks current log for
//	added events and asserts log size equal to three.
	@Test
	public void testAddThree() {
		
		Logger logger = Logger.getLogger("test");
		logger.addAppender(memAppender);
		memAppender.setLayout(new MVELLayout());
		
		logger.debug("hello");
		logger.error("world");
		logger.warn("foo");
		
		assertEquals(memAppender.getCurrentLog().get(0).getMessage(), "hello");
		assertEquals(memAppender.getCurrentLog().get(1).getMessage(), "world");
		assertEquals(memAppender.getCurrentLog().get(2).getMessage(), "foo");
		assertEquals(memAppender.getCurrentLog().size(), 3);
	}
	
// 	Adds three different level logs to event then checks current log for 
// 	corresponding log levels. 
	@Test
	public void testLogLevel() {
		Logger logger = Logger.getLogger("test");
		logger.addAppender(memAppender);
		memAppender.setLayout(new MVELLayout());
		
		logger.debug("hello");
		logger.error("world");
		logger.warn("foo");
		
		assertEquals(memAppender.getCurrentLog().get(0).getLevel().toString(),"DEBUG");
		assertEquals(memAppender.getCurrentLog().get(1).getLevel().toString(),"ERROR");
		assertEquals(memAppender.getCurrentLog().get(2).getLevel().toString(),"WARN");
	}

// 	Sets maxSize to 200 then adds 250 to logger. Asserts that logger is discarding
//	all logs over maxSize.	
	@Test
	public void testAddLots() {
		
		Logger logger = Logger.getLogger("test");
		logger.addAppender(memAppender);
		memAppender.setLayout(new MVELLayout());
		
		memAppender.setMaxSize(200);
		
		for(int i = 0; i < 250; i++) {
			logger.debug("test add lots" + i);
		}
		
		assertEquals(memAppender.getCurrentLog().size(), 200);
		assertEquals(memAppender.getDiscardedLogs(), 50);
	}
	
//	Sets logger list type to LinkeList, asserts current log is instance of LinkedList.
	@Test
	public void testLinkedList() {
		
		memAppender.setList(new LinkedList<LoggingEvent>());
		
		Logger logger = Logger.getLogger("test");
		logger.addAppender(memAppender);
		
		assertTrue(memAppender.getCurrentLog() instanceof LinkedList<?>);
	}
	
//	Adds new logging event to logger. Asserts that new log event matches layout set out
//	in MVELLayout class.
	@Test
	public void testLayout() {
		
		Logger logger = Logger.getLogger("test");
		logger.addAppender(memAppender);

		LoggingEvent log = new LoggingEvent("org.apache.log4j.Category", logger, Level.DEBUG, "Hello World", null);
		
		String rendered = new MVELLayout().format(log);
		
		String category = log.getLogger().toString();
		long date = log.getTimeStamp();
		Object message = log.getMessage().toString();
		String level = log.getLevel().toString();
		String thread = log.getThreadName().toString();
		
		assertEquals(category +" " + date + " " + message + " " + level + " " + thread + " %n",rendered);	
	}
	
}
