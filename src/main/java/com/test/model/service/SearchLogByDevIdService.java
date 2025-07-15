package com.test.model.service;

import java.io.ByteArrayOutputStream;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.api.output.DataOutputSrcByDevId;
import com.test.api.output.FraudLogRecOutput;
import com.test.model.entity.LogRecordSrcByDevId;

@Service
public class SearchLogByDevIdService {

    @Autowired
    private DataSource dataSource;

	private Boolean TRUE;

    private static final String newRSALogQuery = 
        "SELECT * FROM ( " +
        "  SELECT * FROM kbz_new_rsa_log WHERE logging_datetime BETWEEN TO_DATE(?, 'DDMONYYYY') AND TO_DATE(?, 'DDMONYYYY') " +
        "  ORDER BY logging_datetime DESC " +
        ") WHERE ROWNUM <= ?";

	private static final Logger logger = LoggerFactory.getLogger(SearchLogByDevIdService.class);

 
    public  DataOutputSrcByDevId searchDeviceInfo(String searchHardwarId ,String fromDate, String toDate, int rowCount) throws Exception {
        String deviceInfo = "";

        System.out.println(" aaaa: ");
        		
        List<LogRecordSrcByDevId> logRecordList  = new ArrayList<LogRecordSrcByDevId>();
        List<LogRecordSrcByDevId> foundLogRecordList  = new ArrayList<LogRecordSrcByDevId>();

        DataOutputSrcByDevId  dataOutputSrcByDevId= new DataOutputSrcByDevId() ;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(newRSALogQuery)) {

            stmt.setString(1, fromDate);
            stmt.setString(2, toDate);
            stmt.setInt(3, rowCount);

            try (ResultSet rs = stmt.executeQuery()) {
            	int foundDevIdCount = 0 ;
                while (rs.next()) {
                    Blob blob = rs.getBlob("deviceinfo");
                    String logIdRefNo= rs.getString("IDREFERENCE") ;
                    if (blob != null) {
                        try (InputStream inputStream = blob.getBinaryStream()) {
                        	
                        	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        	byte[] buffer = new byte[1024]; // Read in 1KB chunks
                        	int bytesRead;
                        	while ((bytesRead = inputStream.read(buffer)) != -1) {
                        	    outputStream.write(buffer, 0, bytesRead);
                        	}
//                        	deviceInfo = outputStream.toString(StandardCharsets.UTF_8);
//                            deviceInfo = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                        	deviceInfo = outputStream.toString(StandardCharsets.UTF_8.name());
                            
                            ObjectMapper mapper = new ObjectMapper();
                            Map<String, Object> mapedJson = mapper.readValue(deviceInfo, Map.class);

                            String escapedDevicePrint = (String) mapedJson.get("devicePrint");
                            String unescapedDevicePrint = StringEscapeUtils.unescapeJson(escapedDevicePrint);

                            Map<String, Object> devicePrint = mapper.readValue(unescapedDevicePrint, Map.class);

                            String deviceName = (String)  devicePrint.get("DeviceName") ;
                            String hardwareID = (String) devicePrint.get("HardwareID");
                          
                            System.out.println("DeviceName " + deviceName + "   LogIdRefNo " + logIdRefNo + "   " );
                            System.out.println("HardwareID " + hardwareID + "\n");


                            logger.info("Thread: {}", Thread.currentThread().getName());
                            logger.info("✅ Found DeviceName: {}   LogIdRefNo: {}", deviceName, logIdRefNo);
                            logger.info("✅ HardwareID: {}", hardwareID);
                            logger.info("DeviceName: {}   LogIdRefNo: {}", deviceName, logIdRefNo);

                        	dataOutputSrcByDevId.setFoundDeviceIdCount(  foundDevIdCount  );

                            LogRecordSrcByDevId logIdOutpuSrcByDevice = new LogRecordSrcByDevId();

                            if (hardwareID.equals(searchHardwarId)) {
                            	logIdOutpuSrcByDevice.setIsFound(Boolean.TRUE);
                            	foundDevIdCount ++ ;
                            	dataOutputSrcByDevId.setFoundDeviceIdCount(  foundDevIdCount  );
                               // System.out.println("*** Found DeviceName: " + deviceName + " *** HardwareID: " + searchHardwarId + "\n");
                            } 
                            
                            if (searchHardwarId != null) {

                                logIdOutpuSrcByDevice.setLogIdRefNo(logIdRefNo);
                                logIdOutpuSrcByDevice.setDeviceName(deviceName);
                                logIdOutpuSrcByDevice.setHardwareId(hardwareID);
                                
                                logRecordList.add(logIdOutpuSrcByDevice);
                            	

                            }
                            
                            if( hardwareID.equals(searchHardwarId) ) {
                            	foundLogRecordList.add(logIdOutpuSrcByDevice);
                            	dataOutputSrcByDevId.setFoundLogRecordList(foundLogRecordList);
                            }
                            
                            
                        }
                    }
                }
            }
            
            dataOutputSrcByDevId.setLogOutputForSrcByDevId(logRecordList);
        }
		return dataOutputSrcByDevId;


    }
}
