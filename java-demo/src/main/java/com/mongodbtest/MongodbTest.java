package com.mongodbtest;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import org.bson.Document;

public class MongodbTest {

    // MongoDB 连接字符串（请替换为你自己的连接字符串）
//    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String CONNECTION_STRING = "mongodb://root:Unicom%4024PSWD%23%25@132.35.231.155:10013/mydb?authSource=admin";
    private static final String DATABASE_NAME = "mydb"; // 你的数据库名称
    private static final String COLLECTION_NAME = "orders"; // 你的集合名称

    public static void main(String[] args) {
        MongoClient mongoClient = null;

        try {
            // 1. 创建 MongoDB 连接
            mongoClient = createMongoClient();

            // 2. 获取 Database 和 Collection
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);


            // 4. 查询文档
            findDocuments(collection);

        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 8. 关闭连接
            if (mongoClient != null) {
                mongoClient.close();
            }
        }
    }

    // 创建 MongoDB 连接
    private static MongoClient createMongoClient() {
        ConnectionString connectionString = new ConnectionString(CONNECTION_STRING);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder().version(ServerApiVersion.V1).build())
                .build();

        MongoClient mongoClient =  MongoClients.create(CONNECTION_STRING);
//        MongoClient mongoClient =  MongoClients.create(settings);
        System.out.println("Successfully connected to MongoDB with direct connection.");
        return mongoClient;
    }

    // 查询文档
    private static void findDocuments(MongoCollection<Document> collection) {
        System.out.println("\nFinding first 10 documents...");

        // 查询所有文档，并限制返回数量为 10
        FindIterable<Document> findIterable = collection.find().limit(10);

        int count = 0;
        for (Document doc : findIterable) {
            System.out.println(doc.toJson());
            count++;
        }

        System.out.println("Total documents found: " + count);
    }

}