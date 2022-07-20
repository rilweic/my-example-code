package com.lichao666.milvus_test;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ResidentModel {
    /**
     * 姓名
     */
    @ExcelProperty(index = 0)
    private String name;

    /**
     * 年龄
     */
    @ExcelProperty(index = 1)
    private String feature;
}

