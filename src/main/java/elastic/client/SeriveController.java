package elastic.client;

import co.elastic.apm.api.CaptureTransaction;
import io.micronaut.http.*;
import io.micronaut.http.annotation.*;

import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.search.SearchHit;

import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Controller("/elastic")
public class SeriveController {

    @Inject
    ElasticService service;

    /*@CaptureTransaction("elastic#search")
    @Get("/{name}")
    public Flux<Object> greet(HttpRequest request, MutableHttpMessage response) {
        return Mono.fromFuture(service.getNotificationsWithValueMark(request,response))
                .doOnSuccess(searchResponse -> {
                    response.getHeaders().add("search.hits",searchResponse.getHits().getTotalHits().toString());
                    response.getHeaders().add("search.status", searchResponse.status().toString());
                }).flatMapMany(res ->
                        Flux.fromArray(res.getHits().getHits())
                                .map(SearchHit::getSourceAsMap));
    }*/

    @CaptureTransaction("SeriveController#greet")
    @Consumes(MediaType.APPLICATION_JSON)
    @Post("/insert")
    public CompletableFuture<MutableHttpMessage> greet(@Body CompletableFuture<HomeLabIndex> request) {
        return request.thenApply(req -> {
                /*Mono.fromFuture(service.bulkInsertHomeLab(req)).doOnSuccess(bulkItemResponses -> {
                    HttpResponse.ok().getHeaders().add("insert.items", bulkItemResponses.getItems().toString());
                    HttpResponse.ok().getHeaders().add("search.status", bulkItemResponses.status().toString());
                });*/

                service.insertHomeLab(req);
                return HttpResponse.created(req);
        });

    }

    @Get("createindex")
    public HttpResponse createIndex(){
        try {
            service.createIndex();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  HttpResponse.ok();
    }
}
