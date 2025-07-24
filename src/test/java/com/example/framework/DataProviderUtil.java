package com.example.framework;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

/**
 * Utility class for reading test data from various sources (CSV, Excel, JSON)
 */
public class DataProviderUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(DataProviderUtil.class);
    
    /**
     * Read test data from CSV file
     */
    public static Object[][] readCSVData(String filePath) {
        List<Object[]> data = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            String[] headers = null;
            boolean isFirstLine = true;
            
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                
                if (isFirstLine) {
                    headers = values;
                    isFirstLine = false;
                } else {
                    Map<String, String> rowData = new HashMap<>();
                    for (int i = 0; i < headers.length && i < values.length; i++) {
                        rowData.put(headers[i].trim(), values[i].trim());
                    }
                    data.add(new Object[]{rowData});
                }
            }
        } catch (IOException e) {
            logger.error("Error reading CSV file: {}", filePath, e);
            throw new RuntimeException("Failed to read CSV data", e);
        }
        
        return data.toArray(new Object[0][]);
    }
    
    /**
     * Read test data from Excel file
     */
    public static Object[][] readExcelData(String filePath, String sheetName) {
        List<Object[]> data = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet not found: " + sheetName);
            }
            
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new RuntimeException("Header row not found in sheet: " + sheetName);
            }
            
            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(getCellValueAsString(cell));
            }
            
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Map<String, String> rowData = new HashMap<>();
                    for (int j = 0; j < headers.size(); j++) {
                        Cell cell = row.getCell(j);
                        String value = cell != null ? getCellValueAsString(cell) : "";
                        rowData.put(headers.get(j), value);
                    }
                    data.add(new Object[]{rowData});
                }
            }
            
        } catch (IOException e) {
            logger.error("Error reading Excel file: {}", filePath, e);
            throw new RuntimeException("Failed to read Excel data", e);
        }
        
        return data.toArray(new Object[0][]);
    }
    
    /**
     * Read test data from JSON file
     */
    public static Object[][] readJSONData(String filePath) {
        List<Object[]> data = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            JsonNode rootNode = mapper.readTree(new File(filePath));
            
            if (rootNode.isArray()) {
                for (JsonNode node : rootNode) {
                    Map<String, String> rowData = new HashMap<>();
                    node.fields().forEachRemaining(entry -> 
                        rowData.put(entry.getKey(), entry.getValue().asText()));
                    data.add(new Object[]{rowData});
                }
            } else {
                Map<String, String> rowData = new HashMap<>();
                rootNode.fields().forEachRemaining(entry -> 
                    rowData.put(entry.getKey(), entry.getValue().asText()));
                data.add(new Object[]{rowData});
            }
            
        } catch (IOException e) {
            logger.error("Error reading JSON file: {}", filePath, e);
            throw new RuntimeException("Failed to read JSON data", e);
        }
        
        return data.toArray(new Object[0][]);
    }
    
    /**
     * Helper method to get cell value as string
     */
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}
