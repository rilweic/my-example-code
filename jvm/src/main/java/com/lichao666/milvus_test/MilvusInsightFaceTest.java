package com.lichao666.milvus_test;

import io.milvus.client.MilvusServiceClient;
import io.milvus.grpc.*;
import io.milvus.param.*;
import io.milvus.param.collection.*;
import io.milvus.param.dml.*;
import io.milvus.param.index.*;
import io.milvus.response.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MilvusInsightFaceTest {

    private static final MilvusServiceClient milvusClient;

    static {
        ConnectParam connectParam = ConnectParam.newBuilder()
                .withHost("localhost")
                .withPort(19530)
                .build();
        milvusClient = new MilvusServiceClient(connectParam);
    }

    private static final String COLLECTION_NAME = "face";
    private static final String ID_FIELD = "userID";
    private static final String VECTOR_FIELD = "userFace";
    private static final Integer VECTOR_DIM = 512;

    private static final IndexType INDEX_TYPE = IndexType.IVF_SQ8;
    private static final String INDEX_PARAM = "{\"nlist\":16384}";

    private static final Integer SEARCH_K = 2;
    private static final String SEARCH_PARAM = "{\"nprobe\":16}";


    private void handleResponseStatus(R<?> r) {
        if (r.getStatus() != R.Status.Success.getCode()) {
            throw new RuntimeException(r.getMessage());
        }
    }

    private R<RpcStatus> createCollection(long timeoutMilliseconds) {
        System.out.println("========== createCollection() ==========");
        FieldType fieldType1 = FieldType.newBuilder()
                .withName(ID_FIELD)
                .withDescription("user identification")
                .withDataType(DataType.Int64)
                .withPrimaryKey(true)
                .withAutoID(true)
                .build();

        FieldType fieldType2 = FieldType.newBuilder()
                .withName(VECTOR_FIELD)
                .withDescription("face embedding")
                .withDataType(DataType.FloatVector)
                .withDimension(VECTOR_DIM)
                .build();


        CreateCollectionParam createCollectionReq = CreateCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withDescription("customer info")
                .addFieldType(fieldType1)
                .addFieldType(fieldType2)
                .build();
        R<RpcStatus> response = milvusClient.withTimeout(timeoutMilliseconds, TimeUnit.MILLISECONDS)
                .createCollection(createCollectionReq);
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    private R<RpcStatus> dropCollection() {
        System.out.println("========== dropCollection() ==========");
        R<RpcStatus> response = milvusClient.dropCollection(DropCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .build());
        System.out.println(response);
        return response;
    }

    private R<Boolean> hasCollection() {
        System.out.println("========== hasCollection() ==========");
        R<Boolean> response = milvusClient.hasCollection(HasCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    private R<RpcStatus> loadCollection() {
        System.out.println("========== loadCollection() ==========");
        R<RpcStatus> response = milvusClient.loadCollection(LoadCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    private R<RpcStatus> releaseCollection() {
        System.out.println("========== releaseCollection() ==========");
        R<RpcStatus> response = milvusClient.releaseCollection(ReleaseCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }

    private R<DescribeCollectionResponse> describeCollection() {
        System.out.println("========== describeCollection() ==========");
        R<DescribeCollectionResponse> response = milvusClient.describeCollection(DescribeCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .build());
        handleResponseStatus(response);
        DescCollResponseWrapper wrapper = new DescCollResponseWrapper(response.getData());
        System.out.println(wrapper.toString());
        return response;
    }

    private R<GetCollectionStatisticsResponse> getCollectionStatistics() {
        System.out.println("========== getCollectionStatistics() ==========");
        R<GetCollectionStatisticsResponse> response = milvusClient.getCollectionStatistics(
                GetCollectionStatisticsParam.newBuilder()
                        .withCollectionName(COLLECTION_NAME)
                        .build());
        handleResponseStatus(response);
        GetCollStatResponseWrapper wrapper = new GetCollStatResponseWrapper(response.getData());
        System.out.println("Collection row count: " + wrapper.getRowCount());
        return response;
    }

    private R<ShowCollectionsResponse> showCollections() {
        System.out.println("========== showCollections() ==========");
        R<ShowCollectionsResponse> response = milvusClient.showCollections(ShowCollectionsParam.newBuilder()
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }


    private R<RpcStatus> createIndex() {
        System.out.println("========== createIndex() ==========");
        R<RpcStatus> response = milvusClient.createIndex(CreateIndexParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withFieldName(VECTOR_FIELD)
                .withIndexType(INDEX_TYPE)
                .withMetricType(MetricType.L2)
                .withExtraParam(INDEX_PARAM)
//                .withSyncMode(Boolean.TRUE)
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }


    private R<DescribeIndexResponse> describeIndex() {
        System.out.println("========== describeIndex() ==========");
        R<DescribeIndexResponse> response = milvusClient.describeIndex(DescribeIndexParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withFieldName(VECTOR_FIELD)
                .build());
        handleResponseStatus(response);
        System.out.println(response);
        return response;
    }


    private R<MutationResult> insert(List<List<Float>> vectors) {
        System.out.println("========== insert() ==========");

        List<InsertParam.Field> fields = new ArrayList<>();
        fields.add(new InsertParam.Field(VECTOR_FIELD, DataType.FloatVector, vectors));

        InsertParam insertParam = InsertParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withFields(fields)
                .build();

        R<MutationResult> response = milvusClient.insert(insertParam);
        handleResponseStatus(response);
        return response;
    }


    private R<SearchResults> searchFace(List<List<Float>> vectors) {
        System.out.println("========== searchFace() ==========");
        long begin = System.currentTimeMillis();

        SearchParam searchParam = SearchParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withMetricType(MetricType.L2)
                .withTopK(SEARCH_K)
                .withVectors(vectors)
                .withVectorFieldName(VECTOR_FIELD)
                .withParams(SEARCH_PARAM)
                .build();

        R<SearchResults> response = milvusClient.search(searchParam);
        long end = System.currentTimeMillis();
        long cost = (end - begin);
        System.out.println("Search time cost: " + cost + "ms");

        handleResponseStatus(response);
        SearchResultsWrapper wrapper = new SearchResultsWrapper(response.getData().getResults());
        for (int i = 0; i < vectors.size(); ++i) {
            System.out.println("Search result of No." + i);
            List<SearchResultsWrapper.IDScore> scores = wrapper.getIDScore(i);
            System.out.println(scores);
            System.out.println("Output field data for No." + i);
        }

        return response;
    }

    public static void main(String[] args) {

        List<Double> doubleVectors = TrashData.liuyifei;

        List<Float> floatVectors = doubleVectors.stream().map(e -> Float.parseFloat(e.toString())).collect(Collectors.toList());
        List<List<Float>> searchVectors = new ArrayList<>();
        searchVectors.add(floatVectors);

        MilvusInsightFaceTest example = new MilvusInsightFaceTest();
        // 创建集合
        example.createCollection(2000);
        // 加载excel里的数据
        List<ResidentModel> residentModels = ExcelLoader.getResidentModels("datas/feature.xlsx");
        List<List<Float>> allData = new ArrayList<>();
        Map<Long, String> cache = new HashMap<>();
        for (ResidentModel residentModel : residentModels) {
            List<List<Float>> tmpData = new ArrayList<>();
            tmpData.add(converStr2List(residentModel.getFeature()));
            // 插入数据
            R<MutationResult> result = example.insert(tmpData);
            MutationResultWrapper wrapper = new MutationResultWrapper(result.getData());
            List<Long> ids = wrapper.getLongIDs();
            cache.put(ids.get(0), residentModel.getName());
            allData.add(converStr2List(residentModel.getFeature()));
        }

        Iterator<Map.Entry<Long, String>> entries = cache.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Long, String> entry = entries.next();
            System.out.println("key: " + entry.getKey() + ", value: " + entry.getValue());

        }


        // 一次性全部插入
//        R<MutationResult> result = example.insert(allData);
//        MutationResultWrapper wrapper = new MutationResultWrapper(result.getData());
//        List<Long> ids = wrapper.getLongIDs();
//        ids.stream().forEach(System.out::println);

        example.getCollectionStatistics();
        example.createIndex();
        example.describeIndex();
        // 加载数据到内存
        example.loadCollection();

        R<SearchResults> response = example.searchFace(searchVectors);
        SearchResultsWrapper wrapper = new SearchResultsWrapper(response.getData().getResults());

        for (int i = 0; i < searchVectors.size(); ++i) {
            System.out.println("Search result of No." + i);
            List<SearchResultsWrapper.IDScore> scores = wrapper.getIDScore(i);
            System.out.println("搜索结果");
            for (SearchResultsWrapper.IDScore s : scores) {
                System.out.println(cache.get(s.getLongID()) + "  偏离度：" + s.getScore());
            }
        }

        example.releaseCollection();
        example.dropCollection();

    }

    private static List<Float> converStr2List(String originStr) {
        List<String> ss = Arrays.asList(originStr.substring(1, originStr.length() - 1).split(","));
        List<Float> result = new ArrayList<>();
        for (String s : ss) {
            result.add(Float.parseFloat(s));
        }

        return result;
    }
}