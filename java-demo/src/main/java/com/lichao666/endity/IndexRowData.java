package com.lichao666.endity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 通过名称来识别列
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class IndexRowData {
    @ExcelProperty("*逻辑实体中文名称")
    private String logicEntityChineseName;
    @ExcelProperty("*逻辑实体英文名称")
    private String logicEntityEnglishName;

    @ExcelProperty("属性设计中文名称")
    private String propertyChineseName;

    @ExcelProperty("属性设计英文名称")
    private String propertyEnglishName;

    @ExcelProperty("字段类型")
    private String fieldType;

    @ExcelProperty("字段长度")
    private String fieldLength;



}
