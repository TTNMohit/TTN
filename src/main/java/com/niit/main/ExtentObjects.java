package com.niit.main;

import java.sql.Connection;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ExtentObjects {
	private ExtentReports report;
	private String path;
	private ExtentTest parent;
	private WebDriver driver;
	private String apiurl;
	private Connection connObj;
	
	public String getapiurl() {
		return this.apiurl;
	}

	public void setapiurl(String apiurl) {
		this.apiurl = apiurl;
	}
	
	public Connection getConnection() {
		return this.connObj;
	}

	public void setConnection(Connection connObj) {
		this.connObj = connObj;
	}

	public WebDriver getDriver() {
		return this.driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public ExtentTest getParent() {
		return this.parent;
	}

	public void setParent(ExtentTest parent) {
		this.parent = parent;
	}
	
	public String getpath() {
		return this.path;
	}

	public void setpath(String path) {
		this.path = path;
	}

	public ExtentReports getReport() {
		return this.report;
	}

	public void setReport(ExtentReports report) {
		this.report = report;
	}
}
