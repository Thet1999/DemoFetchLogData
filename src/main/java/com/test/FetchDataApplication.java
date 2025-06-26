package com.test;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.test.model.service.FetchLogDataService;
import com.test.model.service.SearchUsrByDeviceInfo;

@SpringBootApplication
public class FetchDataApplication implements CommandLineRunner {

    private final List<String> refList2 = List.of(
        "001IATR251010410", "016IBTB251010002", "053IATR250980412",
        "123IATR250980229", "229IATR250930124", "230IBTB250930004",
        "347IATR250930121", "079IATR241030006"
    );

    private final List<String> refList = List.of("014IATR250920130");

    @Autowired
    private FetchLogDataService fetchTrsService;

    @Autowired
    private SearchUsrByDeviceInfo searchUsrByDevinfo;

    public static void main(String[] args) {
        SpringApplication.run(FetchDataApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
//            String fromDate = "01JUN2025";
//            String toDate = "20JUN2025";
//            int rowCount = 10;
//
//            String searchHardwareID = "52513ab03f6988f5";  
//
//            Map<String , String > deviceMap = searchUsrByDevinfo.searchDeviceInfo(searchHardwareID,fromDate, toDate, rowCount);
//            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
//	@Override
//	public void run(String... args) {
//		try {
//			//fetchTrsService.fetchAll(refList);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		};
//	}
    
}
