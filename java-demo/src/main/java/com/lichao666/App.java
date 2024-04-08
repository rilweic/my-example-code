package com.lichao666;

import com.alibaba.excel.EasyExcel;
import com.lichao666.endity.IndexRowData;
import com.lichao666.listener.IndexOrNameDataListener;

import java.net.URL;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        URL resourceUrl = App.class.getClassLoader().getResource("L2-L5.xlsx");
        System.out.println(resourceUrl);
        // 这里默认读取第一个sheet
        EasyExcel.read(
                resourceUrl.getPath(),
                IndexRowData.class,
                new IndexOrNameDataListener()
        ).headRowNumber(2).sheet("5.1识别属性").doRead();

    }



}
