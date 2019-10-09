package smartshare.lockservice.service.reads;




public interface ILockService {

    Boolean getLockStatusForCurrentFile(String fileName);
    Boolean getLockStatusForCurrentFolder(String folderName);
}
