package com.test.model.entity;


public class LogRecordSrcByDevId {
    private String logIdRefNo;
    private String deviceName;
    private String hardwareId;
    private Boolean isFound ;
    //  
    public LogRecordSrcByDevId(String logIdRefNo, String deviceName, String hardwareId , Boolean isFound) {
        this.logIdRefNo = logIdRefNo;
        this.deviceName = deviceName;
        this.hardwareId = hardwareId;
        this.isFound = isFound ;
    }
    
    

     public Boolean getIsFound() {
		return isFound;
	}



	public void setIsFound(Boolean isFound) {
		this.isFound = isFound;
	}



	public LogRecordSrcByDevId() {
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

 

	
}
