package elastic.client;
import java.util.concurrent.CompletableFuture;
import org.elasticsearch.action.ActionListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ElasticAsyncTool {

    public static <T> ActionListener<T> toActionListener(CompletableFuture<T> future) {
        return new ActionListener<T>() {
            @Override
            public void onResponse(T t) {
                future.complete(t);

            }

            @Override
            public void onFailure(Exception e) {
                future.completeExceptionally(e);
            }
        };
    }
}
