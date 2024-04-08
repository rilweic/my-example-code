package com.lichao666.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import java.util.List;
import java.util.UUID;

public class CassandraAction {
    public static void main(String[] args) {
        Cluster cluster = null;
        Session session = null;

        try {
            // 定义一个cluster类
            cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
            // 获取session对象
            session = cluster.connect();

            for (int i = 1; i < 40; i++) {
                String tempCQL = String.format("insert into demo.test_table(id, name, email, created_at) " +
                        "VALUES (%s, %s, %s, toTimestamp(now()))", UUID.randomUUID().toString() + "", "'name" + i + "'", "'email" + i + "'");
                session.execute(tempCQL);
            }

            // 查询数据
            query01(session);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (session != null)
                session.close();
            if (cluster != null)
                cluster.close();
        }

    }

    private static void query01(Session session) {
        System.out.println("开始查询--------------------------");
        String queryCQL = "select * from demo.test_table";
        ResultSet rs = session.execute(queryCQL);
        List<Row> dataList = rs.all();
        for (Row row : dataList) {
            System.out.println("==>id:" + row.getUUID("id"));
            System.out.println("==>name:" + row.getString("name"));
            System.out.println("==>email:" + row.getString("email"));
        }
        System.out.println("结束查询--------------------------");
    }

}
