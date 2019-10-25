package smartshare.lockservice.service.reads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smartshare.lockservice.model.S3Object;
import smartshare.lockservice.repository.ObjectRepository;

import java.util.Optional;

@Service(value = "redisLockReadService")
public class LockServiceReadImp implements ILockService {


    private ObjectRepository objectRepository;

    @Autowired
    LockServiceReadImp(ObjectRepository objectRepository) {
        this.objectRepository = objectRepository;
    }


    @Override
    public Boolean getLockStatusOfObject(String objectName) {
        Optional<S3Object> currentObjectWhoseLockStatusIsNeeded = objectRepository.findById( objectName );
        return currentObjectWhoseLockStatusIsNeeded.map( S3Object::getLockStatus ).orElse( null );
    }
}
