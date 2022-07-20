package com.lichao666.milvus_test;

import io.milvus.client.MilvusServiceClient;
import io.milvus.grpc.DataType;
import io.milvus.grpc.MutationResult;
import io.milvus.grpc.SearchResults;
import io.milvus.param.ConnectParam;
import io.milvus.param.IndexType;
import io.milvus.param.MetricType;
import io.milvus.param.R;
import io.milvus.param.collection.CreateCollectionParam;
import io.milvus.param.collection.FieldType;
import io.milvus.param.collection.LoadCollectionParam;
import io.milvus.param.collection.ReleaseCollectionParam;
import io.milvus.param.dml.InsertParam;
import io.milvus.param.dml.SearchParam;
import io.milvus.param.index.CreateIndexParam;
import io.milvus.response.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MilvusJavaTest {
    public static void main(String[] args) {
        MilvusServiceClient milvusClient = new MilvusServiceClient(
                ConnectParam.newBuilder()
                        .withHost("localhost")
                        .withPort(19530)
                        .build());
        FieldType fieldType1 = FieldType.newBuilder()
                .withName("book_id")
                .withDataType(DataType.Int64)
                .withPrimaryKey(true)
                .withAutoID(false)
                .build();
        FieldType fieldType2 = FieldType.newBuilder()
                .withName("word_count")
                .withDataType(DataType.Int64)
                .build();
        FieldType fieldType3 = FieldType.newBuilder()
                .withName("book_intro")
                .withDataType(DataType.FloatVector)
                .withDimension(2)
                .build();
        CreateCollectionParam createCollectionReq = CreateCollectionParam.newBuilder()
                .withCollectionName("book")
                .withDescription("Test book search")
                .withShardsNum(2)
                .addFieldType(fieldType1)
                .addFieldType(fieldType2)
                .addFieldType(fieldType3)
                .build();
        // 创建集合，类似创建表
        milvusClient.createCollection(createCollectionReq);

        // 插入数据

        //生成数据
        Random ran = new Random();
        List<Long> book_id_array = new ArrayList<>();
        List<Long> word_count_array = new ArrayList<>();
        List<List<Float>> book_intro_array = new ArrayList<>();
        for (long i = 0L; i < 2000; ++i) {
            book_id_array.add(i);
            word_count_array.add(i + 10000);
            List<Float> vector = new ArrayList<>();
            for (int k = 0; k < 2; ++k) {
                vector.add(ran.nextFloat());
            }
            book_intro_array.add(vector);
        }


        List<InsertParam.Field> fields = new ArrayList<>();
        fields.add(new InsertParam.Field("book_id", DataType.Int64, book_id_array));
        fields.add(new InsertParam.Field("word_count", DataType.Int64, word_count_array));
        fields.add(new InsertParam.Field("book_intro", DataType.FloatVector, book_intro_array));

        InsertParam insertParam = InsertParam.newBuilder()
                .withCollectionName("book")
                .withPartitionName("novel")
                .withFields(fields)
                .build();
        R<MutationResult> insert = milvusClient.insert(insertParam);

        // 创建索引
        final IndexType INDEX_TYPE = IndexType.IVF_FLAT;   // IndexType
        final String INDEX_PARAM = "{\"nlist\":1024}";     // ExtraParam
        milvusClient.createIndex(
                CreateIndexParam.newBuilder()
                        .withCollectionName("book")
                        .withFieldName("book_intro")
                        .withIndexType(INDEX_TYPE)
                        .withMetricType(MetricType.L2)
                        .withExtraParam(INDEX_PARAM)
                        .withSyncMode(Boolean.FALSE)
                        .build());

        // 查询数据 向量搜索
        // 加载数据到内存
        milvusClient.loadCollection(
                LoadCollectionParam.newBuilder()
                        .withCollectionName("book")
                        .build());

        final Integer SEARCH_K = 2;                       // TopK
        final String SEARCH_PARAM = "{\"nprobe\":10}";    // Params

        List<String> search_output_fields = Arrays.asList("book_id");
        List<List<Float>> search_vectors = Arrays.asList(Arrays.asList(0.1f, 0.2f));

        SearchParam searchParam = SearchParam.newBuilder()
                .withCollectionName("book")
                .withMetricType(MetricType.L2)
                .withOutFields(search_output_fields)
                .withTopK(SEARCH_K)
                .withVectors(search_vectors)
                .withVectorFieldName("book_intro")
                .withParams(SEARCH_PARAM)
                .build();
        R<SearchResults> respSearch = milvusClient.search(searchParam);

        SearchResultsWrapper wrapperSearch = new SearchResultsWrapper(respSearch.getData().getResults());
        System.out.println(wrapperSearch.getIDScore(0));
        System.out.println(wrapperSearch.getFieldData("book_id", 0));


        milvusClient.releaseCollection(
                ReleaseCollectionParam.newBuilder()
                        .withCollectionName("book")
                        .build());

    }

}
