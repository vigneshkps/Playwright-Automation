package org.automationtesting.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;


public class ExcelUtils {

    private static final String EXCEL_PATH = "test-data/TestData.xlsx";

    // ---------- Load Sheet ----------
    private static Sheet getSheet(String sheetName) {
        try {
            FileInputStream file = new FileInputStream(EXCEL_PATH);
            Workbook workbook = new XSSFWorkbook(file);
            return workbook.getSheet(sheetName);
        } catch (Exception e) {
            throw new RuntimeException("Error loading Excel sheet: " + e.getMessage());
        }
    }

    // ---------- Existing Method (Index-based) ----------
    public static String getCellData(String sheetName, int rowNum, int colNum) {
        Sheet sheet = getSheet(sheetName);
        Row row = sheet.getRow(rowNum);
        Cell cell = row.getCell(colNum);
        return cell.getStringCellValue();
    }

    // ---------- NEW Method (Header-based) ----------
    public static String getCellDataByColumnName(
            String sheetName,
            int rowNum,
            String columnName) {

        Sheet sheet = getSheet(sheetName);
        Row headerRow = sheet.getRow(0);

        int columnIndex = -1;

        for (Cell cell : headerRow) {
            if (cell.getStringCellValue().equalsIgnoreCase(columnName)) {
                columnIndex = cell.getColumnIndex();
                break;
            }
        }

        if (columnIndex == -1) {
            throw new RuntimeException("Column not found: " + columnName);
        }

        Row dataRow = sheet.getRow(rowNum);
        Cell dataCell = dataRow.getCell(columnIndex);

        return dataCell.getStringCellValue();
    }
    public static void setCellDataByColumnName(
            String sheetName,
            int rowNum,
            String columnName,
            String value) {

        try {
            FileInputStream file = new FileInputStream(EXCEL_PATH);
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheet(sheetName);

            Row headerRow = sheet.getRow(0);
            int columnIndex = -1;

            for (Cell cell : headerRow) {
                if (cell.getStringCellValue().equalsIgnoreCase(columnName)) {
                    columnIndex = cell.getColumnIndex();
                    break;
                }
            }

            if (columnIndex == -1) {
                throw new RuntimeException("Column not found: " + columnName);
            }

            Row row = sheet.getRow(rowNum);
            if (row == null) {
                row = sheet.createRow(rowNum);
            }

            Cell cell = row.getCell(columnIndex);
            if (cell == null) {
                cell = row.createCell(columnIndex);
            }

            cell.setCellValue(value);

            FileOutputStream out = new FileOutputStream(EXCEL_PATH);
            workbook.write(out);
            out.close();
            workbook.close();

        } catch (Exception e) {
            throw new RuntimeException("Error writing to Excel: " + e.getMessage());
        }
    }
    public static int getRowCount(String sheetName) {
        try {
            FileInputStream file = new FileInputStream(EXCEL_PATH);
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheet(sheetName);

            // getLastRowNum() is 0-based
            int rowCount = sheet.getLastRowNum();

            workbook.close();
            return rowCount;

        } catch (Exception e) {
            throw new RuntimeException("Error getting row count: " + e.getMessage());
        }
    }


}
