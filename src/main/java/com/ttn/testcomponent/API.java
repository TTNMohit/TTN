package com.ttn.testcomponent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import com.ttn.main.ExtentObjects;
import com.ttn.resources.CommonFunctions;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class API {

	@SuppressWarnings("resource")
	public void HomePageAPI(ExtentObjects obj) {
		CommonFunctions common = new CommonFunctions(obj);
		try {
			String brandq = "select * from brand b join brand_extension be on b.id = be.brand_id where id = 2 "
					+ "and lang_code = 'ENG' limit 1;";
			ResultSet rs = executeDbQuery(brandq, obj);
			while (rs.next()) {
				String title 
				= rs.getString("title_brief");
				String endPoint = obj.getapiurl();
				Response apiResponse = null;
				apiResponse = RestAssured.given().when().get(endPoint + "/content-detail/pub/api/v1/brand/2").then()
						.extract().response();
				JsonPath jsonPath = apiResponse.jsonPath();
				int statusCode = apiResponse.getStatusCode();
				if (statusCode == 200) {
					String brandName = title;
					if (brandName.toLowerCase().equals(jsonPath.getString("data.meta.brandTitle").toLowerCase())) {
						System.out.println(brandName + "," + jsonPath.getString("data.meta.brandTitle"));
					}

					String statusResponse = jsonPath.getString("data.meta.vodId");
					String query = "select * from vod v join vod_extension ext on ext.vod_id  = v.id "
							+ "join vod_contributor_extension vce on vce.vod_extension_id = ext.id " + "where v.id = "
							+ statusResponse + " and ext.lang_code = 'ENG';";
					rs = executeDbQuery(query, obj);
					while (rs.next()) {
						String brandTitle = rs.getString("title_long");
						if (brandTitle.toLowerCase().equals(jsonPath.getString("data.meta.vodTitle").toLowerCase())) {
							System.out.println(brandTitle + "," + jsonPath.getString("data.meta.vodTitle"));
						}
						String downloadExpiry = rs.getString("download_expiry");
						if (downloadExpiry.toLowerCase()
								.equals(jsonPath.getString("data.meta.downloadExpiry").toLowerCase())) {
							System.out.println(downloadExpiry + "," + jsonPath.getString("data.meta.downloadExpiry"));
						}

						String description = rs.getString("summary_long");
						if (description.toLowerCase()
								.equals(jsonPath.getString("data.meta.description").toLowerCase())) {
							System.out.println(description + "," + jsonPath.getString("data.meta.description"));
						}

						String contribution = rs.getString("contribution");

						if (contribution.toLowerCase().equals("producer")) {
							String name = rs.getString("first_name") + " " + rs.getString("last_name");
							if (jsonPath.getString("data.meta.producer").toLowerCase().contains(name.toLowerCase())) {
								System.out.println(name + "," + jsonPath.getString("data.meta.producer"));
							}

						}

						else if (contribution.toLowerCase().equals("director")) {
							String name = rs.getString("first_name") + " " + rs.getString("last_name");
							if (jsonPath.getString("data.meta.director").toLowerCase().contains(name.toLowerCase())) {
								System.out.println(name + "," + jsonPath.getString("data.meta.director"));
							}
						}

						else if (contribution.toLowerCase().equals("actor")) {
							String name = rs.getString("first_name") + " " + rs.getString("last_name");
							if (jsonPath.getString("data.meta.actor").toLowerCase().contains(name.toLowerCase())) {
								System.out.println(name + "," + jsonPath.getString("data.meta.actor"));
							}
						}

						common.pass(common.createChild("HomePageAPI"), "HomePageAPI Passed");
					}
				}

			}
		} catch (Exception e) {
			common.fail(obj.getDriver(), common.createChild("HomePageAPI"), "HomePageAPI failed");
			System.out.println(e.toString());

		}

	}

	public ResultSet executeDbQuery(String query, ExtentObjects obj) throws Exception {
		ResultSet rsObj = null;
		// // Code for Data pooling
		try {
			System.out.println("ff");
			PreparedStatement pstmtObj = null;
			pstmtObj = obj.getConnection().prepareStatement(query);
			rsObj = pstmtObj.executeQuery();
		}

		catch (Exception e) {
			System.out.println(e.toString());
		}

		return rsObj;
	}

	public void HomePageAPI1() {
		try {
			String endpoint = "https://tatasky-uat-kong.videoready.tv/homescreen/pub/api/v1/page/HOMEPAGE";
			Response apiResponse = null;
			apiResponse = RestAssured.given().header("platform", "web").when().get(endpoint).then().extract()
					.response();
			int statusCode = apiResponse.getStatusCode();

			JsonPath jsonPath = apiResponse.jsonPath();

			//// Replace all column name with value
			Map<String, Object> books = jsonPath.get("data.items.find {item -> item.id == 398}"
					+ ".contentList.find { contentList -> contentList.id == 24}");

			for (Map.Entry<String, Object> entry : books.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			/*
			 * List<Map<String, Object>> books = jsonPath.
			 * get("data.items.find {item -> item.id == 398}.contentList.findAll { contentList -> contentList.id == 24}"
			 * );
			 * 
			 * 
			 * for (Map<String, Object> map : books) { for (Map.Entry<String,Object> entry :
			 * map.entrySet()) { System.out.println("Key = " + entry.getKey() + ", Value = "
			 * + entry.getValue());
			 * 
			 * } }
			 * 
			 * 
			 * 
			 * List<Map<String, Object>> companies = jsonPath.getList("contentList");
			 * Map<String, Object> elements = jsonPath.getMap("contentList[0]");
			 * List<String> jsonResponse = apiResponse.jsonPath().getList("$");
			 * System.out.println(jsonResponse);
			 * 
			 * for (Map.Entry<String,Object> entry : elements.entrySet()) {
			 * System.out.println("Key = " + entry.getKey() + ", Value = " +
			 * entry.getValue()); }
			 */

			String statusResponse = jsonPath.getString("data.items");
			String messageResponse = jsonPath.getString("message");
			System.out.println(statusCode);
			System.out.println(statusResponse);
			System.out.println(messageResponse);
			System.out.println(jsonPath);

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

}
