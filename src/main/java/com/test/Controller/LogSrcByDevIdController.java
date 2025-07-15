package com.test.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.api.input.InputForLogSrcByDevId;
import com.test.api.output.DataOutputSrcByDevId;
import com.test.model.entity.LogRecordSrcByDevId;
import com.test.model.service.SearchLogByDevIdService;


@RestController
@RequestMapping("/log/search")
public class LogSrcByDevIdController {
	
	@Autowired
    SearchLogByDevIdService searchUsrByDevinfo ;
    
	@PostMapping("/bydeviceid")
	public ResponseEntity<DataOutputSrcByDevId> searchUsrByDeviceId(@RequestBody InputForLogSrcByDevId request) {
		try {
			String searchHardwareID = request.getSearchHardwareID();
			String fromDate = request.getFromDate();
			String toDate = request.getToDate();
			int rowCount = request.getRowCount();

			DataOutputSrcByDevId dataOutpurSrcByDevId = searchUsrByDevinfo.searchDeviceInfo(searchHardwareID, fromDate, toDate,
					rowCount);
			return ResponseEntity.ok(dataOutpurSrcByDevId);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
    
	/*Payload
	{
		  "searchHardwareID": "52513ab03f6988f5",
		  "fromDate": "01JUN2025",
		  "toDate": "20JUN2025",
		  "rowCount": 10
		}
		
		*/

    


}
