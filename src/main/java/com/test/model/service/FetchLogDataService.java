package com.test.model.service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.test.api.output.FraudLogRecOutput;
import com.test.model.entity.FraudLogRecord;

@Service
public class FetchLogDataService {

	@Autowired
	private DataSource dataSource;
 

	// @Value ("${app.password}" )
	private int dbPass;
	private String sql = "SELECT TXNID, IDINITIATOR, DATINITIATION FROM admintxnunauthdata WHERE hostrefrenceno = ?";

	public List<FraudLogRecOutput> fetchAll(List<String> hostRefNList) throws SQLException {
		List<FraudLogRecOutput> logRecOutputList = new ArrayList<FraudLogRecOutput>();
		int i = 1;
		for (String hostRefN : hostRefNList) {
			FraudLogRecOutput logRecordOutput = fetchData(hostRefN);
			logRecordOutput.setIndex(i++);
			logRecOutputList.add(logRecordOutput);
		}
		return logRecOutputList;
	}

 
	public FraudLogRecOutput fetchData(String hostRefN) throws SQLException {
		boolean found = false;
        FraudLogRecOutput logRecordOutput = new FraudLogRecOutput();


		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, hostRefN);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					found = true;
					String txnId = rs.getString("TXNID");
					String idInitiator = rs.getString("IDINITIATOR");
					Timestamp timestamp = rs.getTimestamp("DATINITIATION");
					LocalDateTime dateInitiation = timestamp.toLocalDateTime();

					
					String logRecordOutPut =  " hostRefN: "+ hostRefN + ", txnId: " + txnId + ", idInitiator: " + idInitiator + ", dateInitiation: "
							+ dateInitiation;
					System.out.println( " hostRefN: "+ hostRefN + ", txnId: " + txnId + ", idInitiator: " + idInitiator + ", dateInitiation: "
							+ dateInitiation);

	                java.sql.Date sqlDate = java.sql.Date.valueOf(dateInitiation.toLocalDate());
					//System.out.println(sqlDate);

					String sql2 = "select deviceinfo,LOGGING_DATETIME , AUTHENTICATIONTYPE  from kbz_new_rsa_log where iduser in (?) and"
							+ " trunc(logging_datetime)= (?) and idtxn= ? ORDER BY LOGGING_DATETIME Desc   " ;
					 try (PreparedStatement stmt2 = conn.prepareStatement(sql2)) {

					List<FraudLogRecord> records = new ArrayList<>();
					stmt2.setString(1, idInitiator);
					stmt2.setDate(2, sqlDate);
					stmt2.setString(3, txnId);
					try (ResultSet rs2 = stmt2.executeQuery()) {
					 while (rs2.next()) {
					        Blob blob = rs2.getBlob("deviceinfo");
					        Timestamp rs2Timestamp = rs2.getTimestamp("LOGGING_DATETIME");
					        LocalDateTime rs2DateTime = rs2Timestamp.toLocalDateTime();
					        String authenticationType = rs2.getString("AUTHENTICATIONTYPE"); 
					        
					        String deviceInfoJson = null;
					        if (blob != null) {
					           // try (InputStream inputStream = blob.getBinaryStream()) {
					            //    deviceInfoJson = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
					            records.add(new FraudLogRecord(rs2DateTime,blob,authenticationType));
					            }
					        }
					        
					    }
				     FraudLogRecord nearest = null;
				        long smallestDiffSeconds = Long.MAX_VALUE;

				        LocalDateTime inputDateTime = dateInitiation;  
				        for (FraudLogRecord rec : records) {
				            if (!rec.getLoggingDateTime().isAfter(inputDateTime)) {  
				                long diff = java.time.Duration.between(rec.getLoggingDateTime(), inputDateTime).getSeconds();
				            //   System.out.println(diff);
				            //   System.out.println(smallestDiffSeconds);
						        System.out.println(" Date Time - ---> " + rec.getLoggingDateTime()  );
						        String    recString = "";
				                if (diff < smallestDiffSeconds) {
				                    smallestDiffSeconds = diff;
				                    try (InputStream inputStream = rec.getDeviceInfo().getBinaryStream()) {
						         recString = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8); }
 				                    if (recString.equals("{\"__hashCodeCalc\":false}")) {
 				                    	smallestDiffSeconds = Long.MAX_VALUE;
 				                    	recString = "";
 				                    	continue;
 				                    }
				                    nearest = rec;
				                    break;
				                }
				            }
				        }
				        String deviceInfoJson = null;
				        try (InputStream inputStream = nearest.getDeviceInfo().getBinaryStream()) {
			                deviceInfoJson = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
			          
			            logRecordOutput.setInputTrsData(logRecordOutPut );
			            logRecordOutput.setDeviceInfo(deviceInfoJson);
			            logRecordOutput.setLogRecordDateTime(nearest.getLoggingDateTime());
			                		
 			        System.out.println(" Device Info ---> " + deviceInfoJson + "\n" );
 			        


					  }
					
					
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (!found) {
	        System.out.println("hostRefN: " + hostRefN + " - No transaction found.");
	    }
	}

        return logRecordOutput ;


}
}
