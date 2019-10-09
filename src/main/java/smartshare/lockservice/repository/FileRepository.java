package smartshare.lockservice.repository;

import org.springframework.data.repository.CrudRepository;
import smartshare.lockservice.model.File;



public interface FileRepository extends CrudRepository<File,String> {
}
