package smartshare.lockservice.service.reads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smartshare.lockservice.model.File;
import smartshare.lockservice.repository.FileRepository;
import smartshare.lockservice.repository.FolderRepository;


import java.util.Optional;

@Service(value = "redisLockReadService")
public class LockServiceReadImp implements ILockService {

    private FileRepository fileRepository;

    private FolderRepository folderRepository;

    @Autowired
    LockServiceReadImp(FileRepository fileRepository, FolderRepository folderRepository){
        this.fileRepository = fileRepository;
        this.folderRepository = folderRepository;
    }

    @Override
    public Boolean getLockStatusForCurrentFile(String fileName) {

        Optional<File> currentFileWhoseLockStatusIsNeeded = fileRepository.findById( fileName );
        //not sure how it works
        return currentFileWhoseLockStatusIsNeeded.map( File::getLockStatus ).orElse( null );
    }

    @Override
    public Boolean getLockStatusForCurrentFolder(String folderName) {
        folderRepository.findById( folderName );
        return null;
    }
}
