package edu.ucla.cs.tracetool;

public abstract class Tracer {
	
	protected String traceFile;
	protected TraceService traceService;
	
	protected abstract TraceRecord sample();
	
	public void trace() {
		if (traceService.getTracingApp() != null) {
			saveRecord(sample());
		}		
	}
	
	private void saveRecord(TraceRecord record) {
		FileUtility.writeDataToFile(traceFile, record.toString(), true);
	}

}
