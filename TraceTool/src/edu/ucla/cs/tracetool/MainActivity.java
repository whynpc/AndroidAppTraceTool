package edu.ucla.cs.tracetool;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.AndroidCharacter;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends FragmentActivity {

	private ListView appListView;
	private TextView tracingAppTextView;
	private ToggleButton serviceToggleButton;
	BaseAdapter appListAdapter;
	List<AppInfo> appInfos;
	
	TraceService traceService;

	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			traceService = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			traceService = ((TraceService.ServiceBinder) service).getService();
			if (traceService.isStart()) {
				serviceToggleButton.setChecked(true);
				tracingAppTextView.setText("Trace: "
						+ traceService.getTracingApp().getProcessName());
			} else {
				serviceToggleButton.setChecked(false);
			}

			serviceToggleButton
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							if (isChecked) {
								traceService.start();
							} else {
								traceService.stop();
							}
						}
					});
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.tracingAppTextView = (TextView) this
				.findViewById(R.id.tracingAppTextView);

		this.appListView = (ListView) this.findViewById(R.id.appListView);
		appInfos = new ArrayList<AppInfo>();
		refreshAppInfos();
		appListAdapter = new ArrayAdapter<AppInfo>(this,
				android.R.layout.simple_list_item_1, appInfos);
		appListView.setAdapter(appListAdapter);

		appListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				AppInfo tracingAppInfo = appInfos.get((int) arg3);
				if (traceService != null) {
					traceService.setTracingApp(tracingAppInfo);
				}
				tracingAppTextView.setText("Tracing: "
						+ tracingAppInfo.getProcessName());
			}
		});

		this.serviceToggleButton = (ToggleButton) this
				.findViewById(R.id.serviceToggleButton);

		this.bindService(new Intent("TraceService"), this.serviceConnection,
				BIND_AUTO_CREATE);

	}

	@Override
	public void onDestroy() {
		if (traceService != null) {
			this.unbindService(serviceConnection);
		}
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void refreshAppInfos() {
		ActivityManager activityManager = (ActivityManager) this
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> rAppInfos = activityManager
				.getRunningAppProcesses();

		appInfos.clear();
		for (ActivityManager.RunningAppProcessInfo rAppInfo : rAppInfos) {
			appInfos.add(new AppInfo(rAppInfo));
		}
		if (appListAdapter != null)
			appListAdapter.notifyDataSetChanged();
	}

	public void onClickRefresh(View view) {
		refreshAppInfos();
	}
	
	public void onClickSetInterval(View view) {
		IntervalSettingDialogFragment dialogFragment = new IntervalSettingDialogFragment();
		dialogFragment.show(this.getSupportFragmentManager(), "dialog");
	}
	
	public long getInterval() {
		if (traceService != null) {
			return traceService.getInterval();
		}
		return 10000;
	}
	
	public void setInterval(long interval) {
		if (traceService != null) {
			traceService.setInterval(interval);
		}
	}

}
