package smartshare.lockservice.repository;

import org.springframework.data.repository.CrudRepository;
import smartshare.lockservice.model.Folder;


public interface FolderRepository extends CrudRepository<Folder, String> {
}
