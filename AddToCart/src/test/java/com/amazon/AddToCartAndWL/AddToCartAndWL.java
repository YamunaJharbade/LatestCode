package com.amazon.AddToCartAndWL;


import org.testng.annotations.Test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import org.testng.annotations.Test;

import com.amazon.pages.AddToCartPage;
import com.amazon.pages.AddToWishList;
import com.amazon.pages.LoginPage;
import com.amazon.util.ReadExcel;
import com.amazon.util.WebEventListener;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.relevantcodes.extentreports.LogStatus;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.AfterTest;


public class AddToCartAndWL {
	WebDriver driver;
	public static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	ExtentReports extent;
	ExtentHtmlReporter reporter=new ExtentHtmlReporter("./Reports/ExtentReportDemo.html");
	ExtentTest logger;
	 
	@BeforeSuite
	public void setupSuite() {
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		}

	@BeforeTest
	public void launchBrowser() {
		System.setProperty("webdriver.chrome.driver", "C:\\AddToCartAndWL\\AddToCart\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver(); // Launch the Chrome Browser

		e_driver = new EventFiringWebDriver(driver);
		// create object of EventListener to register it with EventFiringWebDriver
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;

		driver.manage().window().maximize(); // maximize window
		driver.manage().deleteAllCookies(); // delete all the cookies

		// Global wait
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		System.setProperty("org.freemarker.loggerLibrary", "none");
	}

	@BeforeMethod
	public void LaunchURL() {
		driver.get("https://www.amazon.in/");

	}
	 
	// Login to Application
    @Test(priority = 1)
	public void Login() throws Exception {
    	System.out.println("TC01_Login to Amazon");
    	logger = extent.createTest("Login Test");
		logger.log(Status.INFO, "Login to amazon");
		LoginPage LP = new LoginPage(driver);
		LP.Login();
		String actualresult = driver.findElement(By.xpath("//*[@id=\"nav-link-accountList\"]/span[1]")).getText();
		Assert.assertEquals(actualresult, "Hello, Yamuna");
		logger.log(Status.PASS, "Title verified");
		System.out.println("Registered user successfully logged in to the application");
		//captureScreenshot.takeScreenShot("TC_01_Login Test", driver); // take screenshot
	}
 
   /*
	//Test to search the product from 'Shop By Category' and 'Add to Cart'
	@Test(priority=2) 
	public void searchByCategoryCarttest() throws Exception {
		System.out.println("TC02_Verify that registered user is able to Search the product from Category and Add it to cart");
		logger = extent.createTest("Search the Product from Category section and Add to Cart"); 
		logger.log(Status.INFO, "Search the product from 'Shop By Category' and 'Add to Cart'");
		AddToCartPage cart = new AddToCartPage(driver);
	    cart.searchByCategorytest();
	    String pageHeading = cart.AddtoCart();
	    Assert.assertEquals(driver.findElement(By.xpath("//h2[contains(text(), 'Shopping Cart')]")).getText(), pageHeading);
	}

  */
	//Test to search the product from 'Shop By Text' and 'Add to Wishlist'
  	@Test(priority = 3, dataProvider = "getData")
	   public void searchbyTextWLtest(String product) throws Exception {
	    logger = extent.createTest("Search the product from search textbox and Add to Wishlist");
		logger.log(Status.INFO, "Search the product from search texbox and 'Add to Wislist'");
  		System.out.println("TC03_Verify that registered user is able to search the product from search textbox and Add it to Wishlist");	
  		AddToWishList wl = new AddToWishList(driver);
  		wl.searchByText(product);
  		//wl.Wishlist();
  		String[] Result = wl.Wishlist();
  		
  		//String expectedString = driver.findElement(By.xpath("//*[@id=\"WLHUC_result_listName\"]")).getText();
  		Assert.assertTrue(Result[0].contains(Result[1]));

  	}

	
    @DataProvider
   	public Object[][] getData() throws IOException{
   	String currentDirectory = System.getProperty("user.dir");
    String datafile =currentDirectory+ "\\src\\test\\java\\com\\amazon\\testdata\\TestData.xlsx";  
   	System.out.println(datafile);
    Object[][] myTestData = ReadExcel.readTestData(datafile);
    return myTestData;		
   	}

	@AfterMethod
	public void CheckResults(ITestResult Results) {
		if (Results.getStatus() == ITestResult.FAILURE) {
			logger.log(Status.FAIL, "Test case is failed due to below problem");
			logger.log(Status.FAIL, Results.getThrowable());
		} else if (Results.getStatus() == ITestResult.SUCCESS) {
			logger.log(Status.PASS, "Test case is Pass");
		} else {
			logger.log(Status.SKIP, Results.getThrowable());
		}
	
	}
 
	@AfterSuite
	public void teardown() {
		driver.quit();
		extent.flush();
		extent.close();
	}

}
