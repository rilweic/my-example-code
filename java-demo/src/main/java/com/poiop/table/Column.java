package com.poiop.table;

import lombok.Data;

@Data
public class Column {
    private String name;
    private String type;
    private String length;
    private String comment;
    private String defaultValue;

    // Constructor, getters, and setters
    // ...


    @Override
    public String toString() {
        return String.format("列名: %s, 字段类型: %s, 字段长度: %s, 列注释: %s, 默认值: %s",
                name, type, length, comment, defaultValue);
    }
}