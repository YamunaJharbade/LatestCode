package com.amazon.pages;

import javax.swing.JOptionPane;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.log4testng.Logger;

//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.model.Log;

public class LoginPage {

	private WebDriver driver;
	String encodedpwd = "Ymxlc3NtZWdvZEAxOTgy";

	@FindBy(linkText = "Sign in")
	private WebElement SignIn;

	@FindBy(name = "email")
	private WebElement Email;

	@FindBy(id = "continue")
	private WebElement continuebutton;

	@FindBy(id = "ap_password")
	private WebElement Password;

	@FindBy(id = "signInSubmit")
	private WebElement Submit;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void Login() throws Exception {
		SignIn.click();
		if (continuebutton.isDisplayed()) {
			Email.sendKeys("jharbadeyamuna@gmail.com");
			continuebutton.click();
			if (Password.isDisplayed()) {
				byte[] decodedPassword = Base64.decodeBase64(encodedpwd);
				Password.sendKeys(new String(decodedPassword));

				// Password.sendKeys("");
				Thread.sleep(1000);
				Submit.click();
			} else {
				Assert.assertTrue(false);

			}
		} else {
			Assert.assertTrue(false);
		}

	}
}
