package com.niit.resources;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.Test;
import com.niit.main.ExtentObjects;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class CommonFunctions {
	AppiumDriverLocalService service;
	ExtentObjects extentObj = new ExtentObjects();

	public CommonFunctions(ExtentObjects obhh) {
		extentObj = obhh;
	}

	public void appiumsetup() {
		// intialize Appium Service
		service = AppiumDriverLocalService
				.buildService(new AppiumServiceBuilder().withIPAddress(Variables.prop.getProperty("ip"))
						.usingPort(Integer.parseInt(Variables.prop.getProperty("port"))));
		// Start Appium Service
		service.start();
	}

	// Stop Appium Service
	public void appiumstop() {
		service.stop();
	}

	public static ExtentTest createParent(ExtentReports report, String title, String description) {
		return report.createTest(title, description);
	}

	public ExtentTest createChild(String childName) {

		boolean flag = false;
		ExtentTest child = null;
		// ExtentTest chilfd = extentObj.getParent().

		List<Test> childe = extentObj.getParent().getModel().getNodeContext().getAll();

		ExtentTest ff = extentObj.getParent().pass(childName);

		for (Test test : childe) {
			System.out.println(test.getName());
			if (test.getName().toLowerCase().equals(childName.toLowerCase())) {
				break;
			}
		}

		if (flag == false) {
			child = extentObj.getParent().createNode(childName);
		}

		return child;
	}

	public void pass(ExtentTest child, String description) {
		child.log(Status.PASS, description);
	}

	public void fail(WebDriver driver1, ExtentTest child1, String description) {
		String path = "";
		ExtentTest child = child1;
		WebDriver driver = driver1;

		System.out.println(driver.getClass());

		try {
			path = extentObj.getpath();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			String format = "jpg";
			String SS_Path = "SS_" + sdf.format(timestamp) + "." + format;
			String fileName = path + "\\" + SS_Path;

			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			FileUtils.copyFile(scrFile, new File(fileName));
			child.addScreenCaptureFromPath(("./Screenshots/" + SS_Path));
			child.log(Status.FAIL, description);

		} catch (Exception e) {
			child.log(Status.FAIL, description);
		}

	}

	public void info(ExtentTest child, String description) {
		child.log(Status.INFO, description);
	}

	public void error(ExtentTest child, String description) {
		child.log(Status.ERROR, description);
	}

	public void warning(ExtentTest child, String description) {
		child.log(Status.WARNING, description);
	}

	public void skip(ExtentTest child, String description) {
		child.log(Status.SKIP, description);
	}

	public void logPass(String description) {

	}

	public void logFail(String description) {

	}

	public void logMessage(String description) {

	}
}