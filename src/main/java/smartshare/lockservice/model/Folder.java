package smartshare.lockservice.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.List;

@RedisHash(value = "lock", timeToLive = 60)
public class Folder {

    @Id
    @Indexed
    private String folderName;
    private List<File> files;


    Folder(String folderName, List<File> files){
        this.folderName = folderName;
        this.files = files;

    }

    public List<File> getFiles() {
        return files;
    }

    public String getFolderName() {
        return folderName;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "files=" + files +
                '}';
    }
}
