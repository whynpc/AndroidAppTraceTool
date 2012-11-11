package edu.ucla.cs.tracetool;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class TraceService extends Service {

	public class ServiceBinder extends Binder {
		public TraceService getService() {
			return TraceService.this;
		}
	}

	private boolean started;
	private boolean threadDisabled;
	private ServiceBinder binder = new ServiceBinder();
	
	

	private long interval = 10000;

	private AppInfo tracingAppInfo;
	
	private List<Tracer> tracers;

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

	public AppInfo getTracingApp() {
		return tracingAppInfo;
	}

	public void setTracingApp(AppInfo tracingApp) {
		this.tracingAppInfo = tracingApp;
	}

	public boolean isStart() {
		return false;
	}

	public void start() {
		started = true;
		Log.d("tracetool", "start service");
	}

	public void stop() {
		started = false;
		Log.d("tracetool", "stop service");
	}

	public void trace() {
		for (Tracer tracer : tracers) {
			tracer.trace();
		}
	}

	@Override
	public void onCreate() {
		Log.d("tracetool", "create service");
		super.onCreate();
		
		tracers = new ArrayList<Tracer>();
		// TODO: add different tracers
		tracers.add(new TrafficTracer(this, "tracetool_traffic.txt"));
		tracers.add(new CpuMemoryTracer(this, "tracetool_cpumemory.txt"));

		threadDisabled = false;
		Thread thread = new Thread() {
			@Override
			public void run() {
				while (!threadDisabled) {					
					try {
						if (started) {
							trace();
						}
						Thread.sleep(interval);
					} catch (Exception e) {
						Log.d("tracetool", "exception: " + e.toString());
					}
				}
			}
		};
		thread.start();
	}

	@Override
	public void onDestroy() {
		Log.d("tracetool", "destroy service");
		super.onDestroy();
		threadDisabled = true;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

}
