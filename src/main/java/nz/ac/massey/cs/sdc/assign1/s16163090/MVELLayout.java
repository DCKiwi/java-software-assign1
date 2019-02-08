package nz.ac.massey.cs.sdc.assign1.s16163090;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.spi.LoggingEvent;
import org.mvel2.templates.TemplateRuntime;

// Uses MVEL2 template engine to override log4j layout class 
// @author David Cockerill
public class MVELLayout extends org.apache.log4j.Layout  {
	
//	MVEL Layout template
	private String template = "@{c} @{d} @{m} @{p} @{t} @{n}";
	
	public void activateOptions() {
	}

//	Log4j Layout class format method overridden. Hash map used to bind logging event
//	objects to MVEL template.
	@Override
	public String format(LoggingEvent event) {
		
		Map<String, Object> bindings = new HashMap<String, Object>();
		bindings.put("c", event.getLogger());
		bindings.put("d", event.getTimeStamp());
		bindings.put("m", event.getMessage());
		bindings.put("p", event.getLevel());
		bindings.put("t", event.getThreadName());
		bindings.put("n", "%n");
		
		String output = (String) TemplateRuntime.eval(template, bindings);
		
		return output;
	}

	@Override
	public boolean ignoresThrowable() {
		return false;
	}
	
}
