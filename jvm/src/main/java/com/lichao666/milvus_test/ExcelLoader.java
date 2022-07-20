package com.lichao666.milvus_test;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class ExcelListener extends AnalysisEventListener<ResidentModel> {

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息"   + headMap);
    }

    @Override
    public void invoke(ResidentModel residentModel, AnalysisContext analysisContext) {

//        System.out.println(residentModel);
        ExcelLoader.residentModels.add(residentModel);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("所有数据解析完成");
    }
}

public  class ExcelLoader {

    public static List<ResidentModel> residentModels = new ArrayList<>();

    public static List<ResidentModel> getResidentModels(String filePath) {
        EasyExcel.read(filePath, ResidentModel.class, new ExcelListener()).sheet("Sheet1").doRead();
        return residentModels;
    }


}
