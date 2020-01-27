package com.niit.testcase;

import com.niit.main.ExtentObjects;
import com.niit.resources.CommonFunctions;
import com.niit.teststep.Front_End_Object;

public class Front_End_Test {
	static CommonFunctions common = null;
	static ExtentObjects obj;

	public void Initialize(ExtentObjects objw) {
		obj = objw;
		common = new CommonFunctions(objw);
	}
	
	
	///// Test Case
	public void login(String loginName, String password) {
		Front_End_Object test = new Front_End_Object();
	
		try {			
			test.openurl("https://www.gmail.com");
			//test.clickLogin();
			test.typeUserName(loginName);
			test.clickNext();
			test.typepassword(password);
			test.clicksignin();
			Thread.sleep(10000);
			common.pass(common.createChild("login"), "Login Passed");
		}

		catch (Exception e) {
			common.fail(obj.getDriver(), common.createChild("login"), "Login Failed");
		}
	}

	public void logi2n(String loginName, String password) {
		try {
			Front_End_Object test = new Front_End_Object();
			// g.getDriver().get("https://www.gmail.com");
			test.clickLogin();
			test.typeUserName(loginName);
			test.clickNext();
			test.typepassword(password);
			test.clicksignin();
			Thread.sleep(10000);
			common.pass(common.createChild("login"), "Login Passed");

		}

		catch (Exception e) {
			common.fail(obj.getDriver(), common.createChild("login"), "Login Failed");
		}
	}
}
