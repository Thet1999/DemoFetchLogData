package com.test;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.test.model.service.FetchLogDataService;

@SpringBootApplication
//(scanBasePackages = { "com.test.t", "com.test.model.service","com.test.security", "com.test.Controller" })
public class FetchDataApplication implements CommandLineRunner {

	private List<String> refList2 = List.of("001IATR251010410", "016IBTB251010002", "053IATR250980412"
			,"123IATR250980229" , "229IATR250930124", "230IBTB250930004", "347IATR250930121"
			,"079IATR241030006" ) ;
	
	private List<String> refList = List.of("014IATR250920130") ;
	
	@Autowired
	private FetchLogDataService fetchTrsService;

	public static void main(String[] args) {
		SpringApplication.run(FetchDataApplication.class, args);
	}
	
//	@Override
//	public void run(String... args) {
//		try {
//			//fetchTrsService.fetchAll(refList);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		};
//	}

	@Override
	public void run(String... args) throws SQLException {
		//fetchTrsService.fetchAll(refList);
		
	}

}
