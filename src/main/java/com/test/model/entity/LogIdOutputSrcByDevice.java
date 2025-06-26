package com.test.model.entity;


public class LogIdOutputSrcByDevice {
    private String logIdRefNo;
    private String deviceName;
    private String hardwareId;
    private String isFound = "_";
    //  
    public LogIdOutputSrcByDevice(String logIdRefNo, String deviceName, String hardwareId) {
        this.logIdRefNo = logIdRefNo;
        this.deviceName = deviceName;
        this.hardwareId = hardwareId;
    }

     public LogIdOutputSrcByDevice() {
		// TODO Auto-generated constructor stub
	}

	public String getLogIdRefNo() {
        return logIdRefNo;
    }

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getHardwareId() {
		return hardwareId;
	}

	public void setHardwareId(String hardwareId) {
		this.hardwareId = hardwareId;
	}

	public void setLogIdRefNo(String logIdRefNo) {
		this.logIdRefNo = logIdRefNo;
	}

	public String getIsFound() {
		return isFound;
	}

	public void setIsFound(String isFound) {
		this.isFound = isFound;
	}

	
}
