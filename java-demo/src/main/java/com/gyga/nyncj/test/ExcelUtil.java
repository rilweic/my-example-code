package com.gyga.nyncj.test;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import com.gyga.nyncj.ColumnInfo;
import com.gyga.nyncj.TableInfo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExcelUtil {
    public static void writeTableInfo(String fileName, TableInfo tableInfo) {
        // 定义Excel的路径
        String filePath = fileName + ".xlsx";

        // 写入Excel
        EasyExcel.write(filePath)
//                .head(head(tableInfo.getColumns().size()))
//                .registerWriteHandler(new CustomMergeStrategy())
                .sheet("meta")
                .doWrite(dataList(tableInfo));
    }

    // 生成表头
    private static List<List<String>> head(int columnSize) {
        List<List<String>> list = new ArrayList<>();
        for (int i = 0; i < columnSize; i++) {
            list.add(Arrays.asList("表名", "表注释", "列名", "数据类型", "列注释"));
        }
        return list;
    }

    // 转换数据
    private static List<List<Object>> dataList(TableInfo tableInfo) {
        List<List<Object>> list = new ArrayList<>();
        for (ColumnInfo column : tableInfo.getColumns()) {
            list.add(Arrays.asList(tableInfo.getTableName(), tableInfo.getTableComment(),
                    column.getColumnName(), column.getDataType(), column.getColumnComment()));
        }
        return list;
    }

    // 自定义合并策略
    private static class CustomMergeStrategy extends AbstractMergeStrategy {
        @Override
        protected void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex) {
            if (head.getColumnIndex() == 0 || head.getColumnIndex() == 1) {
                if (relativeRowIndex == 0) {
                    sheet.addMergedRegionUnsafe(new CellRangeAddress(1, sheet.getLastRowNum(), head.getColumnIndex(), head.getColumnIndex()));
                }
            }
        }
    }
}
