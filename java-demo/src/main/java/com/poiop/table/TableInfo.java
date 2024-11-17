package com.poiop.table;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TableInfo {
    private String category;
    private String requirementName;
    private String tableName;
    private String tableDescription;
    private List<Column> columns;

    public TableInfo() {
        this.columns = new ArrayList<>();
    }

    // Getters and setters
    // ...

    public void addColumn(Column column) {
        this.columns.add(column);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("数据应用需求分类: ").append(category).append("\n");
        sb.append("数据应用需求名称: ").append(requirementName).append("\n");
        sb.append("表名: ").append(tableName).append("\n");
        sb.append("表描述: ").append(tableDescription).append("\n");
        sb.append("列信息:\n");
        for (Column column : columns) {
            sb.append("  ").append(column.toString()).append("\n");
        }
        return sb.toString();
    }
}



