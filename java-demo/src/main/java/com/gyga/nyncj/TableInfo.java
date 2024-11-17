package com.gyga.nyncj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data               // 自动生成 getters, setters, toString, equals, hashCode
@AllArgsConstructor // 自动生成全参数构造函数
@NoArgsConstructor  // 自动生成无参数构造函数
@ToString
public class TableInfo {
    private String tableName;
    private String tableComment;
    private List<ColumnInfo> columns;
}
