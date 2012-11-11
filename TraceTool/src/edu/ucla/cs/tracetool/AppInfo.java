package edu.ucla.cs.tracetool;

import android.app.ActivityManager;


public class AppInfo {
	
	private int pid;
	private int uid;
	private String processName;
	
	public AppInfo(ActivityManager.RunningAppProcessInfo info) {
		this.pid = info.pid;
		this.uid = info.uid;
		this.processName = info.processName;
	}
	
	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	@Override
	public String toString() {
		return processName + " UID=" + uid + " PID=" + pid;
	}
	

}
