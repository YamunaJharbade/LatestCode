package com.amazon.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class captureScreenshot {

	public static void takeScreenShot(String ScreenshotName, WebDriver driver) {

		TakesScreenshot screenshot = ((TakesScreenshot) driver);

		File scrFile = screenshot.getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(scrFile, new File("./Screenshots/" + ScreenshotName + ".png"));
		} catch (IOException e) {
			System.out.println("Unable to take screenshot " +e.getMessage());
			
		}
	}
}
