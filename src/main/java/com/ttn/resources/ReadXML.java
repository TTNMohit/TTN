package com.ttn.resources;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class ReadXML {

	@SuppressWarnings("unused")
	public void XMLReader() {
		try {
			String xmlPath;
			xmlPath = new File(".").getCanonicalPath();
			xmlPath = xmlPath + "\\src\\test\\resources\\" + "ObjectRepo.xml";
			File xmlFile = new File(xmlPath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlPath);
			NodeList nList = doc.getElementsByTagName("keyword");
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				Element eElement = (Element) nNode;
				Variables.objects.put(
						eElement.getAttribute("id"),
						new String[] {
								eElement.getElementsByTagName("identifier")
										.item(0).getTextContent(),
								eElement.getElementsByTagName("value").item(0)
										.getTextContent() });
			}
		} catch (Exception e) {
			System.out.print(e.toString());

		}
	}

}
