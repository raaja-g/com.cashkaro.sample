package com.cashkaro.sample.pages;

import org.openqa.selenium.By;

import com.cashkaro.sample.scripts.browserAction;

public class couponSearchPage extends browserAction {

	// All the objects related to the page.
	// Any changes to the object we change it here.

	private final By searchStore = By.id("search_store");
	private final By searchSubmit = By.id("searchFormSubmit");
	private final By viewAllButton = By.xpath("//*[@id='s1']/h2/a");
	private final By searchResults = By.xpath("//article/div");
	private final By myAccountButton = By.id("link_myaccount");
	private final By logout = By.id("idMenuLogout");
	
	public couponSearchPage() {
	}
	
	//Click on Coupon Search
	public void clickSearchBox() {
		click(searchStore);
	}

	// Enters the value "Data Catalog" in the search bar.
	// This is parameterized.
	// We can send the value from the driver script @Test

	public void searchStore(String storeName) {
		sendKeys(searchStore, storeName);
	}

	// Clicks the Search button.
	public void performSearchGo() {
		click(searchSubmit);
	}
	
	// View All the Results.
	public void clickAllResults() {
		click(viewAllButton);
	}
	
	// Get All Result cards.
	public void getAllResults() {
		
	}
	
	// Click my Account menu option.
	public void clickMyAccount() {
		click(myAccountButton);
	}
	
	// Click my Account menu option.
	public void clicklogout() {
		click(logout);
	}

}
