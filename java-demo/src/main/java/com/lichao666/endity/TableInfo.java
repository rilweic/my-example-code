package com.lichao666.endity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Data               // 自动生成 getters, setters, toString, equals, hashCode
@AllArgsConstructor // 自动生成全参数构造函数
@NoArgsConstructor  // 自动生成无参数构造函数
@ToString
public class TableInfo {
    private String englishName;     // 表英文名称
    private String chineseName;    // 表中文名称
    private String businessDefinition;  // 表业务定义
    private Set<TableColumn> columns;  // 表的字段信息

    public void addColumn(TableColumn column){
        columns.add(column);
    }
}


