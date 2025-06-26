package com.test.model.service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.model.entity.LogIdOutputSrcByDevice;
import com.test.model.entity.LogRecordOutput;

@Service
public class SearchUsrByDeviceInfo {

    @Autowired
    private DataSource dataSource;

    private static final String newRSALogQuery = 
        "SELECT * FROM ( " +
        "  SELECT * FROM kbz_new_rsa_log WHERE logging_datetime BETWEEN TO_DATE(?, 'DDMONYYYY') AND TO_DATE(?, 'DDMONYYYY') " +
        "  ORDER BY logging_datetime DESC " +
        ") WHERE ROWNUM <= ?";

    public List<LogIdOutputSrcByDevice > searchDeviceInfo(String searchHardwarId ,String fromDate, String toDate, int rowCount) throws Exception {
        String deviceInfo = "";

        List<LogIdOutputSrcByDevice> logRecordList  = new ArrayList<LogIdOutputSrcByDevice>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(newRSALogQuery)) {

            stmt.setString(1, fromDate);
            stmt.setString(2, toDate);
            stmt.setInt(3, rowCount);

            try (ResultSet rs = stmt.executeQuery()) {
            	
                while (rs.next()) {
                    Blob blob = rs.getBlob("deviceinfo");
                    String logIdRefNo= rs.getString("IDREFERENCE") ;
                    if (blob != null) {
                        try (InputStream inputStream = blob.getBinaryStream()) {
                            deviceInfo = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

                            ObjectMapper mapper = new ObjectMapper();
                            Map<String, Object> mapedJson = mapper.readValue(deviceInfo, Map.class);

                            String escapedDevicePrint = (String) mapedJson.get("devicePrint");
                            String unescapedDevicePrint = StringEscapeUtils.unescapeJson(escapedDevicePrint);

                            Map<String, Object> devicePrint = mapper.readValue(unescapedDevicePrint, Map.class);

                            String deviceName = (String)  devicePrint.get("DeviceName") ;
                            String hardwareID = (String) devicePrint.get("HardwareID");
                          
                            System.out.println("DeviceName " + deviceName + "   LogIdRefNo " + logIdRefNo + "   " );
                            System.out.println("HardwareID " + hardwareID + "\n");
                            System.out.flush();  
                            
                            LogIdOutputSrcByDevice logIdOutpuSrcByDevice = new LogIdOutputSrcByDevice();

                            if (hardwareID.equals(searchHardwarId)) {
                            	logIdOutpuSrcByDevice.setIsFound("TRUE");
                                System.out.println("*** Found DeviceName: " + deviceName + " *** HardwareID: " + searchHardwarId + "\n");
                            } 
                            
                            if (searchHardwarId != null) {

                                logIdOutpuSrcByDevice.setLogIdRefNo(logIdRefNo);
                                logIdOutpuSrcByDevice.setDeviceName(deviceName);
                                logIdOutpuSrcByDevice.setHardwareId(hardwareID);
                                
                                logRecordList.add(logIdOutpuSrcByDevice);
                            	

                            }
                            
                            
                            
                        }
                    }
                }
            }
        }
		return logRecordList;


    }
}
