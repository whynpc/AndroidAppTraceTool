package edu.ucla.cs.tracetool;

import android.net.TrafficStats;
import android.util.Log;

public class TrafficTracer extends Tracer {

	public TrafficTracer(TraceService traceService, String traceFile) {
		this.traceService = traceService;
		this.traceFile = traceFile;
	}

	@Override
	protected TraceRecord sample() {
		int uid = traceService.getTracingApp().getUid();
		TrafficTraceRecord record = (new TrafficTraceRecord(traceService
				.getTracingApp().getProcessName(), System.currentTimeMillis(),
				TrafficStats.getUidRxBytes(uid),
				TrafficStats.getUidTxBytes(uid)));
		Log.d("tracetool", "Traffic: " + record.toString());
		return record;
	}

}
