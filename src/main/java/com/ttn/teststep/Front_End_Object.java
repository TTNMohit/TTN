package com.ttn.teststep;

import com.ttn.main.Objects;
import com.ttn.resources.FetchData;

public class Front_End_Object {
	static Objects obj;

	public void Initialize(Objects objw) {
		obj = objw;
	}
	
	
	// // Test Step
	public void clickLogin() {
		obj.getDriver().findElement(FetchData.readOR("clickGmailLogin")).click();
	}

	public void openurl(String Url) {
		obj.getDriver().get(Url);
	}

	public void typeUserName(String Name) {
		obj.getDriver().findElement(FetchData.readOR("username")).sendKeys(Name);
	}

	public void clickNext() {
		obj.getDriver().findElement(FetchData.readOR("next")).click();
	}

	public void typepassword(String Name) {
		obj.getDriver().findElement(FetchData.readOR("pass")).sendKeys(Name);
	}

	public void clicksignin() {
		obj.getDriver().findElement(FetchData.readOR("passnext")).click();
	}

}
