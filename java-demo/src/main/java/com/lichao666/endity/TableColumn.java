package com.lichao666.endity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data               // 自动生成 getters, setters, toString, equals, hashCode
@AllArgsConstructor // 自动生成全参数构造函数
@NoArgsConstructor  // 自动生成无参数构造函数
public class TableColumn {
    private String englishName;   // 字段英文名称
    private String chineseName;  // 字段中文注释
    private String type;         // 字段类型
    private String length;          // 字段长度

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableColumn column = (TableColumn) o;
        return Objects.equals(englishName, column.englishName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(englishName);
    }
}
