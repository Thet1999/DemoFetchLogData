package com.test.api.input;

public class InputForLogSrcByDevId {
    private String searchHardwareID;
    private String fromDate;
    private String toDate;
    private int rowCount;

     
    public String getSearchHardwareID() {
        return searchHardwareID;
    }
    public void setSearchHardwareID(String searchHardwareID) {
        this.searchHardwareID = searchHardwareID;
    }

    public String getFromDate() {
        return fromDate;
    }
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public int getRowCount() {
        return rowCount;
    }
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }
}
