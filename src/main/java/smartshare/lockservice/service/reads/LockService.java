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


    private ObjectRepository objectRepository;

    @Autowired
    LockService(ObjectRepository objectRepository) {
        this.objectRepository = objectRepository;
    }


    public Boolean getLockStatusOfObject(String objectName) { // for file
        log.info( "Inside getLockStatusOfObject" );
        Optional<S3Object> isRecordExists = objectRepository.findByObjectName( objectName );
        System.out.println( "isRecordExists------->" + isRecordExists );
        return isRecordExists.isPresent() ? Boolean.TRUE : Boolean.FALSE;
    }


    public Boolean getLockStatusOfObjects(String objectName) { // for folder
        log.info( "Inside getLockStatusOfObjects" );
        List<S3Object> isLockedRecordExists = objectRepository.findAllByObjectNameStartingWith( objectName );
        System.out.println( "isLockedRecordExists------->" + isLockedRecordExists );
        return isLockedRecordExists.size() > 1 ? Boolean.TRUE : Boolean.FALSE;
    }

    private void lockObject(S3Object s3Object) {
        log.info( "Inside lockObject" );
        try {
            S3Object result = objectRepository.save( s3Object );
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
            log.error( "Error while locking the Object " + s3Objects + " " + e );
        }
        return Boolean.FALSE;
    }

    private void unLockObject(S3Object s3Object) {
        log.info( "Inside unLockObject" );
        try {
            Optional<S3Object> isObjectExists = objectRepository.findByObjectName( s3Object.getObjectName() );
            System.out.println( "isObjectExists--------->" + isObjectExists );
            isObjectExists.ifPresent( objectRepository::delete );
        } catch (Exception e) {
            log.error( "Error while locking the Object " + s3Object + " " + e );
        }
    }

    public Boolean unLockObjects(List<S3Object> s3Objects) {
        log.info( "Inside unLockObjects" );
        try {
            s3Objects.forEach( this::unLockObject );
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error( "Error while locking the Object " + s3Objects + " " + e );
        }
        return Boolean.FALSE;
    }


}
