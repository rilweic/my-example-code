//package com.gyga.nyncj;
//
//import com.alibaba.excel.EasyExcel;
//
//import java.sql.Connection;
//import java.sql.DatabaseMetaData;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class KingbaseOperator {
//    public static void main(String[] args) {
//        // 数据库连接配置
//        String url = "jdbc:kingbase8://192.168.38.85:50037/data_center";
////        String url = "jdbc:kingbase8://192.168.90.30:54321/data_center";
//        String user = "dc_dev";
//        String password = "cUdTdc@2024";
//
//        Connection connection = null;
//        ResultSet tables = null;
//        int count = 0;
//
//        String excelFilePath = "gyny2.xlsx";
//        String sheetName= "metadata";
////        List<TableInfo> tableInfos = new ArrayList<>();
//        List<ExcelRow> excelRows = new ArrayList<>();
//        try {
//            // 显式加载JDBC驱动
//            Class.forName("com.kingbase8.Driver");
//            // 建立数据库连接
//            connection = DriverManager.getConnection(url, user, password);
//            // 获取数据库元数据
//            DatabaseMetaData metaData = connection.getMetaData();
//            // 获取数据库中所有表的列表
//            tables = metaData.getTables(null, "ods", "%", new String[]{"TABLE"});
//
//            while (tables.next()) {
//                String tableName = tables.getString("TABLE_NAME");
////                System.out.println("Table: " + tableName);
//                String tableComment = getTableComment(connection, tableName); // 获取表注释的方法调用
////                System.out.println("tableComment: " + tableComment);
////                System.out.println("---------------------------");
//                ++count;
//
////                TableInfo tableInfo = new TableInfo();
////                tableInfo.setTableName(tableName);
////                tableInfo.setTableComment(tableComment);
//
//                List<ColumnInfo> columns = new ArrayList<>();
//                // 获取当前表的列信息
//                ResultSet columnsRs = metaData.getColumns(null, null, tableName, null);
//                while (columnsRs.next()) {
//                    String columnName = columnsRs.getString("COLUMN_NAME");
//                    String dataTypeName = columnsRs.getString("TYPE_NAME");
//                    int columnSize = columnsRs.getInt("COLUMN_SIZE");
//                    String columnComment = columnsRs.getString("REMARKS");  // 获取字段注释
////                    System.out.println("列名: " + columnName + "字段注释:" + columnComment + "\t" + "数据类型: " + dataTypeName + "\t" + "字段长度: " + columnSize);
////                    System.out.println();
//                    // 添加列信息
////                    ColumnInfo column = new ColumnInfo();
////                    column.setColumnName(columnsRs.getString("COLUMN_NAME"));
////                    column.setDataType(columnsRs.getString("TYPE_NAME"));
////                    column.setColumnSize(columnsRs.getInt("COLUMN_SIZE"));
////                    column.setColumnComment(columnsRs.getString("REMARKS"));
////                    column.setIsNullable("YES".equals(columnsRs.getString("IS_NULLABLE")));
////                    column.setDefaultValue(columnsRs.getString("COLUMN_DEF"));
////                    columns.add(column);
//
//                    ExcelRow row = new ExcelRow();
//                    row.setTableName(tableName);
//                    row.setTableComment(tableComment);
//                    row.setColumnName(columnsRs.getString("COLUMN_NAME"));
//                    row.setDataType(columnsRs.getString("TYPE_NAME"));
//                    row.setColumnSize(columnsRs.getInt("COLUMN_SIZE"));
//                    row.setColumnComment(columnsRs.getString("REMARKS"));
//                    row.setIsNullable("YES".equals(columnsRs.getString("IS_NULLABLE")));
//                    row.setDefaultValue(columnsRs.getString("COLUMN_DEF"));
//
//                    excelRows.add(row);
//                }
////                tableInfo.setColumns(columns);
////                tableInfos.add(tableInfo);
//                columnsRs.close(); // 关闭内层ResultSet
//                System.out.println("End of Table: " + tableName);
//                System.out.println("======================================\n");
//
//            }
//            EasyExcel.write(excelFilePath,ExcelRow.class).sheet(sheetName).doWrite(excelRows);
//            System.out.println("表总数为:\t" + count);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            // 在finally块中关闭资源
//            try {
//                if (tables != null) tables.close();
//                if (connection != null) connection.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        System.out.println("--------------开始写入Excel!!!--------------");
////        writeToExcel(tableInfos,excelFilePath);
//    }
//
//    private static String getTableComment(Connection connection, String tableName) throws SQLException {
//        // 这里需要根据具体数据库的实现来获取表注释，下面是一个示例SQL
//        String query = "SELECT description FROM pg_description JOIN pg_class ON pg_description.objoid = pg_class.oid WHERE relname = ?";
//        try (PreparedStatement ps = connection.prepareStatement(query)) {
//            ps.setString(1, tableName);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return rs.getString("description");
//            }
//        }
//        return tableName;
//    }
//
//
//}
