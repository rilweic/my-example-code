package com.lichao666.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.util.StringUtils;
import com.lichao666.endity.IndexRowData;
import com.lichao666.endity.TableColumn;
import com.lichao666.endity.TableInfo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IndexOrNameDataListener extends AnalysisEventListener<IndexRowData> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 50;
    private List<IndexRowData> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    private Map<String, TableInfo> tableMap = new LinkedHashMap<>();

    private Set<String> types = new HashSet<>();

    @Override
    public void invoke(IndexRowData data, AnalysisContext context) {
//        System.out.println("解析到一条数据:" + JSON.toJSONString(data));
        cachedDataList.add(data);
        if (cachedDataList.size() >= BATCH_COUNT) {
            processData();
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    // 处理最后剩余的数据信息
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        processData();
        System.out.println("所有数据解析完成！");

        ClassLoader classLoader = IndexOrNameDataListener.class.getClassLoader();
        String basePath = "/Users/lichao/IdeaProjects/big-data/java-demo/src/main/java/com/lichao666";
        String filePath = basePath + File.separator + "ddl.sql";
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                // 不存在则创建新文件
                boolean isCreated = file.createNewFile();
                if (isCreated) {
                    System.out.println("文件已创建：" + filePath);
                } else {
                    System.out.println("文件未能创建：" + filePath);
                }
            } catch (IOException e) {
                // 捕获异常，例如没有权限或路径错误
                System.err.println("无法创建文件，错误：" + e.getMessage());
            }
        } else {
            System.out.println("文件已存在：" + filePath);
            System.out.println("清空文件内容");
            clearFile(filePath);
        }

        FileWriter fileWriter = null;
        PrintWriter printWriter = null;

        try {
            // 第二个参数为true表示开启追加模式
            fileWriter = new FileWriter(filePath, true);
            printWriter = new PrintWriter(fileWriter);

            for (Map.Entry<String, TableInfo> entry : tableMap.entrySet()) {
                String ddl = generateCreateTableSQL(entry.getValue());
                printWriter.println(ddl);
            }

        } catch (IOException e) {
            System.err.println("写入文件时出错：" + e.getMessage());
        } finally {
            // 关闭流确保不会有资源泄漏
            if (printWriter != null) {
                printWriter.close();
            }
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                System.err.println("关闭文件时出错：" + e.getMessage());
            }
        }


    }

    public void clearFile(String filePath) {
        try (FileWriter fw = new FileWriter(filePath, false)) {
            // 自动资源管理块（try-with-resources）会在结束时自动关闭资源，这里什么都不做，从而达到清空文件的效果
        } catch (IOException e) {
            System.err.println("无法清空文件内容: " + e.getMessage());
        }
    }

    /**
     * 加上存储数据库
     */
    private void processData() {
//        System.out.println(cachedDataList.size() + " 条数据，开始存储数据库！");

        for (IndexRowData l : cachedDataList) {
            String fieldType = l.getFieldType();
            // 去除注释和空白行
            if (StringUtils.isEmpty(fieldType) || fieldType.startsWith("业务")) {
                break;
            }
            TableColumn column;

            if (fieldType.toUpperCase().startsWith("DECIMAL")) {
                column = generateColumn(l);
            } else {
                fieldType = fieldType.toUpperCase().replaceAll("\\(.*?\\)", "").replace(")", "").replaceAll("[\\s\\p{Z}]+", "");

                column = new TableColumn(
                        l.getPropertyEnglishName(), l.getPropertyChineseName(), fieldType, l.getFieldLength()
                );
            }

            types.add(fieldType);
            String tableEngName = l.getLogicEntityEnglishName();

            if (StringUtils.isEmpty(tableEngName)) {
                break;
            }
            tableEngName = tableEngName.replace(" ", "_");

            if (!tableMap.containsKey(tableEngName)) {
                TableInfo tmp = new TableInfo(tableEngName, l.getLogicEntityChineseName(), "", new HashSet<>());
                tmp.addColumn(column);
                tableMap.put(tableEngName, tmp);
            } else {
                TableInfo tableInfo = tableMap.get(tableEngName);
                tableInfo.addColumn(column);
            }

        }

    }

    // 生成建表语句
    public String generateCreateTableSQL(TableInfo tableInfo) {

        StringBuilder createSQL = new StringBuilder();
        createSQL.append("DROP TABLE IF EXISTS `").append(tableInfo.getEnglishName()).append("` ;\n");
        createSQL.append("CREATE TABLE `");
        createSQL.append(tableInfo.getEnglishName()).append("` (\n");

        for (TableColumn column : tableInfo.getColumns()) {
            createSQL.append("`").append(column.getEnglishName()).append("` ");
            String convertColumnType = convertColumnType(column);

            String fieldLength = column.getLength();

            if (StringUtils.isBlank(fieldLength)) {
                fieldLength = "50";
            }
            if ("VARCHAR".equals(convertColumnType)) {
                if (column.getLength() != null ) {
                    if(column.getLength().contains(",") || column.getLength().contains("，") || column.getLength().contains("（")){
                        column.setType("DECIMAL");
                        convertColumnType = "DECIMAL";
                    }

                }
            }
            // 如果是字符类型并且有长度定义，添加长度
            if ("VARCHAR".equals(convertColumnType) || "CHAR".equals(convertColumnType)) {
                createSQL.append(convertColumnType).append(" ");
                createSQL.append("(").append(fieldLength).append(")");
            } else if ("DECIMAL".equals(column.getType())) {
                createSQL.append(convertColumnType).append(" ");
                createSQL.append("(").append(column.getLength()
                        .replace("，", ",")
                        .replace("（", "")
                        .replace("）", "")
                        .replace("(", "")
                        .replace(")", "")
                ).append(")");
            } else if("TIMESTAMP".equals(convertColumnType)){
                createSQL.append(convertColumnType).append(" ").append("DEFAULT CURRENT_TIMESTAMP");
            }
            else {
                createSQL.append(convertColumnType).append(" ");
            }


            createSQL.append(" COMMENT '").append(column.getChineseName()).append("',\n");
        }

        // 移除最后的逗号
        int lastIndex = createSQL.lastIndexOf(",");
        if (lastIndex > -1) {
            createSQL.deleteCharAt(lastIndex);
        }

        createSQL.append("\n) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ");

        if (tableInfo.getChineseName() != null && !tableInfo.getChineseName().isEmpty()) {
            createSQL.append(" COMMENT='").append(tableInfo.getChineseName()).append("'");
        }
        createSQL.append(";\n -- -----------------------------------------------");

        return createSQL.toString();
    }

    private String convertColumnType(TableColumn column) {
        // 在这里添加更多的映射规则
        String mysqlType = "";

        switch (column.getType().toUpperCase()) {
            case "NUMBER":
                mysqlType = "DECIMAL";
                break;
            case "BLOB":
            case "LONGTEXT":
            case "TEXT":
            case "TIME":
            case "BIT":
            case "DATE":
            case "DATETIME":
            case "TIMESTAMP":
            case "VARCHAR":
            case "TINYINT":
            case "BIGINT":
            case "INT":
            case "CHAR":
            case "DOUBLE":
                mysqlType = column.getType();
                break;
            case "VARCHAR2":
                mysqlType = "VARCHAR";
                break;
            default:
                return column.getType();
        }
        return mysqlType;
    }


    public String[] normalizeTypeAndLength(String type, String length) {
        String normalizedType = "DECIMAL";
        String normalizedLength = "";

        // 检查type中是否已经包含了精度和规模
        if (type.toUpperCase().startsWith("DECIMAL") && type.contains("(") && type.contains(")")) {
            // 从type字符串中提取精度和规模
            normalizedLength = type.substring(type.indexOf('(') + 1, type.indexOf(')'));
        } else if (length != null && !length.isEmpty()) {
            // 如果length包含括号，则去除括号
            normalizedLength = length.replaceAll("[()]", "");
        }

        return new String[]{normalizedType, normalizedLength};
    }

    private TableColumn generateColumn(IndexRowData row) {
        String type = row.getFieldType();
        String length = row.getFieldLength();
        if (type.toUpperCase().startsWith("DECIMAL")) {
            String[] ss = normalizeTypeAndLength(row.getFieldType(), row.getFieldLength());
            type = ss[0];
            length = ss[1];
        }


        TableColumn column = new TableColumn(
                row.getPropertyEnglishName(),
                row.getPropertyChineseName(),
                type,
                length
        );
        return column;
    }

}
