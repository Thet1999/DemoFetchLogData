package com.test.api.output;
import com.test.model.entity.LogRecordSrcByDevId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataOutputSrcByDevId {
	
	Integer foundDeviceIdCount ;
	List<LogRecordSrcByDevId> foundLogRecordList  = new ArrayList<>();
	List<LogRecordSrcByDevId> LogOutputForSrcByDevId  = new ArrayList<>();

}
