package com.niit.resources;

public class ServerSetting {

	public static String[] settings(String server)
	{
		String[] credentials = new String[5];
		switch(server.toLowerCase())
		{
		case "uat":
			credentials[0] = "jdbc:mysql://127.0.0.1:3306/sys";
			credentials[1] = "jdbc:mysql://127.0.0.1:3306/sys";
			credentials[2] = "root";
			credentials[3] = "mysql";
			credentials[4] = "https://tatasky-uat-kong.videoready.tv";
			break;
		
		default :
			break;
		}
		
		return credentials;
		
	}
	
	
}
