package com.cashkaro.sample.scripts;

import java.io.File;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Browser {

	private Logger logger = Logger.getLogger(Browser.class.getName());
	private WebDriver driver = null;
	private String browserName;
	private String ChromeDriverName = "chromedriver"; // it is "chromedriver.exe for windows"
	private String firefoxDriverName = "geckodriver";

	public Browser(String browserName) {

		// Initializing the logger

		initializeLog();
		this.browserName = browserName;
	}

	public WebDriver initBrowser() {

		// Logging the driver path.
		Log.info("Webdriver path: " + Constant.getResourceFolder + File.separator);

		// Below Condition to initiate respective Browsers
		// Checking for Firefox browser
		if (browserName.equalsIgnoreCase("FIREFOX")) {

			FirefoxProfile ffprofile = new ProfilesIni().getProfile("default");
			ffprofile.setPreference("browser.download.folderList", 2);
			ffprofile.setPreference("browser.download.manager.showWhenStarting", false);
			ffprofile.setPreference("browser.helperApps.neverAsk.saveToDisk",
					"text/csv,application/zip,application/octet-stream");

			// Added to Suppress the driver warnings

			LoggingPreferences pref = new LoggingPreferences();
			pref.enable(LogType.BROWSER, Level.OFF);
			pref.enable(LogType.CLIENT, Level.OFF);
			pref.enable(LogType.DRIVER, Level.OFF);
			pref.enable(LogType.PERFORMANCE, Level.OFF);
			pref.enable(LogType.PROFILER, Level.OFF);
			pref.enable(LogType.SERVER, Level.OFF);

			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability(FirefoxDriver.PROFILE, ffprofile);
			capabilities.setCapability(CapabilityType.LOGGING_PREFS, pref);

			if (System.getProperty("os.name").toLowerCase().contains("mac")) {

				// Getting the respective driver for MAC OS
				System.setProperty("webdriver.gecko.driver",
						Constant.getResourceFolder + File.separator + firefoxDriverName);
			} else {

				// Getting the respective driver for Windows OS
				System.setProperty("webdriver.gecko.driver",
						Constant.getResourceFolder + File.separator + firefoxDriverName + ".exe");
			}

			System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
			System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");

			driver = new FirefoxDriver(capabilities);

			logger.info("Starting Firefox browser... ");

			// Checking for Chrome browser
		} else if (browserName.equalsIgnoreCase("CHROME")) {
			if (System.getProperty("os.name").toLowerCase().contains("mac")) {
				System.setProperty("webdriver.chrome.driver",
						Constant.getResourceFolder + File.separator + ChromeDriverName);
			} else {
				System.setProperty("webdriver.chrome.driver",
						Constant.getResourceFolder + File.separator + ChromeDriverName + ".exe");
			}
			ChromeOptions options = new ChromeOptions();
			//options.addArguments("start-maximized"); // For Windows OS
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			options.setExperimentalOption("prefs", chromePrefs);
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			driver = new ChromeDriver(options);
			logger.info("Starting Chrome browser... ");

		}

		// For all other browsers, we would initialize Chrome browser as default browser
		else {
			System.setProperty("webdriver.chrome.driver",
					Constant.getResourceFolder + File.separator + ChromeDriverName);
			driver = new ChromeDriver();
			logger.info("Given browser name : " + browserName + " not supported. Starting Firefox browser");
			logger.info("Starting Firefox browser... ");
		}

		// Maximizing the browser window
		//driver.manage().window().maximize();

		// Returning the respective browser driver.
		return driver;
	}

	/*
	 * Initializing the Logger
	 */
	@SuppressWarnings("unused")
	public void initializeLog() {
		try {

			// Setting up the logger properties
			Properties properties = new Properties();

			// Setting up the logger directory.
			properties.setProperty("log4j.appender.MyFile.File",
					Constant.workingDirectory + File.separator + "browser_activity_log" + ".log");
			// Logger Properties
			properties.setProperty("log4j.rootLogger", "INFO,stdout,MyFile");
			properties.setProperty("log4j.rootCategory", "INFO");

			properties.setProperty("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
			properties.setProperty("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
			properties.setProperty("log4j.appender.stdout.layout.ConversionPattern",
					"%d{yyyy/MM/dd HH:mm:ss.SSS} [%5p] %t (%F) - %m%n");

			properties.setProperty("log4j.appender.MyFile", "org.apache.log4j.RollingFileAppender");
			properties.setProperty("log4j.appender.MyFile.MaxBackupIndex", "1");
			properties.setProperty("log4j.appender.MyFile.layout", "org.apache.log4j.PatternLayout");
			properties.setProperty("log4j.appender.MyFile.layout.ConversionPattern",
					"%d{yyyy/MM/dd HH:mm:ss.SSS} [%5p] %t (%F) - %m%n");
			PropertyConfigurator.configure(properties);

			// Initializing the logger for logging browser activities.
			Logger logger = Logger.getLogger("MyFile");
			System.out.println("Successfully Initialized the logger");
		}

		catch (StaleElementReferenceException sere) {
			System.out.println("Logger is not initialized");
		}
	}
}
