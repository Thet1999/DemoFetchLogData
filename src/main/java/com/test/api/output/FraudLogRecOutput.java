package com.test.api.output;

import java.sql.Blob;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FraudLogRecOutput {
	
	String inputTrsData ;
	LocalDateTime logRecordDateTime;
	int index; 
	String  deviceInfo;
	

}
