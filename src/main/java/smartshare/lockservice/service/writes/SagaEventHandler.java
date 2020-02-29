package smartshare.lockservice.service.writes;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import smartshare.lockservice.model.SagaEvent;
import smartshare.lockservice.service.reads.LockService;


@Slf4j
@Service
public class SagaEventHandler {

    private LockService lockService;

    @Autowired
    SagaEventHandler(LockService lockService) {
        this.lockService = lockService;
    }


    private SagaEvent lockEventHandler(SagaEvent sagaEvent) {
        log.info( "Inside lockEventHandler" );
        SagaEvent sagaEventResult = new SagaEvent();
        sagaEventResult.setEventId( sagaEvent.getEventId() );
        sagaEventResult.setStatus( lockService.lockObjects( sagaEvent.getObjects() ) ? "success" : "failed" );
        sagaEventResult.setObjects( sagaEvent.getObjects() );
//        sagaEventResult.setObjects( sagaEvent.getObjects() ); testing..
        return sagaEventResult;
    }

    private SagaEvent unLockEventHandler(SagaEvent sagaEvent) {
        log.info( "Inside deleteEventHandler" );
        SagaEvent sagaEventResult = new SagaEvent();
        sagaEventResult.setEventId( sagaEvent.getEventId() );
        sagaEventResult.setStatus( lockService.unLockObjects( sagaEvent.getObjects() ) ? "success" : "failed" );
        sagaEventResult.setObjects( sagaEvent.getObjects() );
        return sagaEventResult;
    }

    // Testing

//    @KafkaListener(groupId = "sagaLockResultConsumer",topics = "sagaLockResult")
//    public void result(SagaEvent results) {
//        log.info( "Inside sagaEventResultConsumer" );
//        System.out.println( results);
//    }


    @KafkaListener(groupId = "sagaEventConsumer", topics = "sagaLock", containerFactory = "SagaEventKafkaListenerContainerFactory")
    @SendTo("sagaLockResult")
    public SagaEvent consume(SagaEvent sagaEvent, ConsumerRecord record) {

        try {

            switch (record.key().toString()) {
                case "lock":
                    log.info( "Consumed lock saga Events" );
                    return this.lockEventHandler( sagaEvent );
                case "unlock":
                    log.info( "Consumed unlock saga Events" );
                    return this.unLockEventHandler( sagaEvent );
                default:
                    log.error( "Unsupported Lock event" );
            }
        } catch (Exception e) {
            log.error( "Exception while handling the LockConsumer events " + e.getMessage() );
        }

        return sagaEvent;
    }
}
