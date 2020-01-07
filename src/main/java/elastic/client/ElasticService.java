package elastic.client;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.MutableHttpMessage;
import io.reactivex.Observable;
import io.reactivex.internal.operators.maybe.MaybeJust;
import lombok.extern.slf4j.Slf4j;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.BooleanUtils;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.UUIDs;
import org.elasticsearch.common.io.Streams;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.apache.commons.lang3.math.NumberUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import elastic.client.ElasticAsyncTool;

@Singleton
@Slf4j
@AllArgsConstructor
public class ElasticService {
    private static final String INDEX = "home-lab";
    @Inject
    private final RestHighLevelClient client;
    //private final ElasticConfig client;
    private final ObjectMapper mapper;

    public void createIndex() throws IOException {
        boolean exists = client.indices().exists(new GetIndexRequest(INDEX), RequestOptions.DEFAULT);
        log.info("Indice actual : {}", client.indices().toString());
        if (exists) {
            client.indices().delete(new DeleteIndexRequest(INDEX), RequestOptions.DEFAULT);
            log.info("Indice eliminado : {}", exists);
        }
        try (Reader readerSettings = new InputStreamReader(this.getClass().getResourceAsStream(
                "/elastic/notifications-settings.json"), StandardCharsets.UTF_8);
             Reader readerMappings = new InputStreamReader(this.getClass().getResourceAsStream(
                     "/elastic/notifications-mappings.json"), StandardCharsets.UTF_8)) {
            String settings = Streams.copyToString(readerSettings);
            String mapping = Streams.copyToString(readerMappings);
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(INDEX)
                    .settings(settings, XContentType.JSON)
                    .mapping(mapping, XContentType.JSON);
            client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("Ocurrió un error al crear un nuevo índice :", e);
            e.printStackTrace();
        }
    }

    public CompletableFuture<BulkResponse> bulkInsert(List<HomeLabIndex> operations) {
        BulkRequest request = new BulkRequest();
        try {
            for (HomeLabIndex operation : operations) {
                IndexRequest indexRequest = new IndexRequest(INDEX)
                        .id(UUIDs.randomBase64UUID())
                        .opType(DocWriteRequest.OpType.CREATE)
                        .source(mapper.writeValueAsString(operation), XContentType.JSON);
                request.add(indexRequest);
            }
        } catch (IOException ex) {
            log.error("Failed process", ex);
        }

        final CompletableFuture<BulkResponse> asyncResponse = new CompletableFuture<>();
        client.bulkAsync(request, RequestOptions.DEFAULT, ElasticAsyncTool.toActionListener(asyncResponse));
        return asyncResponse;
    }


    public CompletableFuture<SearchResponse> getNotificationsWithValueMark(
            String query, String valueOfMark, HttpHeaders httpHeaders) {
        log.info("getNotificationsWithValueMark --> {} -- httpHeaders --> {} ", query, httpHeaders);
        SearchRequest request = new SearchRequest(INDEX);
        SearchSourceBuilder searchSourceBuilder = prepareSearchSource(httpHeaders);
        BoolQueryBuilder builder = QueryBuilders.boolQuery()
                .must(QueryBuilders.multiMatchQuery(query, "user"));

        Optional.ofNullable(BooleanUtils.toBooleanObject(valueOfMark))
                .ifPresent(mark -> builder.must(QueryBuilders.multiMatchQuery(mark, "markedAsRead")));
        ;
        searchSourceBuilder.query(builder);
//    searchSourceBuilder.sort("creationTime").sort("creationTime", SortOrder.DESC);
        searchSourceBuilder.sort("creationTime", SortOrder.DESC);
        log.info("Obtenemos : " + searchSourceBuilder);
        request.source(searchSourceBuilder);
        final CompletableFuture<SearchResponse> response = new CompletableFuture<>();
        client.searchAsync(request, RequestOptions.DEFAULT, ElasticAsyncTool.toActionListener(response));
        return response;
    }

    //-- Transformar este método a un singleton.
    private SearchSourceBuilder prepareSearchSource(HttpHeaders httpHeaders) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // Parsing size HTTP header
       /* Optional.ofNullable(httpHeaders.get("page-size"))
                .ifPresent(headers -> headers.lines()
                        .filter(NumberUtils::isParsable)
                        .map(NumberUtils::toInt)
                        .forEach(sourceBuilder::size)
                );

        // Parsing from HTTP header
        Optional.ofNullable(httpHeaders.get("page-offset"))
                .ifPresent(headers -> headers.lines()
                        .filter(NumberUtils::isParsable)
                        .map(NumberUtils::toInt)
                        .forEach(sourceBuilder::from)
                );*/
        return sourceBuilder;
    }

    public void insertHomeLab(HomeLabIndex operation) {
        BulkRequest request = new BulkRequest();
        //operation.setCreationTime(Date.valueOf(LocalDate.now()));
        operation.setCreationTime(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        try {
            IndexRequest indexRequest = new IndexRequest(INDEX)
                    .id(UUIDs.randomBase64UUID())
                    .opType(DocWriteRequest.OpType.INDEX)
                    //.setRefreshPolicy("true")
                    .source(mapper.writeValueAsString(operation), XContentType.JSON);
            request.add(indexRequest);


            //client.index(indexRequest,RequestOptions.DEFAULT);
            //log.error(asyncResponse.get().buildFailureMessage());
            //Mono.fromFuture(asyncResponse).subscribe();
         //   log.info("Subscribed!");
         //   asyncResponse.get().forEach(er -> {
              //  log.error(er.getFailureMessage());
         //       log.info("Saludos Terricolas");

          //  });

        } catch (IOException ex) {
            log.error("Failed process", ex);
       // } catch (InterruptedException e) {
        //    e.printStackTrace();
         //   log.error("failed waiting", e);
       /* } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
    }
        final CompletableFuture<BulkResponse> asyncResponse = new CompletableFuture<>();
        client.bulkAsync(request, RequestOptions.DEFAULT, ElasticAsyncTool.toActionListener(asyncResponse));
        Observable.fromFuture(asyncResponse).subscribe(v -> System.out.println("Got " + v),
                e -> System.err.println("Exception " + e),
                () -> System.out.println("All done"));
        //return asyncResponse;

}}
