package com.poiop;


import com.alibaba.excel.util.StringUtils;
import com.poiop.table.Column;
import com.poiop.table.TableInfo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyle;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHpsMeasure;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPrGeneral;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyle;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTbl;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STStyleType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PoiOperator {
    public static void main(String[] args) throws Exception {
        URL resourceUrl = PoiOperator.class.getClassLoader().getResource("table_list_demo.xlsx");
        String filePath = resourceUrl.getPath();
        String wordFilePath = "/Users/lichao/IdeaProjects/big-data/java-demo/src/main/resources/list.docx";
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis);
             XWPFDocument document = new XWPFDocument()) {

            addCustomHeadingStyle(document, "Heading1", 6, 14, "仿宋");
            addCustomHeadingStyle(document, "Heading2", 7, 12, "仿宋");
//            addCustomHeadingStyle(document, "Heading3", 3, 12, "Calibri");


            Sheet sheet = workbook.getSheetAt(0);
            Map<String, Map<String, List<TableInfo>>> categorizedTables = readExcelData(sheet);


            writeToWord(document, categorizedTables);

            // Save the document
            try (FileOutputStream out = new FileOutputStream(wordFilePath)) {
                document.write(out);
            }

            System.out.println("Word document created successfully.");

            printCategorizedTables(categorizedTables);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void printCategorizedTables(Map<String, Map<String, List<TableInfo>>> categorizedTables) {
        for (Map.Entry<String, Map<String, List<TableInfo>>> categoryEntry : categorizedTables.entrySet()) {
            System.out.println("一级分类: " + categoryEntry.getKey());
            for (Map.Entry<String, List<TableInfo>> requirementEntry : categoryEntry.getValue().entrySet()) {
                System.out.println("  二级分类: " + requirementEntry.getKey());
                for (TableInfo table : requirementEntry.getValue()) {
                    System.out.println("    " + table);
                    System.out.println("    --------------------");
                }
            }
            System.out.println("====================");
        }
    }

    private static void addCustomHeadingStyle(XWPFDocument doc, String styleId, int level, int fontSize, String fontName) {
        XWPFStyles styles = doc.createStyles();

        CTStyle ctStyle = CTStyle.Factory.newInstance();
        ctStyle.setStyleId(styleId);

        CTString styleName = CTString.Factory.newInstance();
        styleName.setVal("Heading " + level);
        ctStyle.setName(styleName);

        CTPPrGeneral ppr = ctStyle.addNewPPr();
        CTSpacing spacing = ppr.addNewSpacing();
        spacing.setAfter(BigInteger.valueOf(200));  // 设置段后间距
        spacing.setBefore(BigInteger.valueOf(200)); // 设置段前间距

        CTFonts fonts = CTFonts.Factory.newInstance();
        fonts.setAscii(fontName);
        fonts.setHAnsi(fontName);

        CTHpsMeasure size = CTHpsMeasure.Factory.newInstance();
        size.setVal(BigInteger.valueOf(2 * fontSize));  // Word大小以半点为单位

        CTRPr rpr = ctStyle.addNewRPr();
        rpr.addNewRFonts().setAscii(fontName);
        rpr.addNewRFonts().setHAnsi(fontName);
        rpr.addNewSz().setVal(BigInteger.valueOf(2 * fontSize));
        rpr.addNewSzCs().setVal(BigInteger.valueOf(2 * fontSize));

        XWPFStyle style = new XWPFStyle(ctStyle);  // 注意这里的构造函数调用
        style.setType(STStyleType.PARAGRAPH);
        styles.addStyle(style);
    }

    private static Map<String, Map<String, List<TableInfo>>> readExcelData(Sheet sheet) {
        Map<String, Map<String, List<TableInfo>>> categorizedTables = new LinkedHashMap<>();
        TableInfo currentTable = null;

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Skip header row

            Cell categoryCell = row.getCell(1);
            Cell requirementNameCell = row.getCell(2);
            Cell tableNameCell = row.getCell(4);
            Cell tableDescriptionCell = row.getCell(5);

            if ("ods_rleims_cgtp_rectification_manage".equals(getCellValueAsString(tableNameCell))) {
                System.out.println(getCellValueAsString(tableNameCell));
            }

            if (categoryCell != null && categoryCell.getCellType() != CellType.BLANK) {
                if (currentTable != null) {
                    addTableToCategory(categorizedTables, currentTable);
                }
                currentTable = new TableInfo();
                currentTable.setCategory(getCellValueAsString(categoryCell));
                currentTable.setRequirementName(getCellValueAsString(requirementNameCell));
                currentTable.setTableName(getCellValueAsString(tableNameCell));
                if (StringUtils.isEmpty(currentTable.getTableName())) {
                    System.out.println(currentTable.getCategory());
                }
                currentTable.setTableDescription(getCellValueAsString(tableDescriptionCell));
            }

            if (currentTable != null) {
                Column column = new Column();
                column.setName(getCellValueAsString(row.getCell(6)));
                column.setType(getCellValueAsString(row.getCell(7)));
                column.setLength(getCellValueAsString(row.getCell(8)));
                column.setComment(getCellValueAsString(row.getCell(9)));
                column.setDefaultValue(getCellValueAsString(row.getCell(11)));
                currentTable.addColumn(column);
            }
        }

        if (currentTable != null) {
            addTableToCategory(categorizedTables, currentTable);
        }

        return categorizedTables;
    }

    private static Map<String, Map<String, List<TableInfo>>> readExcelData2(Sheet sheet) {
        Map<String, Map<String, List<TableInfo>>> categorizedTables = new LinkedHashMap<>();
        TableInfo currentTable = null;

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Skip header row

            Cell categoryCell = getMergedCell(sheet, row.getRowNum(), 1);
            Cell requirementNameCell = getMergedCell(sheet, row.getRowNum(), 2);
            Cell tableNameCell = getMergedCell(sheet, row.getRowNum(), 4);
            Cell tableDescriptionCell = getMergedCell(sheet, row.getRowNum(), 5);

            if (categoryCell != null && categoryCell.getCellType() != CellType.BLANK) {
                if (currentTable != null) {
                    addTableToCategory(categorizedTables, currentTable);
                }
                currentTable = new TableInfo();
                currentTable.setCategory(getCellValueAsString(categoryCell));
                currentTable.setRequirementName(getCellValueAsString(requirementNameCell));
                currentTable.setTableName(getCellValueAsString(tableNameCell));
                currentTable.setTableDescription(getCellValueAsString(tableDescriptionCell));
            }

            if (currentTable != null) {
                Column column = new Column();
                column.setName(getCellValueAsString(row.getCell(6)));
                column.setType(getCellValueAsString(row.getCell(7)));
                column.setLength(getCellValueAsString(row.getCell(8)));
                column.setComment(getCellValueAsString(row.getCell(9)));
                column.setDefaultValue(getCellValueAsString(row.getCell(11)));
                currentTable.addColumn(column);
            }
        }

        if (currentTable != null) {
            addTableToCategory(categorizedTables, currentTable);
        }

        return categorizedTables;
    }

    private static Cell getMergedCell(Sheet sheet, int rowNum, int colNum) {
        for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
            CellRangeAddress mergedRegion = sheet.getMergedRegion(i);
            if (mergedRegion.isInRange(rowNum, colNum)) {
                Row mergedRow = sheet.getRow(mergedRegion.getFirstRow());
                return mergedRow.getCell(mergedRegion.getFirstColumn());
            }
        }
        return sheet.getRow(rowNum).getCell(colNum);
    }

    private static void addTableToCategory(Map<String, Map<String, List<TableInfo>>> categorizedTables, TableInfo table) {
        categorizedTables
                .computeIfAbsent(table.getCategory(), k -> new LinkedHashMap<>())
                .computeIfAbsent(table.getRequirementName(), k -> new ArrayList<>())
                .add(table);
    }

    private static void writeToWord(XWPFDocument document, Map<String, Map<String, List<TableInfo>>> categorizedTables) {
        for (Map.Entry<String, Map<String, List<TableInfo>>> categoryEntry : categorizedTables.entrySet()) {
            // 一级标题
            XWPFParagraph para1 = document.createParagraph();
            para1.setStyle("Heading1");
            XWPFRun run1 = para1.createRun();
            run1.setText(categoryEntry.getKey());

            for (Map.Entry<String, List<TableInfo>> requirementEntry : categoryEntry.getValue().entrySet()) {
                // 二级标题
                XWPFParagraph para2 = document.createParagraph();
                para2.setStyle("Heading2");
                XWPFRun run2 = para2.createRun();
                run2.setText(requirementEntry.getKey());

                for (TableInfo table : requirementEntry.getValue()) {
                    // 三级标题
                    XWPFParagraph para3 = document.createParagraph();
                    para3.setStyle("Heading3");
                    XWPFRun run3 = para3.createRun();
                    run3.setText(table.getTableName() + " - " + table.getTableDescription());

                    // 创建表格
                    XWPFTable xwpfTable = document.createTable();
                    setTableWidth(xwpfTable, 9000); // 设置表格宽度

                    XWPFTableRow headerRow = xwpfTable.getRow(0);

                    setHeaderCell(headerRow.getCell(0), "列名");
                    setHeaderCell(headerRow.addNewTableCell(), "字段类型");
                    setHeaderCell(headerRow.addNewTableCell(), "字段长度");
                    setHeaderCell(headerRow.addNewTableCell(), "列注释");
                    setHeaderCell(headerRow.addNewTableCell(), "默认值");
                    for (Column column : table.getColumns()) {
                        XWPFTableRow row = xwpfTable.createRow();
                        row.getCell(0).setText(column.getName());
                        row.getCell(1).setText(column.getType());
                        row.getCell(2).setText(column.getLength());
                        row.getCell(3).setText(column.getComment());
                        row.getCell(4).setText(column.getDefaultValue());
                    }

                    document.createParagraph(); // 添加空行
                }
            }
        }
    }

    private static void setHeaderCell(XWPFTableCell cell, String text) {
        cell.setText(text);
        cell.setColor("CCCCCC"); // 设置背景色为灰色
    }

    private static void setTableWidth(XWPFTable table, int width) {
        CTTbl tableCT = table.getCTTbl();
        CTTblPr tblPr = tableCT.getTblPr();
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        tblWidth.setW(BigInteger.valueOf(width));
        tblWidth.setType(STTblWidth.DXA);
    }
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim().replaceAll(" ", "");
            case NUMERIC:
                return String.valueOf((int)cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }

    private static void setHeadingStyle(XWPFParagraph paragraph, String styleName) {
        paragraph.setStyle(styleName);
        // 如果样式设置失败，使用替代方法
        if (paragraph.getStyle() == null) {
            paragraph.setNumID(BigInteger.valueOf(getHeadingLevel(styleName)));
        }
    }

    private static int getHeadingLevel(String styleName) {
        switch (styleName) {
            case "Heading1":
                return 1;
            case "Heading2":
                return 2;
            case "Heading3":
                return 3;
            default:
                return 0;
        }
    }
}
