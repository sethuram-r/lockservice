package smartshare.lockservice.service.writes;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import smartshare.lockservice.model.S3Object;
import smartshare.lockservice.model.S3ObjectsWrapper;
import smartshare.lockservice.repository.ObjectRepository;

import java.io.IOException;

import static smartshare.lockservice.constant.KafkaKeys.OBJECT;
import static smartshare.lockservice.constant.KafkaKeys.OBJECTS;

@Slf4j
@Service
public class LockServiceWrites {


    private ObjectRepository objectRepository;

    private ObjectMapper jsonConverter;


    @Autowired
    LockServiceWrites(ObjectRepository objectRepository, ObjectMapper jsonConverter) {
        this.objectRepository = objectRepository;
        this.jsonConverter = jsonConverter;
    }


    @KafkaListener(groupId = "lockConsumer", topics = "lock")
    public void consume(String objectInJsonAsStringFormat, ConsumerRecord record) throws IOException {
        System.out.println( "fileOrFolderObjectInJsonAsStringFormat------------->" + objectInJsonAsStringFormat );
        System.out.println("record--------->"+record);

        if (record.key() == (OBJECT)) {
            log.info( "Consumed Object Lock Event" );
            S3Object objectWhoseLockStatusHasToBeChanged = jsonConverter.readValue( objectInJsonAsStringFormat, S3Object.class );
            System.out.println( "result----object----->" + objectWhoseLockStatusHasToBeChanged );
            try {
                S3Object savedObject = objectRepository.save( objectWhoseLockStatusHasToBeChanged );
                System.out.println( "result----Objects----->" + savedObject );
            } catch (Exception e) {
                log.error( String.format( "Error occurred while persisting the file lock event %s", e.getMessage() ) );
            }

        }
        if (record.key() == (OBJECTS)) {
            log.info( "Consumed Objects Lock Event" );
            S3ObjectsWrapper objectsWhoseLockStatusHasToBeChanged = jsonConverter.readValue( objectInJsonAsStringFormat, S3ObjectsWrapper.class );
            System.out.println( "result----file----->" + objectsWhoseLockStatusHasToBeChanged );
            try {
                Iterable<S3Object> savedObjects = objectRepository.saveAll( objectsWhoseLockStatusHasToBeChanged );
                savedObjects.forEach( s3Object -> System.out.println( s3Object.getLockStatus() ) );
            } catch (Exception e) {
                log.error( String.format( "Error occurred while persisting the file lock event %s", e.getMessage() ) );
            }

        }


    }

}
