package com.example.traveljournal;

import com.example.traveljournal.controller.LocationControllerTest;
import com.example.traveljournal.domain.LocationTest;
import com.example.traveljournal.service.LocationServiceImplTest;
import com.example.traveljournal.validation.ValidDateValidatorTest;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
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
