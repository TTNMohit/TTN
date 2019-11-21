package com.ttn.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.ttn.resources.ReadConfigFile;
import com.ttn.resources.ReadXML;
import com.ttn.resources.Variables;

public class Selenium {

    public static void main(String[] args){
        String driverSheetPath = null;
        try {
            //// Read Driver Suite for FrontEnd and Api
            driverSheetPath = new java.io.File(".").getCanonicalPath();
            driverSheetPath = driverSheetPath + "\\src\\test\\resources\\" + "DriverScript.xlsx";
            DriverScriptExcel.readDriverExcel(driverSheetPath);

            //// Read Config file and XML OR
            ReadConfigFile rr = new ReadConfigFile();
            rr.ReadMyConfigFile();
            ReadXML rXml = new ReadXML();
            rXml.XMLReader();

            //// Create Main Extent Report Folder
            Variables.createExtinctReportPath();

            //// List of Thread to execute multiple threads together
            List<Thread> threadList = new ArrayList<Thread>();

            for (Entry<String, HashMap<String, String[]>> driverObject : Variables.driverExcelObject
                    .entrySet()) {
                String className = driverObject.getKey();
                HashMap<String, String[]> testCase = Variables.driverExcelObject
                        .get(className);
                ThreadClass th = new ThreadClass(className, testCase);
                Thread tobj = null;
                tobj = new Thread(th);
                tobj.start();
                threadList.add(tobj);
                Thread.sleep(10000);
            }
            for (Thread t : threadList) {
                // Waits for this thread to die
                t.join();
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
