package com.ttn.main;

import java.io.File;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;
import com.ttn.resources.CommonFunctions;
import com.ttn.resources.DataPooling;
import com.ttn.resources.DriverInstance;
import com.ttn.resources.SendEmail;
import com.ttn.resources.ServerSetting;
import com.ttn.resources.Variables;

public class ThreadClass implements Runnable {

	String className = "";
	HashMap<String, String[]> testCases = new HashMap<String, String[]>();
	Variables variable = new Variables();

	public ThreadClass(String className, HashMap<String, String[]> testCase) {
		this.className = className;
		this.testCases = testCase;
	}

	@Override
	public void run() {
		executeScript();
	}

	public String getServerSettings(String className, String server) {
		String serverSettings[] = ServerSetting.settings(server);

		switch (className.toLowerCase()) {

		case "url":
			return serverSettings[0];

		case "dburl":
			return serverSettings[1];

		case "dbname":
			return serverSettings[2];

		case "dbpassword":
			return serverSettings[3];

		case "endpointurl":
			return serverSettings[4];

		default:
			return "no data";
		}
	}

	@SuppressWarnings("rawtypes")
	private void executeScript() {
		Objects object = new Objects();
		ExtentObjects extentObject = new ExtentObjects();
		try {
			DriverInstance driverInstance = new DriverInstance();
			// // Create new folder
			String path = Variables.extinctReportsPath + "\\" + this.className;
			File file = new File(path);
			file.mkdir();
			String url = "";
			String usr = "";
			String pass = "";
			String apiEndPoint = "";
			for (Entry<String, String[]> tc : this.testCases.entrySet()) {
				String[] data = new String[6];
				data = tc.getValue();
				url = getServerSettings("dburl", data[5]);
				usr = getServerSettings("dbname", data[5]);
				pass = getServerSettings("dbpassword", data[5]);
				apiEndPoint = getServerSettings("endpointurl", data[5]);
				break;
			}
			try {
				object.setDataPooling(new DataPooling(url, usr, pass));
				object.setDataSource(object.getDataPooling().setUpPool());
				object.setConnection(object.getDataSource().getConnection());
				extentObject.setConnection(object.getDataSource().getConnection());

			} catch (Exception e) {
				System.out.println(e.toString());
				// // db issue in
			}

			object.setapiurl(apiEndPoint);
			extentObject.setapiurl(apiEndPoint);
			String path2 = Variables.extinctReportsPath + "\\" + this.className + "\\Screenshots";
			File file2 = new File(path2);
			file2.mkdir();
			extentObject.setpath(path2);

			extentObject.setReport(variable.createExtinctReport(path, this.className));
			object.setmoduleName(this.className);

			String email = "mohit.goel@tothenew.com , mohit.goel@tothenew.com";

			for (Entry<String, String[]> tc : this.testCases.entrySet()) {
				try {
					String[] data = new String[6];
					String testCase = tc.getKey();
					data = tc.getValue();
					email = data[6];
					object.setcomponentDescription(data[3]);
					object.setcomponentDetailDescription(data[4]);
					object.setserver(data[5]);
					object.setURL(getServerSettings("url", data[5]));
					object.setdbURL(getServerSettings("dburl", data[5]));
					object.setdbUserName(getServerSettings("dbname", data[5]));
					object.setdbPassword(getServerSettings("dbpassword", data[5]));
					String device = data[1];
					String browser = data[2];
					object.setbrowser(browser);
					object.setdevice(device);
					if (!device.equalsIgnoreCase("api") || !browser.equalsIgnoreCase("API")) {
						object.setDriver(driverInstance.Driver(device, browser));
						extentObject.setDriver(object.getDriver());

						//// Object CLass
						Class objectClass = Class.forName("com.ttn.teststep." + this.className + "_Object");
						Object objectObject = objectClass.newInstance();
						String objectMethod = "Initialize";
						Method methodsw = objectObject.getClass().getMethod(objectMethod,
								new Class[] { Objects.class });
						methodsw.invoke(objectObject, object);

						//// Test Class
						Class testClass = Class.forName("com.ttn.testcase." + this.className + "_Test");
						Object testObject = testClass.newInstance();
						String testmethod = "Initialize";
						Method testMethods = testObject.getClass().getMethod(testmethod,
								new Class[] { ExtentObjects.class });
						testMethods.invoke(testObject, extentObject);

						//// Class Main Class
						Class cls = Class.forName("com.ttn.testcomponent." + this.className);
						Object obj = cls.newInstance();
						String mkey = testCase;
						extentObject.setParent(CommonFunctions.createParent(extentObject.getReport(),
								object.getcomponentDescription(), object.getcomponentDetailDescription()));
						Method methods = obj.getClass().getMethod(mkey);
						methods.invoke(obj);

					}

					else {
						Class cls = Class.forName("com.ttn.testcomponent." + this.className);
						Object obj = cls.newInstance();
						String mkey = testCase;
						extentObject.setParent(CommonFunctions.createParent(extentObject.getReport(),
								object.getcomponentDescription(), object.getcomponentDetailDescription()));
						Method testMethods = obj.getClass().getMethod(mkey, new Class[] { ExtentObjects.class });
						testMethods.invoke(obj, extentObject);
					}

				} catch (Exception e) {
					e.printStackTrace();
					continue;
				} finally {
					if (!(object.getDriver() == null))
						object.getDriver().quit();
					variable.closeExtinctReport(extentObject.getReport());
				}
			}
			// // Send Mail
			String reportpath;
			reportpath = path;
			SendEmail se = new SendEmail();
			// se.sendFromGMail(this.className, reportpath, email);
		} catch (Exception e) {
			System.out.print(e.toString());

		} finally {
			try {
				object.getConnection().close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
