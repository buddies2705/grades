package com.gradefriend.grade;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GradesApplicationTests {


	@Autowired
	EmailService emailService;


	@Test
	@Ignore
	public void contextLoads() {
	}

	@Test
	@Ignore
	public void testEmail(){

		String to = "buddies2705@gmail.com";
		String subject = "This is Test";
		String text ="This is Test";
		emailService.sendSimpleMessage(to , subject , text);
	}

}
