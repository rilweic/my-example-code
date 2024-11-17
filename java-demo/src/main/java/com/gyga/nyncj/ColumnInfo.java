package com.gyga.nyncj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data               // 自动生成 getters, setters, toString, equals, hashCode
@AllArgsConstructor // 自动生成全参数构造函数
@NoArgsConstructor  // 自动生成无参数构造函数
@ToString
public class ColumnInfo {
    private String columnName;
    private String dataType;
//    private Integer columnSize;
    private String columnComment;
//    private Boolean isNullable;
//    private String defaultValue;
}
