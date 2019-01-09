/**
 * 
 */
package com.cashkaro.sample.scripts;

/**
 * @author rganesan
 *
 */
import java.io.File;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {

	private static ExtentReports extent;

	public synchronized static ExtentReports getReporter(String filePath) {
		if (extent == null) {
			extent = new ExtentReports(Constant.workingDirectory + File.separator + "AutomationReport.html", true);
			extent.addSystemInfo("Host Name", "Raja").addSystemInfo("Environment", "QA");
		}

		return extent;
	}

}