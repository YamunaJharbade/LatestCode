package com.amazon.pages;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ThreadFactory;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

//import com.util.captureScreenshot;

public class AddToWishList {

	private WebDriver driver;
	String actualString, expectedString;

	
	//Locators for Add to WishList
	@FindBy(name = "dropdown_selected_size_name") private WebElement Dropdown;
	@FindBy(xpath = "//input[@id='add-to-wishlist-button-submit']") private WebElement AddToWishListButton;
	
	
	//Locators for product search from 'SearchByText'
		@FindBy(xpath="//*[@id='twotabsearchtextbox']")private WebElement SearchItem;
		@FindBy(xpath="//*[@value='Go']")private WebElement Go;
	   
	
	public void searchByText(String product) throws Exception{
	    SearchItem.sendKeys(product);
		Go.click();
		Thread.sleep(10000);
		driver.findElement(By.xpath("//*[@id=\"search\"]//h2/a[1]")).click();
		//SearchedProduct.click();
		}
	

	public AddToWishList(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public String[] Wishlist() throws Exception {
		String MainWindow = driver.getWindowHandle();
		String[] Result = new String[2];
		// To handle the new open window
		Set<String> handler = driver.getWindowHandles();
		Iterator<String> it = handler.iterator();

		while (it.hasNext()) {
			String childwindowid = it.next();
			if (!MainWindow.equalsIgnoreCase(childwindowid)) {
				// Switching to Child window
				driver.switchTo().window(childwindowid);

				try {
					WebElement selectdd = driver.findElement(By.xpath("//*[@id=\"native_dropdown_selected_size_name\"]"));
					selectdd.isDisplayed();
					System.out.println("Size selected from the Size dropdown");
					Select dropdown = new Select(Dropdown);
					dropdown.selectByIndex(2);
					Thread.sleep(1000);
					
				} catch (Exception e) {
					System.out.println("Size is not required for this product");
				}
				 	  
				try {
					Select SelectQuantity = new Select(driver.findElement(By.id("quantity")));
					SelectQuantity.selectByIndex(0);
					//captureScreenshot.takeScreenShot("TC_03_Select the product to add to Wishlist", driver); // take screenshot
				} catch (Exception e) {
					System.out.println("Unable to select the quantity");
				}

	            //Add to WishList
				AddToWishListButton.click();
				//captureScreenshot.takeScreenShot("TC_03_Product added to Wishlist", driver); // take screenshot
				actualString = driver.findElement(By.xpath("//*[@id=\"WLHUC_result_success\"]/div")).getText();
				expectedString = driver.findElement(By.xpath("//*[@id=\"WLHUC_result_listName\"]")).getText();
				
				
				Result[0]=actualString;
				Result[1]=expectedString;
				
				Thread.sleep(1000);
				driver.findElement(By.xpath("//span[text()='View Wish List']")).click();
				Thread.sleep(1000);
				
				driver.navigate().refresh();
				driver.close(); // Close the child window
			}
		}
	// switch back to parent window
	driver.switchTo().window(MainWindow);
	driver.navigate().refresh();
	return Result;
}
}

