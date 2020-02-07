package com.amazon.pages;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

//import com.aventstack.extentreports.Status;
//import com.util.captureScreenshot;

public class AddToCartPage {

	private WebDriver driver;
	
	//Product search from 'Shop By Category'option
	@FindBy(xpath = "//i[@class='hm-icon nav-sprite']") private WebElement CategoryMenu;
	@FindBy(xpath = "//*[@id=\"hmenu-content\"]/ul/li/a[@data-menu-id ='10']") private WebElement SelectCategory;
	@FindBy(xpath = "//*[@id=\"hmenu-content\"]/ul[9]/li[4]/a/div") private WebElement Option;
	@FindBy(xpath = "//span[text()= 'Top Brands']")	private WebElement AmazonFashionfilter;

	// PriceFilters
	@FindBy(id = "low-price") private WebElement LowPriceFilter;
	@FindBy(id = "high-price") private WebElement HighPriceFilter;
	@FindBy(xpath = "//span[@id='a-autoid-1']") private WebElement GoButton;
	
	//Sort by filter
	@FindBy(xpath = "//*[@id ='s-result-sort-select']")
	private WebElement SortDropdown;
	
	// select the product to add to cart
	@FindBy(xpath = "//img[@data-image-index ='4']")
	private WebElement SelectProduct;
	
	
	//Locators for Add to Cart
	@FindBy(name = "dropdown_selected_size_name") private WebElement Dropdown;
	@FindBy(xpath = "//input[@id='add-to-cart-button']") private WebElement AddToCartBtn;
	
	//Locator to view the Shopping cart by clicking on 'Cart' button
	@FindBy(xpath = "//*[@id='hlb-view-cart-announce']") private WebElement CartBtn;

	public AddToCartPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void searchByCategorytest() throws Exception {
		CategoryMenu.click();
     	SelectCategory.click();
		Option.click();
		Thread.sleep(1000);
		AmazonFashionfilter.click();
		Thread.sleep(1000);
		LowPriceFilter.sendKeys("1000");
		HighPriceFilter.sendKeys("2000");
		GoButton.click();
		//captureScreenshot.takeScreenShot("TC03_Search Product from Shop By Category Panel", driver); // take screenshot
		Thread.sleep(1000);
		//Select dropdown = new Select(SortDropdown);
		//dropdown.selectByVisibleText("Newest Arrivals");
		Thread.sleep(1000);
		//captureScreenshot.takeScreenShot("TC04_Select the Product", driver); // take screenshot
		SelectProduct.click();
		}

	public String AddtoCart() throws Exception {
		String MainWindow = driver.getWindowHandle();
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
					SelectQuantity.selectByIndex(1);
				} catch (Exception e) {
					System.out.println("Unable to select the quantity from the dropdown");
				}
				
			    try {
					WebElement emailbtn = driver.findElement(By.xpath("//a[@title='Email Me']"));
					emailbtn.isDisplayed();
					emailbtn.click();
					System.out.println("Product is currently unavailable, Email will be sent when the product is available ");
					//driver.close();
    			} catch (Exception e) {
					System.out.println(e.getMessage());
				}
		
				//Add to cart
				AddToCartBtn.click();
				try 
				{
				WebElement tickicon = driver.findElement(By.xpath("//*[@id=\"huc-v2-order-row-confirm-text\"]/h1"));
				tickicon.isDisplayed();
				//captureScreenshot.takeScreenShot("TC_02_Product is successfully added to the cart", driver); // take screenshot
				System.out.println("Product is successfully added to the cart");
				
				CartBtn.click();
				String pageHeading = "Shopping Cart";
				//captureScreenshot.takeScreenShot("TC_02_Product is available in the shopping cart", driver); // take screenshot
				}catch (Exception e) {
					System.out.print("Product is not added to the cart");
					return "";
				}
				finally
				{
				driver.navigate().refresh();
				driver.close(); // Close the child window
				}
     		}	
		}
		
		// switch back to parent window
		driver.switchTo().window(MainWindow);
		driver.navigate().refresh();
		return "Shopping Cart";
	}
}
