package com.example.traveljournal;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@Suite
@SelectPackages("com.example.traveljournal")
@SpringBootTest
class TravelJournalApplicationTests {

	@Test
	void contextLoads() {
	}

}
