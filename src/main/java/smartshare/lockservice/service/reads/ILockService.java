package smartshare.lockservice.service.reads;


import java.util.List;

public interface ILockService {

    Boolean getLockStatusOfObject(String folderName);

    List<Boolean> getLockStatusOfObjects(List<String> objectNames);

}
