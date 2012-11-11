package edu.ucla.cs.tracetool;

public class TrafficTraceRecord extends TraceRecord {
	private long rxBytes;
	private long txBytes;

	public TrafficTraceRecord(String appName, long timeStamp, long rxBytes,
			long txBytes) {
		this.appName = appName;
		this.timeStamp = timeStamp;
		this.rxBytes = rxBytes;
		this.txBytes = txBytes;

	}

	public long getRxBytes() {
		return rxBytes;
	}

	public void setRxBytes(long rxBytes) {
		this.rxBytes = rxBytes;
	}

	public long getTxBytes() {
		return txBytes;
	}

	public void setTxBytes(long txBytes) {
		this.txBytes = txBytes;
	}

	@Override
	public String toString() {
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append(appName);
		sBuffer.append('\t');
		sBuffer.append(timeStamp);
		sBuffer.append('\t');
		sBuffer.append(rxBytes);
		sBuffer.append('\t');
		sBuffer.append(txBytes);
		sBuffer.append('\n');
		return sBuffer.toString();
	}

}
