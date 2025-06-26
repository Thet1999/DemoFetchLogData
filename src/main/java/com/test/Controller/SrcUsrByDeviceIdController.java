package com.test.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.model.entity.LogIdOutputSrcByDevice;
import com.test.model.entity.SearchRequestByDeviceId;
import com.test.model.service.SearchUsrByDeviceInfo;


@RestController
@RequestMapping("/log/bydeviceid")
public class SrcUsrByDeviceIdController {
	
	@Autowired
    SearchUsrByDeviceInfo searchUsrByDevinfo ;
    
	@PostMapping("/searchuser")
	public ResponseEntity<List<LogIdOutputSrcByDevice>> searchUsrByDeviceId(@RequestBody SearchRequestByDeviceId request) {
		try {
			String searchHardwareID = request.getSearchHardwareID();
			String fromDate = request.getFromDate();
			String toDate = request.getToDate();
			int rowCount = request.getRowCount();

			List<LogIdOutputSrcByDevice> logInfo = searchUsrByDevinfo.searchDeviceInfo(searchHardwareID, fromDate, toDate,
					rowCount);
			return ResponseEntity.ok(logInfo);
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
