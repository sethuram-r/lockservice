package smartshare.lockservice.service.writes;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import smartshare.lockservice.model.S3Object;
import smartshare.lockservice.service.reads.LockService;

import java.util.Arrays;


@Slf4j
@Service
public class LockServiceWrites {


    private final LockService lockService;


    @Autowired
    LockServiceWrites(LockService lockService) {
        this.lockService = lockService;
    }


    @KafkaListener(groupId = "lockConsumer", topics = "lock")
    public void consume(S3Object[] s3Object, ConsumerRecord record) {

        switch (record.key().toString()) {
            case "lock":
                log.info( "Consumed Event for lockObjects operation" );
                this.lockService.lockObjects( Arrays.asList( s3Object ) );
                break;
            case "unlock":
                log.info( "Consumed Event for unLockObjects operation" );
                this.lockService.unLockObjects( Arrays.asList( s3Object ) );
                break;
            default:
                log.info( "Inside default operation" );
        }
    }

}
