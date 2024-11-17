package com.gyga.nyncj.test;

import com.gyga.nyncj.ColumnInfo;
import com.gyga.nyncj.TableInfo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class EasyExcelMergeCells {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String fileName = "merged_cells.xlsx";

        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableName("用户信息表");
        tableInfo.setTableComment("包含用户的基本信息");
        List<ColumnInfo> columns = new ArrayList<>();
        columns.add(new ColumnInfo("用户名", "String", "用户的登录名"));
        columns.add(new ColumnInfo("年龄", "Integer", "用户的年龄"));
        tableInfo.setColumns(columns);

        ExcelUtil.writeTableInfo("abc",tableInfo);
    }
}