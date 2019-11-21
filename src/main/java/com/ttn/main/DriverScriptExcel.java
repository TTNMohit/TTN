package com.ttn.main;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.ttn.resources.Variables;

import java.io.FileInputStream;
import java.util.HashMap;

public class DriverScriptExcel {
    public static Row row;

    public static void readDriverExcel(String path) throws Exception {
        String sheetPath = path;
        try {
            FileInputStream inputStream = new FileInputStream(sheetPath);
            @SuppressWarnings("resource")
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            Sheet firstSheet = workbook.getSheet("Driver");
            int rows = firstSheet.getLastRowNum();
            for (int i = 1; i < rows + 1; i++) {
                row = firstSheet.getRow(i);
                String component = getCellData(1);
                String description = getCellData(2);
                String runMode = getCellData(3);
                String device = getCellData(4);
                String browser = getCellData(5);
                String server = getCellData(6);
                String email = getCellData(7);

                HashMap<String, String[]> temp = new HashMap<String, String[]>();
                if (runMode.equalsIgnoreCase("y")) {
                    Sheet componentSheet = workbook.getSheet(component);
                    int rows1 = componentSheet.getLastRowNum();
                    for (int j = 1; j < rows1 + 1; j++) {
                        row = componentSheet.getRow(j);
                        String testCase = getCellData(1);
                        String shortDecription = getCellData(2);
                        String componentDescription = getCellData(3);
                        String componentRunMode = getCellData(4);
                        if (componentRunMode.equalsIgnoreCase("y")) {
                            temp.put(testCase, new String[] { description,
                                    device, browser, shortDecription,
                                    componentDescription, server, email });
                        }

                    }
                    Variables.driverExcelObject.put(component, temp);
                }
            }
        }

        catch (Exception e) {

            throw e;
        }
    }

    @SuppressWarnings("deprecation")
    public static String getCellData(int colNum) {
        String cellData = "";
        Cell cell = row.getCell(colNum);
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                cellData = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                cellData = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                cellData = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                cellData = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BLANK:
                cellData = "";
        }
        return cellData;
    }
}
