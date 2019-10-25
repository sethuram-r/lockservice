package smartshare.lockservice.repository;

import org.springframework.data.repository.CrudRepository;
import smartshare.lockservice.model.S3Object;


public interface ObjectRepository extends CrudRepository<S3Object, String> {
}
