package edu.ucla.cs.tracetool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.R.integer;
import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

public class CpuMemoryTracer extends Tracer {
	
	private static final int K = 1024;
	
	private Pattern patternCPU = Pattern.compile("\\s*(\\d+)\\s+(\\d+)%\\s+.*");

	public CpuMemoryTracer(TraceService traceService, String traceFile) {
		this.traceService = traceService;
		this.traceFile = traceFile;
	}

	@Override
	protected TraceRecord sample() {		
		CpuMemoryTraceRecord record = new CpuMemoryTraceRecord(traceService
				.getTracingApp().getProcessName(), 0, 0, 0, 0);

		getCpuUsage(record);
		getMemoryUsage(record);
		record.setTimeStamp(System.currentTimeMillis());
		Log.d("tracetool", "CpuMemory: " + record.toString());
		return record;
	}

	private void getCpuUsage(CpuMemoryTraceRecord record) {
		BufferedReader in = null;

		try {
			Process process = null;
			process = Runtime.getRuntime().exec("top -n 1 -d 1");

			in = new BufferedReader(new InputStreamReader(
					process.getInputStream()));

			String line = "";
			while ((line = in.readLine()) != null) {				
				Matcher matcher = patternCPU.matcher(line);
				if (matcher.find()) {
					int pid = Integer.parseInt(matcher.group(1).toString());
					if (pid == traceService.getTracingApp().getPid()) {
						int cpuUsage = Integer.parseInt(matcher.group(2).toString());
						record.setCpuUsage(cpuUsage);
						break;
					} 
				}				
			}
		} catch (IOException e) {			
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {					
					e.printStackTrace();
				}
			}
		}
	}

	private void getMemoryUsage(CpuMemoryTraceRecord record) {
		ActivityManager activityManager = (ActivityManager) traceService
				.getSystemService(Context.ACTIVITY_SERVICE);
		int[] pids = {traceService.getTracingApp().getPid()};
		android.os.Debug.MemoryInfo info = activityManager
				.getProcessMemoryInfo(pids)[0];
		int pss = info.getTotalPss();
		int uss = info.getTotalPrivateDirty();
		record.setPss(pss);
		record.setUss(uss);
	}

}
