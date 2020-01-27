package com.niit.resources;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class DriverInstance {
	public WebDriver driver;
	public WebDriver Driver(String device, String browser) throws IOException {
		if (device.equalsIgnoreCase("app")) {
			File apkpath = new File("./kj.apk");
			DesiredCapabilities capabilities1 = new DesiredCapabilities();
			capabilities1.setCapability("BROWSER_NAME",
					Variables.prop.getProperty("platform"));
			capabilities1.setCapability("VERSION",
					Variables.prop.getProperty("androidversion"));
			capabilities1.setCapability("deviceName",
					Variables.prop.getProperty("devicename"));
			capabilities1.setCapability("platformName",
					Variables.prop.getProperty("platform"));
			capabilities1.setCapability("app", apkpath.getCanonicalPath());
			capabilities1.setCapability("appPackage",
					Variables.prop.getProperty("appPackage"));
			capabilities1.setCapability("appActivity",
					Variables.prop.getProperty("appActivity"));
			String appurl = "http://" + Variables.prop.getProperty("ip") + ":"
					+ Variables.prop.getProperty("port") + "/wd/hub";
			driver = new RemoteWebDriver(new URL(appurl),
					capabilities1);
			System.out.println("App Device testing started");
			return driver;

		} else {
			switch (browser.toLowerCase()) {
			case "chrome":
				if (device.equalsIgnoreCase("mobile")) {
					String chromePath = new File(".")
							.getCanonicalPath();
					chromePath = chromePath + "\\src\\test\\resources\\";
					System.setProperty("webdriver.chrome.driver", chromePath
							+ "chromedriver.exe");
					HashMap<String, Object> deviceMetrices = new HashMap<String, Object>();
					deviceMetrices.put("width", 400);
					deviceMetrices.put("height", 600);
					// deviceMetrices.put("pixelRatio", 2);

					HashMap<String, Object> mobileEmulation = new HashMap<String, Object>();
					mobileEmulation.put("deviceMetrics", deviceMetrices);
					mobileEmulation.put("userAgent", "Android");
					// mobileEmulation.put("deviceName", "Nexus 5");
					HashMap<String, Object> chromeOptions = new HashMap<String, Object>();
					chromeOptions.put("mobileEmulation", mobileEmulation);

					DesiredCapabilities capabilities = DesiredCapabilities
							.chrome();

					capabilities.setCapability(ChromeOptions.CAPABILITY,
							chromeOptions);

					driver = new ChromeDriver(capabilities);

					ArrayList<String> tabs2 = new ArrayList<String>(
							driver.getWindowHandles());
					if (tabs2.size() > 1) {
						driver.switchTo().window(tabs2.get(1));
						driver.close();
						driver.switchTo().window(tabs2.get(0));
					}

				} else {
					String chromePath = new File(".")
							.getCanonicalPath();
					chromePath = chromePath + "\\src\\test\\resources\\";
					System.setProperty("webdriver.chrome.driver", chromePath
							+ "chromedriver.exe");
					ChromeOptions options = new ChromeOptions();
					options.addArguments("disable-infobars");

					options.addArguments("--start-maximized");
					driver = new ChromeDriver(options);
					// driver.manage().window().maximize();
					ArrayList<String> tabs2 = new ArrayList<String>(
							driver.getWindowHandles());
					if (tabs2.size() > 1) {
						driver.switchTo().window(tabs2.get(1));
						driver.close();
						driver.switchTo().window(tabs2.get(0));
					}
				}
				break;

			case "firefox":
				if (device.equalsIgnoreCase("mobile")) {
					String geekoPath = new File(".").getCanonicalPath();
					geekoPath = geekoPath + "\\src\\test\\resources\\";
					System.setProperty("webdriver.gecko.driver", geekoPath
							+ "geckodriver.exe");
					FirefoxProfile firefoxProfile = new FirefoxProfile();
					firefoxProfile.setPreference("general.useragent.override",
							"Android");
					driver = new FirefoxDriver(firefoxProfile);
				} else {
					String geekoPath = new File(".").getCanonicalPath();
					geekoPath = geekoPath + "\\src\\test\\resources\\";
					System.setProperty("webdriver.gecko.driver", geekoPath
							+ "geckodriver.exe");
					driver = new FirefoxDriver();
				}
				break;
			default:
				if (device.equalsIgnoreCase("mobile")) {
					String chromePath = new File(".")
							.getCanonicalPath();
					chromePath = chromePath + "\\src\\test\\resources\\";
					System.setProperty("webdriver.chrome.driver", chromePath
							+ "chromedriver.exe");
					HashMap<String, String> mobileEmulation = new HashMap<String, String>();
					mobileEmulation.put("userAgent", "Android");
					HashMap<String, Object> chromeOptions = new HashMap<String, Object>();
					chromeOptions.put("mobileEmulation", mobileEmulation);
					DesiredCapabilities capabilities = DesiredCapabilities
							.chrome();
					capabilities.setCapability(ChromeOptions.CAPABILITY,
							chromeOptions);
					driver = new ChromeDriver(capabilities);
					ArrayList<String> tabs2 = new ArrayList<String>(
							driver.getWindowHandles());
					if (tabs2.size() > 1) {
						driver.switchTo().window(tabs2.get(1));
						driver.close();
						driver.switchTo().window(tabs2.get(0));
					}
				} else {
					String chromePath = new File(".")
							.getCanonicalPath();
					chromePath = chromePath + "\\src\\test\\resources\\";
					System.setProperty("webdriver.chrome.driver", chromePath
							+ "chromedriver.exe");
					ChromeOptions options = new ChromeOptions();
					options.addArguments("disable-infobars");
					options.addArguments("--start-maximized");
					driver = new ChromeDriver(options);
					// driver.manage().window().maximize();
					ArrayList<String> tabs2 = new ArrayList<String>(
							driver.getWindowHandles());
					if (tabs2.size() > 1) {
						driver.switchTo().window(tabs2.get(1));
						driver.close();
						driver.switchTo().window(tabs2.get(0));
					}
				}
				break;
			}
			return driver;
		}
	}
}
