package com.carbon.bizdata360.qa.utils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Utilities {
	
	public static final int IMPLICIT_WAIT_TIME = 10;
	public static final int PAGE_WAIT_TIME = 5;
    
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    public static String generateEmailWithTimeStamp() {
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(formatter);
        return "testing_" + timestamp + "@gmail.com";
    }
    
    public static String caputreScreenshot(WebDriver driver,String testName) {
    	File srcScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String destinaitonScreenshotPath = System.getProperty("user.dir") + "\\Screenshot\\" + testName + ".png";

		try {
			org.openqa.selenium.io.FileHandler.copy(srcScreenshot, new File(destinaitonScreenshotPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destinaitonScreenshotPath;
    }
    
    public static String generateChangePasswordWithRandomInt() {
        Random random = new Random();
        int randomInt = random.nextInt(10000); // Generates a random integer up to 9999
        return "Testing@" + randomInt;
    }
    
}