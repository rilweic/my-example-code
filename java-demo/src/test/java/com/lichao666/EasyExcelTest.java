package com.lichao666;

import com.alibaba.excel.EasyExcel;
import com.lichao666.endity.IndexRowData;
import com.lichao666.listener.IndexOrNameDataListener;
import junit.framework.TestCase;

import java.net.URL;


public class EasyExcelTest extends TestCase {

    public void testIndexOrNameRead() {
        URL resourceUrl = EasyExcelTest.class.getClassLoader().getResource("L2-L5.xlsx");

        System.out.println(resourceUrl);
        // 这里默认读取第一个sheet
        EasyExcel.read(resourceUrl.getPath(), IndexRowData.class, new IndexOrNameDataListener()).headRowNumber(2).sheet("5.2确认属性").doRead();
    }



}
