package com.cashkaro.sample.actions;

import com.cashkaro.sample.pages.loginPage;

public class signIn {

	// We perform Login action here.

	public void performLogin(String userName, String password) {
		loginPage login = new loginPage();
		login.setuserName(userName);
		login.setpassword(password);
		login.performsignIn();

	}

}
