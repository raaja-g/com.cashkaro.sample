package com.cashkaro.sample.scripts;

import java.io.File;

public class Constant {

	// Passing the Browser Name.
	// We need to change the browser Name here
	// Possible values for browserName are FIREFOX and CHROME

	public static String browserName = "CHROME";

	// Passing the URL information here

	public static String URL = "https://www.cashkaro.com/";

	// File Paths
	public static String workingDirectory = System.getProperty("user.dir");
	public static String resourceFolder = "src" + File.separator + "main" + File.separator + "resources";
	public static String getResourceFolder = workingDirectory + File.separator + resourceFolder + File.separator
			+ "Drivers";
	public static String extentReportPath = Constant.workingDirectory + File.separator;
	
}
