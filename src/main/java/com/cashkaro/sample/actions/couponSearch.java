package com.cashkaro.sample.actions;

import com.cashkaro.sample.pages.couponSearchPage;
import com.cashkaro.sample.pages.homePage;

public class couponSearch {

	// This action would result in user Navigating to Home Page.
	
	public void navigateToHomePage() {
		homePage homePage = new homePage();
		homePage.navigateToHomePage();
	}

	// This action would result in user Navigating to Login Page.
	
	public void navigateToLoginPage() {
		homePage homePage = new homePage();
		homePage.navigateToLoginPage();
	}

	// This action would perform Search with given category and Name.
	
	public void searchCoupon(String storeName) throws Exception {
		couponSearchPage searchCoupon = new couponSearchPage();
		searchCoupon.searchStore(storeName);
		searchCoupon.performSearchGo();
		searchCoupon.clickAllResults();
		searchCoupon.clickMyAccount();
		searchCoupon.clicklogout();

	}
}
