package com.test.model.entity;

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
public class LogRecord {
	
	LocalDateTime loggingDateTime;
    Blob deviceInfo;
	String authenticationType ;

}
