package smartshare.lockservice.service.reads;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smartshare.lockservice.model.S3Object;
import smartshare.lockservice.repository.ObjectRepository;

import java.util.List;
import java.util.Optional;

@Service(value = "redisLockReadService")
@Slf4j
public class LockService {

    private final ObjectRepository objectRepository;

    @Autowired
    LockService(ObjectRepository objectRepository) {
        this.objectRepository = objectRepository;
    }


    private void lockObject(S3Object s3Object) {
        log.info( "Inside lockObject" );
        try {
            objectRepository.save( s3Object );
        } catch (Exception e) {
            log.error( "Error while locking the Object " + s3Object + " " + e );
        }
    }

    public Boolean lockObjects(List<S3Object> s3Objects) {
        log.info( "Inside lockObjects" );
        try {
            s3Objects.forEach( this::lockObject );
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error( "Error while locking the Objects " + s3Objects + " " + e );
        }
        return Boolean.FALSE;
    }

    private void unLockObject(S3Object s3Object) {
        log.info( "Inside unLockObject" );
        try {
            Optional<S3Object> isObjectExists = objectRepository.findByObjectName( s3Object.getObjectName() );

            isObjectExists.ifPresent( objectRepository::delete );
        } catch (Exception e) {
            log.error( "Error while unlocking the Object " + s3Object + " " + e );
        }
    }

    public Boolean unLockObjects(List<S3Object> s3Objects) {
        log.info( "Inside unLockObjects" );
        try {
            s3Objects.forEach( this::unLockObject );
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error( "Error while unlocking the Objects " + s3Objects + " " + e );
        }
        return Boolean.FALSE;
    }


}
