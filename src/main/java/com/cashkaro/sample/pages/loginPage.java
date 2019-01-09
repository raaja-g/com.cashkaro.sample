package com.cashkaro.sample.pages;

import org.openqa.selenium.By;

import com.cashkaro.sample.scripts.browserAction;

public class loginPage extends browserAction {

	// All the objects related to the page.
	// Any changes to the object we change it here.

	private final By userName = By.id("uname");
	private final By password = By.id("pwd");
	private final By signInButton = By.id("btnLayoutSignIn");

	public loginPage() {
	}

	// We enter Username with this method.

	public void setuserName(String username) {
		sendKeys(userName, username);
	}

	// We enter Password with this method.

	public void setpassword(String Password) {
		sendKeys(password, Password);
	}
	
	// We Click on Sign In button with this method.

	public void performsignIn() {
		click(signInButton);
	}

}
