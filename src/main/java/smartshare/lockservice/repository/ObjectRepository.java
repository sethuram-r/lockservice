package smartshare.lockservice.repository;

import org.springframework.data.repository.CrudRepository;
import smartshare.lockservice.model.S3Object;

import java.util.List;
import java.util.Optional;


public interface ObjectRepository extends CrudRepository<S3Object, String> {
    Optional<S3Object> findByObjectName(String objectName);

    List<S3Object> findAllByObjectNameStartingWith(String objectName);


}
