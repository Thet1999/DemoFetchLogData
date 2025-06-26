package com.test.Controller;

import org.springframework.web.bind.annotation.*;

import com.test.model.entity.LogRecordOutput;
import com.test.model.service.FetchLogDataService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/test/log")
public class LogRecFetchController {

    @Autowired
    private FetchLogDataService fetchTransService;  
    
    @PostMapping("/fetch")
    public ResponseEntity< List<LogRecordOutput>> fetchLogRecord(@RequestBody Map<String, List<String>> inputHostRef  ) {
        try {
            List<String> hostRefList = inputHostRef.get("hostRefNo");
            List<LogRecordOutput>  output = fetchTransService.fetchAll( hostRefList );
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    
}
