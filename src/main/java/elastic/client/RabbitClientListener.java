package elastic.client;

import co.elastic.apm.api.CaptureTransaction;
import io.micronaut.configuration.rabbitmq.annotation.Queue;
import io.micronaut.configuration.rabbitmq.annotation.RabbitListener;

import javax.inject.Inject;

@RabbitListener
public class RabbitClientListener {
    @Inject
    ElasticService service;

    @CaptureTransaction("RabbitClientListener#saveMeasure")
    @Queue("measure")
    public void saveMeasure(HomeLabIndex measure) {
        service.insertHomeLab(measure);
    }
}
