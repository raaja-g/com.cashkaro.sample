package com.cashkaro.sample.scripts;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.TestException;
import org.apache.commons.lang3.StringUtils;

import com.cashkaro.sample.driverScript.*;

public abstract class browserAction {

	private int timeout = 10;

	// getting the driver from Driver Script.

	public browserAction() {
		driver = driverScript.getDriver();
	}

	public  WebDriver driver;
	public WebDriverWait wait;
	public Actions actions;
	public Select select;

	// This method would wait for the Element to be Displayed on Screen.

	public void waitUntilElementIsDisplayedOnScreen(By by) {
		try {
			wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			throw new NoSuchElementException(String.format("The following element was not visible: ", by));
		}
	}

	// This method would wait for the Element to be Displayed.

	public void waitForElementToDisplay(By Selector) throws Exception {
		WebElement element = getElement(Selector);
		while (!element.isDisplayed()) {
			System.out.println("Waiting for element to display: " + Selector);
			Thread.sleep(200);
		}
	}

	// This methods helps the user in Navigation.
	// We pass the URL parameter here to navigate to the desired screen.

	public void navigateToURL(String URL) {
		try {
			driver.navigate().to(URL);
			Log.info("Navigated Successfully to the URL : " + URL);
		} catch (Exception e) {
			Log.error("FAILURE: URL did not load: " + URL);
			throw new TestException("URL did not load");
		}
	}

	// This method gets the current browser title.
	// We use this value to assert whether we have navigated to the intended Page.

	public String getPageTitle() {
		try {
			Log.info("Page Title is : " + driver.getTitle().toString());
			return driver.getTitle();
		} catch (Exception e) {
			Log.error(e.toString());
			throw new TestException(String.format("Current page title is:", driver.getTitle().toString()));
		}
	}

	// This method gets the current URL of the browser.
	// We use this value to assert whether we have navigated to the intended URL.

	public String getCurrentURL() {
		try {
			Log.info("Current URL is : " + driver.getCurrentUrl());
			return driver.getCurrentUrl();
		} catch (Exception e) {
			Log.error(e.toString());
			throw new TestException(String.format("Current URL is: ", driver.getCurrentUrl()));
		}
	}

	// This method gets the required WebElement.
	// We use this method to find the WebElement

	public WebElement getElement(By by) {
		try {
			return driver.findElement(by);
		} catch (Exception e) {
			Log.error(e.toString());
			System.out.println(String.format("Element does not exist - proceeding", by));
		}
		return null;

	}

	// This method gets the texts of the WebElement.
	// We use this method to get the book information.
	// We assert whether the element is displayed on the screen before getting the
	// text.

	public String getElementText(By by) {
		waitUntilElementIsDisplayedOnScreen(by);
		try {
			String value = driver.findElement(by).getText();
			return StringUtils.trim(value);

		} catch (Exception e) {
			Log.error(e.toString());
			System.out.println(String.format("Element %s does not exist - proceeding", by));
		}
		return null;
	}

	// This method is used to select a value from drop down.
	// Here we are sending 2 By parameters and 1 search criteria

	public void select(By by, By by1, String searchCriteria) throws Exception {

		if (searchCriteria == null) {
			System.out.println("No Search Criteria: " + searchCriteria);
			Log.error("No Search Criteria: " + searchCriteria);
			throw new TestException("Options for the dropdown list cannot be found.");
		} else {
			driver.findElement(by).click();
			new Select(driver.findElement(by)).selectByVisibleText(searchCriteria);
			driver.findElement(by1).click();
			// driver.findElement(By.cssSelector(by1+"'searchCriteria']")).click();
			Log.info("Successfully selected the search criteria : " + searchCriteria);
		}
	}

	// This method is used to click a Link or Button.
	// We Assert whether the element is null. If its null, we again try to find the
	// element.
	// Even in cache section, we again try to click the element before logging the
	// exception.

	public void click(By by) {
		WebElement element = getElement(by);
		try {
			if (element == null) {
				Log.error("No Button Element : " + by.toString());
				driver.findElement(by).click();
			} else {
				Log.info("Successfully Clicked the Button Element : " + element.getText());
				element.click();
			}
		} catch (Exception e) {
			Log.error("Retry Clicking the button..." + element.getText());
			driver.findElement(by).click();
			Log.error("Retry Clicking the button FAILED : " + element.getText());
			throw new TestException(String.format("The following element is not clickable: [%s]", by));
		}
	}

	// We check for the text area / text box to be Empty
	// Maximum retries is set to 10. We can change it.

	public void waitForElementTextToBeEmpty(WebElement element) {
		String text;
		try {
			text = element.getText();
			int maxRetries = 10;
			int retry = 0;
			while ((text.length() >= 1) || (retry < maxRetries)) {
				retry++;
				text = element.getText();
			}
		} catch (Exception e) {
			System.out.print(String.format("The following element could not be cleared: [%s]", element.getText()));
		}

	}

	// This method clears the text area / text box.
	// We assert for the Element has no texts.

	public void clearField(WebElement element) {
		try {
			element.clear();
			waitForElementTextToBeEmpty(element);
			Log.info("Successfully cleared the texts in the Element : " + element.getText());
		} catch (Exception e) {
			Log.error(String.format("The following element could not be cleared: [%s]", element.getText()));
		}
	}

	// This method enters the desired value in the text box / text area.
	// We check whether this field is empty before entering a value.

	public void sendKeys(By by, String value) {
		WebElement element = getElement(by);
		clearField(element);
		try {
			element.sendKeys(value);
			Log.info("Successfully cleared the entered in the Element : " + element.getText());
		} catch (Exception e) {
			throw new TestException(
					String.format("Error in sending [%s] to the following element: [%s]", value, by.toString()));
		}
	}
}
