package edu.ucla.cs.tracetool;


public class CpuMemoryTraceRecord extends TraceRecord {

	private int cpuUsage;
	public int getCpuUsage() {
		return cpuUsage;
	}

	public void setCpuUsage(int cpuUsage) {
		this.cpuUsage = cpuUsage;
	}

	public int getUss() {
		return uss;
	}

	public void setUss(int uss) {
		this.uss = uss;
	}

	public int getPss() {
		return pss;
	}

	public void setPss(int pss) {
		this.pss = pss;
	}

	private int uss; // unique memory size; pages unique to a process
	private int pss; // memory shared size	

	public CpuMemoryTraceRecord(String appName, long timeStamp, int cpuUsage,
			int uss, int pss) {
		this.appName = appName;
		this.timeStamp = timeStamp;
		this.cpuUsage = cpuUsage;
		this.uss = uss;
		this.pss = pss;
	}

	@Override
	public String toString() {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(appName);
		sBuffer.append('\t');
		sBuffer.append(timeStamp);
		sBuffer.append('\t');
		sBuffer.append(cpuUsage);
		sBuffer.append('\t');
		sBuffer.append(uss);
		sBuffer.append('\t');
		sBuffer.append(pss);
		sBuffer.append('\n');
		return sBuffer.toString();
	}

}
