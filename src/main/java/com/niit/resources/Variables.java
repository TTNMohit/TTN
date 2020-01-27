package com.niit.resources;

import java.io.File;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Properties;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Variables {
	public static HashMap<String, HashMap<String, String[]>> driverExcelObject = new HashMap<String, HashMap<String, String[]>>();
	public static Properties prop = new Properties();
	public static InputStream input = null;
	public static HashMap<String, String[]> objects = new HashMap<String, String[]>();
	public static String extinctReportsPath = "";
	public static String extinctScreenshotPath = "";

	public static void createExtinctReportPath() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			String path = new File(".").getCanonicalPath()
					+ "\\src\\test\\resources" + "\\Reports\\"
					+ sdf.format(timestamp);
			File file = new File(path);
			file.mkdir();
			extinctReportsPath = path;
			/*String path1 = path + "\\Screenshots";
			File file2 = new File(path1);
			file2.mkdir();
			extinctScreenshotPath = path1;*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// //
		}
	}
	
	public ExtentReports createExtinctReport(String path, String moduleName) {

		String reportPath = path + "\\" + moduleName
		// + sdf.format(timestamp)
				+ ".html";
		ExtentReports report = null;
		try {
			// SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
			// Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			ExtentHtmlReporter htmlReport = new ExtentHtmlReporter(reportPath);
			report = new ExtentReports();
			report.attachReporter(htmlReport);

		} catch (Exception e) {
			System.out.print(e.toString());
		}
		return report;
	}

	public void closeExtinctReport(ExtentReports report) {
		report.flush();
	}
}
