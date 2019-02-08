package nz.ac.massey.cs.sdc.assign1.s16163090;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

// Stores all log entries to a list. Singleton pattern used to 
// ensure only one instance of MemAppender can be created.
// @author David Cockerill
public class MemAppender extends AppenderSkeleton {
	
	private List<LoggingEvent> log;
	
	public static MemAppender defualtInstance = new MemAppender();
	
	public static MemAppender getInstance() {
		return defualtInstance;
	}
	
//	max number of logs in list
	private int maxSize = 100;
	
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

//	Counts discarded logs
	private long discardedLogs = 0;
	
	public long getDiscardedLogs() {
		return discardedLogs;
	}
	
//	by default we init log as arrayList
	private MemAppender() {
		super();
		log = new ArrayList<LoggingEvent>();
	}
	
//	List can be set to other type
	public void setList(List<LoggingEvent> logEvents){
		this.log = logEvents;
	} 
	
	public List<LoggingEvent> getCurrentLog() {
		return log;
	}
	
//	Used to clear log after each test
	public void clear() {
		log.clear();
	}
	
//	Overrides append method to add log to logging event. If log events reach 
//	maxSize oldest ones are removed from list. Discarded logs tallied. 
	@Override
	protected void append(final LoggingEvent loggingEvent) {	
		log.add(loggingEvent);
		
		if(log.size() > maxSize) {
			log.remove(0);
			discardedLogs++;
		}
	}

//	Must implement inherited abstract methods from AppenderSkeleton 
	public void close() {		
	}
	
	public boolean requiresLayout() {
		return false;
	}

}
